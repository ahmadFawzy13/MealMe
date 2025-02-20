/*
package com.example.mealme.model.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.mealme.home.model.HomeMealsPojo;

@Database(entities = {HomeMealsPojo.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract MealDao getMealDao();
    public static synchronized AppDataBase getInstance(Context context){
        return Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class,"foodplannerdb").build();
    }
}*/
