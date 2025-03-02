package com.example.mealme.meal_details.view;

import com.example.mealme.model.remote.Meal;

import java.util.List;

public interface MealDetailViewer {

    public void showMealDetails(List<Meal> listOfMeals);
    public void showMealDetailsErrorMsg(String err);

}
