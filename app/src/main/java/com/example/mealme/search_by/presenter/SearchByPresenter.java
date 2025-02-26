package com.example.mealme.search_by.presenter;

import com.example.mealme.SearchMealsViewer;
import com.example.mealme.search_by.model.SearchByPojo;
import com.example.mealme.model.repo.Repository;
import com.example.mealme.search_by.view.SearchByAdapter;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchByPresenter {
    Repository repo;
    SearchMealsViewer searchMealsViewer;
    List<SearchByPojo>searchResultList;

    public SearchByPresenter(Repository repo, SearchMealsViewer searchMealsViewer) {
        this.repo = repo;
        this.searchMealsViewer = searchMealsViewer;
    }

    public void getMealsBy(String strCategory,String strArea,String strIngredient){
        repo.getMealsBy(strCategory,strArea,strIngredient)
                .subscribeOn(Schedulers.io())
                .map(item-> item.getListOfSearchMeals())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<SearchByPojo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<SearchByPojo> searchByPojo) {
                        searchMealsViewer.onSearchMealsSuccess(searchByPojo);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        searchMealsViewer.onSearchMealsFailure(e.getLocalizedMessage());
                    }
                });

    }

    public void textSearchWatch(CharSequence s, List<SearchByPojo> searchMealsList, SearchByAdapter searchByAdapter) {

        Observable<SearchByPojo> observable = Observable.fromIterable(searchMealsList);
                observable
                 .subscribeOn(Schedulers.io())
                .filter(item->item.getStrMeal().toLowerCase().startsWith(s.toString().toLowerCase()))
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(searchResult->{
                    searchResultList = searchResult;
                    searchByAdapter.setList(searchResult);
                });

    }
}
