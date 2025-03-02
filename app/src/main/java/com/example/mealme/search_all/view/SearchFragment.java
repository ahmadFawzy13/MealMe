package com.example.mealme.search_all.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

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
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment implements CountrySearchViewer, CategorySearchViewer, IngredientSearchViewer,onSearchItemClickedListener{

    private TextInputEditText textInputEditText;
    private ChipGroup chipGroup;
    private Chip categoryChip,ingChip,countryChip;
    private SearchPresenter searchPresenter;
    private RecyclerView recyclerView;
    private ConstraintLayout searchConstraint;
    private View view;
    private String chipText;
    private MySearchAdapter mySearchAdapter;
    private TextInputLayout textField;

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
        textField = view.findViewById(R.id.textField);
        this.view = view;

        recyclerView = view.findViewById(R.id.recyclerSearch);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(requireActivity(),2,
                RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);

        searchPresenter = setUpPresenter();
        searchPresenter.getSearchCategories();
        searchPresenter.getSearchCountries();
        searchPresenter.getSearchIngredients();

        mySearchAdapter = new MySearchAdapter(new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),requireActivity(),"Category",this);
        recyclerView.setAdapter(mySearchAdapter);

        for(int i = 0; i < chipGroup.getChildCount(); i++){
            Chip chip = (Chip) chipGroup.getChildAt(i);
            chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        chipText = chip.getText().toString();
                        textField.setHint("Search " + chipText);
                        mySearchAdapter.setTextSearch(chipText);
                    }
                }
            });
        }

    }

    private SearchPresenter setUpPresenter(){
        MealLocalDataSource mealLocalDataSource = new MealLocalDataSource(requireActivity());
        MealRemoteDataSource mealRemoteDataSource = new MealRemoteDataSource();
        Repository repo = Repository.getInstance(mealRemoteDataSource,mealLocalDataSource);
        return new SearchPresenter(repo,this,this,this);
    }
    @Override
    public void onCategoryListSuccess(List<CategorySearchPojo> categoriesList) {

        mySearchAdapter.setCategoriesList(categoriesList);

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchPresenter.categoryTextWatching(categoriesList,s,mySearchAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    public void onCountryListSuccess(List<CountrySearchPojo> countriesList) {

        mySearchAdapter.setCountriesList(countriesList);

        textInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchPresenter.countryTextWatching(countriesList,s,mySearchAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    public void onIngredientsListSuccess(List<IngredientSearchPojo> ingredientsList) {

            mySearchAdapter.setIngredientsList(ingredientsList);

            textInputEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    searchPresenter.ingredientTextWatching(ingredientsList,s,mySearchAdapter);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
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
    @Override
    public void onSearchItemClick(String chipText, String searchName) {
        SearchFragmentDirections.ActionSearchFragmentToSearchByFragment action = SearchFragmentDirections.actionSearchFragmentToSearchByFragment(chipText,searchName);
        Navigation.findNavController(view).navigate(action);
    }
}