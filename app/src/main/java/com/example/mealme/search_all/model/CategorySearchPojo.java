package com.example.mealme.search_all.model;

public class CategorySearchPojo {
    private String strCategory;
    private String strCategoryThumb;

    public CategorySearchPojo(String strCategory, String strCategoryThumb) {
        this.strCategory = strCategory;
        this.strCategoryThumb = strCategoryThumb;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrCategoryThumb() {
        return strCategoryThumb;
    }

    public void setStrCategoryThumb(String strCategoryThumb) {
        this.strCategoryThumb = strCategoryThumb;
    }

}
