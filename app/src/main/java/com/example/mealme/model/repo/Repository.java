/*package com.example.mealme.model.repo;

import androidx.lifecycle.LiveData;
import com.example.mealme.model.remote.MealRemoteDataSource;
import java.util.List;

public class Repository {

    MealRemoteDataSource remoteSource;
    MealLocalDataSource localSource;
    private static Repository repo = null;

    private Repository(MealRemoteDataSource remoteSource, MealLocalDataSource localSource) {
        this.remoteSource = remoteSource;
        this.localSource = localSource;
    }

    public static Repository getInstance(MealRemoteDataSource remoteSource, MealLocalDataSource localSource){
        if(repo == null){
            repo = new Repository(remoteSource,localSource);
        }
        return repo;
    }

    public LiveData<List<Meal>> getAllLocalMeals(){
        return localSource.getAllMeals();
    }

    public void getAllRemoteMeals(NetworkCallBack networkCallBack){
        remoteSource.makeNetworkCall(networkCallBack);
    }

    public void insertMeal(Meal meal){
        localSource.insertMeal(meal);
    }

    public void deleteMeal(Meal meal){
        localSource.deleteMeal(product);
    }
}*/
