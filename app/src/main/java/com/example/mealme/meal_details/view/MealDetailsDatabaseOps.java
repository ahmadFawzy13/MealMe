package com.example.mealme.meal_details.view;

public interface MealDetailsDatabaseOps {
    void onSuccessLocalInsertion(String success);
    void onFailedLocalInsertion(String err);
    void onSuccessCalendarInsertion(String success);
    void onFailedCalendarInsertion(String err);
}
