package com.example.mealme.home.view;

import com.example.mealme.home.model.HomeMealsPojo;

import java.util.List;

public interface HomeMealViewer {
    public void showHomeMeal(List<HomeMealsPojo> listOfMeals);
    public void showHomeMealErrorMsg(String err);
}
