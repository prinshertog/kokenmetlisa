package be.freedombox.backend.dto;

import be.freedombox.backend.domain.Category;
import be.freedombox.backend.domain.SubCategory;

public class DishDTO {
    private String name;
    private String description;
    private Category category;
    private SubCategory subCategory;
    private String imageUrl;

    public DishDTO() {
    }

    public DishDTO(String name, String description, Category category, SubCategory subCategory, String imageUrl) {
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public SubCategory getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(SubCategory subCategory) {
        this.subCategory = subCategory;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
