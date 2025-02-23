package com.example.mealme.model.local;

import android.content.Context;
import androidx.lifecycle.LiveData;

import com.example.mealme.CalendarMeal;
import com.example.mealme.meal_details.model.Meal;

import java.util.List;

public class MealLocalDataSource {

    MealDao mealDao;
    CalendarMealDao calendarMealDao;
    public MealLocalDataSource(Context context) {
        AppDataBase db = AppDataBase.getInstance(context);
        mealDao = db.getMealDao();
        calendarMealDao = db.getCalendarMealDao();

    }

    public LiveData<List<Meal>> getAllFavMeals(){
        return mealDao.getAllMeals();
    }

    public void insertMeal(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDao.insertMeal(meal);
            }
        }).start();
    }

    public void deleteMeal(Meal meal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                mealDao.deleteMeal(meal);
            }
        }).start();
    }

    public LiveData<List<CalendarMeal>> getCalendarMealsByDate(String date){
        return calendarMealDao.getCalendarMealsByDate(date);
    }

    public void insertCalendarMeal(CalendarMeal calendarMeal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                calendarMealDao.insertMeal(calendarMeal);
            }
        }).start();
    }

    public void deleteCalenderMeal(CalendarMeal calendarMeal){
        new Thread(new Runnable() {
            @Override
            public void run() {
                calendarMealDao.deleteMeal(calendarMeal);
            }
        }).start();
    }
}
