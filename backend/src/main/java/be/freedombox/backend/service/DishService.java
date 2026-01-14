package be.freedombox.backend.service;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.domain.Dish;
import be.freedombox.backend.dto.DishDTO;
import be.freedombox.backend.exception.DishException;
import be.freedombox.backend.exception.FileException;
import be.freedombox.backend.exception.ObjectDoesNotExistException;
import be.freedombox.backend.repository.CategoryRepository;
import be.freedombox.backend.repository.DishRepository;
import be.freedombox.backend.request.DishRequest;
import be.freedombox.backend.request.DishUpdateRequest;
import be.freedombox.backend.tools.Mapper;
import be.freedombox.backend.tools.Validator;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class DishService {
    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Autowired
    public DishService(DishRepository dishRepository, CategoryRepository categoryRepository, FileService fileService) {
        this.dishRepository = dishRepository;
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
    }

    public List<DishDTO> all() {
        return dishRepository.findAll()
                .stream()
                .map(Mapper::toDishDTO)
                .toList();
    }

    public void create(DishRequest dishRequest, MultipartFile file) {
        try {
            if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("Uploaded file is not an image");
            }
            BufferedImage image = convertToBufferedImage(file);
            BufferedImage resizedImage = resizeImage(image, 600, 600);
            fileService.saveImage(resizedImage, dishRequest.getImageName());
            fileService.saveFile(file, "full-" + dishRequest.getImageName());
            dishRepository.save(Mapper.toDish(dishRequest));
        } catch (Exception e) {
            throw new DishException("Failed to create dish: " + e.getMessage());
        }
    }

    public List<DishDTO> getById(Long id) {
        return dishRepository.findById(id)
                .stream()
                .map(Mapper::toDishDTO)
                .toList();
    }

    public void delete(Long id) {
        if (dishRepository.findById(id).isEmpty())
            throw new ObjectDoesNotExistException("The dish with id " + id + " does not exist");
        fileService.deleteFile(dishRepository
                .findById(id)
                .get()
                .getImageName()
        );
        fileService.deleteFile("full-" + dishRepository.findById(id).get().getImageName());
        dishRepository.deleteById(id);
    }

    public void update(DishUpdateRequest dishUpdateRequest, MultipartFile file) {
        if (dishRepository.findById(dishUpdateRequest.getId()).isEmpty())
            throw new ObjectDoesNotExistException("Dish does not exist so cannot be updated.");

        Dish dish = dishRepository.findById(dishUpdateRequest.getId()).get();

        if (!dishUpdateRequest.getDishName().isEmpty())
            dish.setName(dishUpdateRequest.getDishName());

        if (!dishUpdateRequest.getDescription().isEmpty())
            dish.setDescription(dishUpdateRequest.getDescription());

        List<Category> categories = dishUpdateRequest
                .getCategories()
                .stream()
                .map(Mapper::toCategory)
                .toList();
        dish.setCategories(new ArrayList<>(categories));

        if (file != null) {
            if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
                throw new IllegalArgumentException("Uploaded file is not an image");
            }
            fileService.deleteFile(dish.getImageName());
            dish.setImageName(dishUpdateRequest.getImageName());
            BufferedImage image = convertToBufferedImage(file);
            BufferedImage resizedImage = resizeImage(image, 600, 600);
            fileService.saveImage(resizedImage, dishUpdateRequest.getImageName());
            fileService.saveFile(file, "full-" + dishUpdateRequest.getImageName());
        }

        dishRepository.save(dish);
    }

    public List<DishDTO> getByCategory(String category) {
        Category categoryObject = categoryRepository.findByCategory(
                Validator.initCap(category)
        );

        return dishRepository.getByCategories(categoryObject)
                .stream()
                .map(Mapper::toDishDTO)
                .toList();
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws FileException {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Thumbnails.of(originalImage)
                    .size(targetWidth, targetHeight)
                    .outputFormat("JPEG")
                    .outputQuality(0.50)
                    .toOutputStream(outputStream);
            byte[] data = outputStream.toByteArray();
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new FileException(e.toString());
        }
    }

    private BufferedImage convertToBufferedImage(MultipartFile file) throws FileException {
        try (InputStream inputStream = file.getInputStream()) {
            return ImageIO.read(inputStream);
        } catch (IOException e) {
            throw new FileException(e.toString());
        }
    }

    public List<DishDTO> getDishesForPage(int pageNumber) {
        Pageable pageWithTwoElements = PageRequest.of(pageNumber, 30);
        Page<Dish> allProducts = dishRepository.findAll(pageWithTwoElements);
        return allProducts.getContent()
                .stream()
                .map(Mapper::toDishDTO)
                .toList();
    }
}
