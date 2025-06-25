package be.freedombox.backend.request;

public class SwitchCategoryRequest {
    private String category;
    private boolean up;

    public SwitchCategoryRequest(String category, boolean up) {
        this.category = category;
        this.up = up;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
}
