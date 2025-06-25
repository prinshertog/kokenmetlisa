package be.freedombox.backend.request;

import jakarta.validation.constraints.NotBlank;

public class DishRequest {
    @NotBlank(message = "Dish name is required")
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String category;
    @NotBlank
    private String imageName;

    public DishRequest() {
    }

    public DishRequest(String name, String description, String category, String subCategory, String imageName) {
        this.name = name;
        this.description = description;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
