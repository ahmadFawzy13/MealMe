package com.example.mealme.home.presenter;

import com.example.mealme.home.model.HomeMealViewer;
import com.example.mealme.home.model.HomeMealsPojo;
import com.example.mealme.home.model.RandomMealPojo;
import com.example.mealme.home.model.RandomMealViewer;
import com.example.mealme.model.remote.HomeMealResponse;
import com.example.mealme.model.repo.Repository;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenter {
    private Repository repo;
    HomeMealViewer homeMealViewer;
    RandomMealViewer randomMealViewer;

    public HomePresenter(Repository repo, HomeMealViewer homeMealViewer, RandomMealViewer randomMealViewer) {
        this.repo = repo;
        this.homeMealViewer = homeMealViewer;
        this.randomMealViewer = randomMealViewer;
    }

    public void getHomeMeals(){
        repo.getHomeRemoteMeals()
                .subscribeOn(Schedulers.io())
                .map(item->item.getListOfMealsResponse())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<HomeMealsPojo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<HomeMealsPojo> homeMealsPojo) {
                        homeMealViewer.showHomeMeal(homeMealsPojo);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        homeMealViewer.showHomeMealErrorMsg("No Internet Connection");
                    }
                });
    }

    public void getRandomMeal(){
        repo.getRandomRemoteMeal()
                .subscribeOn(Schedulers.io())
                .map(item->item.getListOfMealsResponse())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<RandomMealPojo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<RandomMealPojo> randomMealPojo) {
                        randomMealViewer.showRandomMeal(randomMealPojo);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        randomMealViewer.showRandomMealErrorMsg("No Internet Connection");
                    }
                });
    }
}
