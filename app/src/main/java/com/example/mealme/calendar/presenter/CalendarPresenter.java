package com.example.mealme.calendar.presenter;

import com.example.mealme.CalendarViewer;
import com.example.mealme.OnCalendarMealDeleted;
import com.example.mealme.calendar.model.CalendarMeal;
import com.example.mealme.model.repo.Repository;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalendarPresenter {

    Repository repo;
    CalendarViewer calendarViewer;
    OnCalendarMealDeleted onCalendarMealDeleted;

    public CalendarPresenter(Repository repo, CalendarViewer calendarViewer, OnCalendarMealDeleted onCalendarMealDeleted) {
        this.repo = repo;
        this.calendarViewer = calendarViewer;
        this.onCalendarMealDeleted = onCalendarMealDeleted;
    }

    public void getCalendarMealByDate(String date){
         repo.getCalendarLocalMealsByDate(date)
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(
                     item ->  calendarViewer.onCalendarMealSuccess(item),
                     error ->  calendarViewer.onCalendarMealFailed(error.getLocalizedMessage())
                 );
    }

    public void deleteCalendarMeal(CalendarMeal calendarMeal){
        repo.deleteCalendarMealLocal(calendarMeal)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                       onCalendarMealDeleted.onCalendarMealDeletionSuccess("Meal Deleted");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        onCalendarMealDeleted.onCalendarMealDeletionFailure("Can't Delete Meal Right Now");
                    }
                });
    }
}
