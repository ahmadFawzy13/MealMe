package com.example.mealme.calendar.presenter;

import android.annotation.SuppressLint;

import com.example.mealme.calendar.view.CalendarViewer;
import com.example.mealme.calendar.view.OnCalendarMealDeleted;
import com.example.mealme.calendar.model.CalendarMeal;
import com.example.mealme.model.repo.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalendarPresenter {

    Repository repo;
    CalendarViewer calendarViewer;
    OnCalendarMealDeleted onCalendarMealDeleted;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestoreDb;
    FirebaseUser firebaseUser;
    String userId;

    public CalendarPresenter(Repository repo, CalendarViewer calendarViewer, OnCalendarMealDeleted onCalendarMealDeleted) {
        this.repo = repo;
        this.calendarViewer = calendarViewer;
        this.onCalendarMealDeleted = onCalendarMealDeleted;
         firebaseAuth = FirebaseAuth.getInstance();
         firestoreDb = FirebaseFirestore.getInstance();
         firebaseUser = firebaseAuth.getCurrentUser();
    }

    @SuppressLint("CheckResult")
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

    public void deleteCalendarMealFirebase(CalendarMeal calendarMeal){
        userId = firebaseUser.getUid();
        firestoreDb.collection("users")
                .document(userId)
                .collection("calendarMeals")
                .whereEqualTo("idMeal",calendarMeal.getIdMeal())
                .whereEqualTo("date",calendarMeal.getDate())
                .get()
                .addOnSuccessListener(itemToDelete ->{
                    for (QueryDocumentSnapshot document : itemToDelete) {
                        document.getReference().delete();
                    }
                });
    }
}
