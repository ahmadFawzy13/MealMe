package com.example.mealme.model.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mealme.meal_details.model.Meal;

@Database(entities = {Meal.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public static AppDataBase instance = null;
    public abstract MealDao getMealDao();
    public static synchronized AppDataBase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,"foodplannerdb").build();
        }
        return instance;
    }
}
