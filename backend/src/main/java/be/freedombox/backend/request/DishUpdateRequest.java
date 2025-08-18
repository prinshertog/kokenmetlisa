package be.freedombox.backend.request;

import be.freedombox.backend.domain.Category;

import java.util.List;

public class DishUpdateRequest {
    private Long id;
    private String dishName;
    private String description;
    private List<String> categories;
    private String imageName;

    public DishUpdateRequest(Long id, String dishName, String description, List<String> categories, String imageName) {
        this.id = id;
        this.dishName = dishName;
        this.description = description;
        this.categories = categories;
        this.imageName = imageName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
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
