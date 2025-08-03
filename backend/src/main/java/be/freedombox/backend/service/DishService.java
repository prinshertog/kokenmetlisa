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
    public void create(DishRequest dishrequest, MultipartFile file) {
        try {
            String dishName = dishrequest.getName();
            String dishDescription = dishrequest.getDescription();
            String imageName = dishrequest.getImageName();
            Category dishCategory = categoryRepository.getCategoriesByCategory(dishrequest.getCategory());

            Dish dish = new Dish(dishName, dishDescription, dishCategory, imageName);

            fileService.saveFile(file, dishrequest.getImageName());
            dishRepository.save(dish);
        } catch (Exception e) {
            throw new DishException("Failed to create dish: " + e.getMessage());
        }
    }

    @Override
    public List<DishDTO> getById(Long id) {
        return dishRepository.findById(id).stream().map(Mapper::toDishDTO).toList();
    }

    @Override
    public void delete(Long id) {
        if (dishRepository.findById(id).isEmpty()) throw new ObjectDoesNotExistException("The dish with id " + id + " does not exist");
        String imageName = dishRepository.findById(id).get().getImageName();
        fileService.deleteFile(imageName);
        dishRepository.deleteById(id);
    }

    @Override
    public void update(DishUpdateRequest dishUpdateRequest, MultipartFile file) {
        if (dishRepository.findById(dishUpdateRequest.getId()).isEmpty()) throw new ObjectDoesNotExistException("Dish does not exist so cannot be updated.");

        Dish dish = dishRepository.findById(dishUpdateRequest.getId()).get();

        if (dishUpdateRequest.getDishName().isEmpty()) dishUpdateRequest.setDishName(dish.getName());
        else dish.setName(dishUpdateRequest.getDishName());

        if (dishUpdateRequest.getDescription().isEmpty()) dishUpdateRequest.setDescription(dish.getDescription());
        else dish.setDescription(dishUpdateRequest.getDescription());

        if (dishUpdateRequest.getCategory().isEmpty()) {
            dishUpdateRequest.setCategory(dish.getCategory().getCategory());
        }
        else {
            Category category = categoryRepository.findByCategory(dishUpdateRequest.getCategory());
            dish.setCategory(category);
        }

        if (dishUpdateRequest.getImageName().isEmpty() || file == null) {
            dishUpdateRequest.setImageName(dish.getImageName());
        } else {
            fileService.deleteFile(dish.getImageName());
            dish.setImageName(dishUpdateRequest.getImageName());
            fileService.saveFile(file, dishUpdateRequest.getImageName());
        }

        dishRepository.save(dish);
    }

    @Override
    public List<DishDTO> getByCategory(String category) {
        String validatedCategory = Validator.initCap(category);
        Category fetchedCategory = categoryRepository.findByCategory(validatedCategory);
        return dishRepository.getByCategory(fetchedCategory).stream().map(Mapper::toDishDTO).toList();
    }
}
