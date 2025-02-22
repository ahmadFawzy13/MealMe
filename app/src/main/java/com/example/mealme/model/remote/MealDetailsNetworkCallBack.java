package com.example.mealme.model.remote;

import com.example.mealme.home.model.RandomMealPojo;
import com.example.mealme.meal_details.model.Meal;

import java.util.List;

public interface MealDetailsNetworkCallBack {
    public void onMealDetailsSuccessResult(List<Meal> listOfMeals);
    public void onMealDetailsFailedResult(String errorMessage);
}
