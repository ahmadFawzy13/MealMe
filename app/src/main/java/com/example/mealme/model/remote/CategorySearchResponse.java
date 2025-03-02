package com.example.mealme.model.remote;

import com.example.mealme.search_all.model.CategorySearchPojo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategorySearchResponse {
    @SerializedName("categories")
    private List<CategorySearchPojo> listOfCategories;

    public List<CategorySearchPojo> getListOfCategories() {
        return listOfCategories;
    }
    public void setListOfCategories(List<CategorySearchPojo> listOfCategories) {
        this.listOfCategories = listOfCategories;
    }
}
