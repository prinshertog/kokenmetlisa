package be.freedombox.backend.dto;

public class CategoryDTO {
    private String category;

    public CategoryDTO() {

    }

    public CategoryDTO(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
