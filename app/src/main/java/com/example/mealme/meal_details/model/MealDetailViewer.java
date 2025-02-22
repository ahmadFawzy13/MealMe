package com.example.mealme.meal_details.model;

import com.example.mealme.home.model.RandomMealPojo;

import java.util.List;

public interface MealDetailViewer {

    public void showMealDetails(List<Meal> listOfMeals);
    public void showMealDetailsErrorMsg(String err);

}
