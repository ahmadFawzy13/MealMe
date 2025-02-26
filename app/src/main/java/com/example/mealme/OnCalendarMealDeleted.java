package com.example.mealme;

public interface OnCalendarMealDeleted {
    void onCalendarMealDeletionSuccess(String msg);
    void onCalendarMealDeletionFailure(String err);
}
