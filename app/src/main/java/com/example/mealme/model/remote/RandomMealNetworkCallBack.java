package com.example.mealme.model.remote;

import com.example.mealme.home.model.RandomMealPojo;

import java.util.List;

public interface RandomMealNetworkCallBack {

    public void onRandomMealSuccessResult(List<RandomMealPojo> listOfMeals);
    public void onRandomMealFailedResult(String errorMessage);

}
