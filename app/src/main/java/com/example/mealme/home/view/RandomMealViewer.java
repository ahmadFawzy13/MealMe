package com.example.mealme.home.view;

import com.example.mealme.home.model.RandomMealPojo;

import java.util.List;

public interface RandomMealViewer {

    public void onRandomMealSuccess(List<RandomMealPojo> listOfMeals);
    public void onRandomMealError(String err);

}
