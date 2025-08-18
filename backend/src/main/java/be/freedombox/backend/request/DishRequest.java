package be.freedombox.backend.request;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class DishRequest {
    @NotBlank(message = "Dish name is required")
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private List<String> categories;
    @NotBlank
    private String imageName;

    public DishRequest() {
    }

    public DishRequest(String name, String description, List<String> categories, String imageName) {
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

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
