package be.freedombox.backend.tools;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.domain.Dish;
import be.freedombox.backend.dto.CategoryDTO;
import be.freedombox.backend.dto.DishDTO;

public class Mapper {
    public static DishDTO toDishDTO(Dish dish) {
        return new DishDTO(dish.getId(), dish.getName(), dish.getDescription(), dish.getCategory(), dish.getImageUrl());
    }

    public static CategoryDTO toCategoryDTO(Category category) {
        return new CategoryDTO(category.getCategory(), category.getParentCategory());
    }
}
