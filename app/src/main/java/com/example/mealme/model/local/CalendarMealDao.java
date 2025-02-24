package com.example.mealme.model.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.example.mealme.calendar.model.CalendarMeal;

import java.util.List;

@Dao
public interface CalendarMealDao {
    @Query("SELECT * FROM calendar_meals_table WHERE date = :date")
    LiveData<List<CalendarMeal>> getCalendarMealsByDate(String date);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMeal(CalendarMeal calendarMeal);

    @Delete
    void deleteMeal(CalendarMeal calendarMeal);

}
