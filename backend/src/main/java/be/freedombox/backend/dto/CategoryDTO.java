package be.freedombox.backend.dto;

import be.freedombox.backend.domain.Category;
import jakarta.validation.constraints.NotBlank;

public class CategoryDTO {
    @NotBlank
    private String category;
    private Category parentCategory;

    public CategoryDTO() {

    }

    public CategoryDTO(String category, Category parentCategory) {
        this.category = category;
        this.parentCategory = parentCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }
}
