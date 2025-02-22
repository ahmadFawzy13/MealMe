package com.example.mealme.model.remote;

import android.util.Log;

import com.example.mealme.meal_details.model.Meal;

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
                .build();

        mealsApi = retrofit.create(MealsApi.class);
    }

    public void makeNetWorkCall(NetworkCallBack networkCallBack){

        Call<HomeMealResponse> call = mealsApi.getHomeMeals();
        call.enqueue(new Callback<HomeMealResponse>() {
            @Override
            public void onResponse(Call<HomeMealResponse> call, Response<HomeMealResponse> response) {
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: Callback " + response.raw() + response.body().toString());
                    networkCallBack.onSuccessResult(response.body().getListOfMealsResponse());
                }
            }

            @Override
            public void onFailure(Call<HomeMealResponse> call, Throwable throwable) {
                Log.i(TAG, "onFailure: Callback");
                networkCallBack.onFailedResult(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }


   public void makeRandomNetworkCall(RandomMealNetworkCallBack randomMealNetworkCallBack){
        Call<RandomMealResponse>call = mealsApi.getRandomMeal();
        call.enqueue(new Callback<RandomMealResponse>() {
            @Override
            public void onResponse(Call<RandomMealResponse> call, Response<RandomMealResponse> response) {
                Log.i(TAG, "onResponse: Callback " + response.raw() + response.body().toString());
                randomMealNetworkCallBack.onRandomMealSuccessResult(response.body().getListOfMealsResponse());
            }

            @Override
            public void onFailure(Call<RandomMealResponse> call, Throwable throwable) {
                Log.i(TAG, "onFailure: Callback");
                randomMealNetworkCallBack.onRandomMealFailedResult(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
   }

    public void makeMealDetailNetworkCall(MealDetailsNetworkCallBack mealDetailsNetworkCallBack, String mealId){
        Call<MealDetailsResponse>call = mealsApi.getMealDetails(mealId);
        call.enqueue(new Callback<MealDetailsResponse>() {
            @Override
            public void onResponse(Call<MealDetailsResponse> call, Response<MealDetailsResponse> response) {
                Log.i(TAG, "onResponse: Callback " + response.raw() + response.body().toString());
                mealDetailsNetworkCallBack.onMealDetailsSuccessResult(response.body().getListOfMealsResponse());
            }

            @Override
            public void onFailure(Call<MealDetailsResponse> call, Throwable throwable) {
                Log.i(TAG, "onFailure: Callback");
                mealDetailsNetworkCallBack.onMealDetailsFailedResult(throwable.getMessage());
                throwable.printStackTrace();
            }
        });
    }

}
