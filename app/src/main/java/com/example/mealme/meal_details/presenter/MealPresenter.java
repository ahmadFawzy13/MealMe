package com.example.mealme.meal_details.presenter;

import com.example.mealme.meal_details.view.MealDetailsDatabaseOps;
import com.example.mealme.calendar.model.CalendarMeal;
import com.example.mealme.Reflector;
import com.example.mealme.meal_details.model.Meal;
import com.example.mealme.meal_details.model.MealDetailViewer;
import com.example.mealme.model.repo.Repository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealPresenter{

    Repository repo;
    MealDetailViewer mealDetailViewer;
    Reflector reflector;
    MealDetailsDatabaseOps mealDetailsDatabaseOps;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestoreDb;
    FirebaseUser  firebaseUser;
    String userId;
    public MealPresenter(Repository repo, MealDetailViewer mealDetailViewer, Reflector reflector, MealDetailsDatabaseOps mealDetailsDatabaseOps){
        this.repo = repo;
        this.mealDetailViewer = mealDetailViewer;
        this.reflector = reflector;
        this.mealDetailsDatabaseOps = mealDetailsDatabaseOps;
        firebaseAuth = FirebaseAuth.getInstance();
        firestoreDb = FirebaseFirestore.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
    }

    public void getMealDetails(String idMeal){
        repo.getMealDetailsRemote(idMeal)
                .subscribeOn(Schedulers.io())
                .map(item->item.getListOfMealsResponse())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<Meal>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<Meal> meals) {
                        Meal meal = meals.get(0);
                        List<String>ingredientsList = new ArrayList<>();
                        List<String>measuresList = new ArrayList<>();

                        for(int i = 1; i <= 20; i++){
                            try {
                                String ingredient = (String) Meal.class.getMethod("getStrIngredient" + i).invoke(meal);
                                String measure = (String) Meal.class.getMethod("getStrMeasure" + i).invoke(meal);

                                if(ingredient != null && !ingredient.isEmpty()){
                                    ingredientsList.add(ingredient);
                                    measuresList.add(measure);
                                }
                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            } catch (InvocationTargetException e) {
                                throw new RuntimeException(e);
                            } catch (NoSuchMethodException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        mealDetailViewer.showMealDetails(meals);
                        reflector.reflectedLists(ingredientsList,measuresList);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mealDetailViewer.showMealDetailsErrorMsg(e.getLocalizedMessage());
                    }
                });
    }

    public void insertMealLocal(Meal meal){
        repo.insertFavMealLocal(meal)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mealDetailsDatabaseOps.onSuccessLocalInsertion("Added To Favourites");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mealDetailsDatabaseOps.onFailedLocalInsertion("Couldn't Add to Favourites");
                    }
                });
    }

    public void insertCalendarMealLocal(CalendarMeal calendarMeal){
        repo.insertCalendarMealLocal(calendarMeal)
                .subscribeOn(Schedulers.io())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        mealDetailsDatabaseOps.onSuccessCalendarInsertion("Added To Calendar");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mealDetailsDatabaseOps.onFailedCalendarInsertion("Couldn't Add To Calendar");
                    }
                });
    }

    public void insertItemToFireStore(CalendarMeal calendarMeal, Meal meal){
        userId = firebaseUser.getUid();
        if(meal == null) {
            firestoreDb.collection("users")
                    .document(userId)
                    .collection("calendarMeals")
                    .add(calendarMeal);

        }else{
            firestoreDb.collection("users")
                    .document(userId)
                    .collection("meals")
                    .add(meal);
        }
    }
}
