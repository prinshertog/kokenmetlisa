package be.freedombox.backend.request;
import jakarta.validation.constraints.NotBlank;

public class CategoryRequest {
    @NotBlank
    private String category;
    private String parentCategory;

    public CategoryRequest() {}

    public CategoryRequest(String category, String parentCategory) {
        this.category = category;
        this.parentCategory = parentCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(String parentCategory) {
        this.parentCategory = parentCategory;
    }
}
