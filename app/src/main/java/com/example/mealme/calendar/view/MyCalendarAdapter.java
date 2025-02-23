package com.example.mealme.calendar.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealme.CalendarMeal;
import com.example.mealme.CalendarMealObjectTransfer;
import com.example.mealme.DeleteCalendarMeal;
import com.example.mealme.DeleteMeal;
import com.example.mealme.MealObjectTransfer;
import com.example.mealme.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class MyCalendarAdapter extends RecyclerView.Adapter<MyCalendarAdapter.ViewHolder> {

    private Context context;
    private List<CalendarMeal> calendarMealsList;
    DeleteCalendarMeal deleteCalendarMeal;
    CalendarMealObjectTransfer calendarMealObjectTransfer;
    CalendarMeal calendarMeal;

    public MyCalendarAdapter(Context context, List<CalendarMeal> calendarMealsList, DeleteCalendarMeal deleteCalendarMeal, CalendarMealObjectTransfer calendarMealObjectTransfer) {
        this.context = context;
        this.calendarMealsList = calendarMealsList;
        this.deleteCalendarMeal = deleteCalendarMeal;
        this.calendarMealObjectTransfer = calendarMealObjectTransfer;
    }

    public void setList(List<CalendarMeal> calendarMealsList) {
        this.calendarMealsList = calendarMealsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyCalendarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v =inflater.inflate(R.layout.recycler_calendar_cards,recyclerView,false);
        return new MyCalendarAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCalendarAdapter.ViewHolder holder, int position) {
        holder.calendarName.setText(calendarMealsList.get(position).getStrMeal());
        holder.calendarCountry.setText(calendarMealsList.get(position).getStrArea());
        holder.calendarType.setText(calendarMealsList.get(position).getStrCategory());
        Glide.with(context).load(calendarMealsList.get(position).getStrMealThumb()).into(holder.calendarImg);

        holder.deleteCalendarButton.setOnClickListener(v->{
            calendarMeal = calendarMealsList.get(position);
            deleteCalendarMeal.calendarMealToDelete(calendarMeal);
        });

        holder.calendarCard.setOnClickListener(v->{
            calendarMeal = calendarMealsList.get(position);
            calendarMealObjectTransfer.calendarMealObjectTransfer(calendarMeal);
        });

    }

    @Override
    public int getItemCount() {
        return calendarMealsList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView calendarName;
        ShapeableImageView calendarImg;
        TextView calendarCountry;
        TextView calendarType;
        Button deleteCalendarButton;
        CardView calendarCard;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            calendarCountry = itemView.findViewById(R.id.calendarCountry);
            calendarName = itemView.findViewById(R.id.calendarMealName);
            calendarImg = itemView.findViewById(R.id.calendarMealImg);
            calendarType = itemView.findViewById(R.id.calendarType);
            deleteCalendarButton = itemView.findViewById(R.id.deleteCalendarBtn);
            calendarCard = itemView.findViewById(R.id.calendarCard);
        }
    }
}
