package com.example.mealme.search_by.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.mealme.common.onMealClickListener;
import com.example.mealme.R;
import com.example.mealme.search_by.model.SearchByPojo;

import java.util.List;

public class SearchByAdapter extends RecyclerView.Adapter<SearchByAdapter.ViewHolder>{

    private Context context;
    private List<SearchByPojo> searchList;
    private onMealClickListener onMealClickListener;

    public SearchByAdapter(Context context, List<SearchByPojo> searchList, onMealClickListener onMealClickListener) {
        this.context = context;
        this.searchList = searchList;
        this.onMealClickListener = onMealClickListener;
    }

    public void setList(List<SearchByPojo> searchList) {
        this.searchList = searchList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchByAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v =inflater.inflate(R.layout.recycler_search_by,recyclerView,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchByAdapter.ViewHolder holder, int position) {
        holder.searchByName.setText(searchList.get(position).getStrMeal());
        Glide.with(context).load(searchList.get(position).getStrMealThumb()).into(holder.searchByImg);

        holder.cardView.setOnClickListener(v->{
            onMealClickListener.onMealClicked(searchList.get(position).getIdMeal());
        });
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView searchByName;
        private ImageView searchByImg;
        private CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchByName = itemView.findViewById(R.id.searchByName);
            searchByImg = itemView.findViewById(R.id.searchByPhoto);
            cardView = itemView.findViewById(R.id.searchByCard);
        }
    }
}
