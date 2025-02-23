package com.example.mealme.favourite_details.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealme.R;

import java.util.List;

public class MyFavouriteMealDetailsAdapter extends RecyclerView.Adapter<MyFavouriteMealDetailsAdapter.ViewHolder> {


    private List<String> ingredientsList;
    private List<String>measuresList;
    private Context context;

    public MyFavouriteMealDetailsAdapter(List<String> ingredientsList, List<String> measuresList, Context context) {
        this.ingredientsList = ingredientsList;
        this.measuresList = measuresList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyFavouriteMealDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v =inflater.inflate(R.layout.favourite_ing_recycler,recyclerView,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFavouriteMealDetailsAdapter.ViewHolder holder, int position) {
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

            ingredientImg = itemView.findViewById(R.id.favIngredientImgRecycler);
            ingredientName = itemView.findViewById(R.id.favIngredientNameRecycler);
            ingredientPortion = itemView.findViewById(R.id.favIngredientPortionRecycler);

        }
    }
}
