package be.freedombox.backend.tools;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.domain.Dish;
import be.freedombox.backend.domain.InspirationDish;
import be.freedombox.backend.domain.User;
import be.freedombox.backend.dto.*;
import be.freedombox.backend.exception.ObjectDoesNotExistException;
import be.freedombox.backend.repository.CategoryRepository;
import be.freedombox.backend.request.DishRequest;
import be.freedombox.backend.request.InspirationDishRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper {
    private static CategoryRepository categoryRepository;

    @Autowired
    public Mapper(CategoryRepository categoryRepository) {
        Mapper.categoryRepository = categoryRepository;
    }

    public static DishDTO toDishDTO(Dish dish) {
        return new DishDTO(
                dish.getId(),
                dish.getName(),
                dish.getDescription(),
                dish.getCategories(),
                dish.getImageName()
        );
    }

    public static InspirationDishDTO toInspirationDishDTO(InspirationDish dish) {
        return new InspirationDishDTO(dish.getId(), dish.getTitle(), dish.getShortDescription(), dish.getImageUrl(), dish.getCategories(), dish.getSourceName(), dish.getExternalUrl());
    }

    public static CategoryDTO toCategoryDTO(Category category) {
        return new CategoryDTO(
                category.getName(),
                category.getParentCategory()
        );
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getRole()
        );
    }

    public static AuthDTO toAuthDTO(String bearerToken, String role) {
        return new AuthDTO(
                bearerToken,
                role
        );
    }

    public static Category toCategory(String category) {
        return categoryRepository.findByName(category).orElseThrow(()
                -> new ObjectDoesNotExistException("Category does not exist!"));
    }

    public static Dish toDish(DishRequest dishRequest) {
        List<Category> categories = dishRequest.getCategories()
                .stream()
                .map(Mapper::toCategory)
                .toList();

        return new Dish(
                dishRequest.getName(),
                dishRequest.getDescription(),
                categories,
                dishRequest.getImageName()
        );
    }

    public static InspirationDish toInspirationDish(InspirationDishRequest request) {
        Set<Category> categories = request.getCategories()
                .stream()
                .map(Mapper::toCategory)
                .collect(Collectors.toSet());

        return new InspirationDish(
                request.getTitle(),
                request.getShortDescription(),
                request.getImageUrl(),
                categories,
                request.getSourceName(),
                request.getExternalUrl()
        );
    }
}
