package be.freedombox.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "Categories")
public class Category {
    @Id
    @NotBlank(message = "Category is required")
    private String category;
    @ManyToOne
    private Category parentCategory;

    public Category() {}

    public Category(String category, Category parentCategory) {
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
