package com.example.mealme.model.remote;

import com.example.mealme.home.model.HomeMealsPojo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeMealResponse {
    @SerializedName("meals")
    List<HomeMealsPojo> listOfMealsResponse;

    public HomeMealResponse(List<HomeMealsPojo> listOfMealsResponse) {
        this.listOfMealsResponse = listOfMealsResponse;
    }

    public List<HomeMealsPojo> getListOfMealsResponse() {
        return listOfMealsResponse;
    }

    public void setListOfMealsResponse(List<HomeMealsPojo> listOfMealsResponse) {
        this.listOfMealsResponse = listOfMealsResponse;
    }
}
