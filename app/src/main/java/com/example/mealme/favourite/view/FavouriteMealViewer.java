package com.example.mealme.favourite.view;

import com.example.mealme.model.remote.Meal;

import java.util.List;

public interface FavouriteMealViewer {
    void onFavouriteMealSuccess(List<Meal> favouriteMealsList);
    void onFavouriteMealFailure(String error);
    void onFavMealDeletionSuccess(String success);
    void onFavMealDeletionFailure(String err);
}
