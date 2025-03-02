package com.example.mealme.model.repo;

import com.example.mealme.calendar.model.CalendarMeal;
import com.example.mealme.model.remote.Meal;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.CategorySearchResponse;
import com.example.mealme.model.remote.CountrySearchResponse;
import com.example.mealme.model.remote.HomeMealResponse;
import com.example.mealme.model.remote.IngredientSearchResponse;
import com.example.mealme.model.remote.MealDetailsResponse;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.model.remote.RandomMealResponse;
import com.example.mealme.model.remote.SearchByResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class Repository {

    private MealRemoteDataSource remoteSource;
    private MealLocalDataSource localSource;
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
    public Flowable<List<Meal>> getAllFavLocalMeals(){
        return localSource.getAllFavMeals();
    }
    public Flowable<List<CalendarMeal>> getCalendarLocalMealsByDate(String date){
        return localSource.getCalendarMealsByDate(date);
    }
    public Single <RandomMealResponse> getRandomRemoteMeal(){
        return remoteSource.getRandomMeal();
    }
    public Single<CategorySearchResponse>getSearchCategories(){
        return remoteSource.getSearchCategories();
    }
    public Single<HomeMealResponse> getHomeRemoteMeals(){
        return remoteSource.getHomeMeal();
    }
    public Single<MealDetailsResponse> getMealDetailsRemote(String mealId){
        return remoteSource.getMealDetails(mealId);
    }
    public Single<CountrySearchResponse>getSearchCountries(){
        return remoteSource.getSearchCountries();
    }
    public Single<IngredientSearchResponse>getSearchIngredients(){
        return remoteSource.getSearchIngredients();
    }
    public Single<SearchByResponse>getMealsBy(String strCategory,String strArea,String strIngredient ){
        return remoteSource.getMealBy(strCategory,strArea,strIngredient);
    }
    public Completable deleteMealsTable(){
        return localSource.deleteMealsTable();
    }
    public Completable deleteCalendarTable(){
        return localSource.deleteCalendarMealsTable();
    }
    public Completable insertCalendarMealLocal(CalendarMeal calendarMeal){return localSource.insertCalendarMeal(calendarMeal);}
    public Completable  deleteCalendarMealLocal(CalendarMeal calendarMeal){return localSource.deleteCalenderMeal(calendarMeal);}
    public Completable insertFavMealLocal(Meal meal){
        return localSource.insertMeal(meal);
    }
    public Completable deleteFavMealLocal(Meal meal){return localSource.deleteMeal(meal);}
}
