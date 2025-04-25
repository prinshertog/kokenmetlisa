package be.freedombox.backend.request;

public class CategoryRequest {
    private String category;

    public CategoryRequest() {}

    public CategoryRequest(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
