package be.freedombox.backend.service;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.domain.Dish;
import be.freedombox.backend.dto.DishDTO;
import be.freedombox.backend.exception.ObjectDoesNotExistException;
import be.freedombox.backend.repository.CategoryRepository;
import be.freedombox.backend.repository.DishRepository;
import be.freedombox.backend.request.DishRequest;
import be.freedombox.backend.tools.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishService implements IDishService {
    private final DishRepository dishRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public DishService(DishRepository dishRepository, CategoryRepository categoryRepository) {
        this.dishRepository = dishRepository;
        this.categoryRepository = categoryRepository;
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
        if (category == null) {
            throw new ObjectDoesNotExistException("The category " + dishrequest.getCategory() + " does not exist");
        }
        Dish dish = new Dish(dishrequest.getName(), dishrequest.getDescription(), category, dishrequest.getImageUrl());
        return Mapper.toDishDTO(dishRepository.save(dish));
    }

    @Override
    public List<DishDTO> getById(Long id) {
        return dishRepository.findById(id).stream().map(Mapper::toDishDTO).toList();
    }
}
