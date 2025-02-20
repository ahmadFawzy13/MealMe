package com.example.mealme.model.local;

import android.content.Context;
import androidx.lifecycle.LiveData;

import com.example.mealme.Meal;

import java.util.List;

public class MealLocalDataSource {

    MealDao mealDao;
    public MealLocalDataSource(Context context) {
        AppDataBase db = AppDataBase.getInstance(context);
        mealDao = db.getMealDao();

    }

    public LiveData<List<Meal>> getAllMeals(){
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
}
