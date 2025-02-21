package com.example.mealme.model.remote;

import com.example.mealme.home.model.RandomMealPojo;
import com.example.mealme.meal_details.model.Meal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealDetailsResponse {

    @SerializedName("meals")
    List<Meal> listOfMealsResponse;

    public List<Meal> getListOfMealsResponse() {
        return listOfMealsResponse;
    }

    public void setListOfMealsResponse(List<Meal> listOfMealsResponse) {
        this.listOfMealsResponse = listOfMealsResponse;
    }

    public MealDetailsResponse(List<Meal> listOfMealsResponse) {
        this.listOfMealsResponse = listOfMealsResponse;
    }
}
