package com.example.mealme.home.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealme.common.onMealClickListener;
import com.example.mealme.R;
import com.example.mealme.home.model.HomeMealsPojo;

import java.util.List;

public class MyHomeMealsAdapter extends RecyclerView.Adapter<MyHomeMealsAdapter.ViewHolder> {


    private Context context;
    private List<HomeMealsPojo> response;

    onMealClickListener onMealClickListener;

    public MyHomeMealsAdapter(Context context, List<HomeMealsPojo> response, onMealClickListener onMealClickListener) {
        this.context = context;
        this.response = response;
        this.onMealClickListener = onMealClickListener;
    }

    public void setList(List<HomeMealsPojo> mealsList) {
        this.response = mealsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v =inflater.inflate(R.layout.home_recycler_row,recyclerView,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.homeMealName.setText(response.get(position).getStrMeal());
        Glide.with(context).load(response.get(position).getStrMealThumb()).into(holder.homeMealPhoto);

        holder.homeMealPhoto.setOnClickListener(v->{
            onMealClickListener.onMealClicked(response.get(position).getIdMeal());
        });
    }

    @Override
    public int getItemCount() {
        return response.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView homeMealName;
        ImageView homeMealPhoto;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            homeMealName = itemView.findViewById(R.id.homeMealName);
            homeMealPhoto = itemView.findViewById(R.id.homeMealPhoto);
            constraintLayout = itemView.findViewById(R.id.recyclerView);
        }
    }
}
