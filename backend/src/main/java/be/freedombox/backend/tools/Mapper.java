package be.freedombox.backend.tools;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.domain.Dish;
import be.freedombox.backend.domain.SubCategory;
import be.freedombox.backend.dto.CategoryDTO;
import be.freedombox.backend.dto.DishDTO;
import be.freedombox.backend.dto.SubCategoryDTO;

public class Mapper {
    public static DishDTO toDishDTO(Dish dish) {
        return new DishDTO(dish.getName(), dish.getDescription(), dish.getCategory(), dish.getSubCategory(), dish.getImageUrl());
    }

    public static CategoryDTO toCategoryDTO(Category category) {
        return new CategoryDTO(category.getCategory());
    }

    public static SubCategoryDTO toSubCategoryDTO(SubCategory subCategory) {
        return new SubCategoryDTO(subCategory.getSubCategory());
    }
}
