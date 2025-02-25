package com.example.mealme;

import com.example.mealme.search_all.model.CategorySearchPojo;

import java.util.List;

public interface CategorySearchViewer {
    void onCategoryListSuccess(List<CategorySearchPojo>categoriesList);
    void onCategoryListFailure(String err);
}
