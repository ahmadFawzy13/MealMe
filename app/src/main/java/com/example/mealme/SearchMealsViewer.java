package com.example.mealme;

import com.example.mealme.search_by.model.SearchByPojo;

import java.util.List;

public interface SearchMealsViewer {
    void onSearchMealsSuccess(List<SearchByPojo> searchMealsList);
    void onSearchMealsFailure(String err);
}
