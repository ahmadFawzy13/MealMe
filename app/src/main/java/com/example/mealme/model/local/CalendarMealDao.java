package com.example.mealme.model.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.mealme.calendar.model.CalendarMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CalendarMealDao {
    @Query("SELECT * FROM calendar_meals_table WHERE date = :date")
    Flowable<List<CalendarMeal>> getCalendarMealsByDate(String date);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insertMeal(CalendarMeal calendarMeal);

    @Delete
    Completable deleteMeal(CalendarMeal calendarMeal);
    @Query("DELETE FROM calendar_meals_table")
    Completable deleteAllCalendarMeals ();

}
