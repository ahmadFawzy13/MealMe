package com.example.mealme.favourite_details.view;

import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mealme.R;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.meal_details.model.Meal;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class FavouriteMealDetailsFragment extends Fragment {

    TextView favMealDeName;
    TextView favMealDeCountry;
    TextView favMealDeType;
    TextView fave_meal_de_Instructions;
    ImageView mealImg;
    RecyclerView recyclerView;
    Meal meal;

    public FavouriteMealDetailsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).showBottomNav(true);
        return inflater.inflate(R.layout.fragment_favourite_meal_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fave_meal_de_Instructions = view.findViewById(R.id.fave_meal_de_Instructions);
        favMealDeName = view.findViewById(R.id.favMealDeName);
        favMealDeCountry = view.findViewById(R.id.favMealDeCountry);
        mealImg = view.findViewById(R.id.mealDetailsImgTwo);
        favMealDeType = view.findViewById(R.id.favMealDeType);
        recyclerView = view.findViewById(R.id.recycler_fav_meal_details_pageTwo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        meal = FavouriteMealDetailsFragmentArgs.fromBundle(getArguments()).getMeal();

        showMealDetails();
        ingredientsAndMeasures(meal);

    }

    private void showMealDetails() {
        Glide.with(requireActivity()).load(meal.getStrMealThumb()).into(mealImg);
        favMealDeName.setText(meal.getStrMeal());
        favMealDeType.setText(meal.getStrCategory());
        favMealDeCountry.setText(meal.getStrArea());
        fave_meal_de_Instructions.setText(meal.getStrInstructions());
    }

    private void ingredientsAndMeasures(Meal meal){

        List<String>ingredients = new ArrayList<>();
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
        MyFavouriteMealDetailsAdapter myFavouriteMealDetailsAdapter =
                new MyFavouriteMealDetailsAdapter(ingredients,measures,requireActivity());
        recyclerView.setAdapter(myFavouriteMealDetailsAdapter);
    }
}