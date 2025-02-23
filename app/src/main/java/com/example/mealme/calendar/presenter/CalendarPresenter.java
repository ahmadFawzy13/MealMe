package com.example.mealme.calendar.presenter;

import androidx.lifecycle.LiveData;

import com.example.mealme.CalendarMeal;
import com.example.mealme.meal_details.model.Meal;
import com.example.mealme.model.repo.Repository;

import java.util.Calendar;
import java.util.List;

public class CalendarPresenter {

    Repository repo;

    public CalendarPresenter(Repository repo) {
        this.repo = repo;
    }

    public LiveData<List<CalendarMeal>> getCalendarMealByDate(String date){
        return repo.getCalendarLocalMealsByDate(date);
    }

    public void deleteCalendarMeal(CalendarMeal calendarMeal){
        repo.deleteCalendarMealLocal(calendarMeal);
    }
}
