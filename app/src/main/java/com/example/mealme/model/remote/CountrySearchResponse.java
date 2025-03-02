package com.example.mealme.model.remote;

import com.example.mealme.search_all.model.CountrySearchPojo;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CountrySearchResponse {
    @SerializedName("meals")
    private List<CountrySearchPojo> listOfCountries;
    public List<CountrySearchPojo> getListOfCountries() {
        return listOfCountries;
    }
    public void setListOfCountries(List<CountrySearchPojo> listOfCountries) {
        this.listOfCountries = listOfCountries;
    }
}
