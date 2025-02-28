package com.example.mealme.favourite.presenter;

import com.example.mealme.model.remote.Meal;

import java.util.List;

public interface FavMealViewer {

    public void showMealDetails(List<Meal> listOfMeals);
    public void showMealDetailsErrorMsg(String err);

}
