package com.example.mealme.model.remote;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealsApi {

    @GET("api/json/v1/1/search.php?f=b")
    Call<HomeMealResponse> getHomeMeals();

    @GET("api/json/v1/1/random.php")
    Call<RandomMealResponse> getRandomMeal();


}
