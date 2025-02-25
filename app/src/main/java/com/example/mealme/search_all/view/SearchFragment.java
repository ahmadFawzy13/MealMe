package com.example.mealme.search_all.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealme.CategorySearchViewer;
import com.example.mealme.CountrySearchViewer;
import com.example.mealme.IngredientSearchViewer;
import com.example.mealme.R;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.model.repo.Repository;
import com.example.mealme.search_all.model.CategorySearchPojo;
import com.example.mealme.search_all.model.CountrySearchPojo;
import com.example.mealme.search_all.model.IngredientSearchPojo;
import com.example.mealme.search_all.presenter.SearchPresenter;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class SearchFragment extends Fragment implements CountrySearchViewer, CategorySearchViewer, IngredientSearchViewer {

    private TextInputEditText textInputEditText;
    private ChipGroup chipGroup;
    private Chip categoryChip;
    private Chip ingChip;
    private Chip countryChip;
    SearchPresenter searchPresenter;
    RecyclerView recyclerView;
    ConstraintLayout searchConstraint;
    View view;

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).showBottomNav(true);
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textInputEditText = view.findViewById(R.id.searchText);
        chipGroup = view.findViewById(R.id.chipGroup);
        categoryChip = view.findViewById(R.id.categoryChip);
        countryChip = view.findViewById(R.id.countryChip);
        ingChip = view.findViewById(R.id.ingChip);
        searchConstraint = view.findViewById(R.id.searchConstraint);
        this.view = view;

        recyclerView = view.findViewById(R.id.recyclerSearch);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(),3,
                RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        searchPresenter = setUpPresenter();
        searchPresenter.getSearchCategories();
        searchPresenter.getSearchCountries();
        searchPresenter.getSearchIngredients();
    }

    private SearchPresenter setUpPresenter(){
        MealLocalDataSource mealLocalDataSource = new MealLocalDataSource(requireActivity());
        MealRemoteDataSource mealRemoteDataSource = new MealRemoteDataSource();
        Repository repo = Repository.getInstance(mealRemoteDataSource,mealLocalDataSource);
        return new SearchPresenter(repo,this,this,this);
    }

    @Override
    public void onCategoryListSuccess(List<CategorySearchPojo> categoriesList) {

    }
    @Override
    public void onCountryListSuccess(List<CountrySearchPojo> countriesList) {

    }
    @Override
    public void onIngredientsListSuccess(List<IngredientSearchPojo> ingredientsList) {

    }

    @Override
    public void onCategoryListFailure(String err) {
        Snackbar.make(view,err,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCountrylistFailure(String err) {
        Snackbar.make(view,err,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onIngredientsListFailure(String err) {
        Snackbar.make(view,err,Snackbar.LENGTH_SHORT).show();
    }
}