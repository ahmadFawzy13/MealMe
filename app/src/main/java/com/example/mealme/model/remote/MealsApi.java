package com.example.mealme.model.remote;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealsApi {
    @GET("search.php?f=b")
    Single<HomeMealResponse> getHomeMeals();
    @GET("random.php")
    Single<RandomMealResponse> getRandomMeal();
    @GET("lookup.php")
    Single<MealDetailsResponse>getMealDetails(@Query("i")String id);
    @GET("list.php?c=list")
    Single<CategorySearchResponse>getSearchCategories();
    @GET("list.php?a=list")
    Single<CountrySearchResponse>getSearchCountries();
    @GET("list.php?i=list")
    Single<IngredientSearchResponse>getSearchIngredients();
}
