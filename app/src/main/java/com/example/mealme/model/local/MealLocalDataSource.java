package com.example.mealme.model.local;

import android.content.Context;

import com.example.mealme.calendar.model.CalendarMeal;
import com.example.mealme.model.remote.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public class MealLocalDataSource {
    private MealDao mealDao;
    private CalendarMealDao calendarMealDao;
    public MealLocalDataSource(Context context) {
        AppDataBase db = AppDataBase.getInstance(context);
        mealDao = db.getMealDao();
        calendarMealDao = db.getCalendarMealDao();
    }

    public Flowable<List<Meal>> getAllFavMeals(){
        return mealDao.getAllMeals();
    }
    public Flowable<List<CalendarMeal>> getCalendarMealsByDate(String date){
        return calendarMealDao.getCalendarMealsByDate(date);
    }
    public Completable insertCalendarMeal(CalendarMeal calendarMeal){
             return calendarMealDao.insertMeal(calendarMeal);
    }
    public Completable deleteCalenderMeal(CalendarMeal calendarMeal){
          return calendarMealDao.deleteMeal(calendarMeal);
    }
    public Completable deleteMealsTable(){
        return mealDao.deleteMealsTable();
    }
    public Completable deleteCalendarMealsTable(){
        return calendarMealDao.deleteAllCalendarMeals();
    }
    public Completable insertMeal(Meal meal){
        return mealDao.insertMeal(meal);
    }
    public Completable deleteMeal(Meal meal){
        return mealDao.deleteMeal(meal);
    }
}
