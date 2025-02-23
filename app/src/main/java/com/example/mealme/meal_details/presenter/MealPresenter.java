package com.example.mealme.meal_details.presenter;

import com.example.mealme.Reflector;
import com.example.mealme.meal_details.model.Meal;
import com.example.mealme.meal_details.model.MealDetailViewer;
import com.example.mealme.model.remote.MealDetailsNetworkCallBack;
import com.example.mealme.model.repo.Repository;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class MealPresenter implements MealDetailsNetworkCallBack {

    Repository repo;
    MealDetailViewer mealDetailViewer;

    Reflector reflector;

    public MealPresenter(Repository repo, MealDetailViewer mealDetailViewer, Reflector reflector) {
        this.repo = repo;
        this.mealDetailViewer = mealDetailViewer;
        this.reflector = reflector;
    }

    public void getMealDetails(String idMeal){
        repo.getMealDetailsRemote(this,idMeal);
    }

    public void insertMeal(Meal meal){
        repo.insertMealLocal(meal);
    }

    @Override
    public void onMealDetailsSuccessResult(List<Meal> listOfMeals) {

        Meal meal = listOfMeals.get(0);
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

        mealDetailViewer.showMealDetails(listOfMeals);
        reflector.reflectedLists(ingredientsList,measuresList);

    }

    @Override
    public void onMealDetailsFailedResult(String errorMessage) {
       mealDetailViewer.showMealDetailsErrorMsg(errorMessage);
    }
}
