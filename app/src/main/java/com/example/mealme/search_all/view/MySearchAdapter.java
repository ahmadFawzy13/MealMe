package com.example.mealme.search_all.view;

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
import com.example.mealme.R;
import com.example.mealme.SearchTextDelivery;
import com.example.mealme.search_all.model.CategorySearchPojo;
import com.example.mealme.search_all.model.CountrySearchPojo;
import com.example.mealme.search_all.model.IngredientSearchPojo;

import java.util.List;

public class MySearchAdapter extends RecyclerView.Adapter<MySearchAdapter.ViewHolder>{

    private List<CategorySearchPojo> categoriesList;
    private List<CountrySearchPojo>countriesList;
    private List <IngredientSearchPojo>ingredientsList;
    private final Context context;
    String searchText;
    onSearchItemClickedListener onSearchItemClickedListener;

    public MySearchAdapter(List<CategorySearchPojo> categoriesList, List<CountrySearchPojo> countriesList, List<IngredientSearchPojo> ingredientsList, Context context, String searchText,onSearchItemClickedListener onSearchItemClickedListener) {
        this.categoriesList = categoriesList;
        this.countriesList = countriesList;
        this.ingredientsList = ingredientsList;
        this.context = context;
        this.searchText = searchText;
        this.onSearchItemClickedListener = onSearchItemClickedListener;
    }

    public void setCategoriesList(List<CategorySearchPojo>categoriesList){
        this.categoriesList = categoriesList;
        notifyDataSetChanged();
    }

    public void setCountriesList(List<CountrySearchPojo>countriesList){
        this.countriesList = countriesList;
        notifyDataSetChanged();
    }

    public void setIngredientsList(List<IngredientSearchPojo>ingredientsList){
        this.ingredientsList = ingredientsList;
        notifyDataSetChanged();
    }

    public void setTextSearch(String searchText){
        this.searchText = searchText;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MySearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup recyclerView, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v =inflater.inflate(R.layout.recycler_search,recyclerView,false);
        return new MySearchAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MySearchAdapter.ViewHolder holder, int position) {

        switch(searchText){
            case "Ingredient":
                Glide.with(context).load("https://www.themealdb.com/images/ingredients/"+ ingredientsList.get(position).getStrIngredient()+".png").into(holder.searchImg);
                holder.searchName.setText(ingredientsList.get(position).getStrIngredient());
                break;
            case "Category":
                Glide.with(context).load(categoriesList.get(position).getStrCategoryThumb()).into(holder.searchImg);
                holder.searchName.setText(categoriesList.get(position).getStrCategory());
                break;
            case "Country":
                String countryCode = getCountryNameCode(countriesList.get(position).getStrArea());
                String flag = "https://www.themealdb.com/images/icons/flags/big/64/" + countryCode + ".png";
                holder.searchName.setText(countriesList.get(position).getStrArea());
                Glide.with(context).load(flag).into(holder.searchImg);
                break;
        }

        holder.cardViewSearchPage.setOnClickListener(v->{
            switch(searchText){
                case "Ingredient":
                    onSearchItemClickedListener.onSearchItemClick(searchText,ingredientsList.get(position).getStrIngredient());
                    break;
                case "Category":
                    onSearchItemClickedListener.onSearchItemClick(searchText,categoriesList.get(position).getStrCategory());
                    break;
                case "Country":
                    onSearchItemClickedListener.onSearchItemClick(searchText,countriesList.get(position).getStrArea());
                    break;
            }
        });
    }

    @Override
    public int getItemCount() {
        switch(searchText) {
            case "Ingredient":
                return ingredientsList.size();
            case "Category":
                return categoriesList.size();
            case "Country":
                return countriesList.size();
        }
        return 0;
    }

    private String getCountryNameCode(String countryName) {
        switch (countryName.toLowerCase()) {
            case "american": return "us";
            case "british": return "gb";
            case "canadian": return "ca";
            case "chinese": return "cn";
            case "croatian": return "hr";
            case "dutch": return "nl";
            case "egyptian": return "eg";
            case "french": return "fr";
            case "greek": return "gr";
            case "indian": return "in";
            case "irish": return "ie";
            case "italian": return "it";
            case "jamaican": return "jm";
            case "japanese": return "jp";
            case "kenyan": return "ke";
            case "malaysian": return "my";
            case "mexican": return "mx";
            case "moroccan": return "ma";
            case "polish": return "pl";
            case "portuguese": return "pt";
            case "russian": return "ru";
            case "spanish": return "es";
            case "thai": return "th";
            case "tunisian": return "tn";
            case "turkish": return "tr";
            case "vietnamese": return "vn";
            case "filipino": return "ph";
            case "ukrainian": return "ua";
            case "uruguayan": return "uy";
            case "norwegian": return "no";
            default: return countryName;
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView searchImg;
        TextView searchName;
        CardView cardViewSearchPage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchImg = itemView.findViewById(R.id.searchImg);
            searchName = itemView.findViewById(R.id.searchName);
            cardViewSearchPage = itemView.findViewById(R.id.cardViewSearchPage);
        }
    }
}
