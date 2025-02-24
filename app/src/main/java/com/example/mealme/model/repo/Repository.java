package com.example.mealme.model.repo;

import com.example.mealme.calendar.model.CalendarMeal;
import com.example.mealme.meal_details.model.Meal;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.HomeMealResponse;
import com.example.mealme.model.remote.MealDetailsResponse;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.model.remote.RandomMealResponse;

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
    public Completable insertFavMealLocal(Meal meal){
        return localSource.insertMeal(meal);
    }
    public Completable deleteFavMealLocal(Meal meal){return localSource.deleteMeal(meal);}
    public Single <RandomMealResponse> getRandomRemoteMeal(){
       return remoteSource.getRandomMeal();
    }
    public Single<HomeMealResponse> getHomeRemoteMeals(){
        return remoteSource.getHomeMeal();
    }
    public Single<MealDetailsResponse> getMealDetailsRemote(String mealId){
        return remoteSource.getMealDetails(mealId);
    }
    public Flowable<List<CalendarMeal>> getCalendarLocalMealsByDate(String date){
        return localSource.getCalendarMealsByDate(date);
    }
    public Completable insertCalendarMealLocal(CalendarMeal calendarMeal){return localSource.insertCalendarMeal(calendarMeal);}
    public Completable  deleteCalendarMealLocal(CalendarMeal calendarMeal){return localSource.deleteCalenderMeal(calendarMeal);}
}
