package com.example.mealme.home.presenter;

import com.example.mealme.home.model.HomeMealViewer;
import com.example.mealme.home.model.HomeMealsPojo;
import com.example.mealme.home.model.RandomMealPojo;
import com.example.mealme.home.model.RandomMealViewer;
import com.example.mealme.model.remote.NetworkCallBack;
import com.example.mealme.model.remote.RandomMealNetworkCallBack;
import com.example.mealme.model.repo.Repository;

import java.util.List;
import java.util.Random;

public class HomePresenter implements NetworkCallBack, RandomMealNetworkCallBack {
    private Repository repo;
    HomeMealViewer homeMealViewer;
    RandomMealViewer randomMealViewer;

    public HomePresenter(Repository repo, HomeMealViewer homeMealViewer, RandomMealViewer randomMealViewer) {
        this.repo = repo;
        this.homeMealViewer = homeMealViewer;
        this.randomMealViewer = randomMealViewer;
    }

    public void getHomeMeals(){
        repo.getAllRemoteMeals(this);
    }

    public void getRandomMeal(){
        repo.getRandomMeal(this);
    }
    @Override
    public void onSuccessResult(List<HomeMealsPojo> listOfMeals) {
        homeMealViewer.showHomeMeal(listOfMeals);
    }

    @Override
    public void onFailedResult(String errorMessage) {
        homeMealViewer.showHomeMealErrorMsg(errorMessage);
    }

    @Override
    public void onRandomMealSuccessResult(List<RandomMealPojo> listOfMeals) {
            randomMealViewer.showRandomMeal(listOfMeals);
    }

    @Override
    public void onRandomMealFailedResult(String errorMessage) {
        randomMealViewer.showRandomMealErrorMsg(errorMessage);
    }
}
