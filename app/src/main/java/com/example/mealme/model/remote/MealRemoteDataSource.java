package com.example.mealme.model.remote;

import android.util.Log;

import com.example.mealme.meal_details.model.Meal;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MealRemoteDataSource {

    public static final String TAG = "Home Meals Client";
    private static final String BASE_URL = "https://www.themealdb.com/";
    private MealsApi mealsApi;
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
}
