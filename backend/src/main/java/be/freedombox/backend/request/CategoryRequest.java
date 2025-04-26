package be.freedombox.backend.request;

import be.freedombox.backend.domain.Category;

public class CategoryRequest {
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
