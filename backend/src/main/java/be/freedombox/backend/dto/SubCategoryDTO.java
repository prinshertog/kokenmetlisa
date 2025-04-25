package be.freedombox.backend.dto;

public class SubCategoryDTO {
    private String subCategory;

    public SubCategoryDTO() {

    }

    public SubCategoryDTO(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
