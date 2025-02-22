package com.example.mealme.meal_details.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mealme.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {

    private Context context;
    private List<String>ingredientsList;
    private List<String>measuresList;

    public MealAdapter(Context context, List<String> ingredientsList, List<String> measuresList) {
        this.context = context;
        this.ingredientsList = ingredientsList;
        this.measuresList = measuresList;
    }

    public void setList(List<String>ingredientsList,List<String>measuresList){
        this.ingredientsList = ingredientsList;
        this.measuresList = measuresList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v =inflater.inflate(R.layout.recycler_ingredients,recyclerView,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String ingredientName = ingredientsList.get(position);
        String ingNameForUrl = ingredientName.replace(" ","_");

        Glide.with(context).load("https://www.themealdb.com/images/ingredients/"+ingNameForUrl+".png").into(holder.ingredientImg);
       holder.ingredientName.setText(ingredientsList.get(position));
       holder.ingredientPortion.setText(measuresList.get(position));
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ingredientImg;
        TextView ingredientName;
        TextView ingredientPortion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ingredientImg = itemView.findViewById(R.id.ingredientImg);
            ingredientName = itemView.findViewById(R.id.ingredientName);
            ingredientPortion = itemView.findViewById(R.id.ingredientPortion);

        }
    }
}
