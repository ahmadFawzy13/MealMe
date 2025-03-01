package com.example.mealme.calendar_details.view;

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
import com.example.mealme.calendar.model.CalendarMeal;
import com.example.mealme.calendar_details.presenter.CalendarDetailsPresenter;
import com.example.mealme.common.Reflector;
import com.example.mealme.main.view.MainActivity;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CalendarDetailsFragment extends Fragment implements Reflector {

    TextView calendarMealDetailsName;
    TextView calendarMealDetailsCountry;
    TextView calendarMealDetailsType;
    TextView calendarMealsInstructions;
    ImageView calendarMealImg;
    RecyclerView recyclerView;
    CalendarMeal calendarMeal;
    YouTubePlayerView youTubePlayerView;

    public CalendarDetailsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).showBottomNav(true);
        return inflater.inflate(R.layout.fragment_calendar_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendarMealsInstructions = view.findViewById(R.id.calendar_meal_details_instructions);
        calendarMealDetailsName = view.findViewById(R.id.calendarDetailsMealName);
        calendarMealDetailsCountry = view.findViewById(R.id.calendarDetailsCountryNamee);
        calendarMealImg = view.findViewById(R.id.calendarDetailsImgPage);
        calendarMealDetailsType = view.findViewById(R.id.calendarDetailsMealTypee);
        recyclerView = view.findViewById(R.id.recycler_calendar_meal_details_pagee);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        calendarMeal = CalendarDetailsFragmentArgs.fromBundle(getArguments()).getCalenderMeal();
        showMealDetails();
        CalendarDetailsPresenter calendarDetailsPresenter = new CalendarDetailsPresenter(this);
        calendarDetailsPresenter.ingredientsAndMeasuresReflection(calendarMeal);
    }

    private void showMealDetails() {
        Glide.with(requireActivity()).load(calendarMeal.getStrMealThumb()).into(calendarMealImg);
        calendarMealDetailsName.setText(calendarMeal.getStrMeal());
        calendarMealDetailsType.setText(calendarMeal.getStrCategory());
        calendarMealDetailsCountry.setText(calendarMeal.getStrArea());
        calendarMealsInstructions.setText(calendarMeal.getStrInstructions());
    }

    @Override
    public void reflectedLists(List<String> ingredientsList, List<String> measuresList) {
        MyCalendarDetailsAdapter myCalendarDetailsAdapter =
                new MyCalendarDetailsAdapter(ingredientsList,measuresList,requireActivity());
        recyclerView.setAdapter(myCalendarDetailsAdapter);
    }
}