package be.freedombox.backend.dto;

import be.freedombox.backend.domain.Category;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class DishDTO {
    @NotBlank
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    private List<Category> categories;
    @NotBlank
    private String imageName;

    public DishDTO() {
    }

    public DishDTO(Long id, String name, String description, List<Category> categories, String imageName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.imageName = imageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
