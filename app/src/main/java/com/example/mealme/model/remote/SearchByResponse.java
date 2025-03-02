package com.example.mealme.model.remote;

import com.example.mealme.search_by.model.SearchByPojo;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SearchByResponse {

    @SerializedName("meals")
    private List<SearchByPojo> listOfSearchMeals;

    public SearchByResponse(List<SearchByPojo> listOfSearchMeals) {
        this.listOfSearchMeals = listOfSearchMeals;
    }

    public List<SearchByPojo> getListOfSearchMeals() {
        return listOfSearchMeals;
    }

    public void setListOfSearchMeals(List<SearchByPojo> listOfSearchMeals) {
        this.listOfSearchMeals = listOfSearchMeals;
    }
}
