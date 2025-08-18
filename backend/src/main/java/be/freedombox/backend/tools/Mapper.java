package be.freedombox.backend.tools;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.domain.Dish;
import be.freedombox.backend.domain.User;
import be.freedombox.backend.dto.AuthDTO;
import be.freedombox.backend.dto.CategoryDTO;
import be.freedombox.backend.dto.DishDTO;
import be.freedombox.backend.dto.UserDTO;
import be.freedombox.backend.repository.CategoryRepository;
import be.freedombox.backend.request.DishRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public static CategoryDTO toCategoryDTO(Category category) {
        return new CategoryDTO(
                category.getCategory(),
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
        return categoryRepository.findByCategory(category);
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
}
