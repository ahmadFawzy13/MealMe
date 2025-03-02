package com.example.mealme.model.remote;

import com.example.mealme.home.model.RandomMealPojo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandomMealResponse {

    @SerializedName("meals")
    private List<RandomMealPojo> listOfMealsResponse;

    public RandomMealResponse(List<RandomMealPojo> listOfMealsResponse) {
        this.listOfMealsResponse = listOfMealsResponse;
    }

    public List<RandomMealPojo> getListOfMealsResponse() {
        return listOfMealsResponse;
    }

    public void setListOfMealsResponse(List<RandomMealPojo> listOfMealsResponse) {
        this.listOfMealsResponse = listOfMealsResponse;
    }
}
