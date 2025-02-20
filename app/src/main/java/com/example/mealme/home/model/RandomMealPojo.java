package com.example.mealme.home.model;

public class RandomMealPojo extends HomeMealsPojo {

    private String strInstructions;


    public RandomMealPojo(String idMeal, String strMeal, String strMealThumb,String strInstructions ) {
        super(idMeal, strMeal, strMealThumb);
        this.strInstructions = strInstructions;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }
}
