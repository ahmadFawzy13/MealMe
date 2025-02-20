package com.example.mealme.home.model;

import java.util.List;

public interface RandomMealViewer {

    public void showRandomMeal(List<RandomMealPojo> listOfMeals);
    public void showRandomMealErrorMsg(String err);

}
