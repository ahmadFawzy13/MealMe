package com.example.mealme.model.remote;

import com.example.mealme.search_all.model.IngredientSearchPojo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class IngredientSearchResponse {

    @SerializedName("meals")
    private List<IngredientSearchPojo> listOfIngredients;

    public List<IngredientSearchPojo> getListOfIngredients() {
        return listOfIngredients;
    }

    public void setListOfIngredients(List<IngredientSearchPojo> listOfIngredients) {
        this.listOfIngredients = listOfIngredients;
    }
}
