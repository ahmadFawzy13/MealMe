package com.example.mealme.favourite.presenter;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.mealme.FavouriteMealViewer;
import com.example.mealme.meal_details.model.Meal;
import com.example.mealme.model.repo.Repository;

import org.reactivestreams.Subscription;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavouritePresenter {

    Repository repo;
    FavouriteMealViewer favouriteMealViewer;

    public FavouritePresenter(Repository repo, FavouriteMealViewer favouriteMealViewer) {
        this.repo = repo;
        this.favouriteMealViewer = favouriteMealViewer;
    }

    public void getFavMealLocal(){
         repo.getAllFavLocalMeals()
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(
                         item -> favouriteMealViewer.onFavouriteMealSuccess(item),
                         error -> favouriteMealViewer.onFavouriteMealFailure(error.getLocalizedMessage())
                 );

    }

    public void deleteFavMeal(Meal meal){
        repo.deleteFavMealLocal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        favouriteMealViewer.onFavMealDeletionSuccess("Meal Deleted");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        favouriteMealViewer.onFavMealDeletionFailure(e.getLocalizedMessage());
                    }
                });
    }
}
