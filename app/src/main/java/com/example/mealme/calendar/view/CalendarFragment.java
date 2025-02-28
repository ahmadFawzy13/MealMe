package com.example.mealme.calendar.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.mealme.calendar.model.CalendarMeal;
import com.example.mealme.R;
import com.example.mealme.calendar.presenter.CalendarPresenter;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.model.repo.Repository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends Fragment implements DeleteCalendarMeal, CalendarMealObjectTransfer, CalendarViewer, OnCalendarMealDeleted {

    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    List<CalendarMeal>calendarMealList;
    View view;
    RecyclerView recyclerView;
    DatePicker datePicker;
    CalendarPresenter calendarPresenter;
    MyCalendarAdapter myCalendarAdapter;
    String date;
    ConstraintLayout constraintLayout;

    public CalendarFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).showBottomNav(true);
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        this.view = view;

        constraintLayout = view.findViewById(R.id.constraintCalendar);
        datePicker = view.findViewById(R.id.datePickerCalendar);
        recyclerView = view.findViewById(R.id.recycler_calendar_cards);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        date = year+"/"+month+"/"+day;
        calendarPresenter = setUpPresenter();
        calendarPresenter.getCalendarMealByDate(date);

        myCalendarAdapter = new MyCalendarAdapter(requireActivity(),new ArrayList<>(),this,this);
        recyclerView.setAdapter(myCalendarAdapter);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = String.valueOf(year)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(dayOfMonth);
                calendarPresenter.getCalendarMealByDate(date);
            };
        });

        datePicker.setMinDate(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_MONTH,6);
        datePicker.setMaxDate(calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_MONTH,-6);
    }

    private CalendarPresenter setUpPresenter(){
        MealLocalDataSource mealLocalDataSource = new MealLocalDataSource(requireActivity());
        MealRemoteDataSource mealRemoteDataSource = new MealRemoteDataSource();
        Repository repository = Repository.getInstance(mealRemoteDataSource,mealLocalDataSource);
        return new CalendarPresenter(repository,this,this);
    }

    @Override
    public void calendarMealToDelete(CalendarMeal calendarMeal) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete Meal")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    calendarPresenter.deleteCalendarMeal(calendarMeal);
                    calendarPresenter.deleteCalendarMealFirebase(calendarMeal);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .show();
    }
    @Override
    public void calendarMealObjectTransfer(CalendarMeal calendarMeal) {
        CalendarFragmentDirections.ActionCalendarFragmentToCalendarDetailsFragment action =
                CalendarFragmentDirections.actionCalendarFragmentToCalendarDetailsFragment(calendarMeal);
                Navigation.findNavController(view).navigate(action);
    }
    @Override
    public void onCalendarMealSuccess(List<CalendarMeal> calendarMeal) {
            myCalendarAdapter.setList(calendarMeal);
    }
    @Override
    public void onCalendarMealFailed(String error) {
        Snackbar.make(constraintLayout,error,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarMealDeletionSuccess(String msg) {
        Snackbar.make(constraintLayout,msg,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCalendarMealDeletionFailure(String err) {
        Snackbar.make(constraintLayout,err,Snackbar.LENGTH_SHORT).show();
    }
}