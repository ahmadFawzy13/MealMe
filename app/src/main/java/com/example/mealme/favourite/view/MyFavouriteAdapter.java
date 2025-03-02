package com.example.mealme.favourite.view;

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
import com.example.mealme.R;
import com.example.mealme.model.remote.Meal;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class MyFavouriteAdapter extends RecyclerView.Adapter<MyFavouriteAdapter.ViewHolder> {

        private Context context;
        private List<Meal> favMeals;
        private DeleteFavMeal deleteFavMeal;
        private FavMealObjectTransfer favMealObjectTransfer;
        private Meal meal;

    public MyFavouriteAdapter(Context context, List<Meal> favMeals, DeleteFavMeal deleteFavMeal, FavMealObjectTransfer favMealObjectTransfer) {
        this.context = context;
        this.favMeals = favMeals;
        this.deleteFavMeal = deleteFavMeal;
        this.favMealObjectTransfer = favMealObjectTransfer;
    }

    public void setList(List<Meal> favMeals) {
            this.favMeals = favMeals;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View v =inflater.inflate(R.layout.favourite_recycler,recyclerView,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyFavouriteAdapter.ViewHolder holder, int position) {
            holder.favMealName.setText(favMeals.get(position).getStrMeal());
            holder.favCountry.setText(favMeals.get(position).getStrArea());
            holder.favType.setText(favMeals.get(position).getStrCategory());
            Glide.with(context).load(favMeals.get(position).getStrMealThumb()).into(holder.favMealImg);

            holder.deleteFavButton.setOnClickListener(v->{
                meal = favMeals.get(position);
                deleteFavMeal.mealToDelete(meal);
            });

            holder.favCard.setOnClickListener(v->{
                meal = favMeals.get(position);
                favMealObjectTransfer.favMealObjectTransfer(meal);
            });

        }

        @Override
        public int getItemCount() {
            return favMeals.size();
        }
        public class ViewHolder extends RecyclerView.ViewHolder{

            TextView favMealName;
            ShapeableImageView favMealImg;
            TextView favCountry;
            TextView favType;
            Button deleteFavButton;
            CardView favCard;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                favCountry = itemView.findViewById(R.id.favCountry);
                favMealName = itemView.findViewById(R.id.favMealName);
                favMealImg = itemView.findViewById(R.id.favMealImg);
                favType = itemView.findViewById(R.id.favType);
                deleteFavButton = itemView.findViewById(R.id.deleteFavBtn);
                favCard = itemView.findViewById(R.id.favCard);

            }
        }
}

