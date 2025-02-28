package com.example.mealme.search_all.view;

import com.example.mealme.search_all.model.CountrySearchPojo;

import java.util.List;

public interface CountrySearchViewer {
    void onCountryListSuccess(List<CountrySearchPojo> countriesList);
    void onCountrylistFailure(String err);

}
