package com.example.mealme.favourite.presenter;

import androidx.lifecycle.LiveData;

import com.example.mealme.meal_details.model.Meal;
import com.example.mealme.model.repo.Repository;

import java.util.List;

public class FavouritePresenter {

    Repository repo;

    public FavouritePresenter(Repository repo) {
        this.repo = repo;
    }

    public LiveData<List<Meal>> getFavMealLocal(){
        return repo.getAllFavLocalMeals();
    }

    public void deleteFavMeal(Meal meal){
        repo.deleteFavMealLocal(meal);
    }

}
