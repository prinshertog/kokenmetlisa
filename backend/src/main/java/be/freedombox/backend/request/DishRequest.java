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
    private String subCategory;
    @NotBlank
    private String imageUrl;

    public DishRequest() {
    }

    public DishRequest(String name, String description, String category, String subCategory, String imageUrl) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.imageUrl = imageUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
