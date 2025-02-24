package com.example.mealme;

import com.example.mealme.meal_details.model.Meal;

import java.util.List;

public interface FavouriteMealViewer {
    void onFavouriteMealSuccess(List<Meal> favouriteMealsList);
    void onFavouriteMealFailure(String error);

    void onFavMealDeletionSuccess(String success);
    void onFavMealDeletionFailure(String err);
}
