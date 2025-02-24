package com.example.mealme;

public interface MealDetailsDatabaseOps {
    void onSuccessLocalInsertion(String success);
    void onFailedLocalInsertion(String err);

    void onSuccessCalendarInsertion(String success);
    void onFailedCalendarInsertion(String err);
}
