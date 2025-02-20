package com.example.mealme.home.model;

import java.util.List;

public interface HomeMealViewer {

    public void showHomeMeal(List<HomeMealsPojo> listOfMeals);
    public void showHomeMealErrorMsg(String err);

}
