package com.example.mealme.search_all.model;

public class CountrySearchPojo {
    public String strArea;
    public CountrySearchPojo(String strArea) {
        this.strArea = strArea;
    }
    public String getStrArea() {
        return strArea;
    }
    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    @Override
    public String toString() {
        return "CountrySearchPojo{" +
                "strArea='" + strArea + '\'' +
                '}';
    }
}
