package com.example.mealme.search_all.presenter;

import com.example.mealme.CategorySearchViewer;
import com.example.mealme.CountrySearchViewer;
import com.example.mealme.IngredientSearchViewer;
import com.example.mealme.model.remote.CountrySearchResponse;
import com.example.mealme.model.repo.Repository;
import com.example.mealme.search_all.model.CategorySearchPojo;
import com.example.mealme.search_all.model.CountrySearchPojo;
import com.example.mealme.search_all.model.IngredientSearchPojo;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter {
    Repository repo;
    CountrySearchViewer countryViewer;
    CategorySearchViewer categoryViewer;
    IngredientSearchViewer ingredientViewer;

    public SearchPresenter(Repository repo, CountrySearchViewer countryViewer, CategorySearchViewer categoryViewer, IngredientSearchViewer ingredientViewer) {
        this.repo = repo;
        this.countryViewer = countryViewer;
        this.categoryViewer = categoryViewer;
        this.ingredientViewer = ingredientViewer;
    }

    public void getSearchCountries(){
        repo.getSearchCountries()
                .subscribeOn(Schedulers.io())
                .map(item->item.getListOfCountries())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CountrySearchPojo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<CountrySearchPojo> countrySearchPojo) {
                        countryViewer.onCountryListSuccess(countrySearchPojo);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        countryViewer.onCountrylistFailure(e.getLocalizedMessage());
                    }
                });
    }
    public void getSearchCategories(){
        repo.getSearchCategories()
                .subscribeOn(Schedulers.io())
                .map(item->item.getListOfCategories())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CategorySearchPojo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<CategorySearchPojo> categorySearchPojo) {
                        categoryViewer.onCategoryListSuccess(categorySearchPojo);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        categoryViewer.onCategoryListFailure(e.getLocalizedMessage());
                    }
                });
    }
    public void getSearchIngredients(){
        repo.getSearchIngredients()
                .subscribeOn(Schedulers.io())
                .map(item -> item.getListOfIngredients())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<IngredientSearchPojo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<IngredientSearchPojo> ingredientSearchPojo) {
                        ingredientViewer.onIngredientsListSuccess(ingredientSearchPojo);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ingredientViewer.onIngredientsListFailure(e.getLocalizedMessage());
                    }
                });
    }
}
