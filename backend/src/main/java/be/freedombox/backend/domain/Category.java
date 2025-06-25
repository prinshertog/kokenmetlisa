package be.freedombox.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.Cascade;

@Entity
@Table(name = "Categories")
public class Category {
    @Id
    @NotBlank(message = "Category is required")
    private String category;
    @ManyToOne
    private Category parentCategory;
    private int position;

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
