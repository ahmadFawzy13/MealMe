package com.example.mealme.favourite_details.presenter;

import com.example.mealme.common.Reflector;
import com.example.mealme.model.remote.Meal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FavMealDetailsPresenter {

    Reflector reflector;

    public FavMealDetailsPresenter(Reflector reflector) {
        this.reflector = reflector;
    }

    public void ingredientsAndMeasuresReflection(Meal meal){
        List<String> ingredients = new ArrayList<>();
        List<String>measures = new ArrayList<>();

        for(int i = 1; i <= 20; i++){
            try {
                String ingredient = (String) Meal.class.getMethod("getStrIngredient"+i).invoke(meal);
                String measure = (String) Meal.class.getMethod("getStrMeasure"+i).invoke(meal);

                if(ingredient != null && !ingredient.isEmpty()){
                    ingredients.add(ingredient);
                    measures.add(measure);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
        reflector.reflectedLists(ingredients,measures);
    }
}
