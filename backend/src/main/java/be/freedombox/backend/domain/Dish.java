package be.freedombox.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

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
    @Column(length = 10000)
    private String description;
    @Valid
    @ManyToMany
    @NotNull
    private List<Category> categories;
    @Valid
    private String imageName;

    public Dish() {

    }

    public Dish(String name, String description, List<Category> categories, String imageName) {
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.imageName = imageName;
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
