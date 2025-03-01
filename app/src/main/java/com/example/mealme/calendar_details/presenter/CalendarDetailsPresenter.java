package com.example.mealme.calendar_details.presenter;

import com.example.mealme.calendar.model.CalendarMeal;
import com.example.mealme.common.Reflector;
import com.example.mealme.model.remote.Meal;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarDetailsPresenter {

    Reflector reflector;

    public CalendarDetailsPresenter(Reflector reflector) {
        this.reflector = reflector;
    }


    public void ingredientsAndMeasuresReflection(CalendarMeal calendarMeal){
        List<String> ingredients = new ArrayList<>();
        List<String>measures = new ArrayList<>();

        for(int i = 1; i <= 20; i++){
            try {
                String ingredient = (String) CalendarMeal.class.getMethod("getStrIngredient"+i).invoke(calendarMeal);
                String measure = (String) CalendarMeal.class.getMethod("getStrMeasure"+i).invoke(calendarMeal);

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
