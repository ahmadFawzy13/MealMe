package com.example.mealme;

import com.example.mealme.search_all.model.IngredientSearchPojo;

import java.util.List;

public interface IngredientSearchViewer {

    void onIngredientsListSuccess(List<IngredientSearchPojo> ingredientsList);
    void onIngredientsListFailure(String err);

}
