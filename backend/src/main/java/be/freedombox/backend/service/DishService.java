package be.freedombox.backend.service;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.domain.Dish;
import be.freedombox.backend.dto.DishDTO;
import be.freedombox.backend.exception.DishException;
import be.freedombox.backend.exception.NotImplementedException;
import be.freedombox.backend.exception.ObjectDoesNotExistException;
import be.freedombox.backend.repository.CategoryRepository;
import be.freedombox.backend.repository.DishRepository;
import be.freedombox.backend.request.CategoryRequest;
import be.freedombox.backend.request.DishRequest;
import be.freedombox.backend.request.DishUpdateRequest;
import be.freedombox.backend.tools.GlobalVariables;
import be.freedombox.backend.tools.Mapper;
import be.freedombox.backend.tools.Validator;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DishService implements IDishService {
    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;

    @Autowired
    public DishService(DishRepository dishRepository, CategoryRepository categoryRepository, FileService fileService) {
        this.dishRepository = dishRepository;
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
    }

    @Override
    public List<DishDTO> all() {
        return dishRepository.findAll()
                .stream()
                .map(Mapper::toDishDTO)
                .toList();
    }

    @Override
    public void create(DishRequest dishRequest, MultipartFile file) {
        try {
            fileService.saveFile(file, dishRequest.getImageName());
            dishRepository.save(Mapper.toDish(dishRequest));
        } catch (Exception e) {
            throw new DishException("Failed to create dish: " + e.getMessage());
        }
    }

    @Override
    public List<DishDTO> getById(Long id) {
        return dishRepository.findById(id)
                .stream()
                .map(Mapper::toDishDTO)
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (dishRepository.findById(id).isEmpty())
            throw new ObjectDoesNotExistException("The dish with id " + id + " does not exist");
        fileService.deleteFile(dishRepository
                .findById(id)
                .get()
                .getImageName()
        );
        dishRepository.deleteById(id);
    }

    @Override
    public void update(DishUpdateRequest dishUpdateRequest, MultipartFile file) {
        if (dishRepository.findById(dishUpdateRequest.getId()).isEmpty())
            throw new ObjectDoesNotExistException("Dish does not exist so cannot be updated.");

        Dish dish = dishRepository.findById(dishUpdateRequest.getId()).get();

        if (!dishUpdateRequest.getDishName().isEmpty())
            dish.setName(dishUpdateRequest.getDishName());

        if (!dishUpdateRequest.getDescription().isEmpty())
            dish.setDescription(dishUpdateRequest.getDescription());

        if (!dishUpdateRequest.getCategories().isEmpty()) {
            List<Category> categories = dishUpdateRequest
                    .getCategories()
                    .stream()
                    .map(Mapper::toCategory)
                    .toList();
            dish.setCategories(new ArrayList<>(categories));
        }

        if (file != null) {
            fileService.deleteFile(dish.getImageName());
            dish.setImageName(dishUpdateRequest.getImageName());
            fileService.saveFile(file, dishUpdateRequest.getImageName());
        }

        dishRepository.save(dish);
    }

    @Override
    public List<DishDTO> getByCategory(String category) {
        Category categoryObject = categoryRepository.findByCategory(
                Validator.initCap(category)
        );

        return dishRepository.getByCategories(categoryObject)
                .stream()
                .map(Mapper::toDishDTO)
                .toList();
    }
}
