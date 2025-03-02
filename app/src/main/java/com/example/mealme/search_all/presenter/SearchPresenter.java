package com.example.mealme.search_all.presenter;

import android.annotation.SuppressLint;

import com.example.mealme.search_all.view.CategorySearchViewer;
import com.example.mealme.search_all.view.CountrySearchViewer;
import com.example.mealme.search_all.view.IngredientSearchViewer;
import com.example.mealme.model.repo.Repository;
import com.example.mealme.search_all.model.CategorySearchPojo;
import com.example.mealme.search_all.model.CountrySearchPojo;
import com.example.mealme.search_all.model.IngredientSearchPojo;
import com.example.mealme.search_all.view.MySearchAdapter;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenter {
    private Repository repo;
    private CountrySearchViewer countryViewer;
    private CategorySearchViewer categoryViewer;
    private IngredientSearchViewer ingredientViewer;
    private List<CategorySearchPojo> categoriesSearch;
    private List<CountrySearchPojo> countriesSearch;
    private List<IngredientSearchPojo> ingredientSearch;

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
                        countryViewer.onCountrylistFailure("Trouble Loading Items");
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
                        categoriesSearch = categorySearchPojo;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        categoryViewer.onCategoryListFailure("Turn on mobile data or connect to wifi");
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
                        ingredientViewer.onIngredientsListFailure("No Internet Connection");
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void categoryTextWatching(List<CategorySearchPojo>categorieList, CharSequence s, MySearchAdapter mySearchAdapter){

        Observable<CategorySearchPojo> observable = Observable.fromIterable(categorieList);
                observable
                        .filter(item->item.getStrCategory().toLowerCase().startsWith(s.toString().toLowerCase()))
                        .toList()
                        .subscribe(item->{
                           categoriesSearch = item;
                           mySearchAdapter.setCategoriesList(categoriesSearch);
                        });
    }
    @SuppressLint("CheckResult")
    public void countryTextWatching(List<CountrySearchPojo>countriesList, CharSequence s, MySearchAdapter mySearchAdapter){
        Observable<CountrySearchPojo> observable = Observable.fromIterable(countriesList);
        observable
                .filter(item-> item.getStrArea().toLowerCase().startsWith(s.toString().toLowerCase()))
                .toList()
                .subscribe(item->{
                     countriesSearch = item;
                    mySearchAdapter.setCountriesList(countriesSearch);
                });
    }

    @SuppressLint("CheckResult")
    public void ingredientTextWatching(List<IngredientSearchPojo>ingredientList, CharSequence s, MySearchAdapter mySearchAdapter){
        Observable<IngredientSearchPojo> observable = Observable.fromIterable(ingredientList);
        observable
                .filter(item-> item.getStrIngredient().toLowerCase().startsWith(s.toString().toLowerCase()))
                .toList()
                .subscribe(item->{
                    ingredientSearch = item;
                    mySearchAdapter.setIngredientsList(ingredientSearch);
                });
    }

}
