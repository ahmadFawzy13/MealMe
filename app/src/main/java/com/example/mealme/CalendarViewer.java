package com.example.mealme;

import com.example.mealme.calendar.model.CalendarMeal;

import java.util.List;

public interface CalendarViewer {
    void onCalendarMealSuccess(List<CalendarMeal> calendarMeal);
    void onCalendarMealFailed(String error);
}
