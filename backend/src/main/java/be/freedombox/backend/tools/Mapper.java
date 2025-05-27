package be.freedombox.backend.tools;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.domain.Dish;
import be.freedombox.backend.domain.User;
import be.freedombox.backend.dto.AuthDTO;
import be.freedombox.backend.dto.CategoryDTO;
import be.freedombox.backend.dto.DishDTO;
import be.freedombox.backend.dto.UserDTO;

public class Mapper {
    public static DishDTO toDishDTO(Dish dish) {
        return new DishDTO(dish.getId(), dish.getName(), dish.getDescription(), dish.getCategory(), dish.getImageUrl());
    }

    public static CategoryDTO toCategoryDTO(Category category) {
        return new CategoryDTO(category.getCategory(), category.getParentCategory());
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUsername(), user.getRole());
    }

    public static AuthDTO toAuthDTO(String bearerToken, String role) {
        return new AuthDTO(bearerToken, role);
    }
}
