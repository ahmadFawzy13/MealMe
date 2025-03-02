package com.example.mealme.model.remote;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSource {
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private final MealsApi mealsApi;
    public MealRemoteDataSource(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        mealsApi = retrofit.create(MealsApi.class);
    }
    public Single<HomeMealResponse> getHomeMeal(){
        return mealsApi.getHomeMeals();
    }

    public Single<RandomMealResponse>getRandomMeal(){
        return mealsApi.getRandomMeal();
    }

    public Single<MealDetailsResponse>getMealDetails(String idMeal){
        return mealsApi.getMealDetails(idMeal);
    }

    public Single<CategorySearchResponse>getSearchCategories(){
        return mealsApi.getSearchCategories();
    }

    public Single<CountrySearchResponse>getSearchCountries(){
        return mealsApi.getSearchCountries();
    }

    public Single<IngredientSearchResponse>getSearchIngredients(){
        return mealsApi.getSearchIngredients();
    }

    public Single<SearchByResponse>getMealBy(String strCategory,String strArea,String strIngredient){
        return mealsApi.getMealsBy(strCategory,strArea,strIngredient);
    }
}
