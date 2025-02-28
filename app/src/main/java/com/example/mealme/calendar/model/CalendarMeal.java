package com.example.mealme.calendar.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;

import com.example.mealme.model.remote.Meal;

import java.io.Serializable;

@Entity(tableName = "calendar_meals_table",primaryKeys = {"idMeal","date"})
public class CalendarMeal extends Meal implements Serializable {
    @NonNull
    String date;

    public CalendarMeal(){
        super();
    }

    public CalendarMeal(Meal meal, @NonNull String date) {
        super();
        this.setIdMeal(meal.getIdMeal());
        this.setStrMeal(meal.getStrMeal());
        this.setStrMealThumb(meal.getStrMealThumb());
        this.setStrInstructions(meal.getStrInstructions());
        this.setStrCategory(meal.getStrCategory());
        this.setStrArea(meal.getStrArea());
        this.setStrTags(meal.getStrTags());
        this.setStrYoutube(meal.getStrYoutube());
        this.setStrIngredient1(meal.getStrIngredient1());
        this.setStrIngredient2(meal.getStrIngredient2());
        this.setStrIngredient3(meal.getStrIngredient3());
        this.setStrIngredient4(meal.getStrIngredient4());
        this.setStrIngredient5(meal.getStrIngredient5());
        this.setStrIngredient6(meal.getStrIngredient6());
        this.setStrIngredient7(meal.getStrIngredient7());
        this.setStrIngredient8(meal.getStrIngredient8());
        this.setStrIngredient9(meal.getStrIngredient9());
        this.setStrIngredient10(meal.getStrIngredient10());
        this.setStrIngredient11(meal.getStrIngredient11());
        this.setStrIngredient12(meal.getStrIngredient12());
        this.setStrIngredient13(meal.getStrIngredient13());
        this.setStrIngredient14(meal.getStrIngredient14());
        this.setStrIngredient15(meal.getStrIngredient15());
        this.setStrIngredient16(meal.getStrIngredient16());
        this.setStrIngredient17(meal.getStrIngredient17());
        this.setStrIngredient18(meal.getStrIngredient18());
        this.setStrIngredient19(meal.getStrIngredient19());
        this.setStrIngredient20(meal.getStrIngredient20());
        this.setStrMeasure1(meal.getStrMeasure1());
        this.setStrMeasure2(meal.getStrMeasure2());
        this.setStrMeasure3(meal.getStrMeasure3());
        this.setStrMeasure4(meal.getStrMeasure4());
        this.setStrMeasure5(meal.getStrMeasure5());
        this.setStrMeasure6(meal.getStrMeasure6());
        this.setStrMeasure7(meal.getStrMeasure7());
        this.setStrMeasure8(meal.getStrMeasure8());
        this.setStrMeasure9(meal.getStrMeasure9());
        this.setStrMeasure10(meal.getStrMeasure10());
        this.setStrMeasure11(meal.getStrMeasure11());
        this.setStrMeasure12(meal.getStrMeasure12());
        this.setStrMeasure13(meal.getStrMeasure13());
        this.setStrMeasure14(meal.getStrMeasure14());
        this.setStrMeasure15(meal.getStrMeasure15());
        this.setStrMeasure16(meal.getStrMeasure16());
        this.setStrMeasure17(meal.getStrMeasure17());
        this.setStrMeasure18(meal.getStrMeasure18());
        this.setStrMeasure19(meal.getStrMeasure19());
        this.setStrMeasure20(meal.getStrMeasure20());

        this.date = date;
    }

    public CalendarMeal(String idMeal, String strMeal, String strMealThumb, String strInstructions,
                        String strCategory, String strArea, String strTags, String strYoutube,
                        String strIngredient1, String strIngredient2, String strIngredient3,
                        String strIngredient4, String strIngredient5, String strIngredient6,
                        String strIngredient7, String strIngredient8, String strIngredient9,
                        String strIngredient10, String strIngredient11, String strIngredient12,
                        String strIngredient13, String strIngredient14, String strIngredient15,
                        String strIngredient16, String strIngredient17, String strIngredient18,
                        String strIngredient19, String strIngredient20, String strMeasure1,
                        String strMeasure2, String strMeasure3, String strMeasure4,
                        String strMeasure5, String strMeasure6, String strMeasure7,
                        String strMeasure8, String strMeasure9, String strMeasure10,
                        String strMeasure11, String strMeasure12, String strMeasure13,
                        String strMeasure14, String strMeasure15, String strMeasure16,
                        String strMeasure17, String strMeasure18, String strMeasure19,
                        String strMeasure20, @NonNull String date) {

        super(idMeal, strMeal, strMealThumb, strInstructions, strCategory, strArea, strTags,
                strYoutube, strIngredient1, strIngredient2, strIngredient3, strIngredient4,
                strIngredient5, strIngredient6, strIngredient7, strIngredient8, strIngredient9,
                strIngredient10, strIngredient11, strIngredient12, strIngredient13, strIngredient14,
                strIngredient15, strIngredient16, strIngredient17, strIngredient18, strIngredient19,
                strIngredient20, strMeasure1, strMeasure2, strMeasure3, strMeasure4, strMeasure5,
                strMeasure6, strMeasure7, strMeasure8, strMeasure9, strMeasure10, strMeasure11,
                strMeasure12, strMeasure13, strMeasure14, strMeasure15, strMeasure16, strMeasure17,
                strMeasure18, strMeasure19, strMeasure20);

            this.date = date;
     }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }
}
