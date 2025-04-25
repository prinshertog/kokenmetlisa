package be.freedombox.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "Dishes")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Valid
    @NotBlank(message = "Dish name is required")
    private String name;
    @Valid
    @NotBlank(message = "Description is required")
    private String description;
    @Valid
    @ManyToOne
    @NotNull
    private Category category;
    @Valid
    @ManyToOne
    @NotNull
    private SubCategory subCategory;
    @Valid
    @NotBlank(message = "Image url is required")
    private String imageUrl;

    public Dish() {

    }

    public Dish(String name, String description, Category category, SubCategory subCategory, String imageUrl) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.subCategory = subCategory;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
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
