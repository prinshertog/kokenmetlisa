package be.freedombox.backend.request;

import be.freedombox.backend.domain.Category;

public class DishUpdateRequest {
    private Long id;
    private String dishName;
    private String description;
    private String category;
    private String imageName;

    public DishUpdateRequest(Long id, String dishName, String description, String category, String imageName) {
        this.id = id;
        this.dishName = dishName;
        this.description = description;
        this.category = category;
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
