package com.example.mealme.model.remote;

import com.example.mealme.meal_details.model.Meal;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MealsApi {

    @GET("api/json/v1/1/search.php?f=b")
    Call<HomeMealResponse> getHomeMeals();

    @GET("api/json/v1/1/random.php")
    Call<RandomMealResponse> getRandomMeal();

    @GET("api/json/v1/1/lookup.php")
    Call<MealDetailsResponse>getMealDetails(@Query("i")String id);


}
