package com.example.mealme.favourite.presenter;

import com.example.mealme.meal_details.model.Meal;

import java.util.List;

public interface FavMealViewer {

    public void showMealDetails(List<Meal> listOfMeals);
    public void showMealDetailsErrorMsg(String err);

}
