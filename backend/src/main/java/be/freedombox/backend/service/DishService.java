package be.freedombox.backend.service;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.domain.Dish;
import be.freedombox.backend.domain.SubCategory;
import be.freedombox.backend.dto.DishDTO;
import be.freedombox.backend.exception.ObjectAlreadyExistsException;
import be.freedombox.backend.exception.ObjectDoesNotExistException;
import be.freedombox.backend.repository.CategoryRepository;
import be.freedombox.backend.repository.DishRepository;
import be.freedombox.backend.repository.SubCategoryRepository;
import be.freedombox.backend.request.DishRequest;
import be.freedombox.backend.tools.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService implements IDishService {
    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Autowired
    public DishService(DishRepository dishRepository, CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.dishRepository = dishRepository;
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    @Override
    public List<DishDTO> all() {
        return dishRepository.findAll()
                .stream()
                .map(Mapper::toDishDTO)
                .toList();
    }

    @Override
    public DishDTO create(DishRequest dishrequest) {
        Category category = categoryRepository.findByCategory(dishrequest.getCategory());
        SubCategory subCategory = subCategoryRepository.findBySubCategory(dishrequest.getSubCategory());
        if (subCategory == null) {
            throw new ObjectDoesNotExistException("The sub category " + dishrequest.getSubCategory() + " does not exist");
        }
        if (category == null) {
            throw new ObjectDoesNotExistException("The category " + dishrequest.getCategory() + " does not exist");
        }
        Dish dish = new Dish(dishrequest.getName(), dishrequest.getDescription(), category, subCategory, dishrequest.getImageUrl());
        return Mapper.toDishDTO(dishRepository.save(dish));
    }
}
