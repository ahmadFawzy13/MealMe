package com.example.mealme.calendar.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.mealme.CalendarViewer;
import com.example.mealme.calendar.model.CalendarMeal;
import com.example.mealme.CalendarMealObjectTransfer;
import com.example.mealme.DeleteCalendarMeal;
import com.example.mealme.R;
import com.example.mealme.calendar.presenter.CalendarPresenter;
import com.example.mealme.favourite.view.FavouriteFragmentDirections;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.model.repo.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CalendarFragment extends Fragment implements DeleteCalendarMeal, CalendarMealObjectTransfer, CalendarViewer {

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

        datePicker.setMinDate(System.currentTimeMillis());
        calendar.add(Calendar.DAY_OF_MONTH,6);
        datePicker.setMaxDate(calendar.getTimeInMillis());
        calendar.add(Calendar.DAY_OF_MONTH,-6);
    }

    private CalendarPresenter setUpPresenter(){
        MealLocalDataSource mealLocalDataSource = new MealLocalDataSource(requireActivity());
        MealRemoteDataSource mealRemoteDataSource = new MealRemoteDataSource();
        Repository repository = Repository.getInstance(mealRemoteDataSource,mealLocalDataSource);
        return new CalendarPresenter(repository,this);
    }

    @Override
    public void calendarMealToDelete(CalendarMeal calendarMeal) {
        calendarPresenter.deleteCalendarMeal(calendarMeal);
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
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date = String.valueOf(year)+"/"+String.valueOf(monthOfYear)+"/"+String.valueOf(dayOfMonth);
                calendarPresenter.getCalendarMealByDate(date);
                    myCalendarAdapter.setList(calendarMeal);
            };
        });
    }
    @Override
    public void onCalendarMealFailed(String error) {
        Toast.makeText(requireActivity(), error, Toast.LENGTH_SHORT).show();
    }
}