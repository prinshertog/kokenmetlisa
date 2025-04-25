package be.freedombox.backend.request;

public class SubCategoryRequest {
    private String subCategory;

    public SubCategoryRequest() {

    }

    public SubCategoryRequest(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
