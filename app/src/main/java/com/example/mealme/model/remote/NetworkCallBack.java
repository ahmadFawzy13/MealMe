package com.example.mealme.model.remote;

import com.example.mealme.home.model.HomeMealsPojo;

import java.util.List;

public interface NetworkCallBack {
    public void onSuccessResult(List<HomeMealsPojo> listOfMeals);
    public void onFailedResult(String errorMessage);
}
