package com.example.mealme.search_by.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mealme.common.IdDelivery;
import com.example.mealme.R;
import com.example.mealme.search_by.model.SearchByPojo;
import com.example.mealme.search_by.presenter.SearchByPresenter;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.model.repo.Repository;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;


public class SearchByFragment extends Fragment implements SearchMealsViewer, IdDelivery {

    SearchByPresenter searchByPresenter;
    String receivedChipText;
    String receivedItemNameToSearch;
    TextInputLayout textSearch;
    RecyclerView recyclerView;
    SearchByAdapter searchByAdapter;
    ConstraintLayout constraintLayout;
    TextInputEditText textInputEditText;
    View view;


    public SearchByFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search_by, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        textSearch = view.findViewById(R.id.textSearch);
        textInputEditText = view.findViewById(R.id.searchTextExclusive);
        constraintLayout = view.findViewById(R.id.searchByConstraint);
        this.view = view;

        recyclerView = view.findViewById(R.id.recyclerSearchExclusive);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        receivedChipText = SearchByFragmentArgs.fromBundle(getArguments()).getChipText();
        receivedItemNameToSearch = SearchByFragmentArgs.fromBundle(getArguments()).getSearchName();

        searchByAdapter = new SearchByAdapter(requireActivity(),new ArrayList<>(),this);
        recyclerView.setAdapter(searchByAdapter);

        searchByPresenter = setUpPresenter();
        itemsToSearchFor(receivedChipText,receivedItemNameToSearch);

    }

    private SearchByPresenter setUpPresenter(){
        MealRemoteDataSource mealRemoteDataSource = new MealRemoteDataSource();
        MealLocalDataSource mealLocalDataSource = new MealLocalDataSource(requireContext());
        Repository repo = Repository.getInstance(mealRemoteDataSource,mealLocalDataSource);
        return new SearchByPresenter(repo,this);
    }

    private void itemsToSearchFor(String receivedChipText, String receivedItemNameToSearch){
        switch(receivedChipText){
            case "Ingredient":
                searchByPresenter.getMealsBy(null,null,receivedItemNameToSearch);
                break;
            case "Category":
                searchByPresenter.getMealsBy(receivedItemNameToSearch,null,null);
                break;
            case "Country":
                searchByPresenter.getMealsBy(null,receivedItemNameToSearch,null);
                break;
        }
    }

    @Override
    public void onSearchMealsSuccess(List<SearchByPojo> searchMealsList) {
       searchByAdapter.setList(searchMealsList);

       textInputEditText.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchByPresenter.textSearchWatch(s,searchMealsList,searchByAdapter);
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });
    }

    @Override
    public void onSearchMealsFailure(String err) {
        Snackbar.make(constraintLayout,err,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void idTransfer(String id) {
        SearchByFragmentDirections.ActionSearchByFragmentToMealFragment action
                = SearchByFragmentDirections.actionSearchByFragmentToMealFragment(id);
        Navigation.findNavController(view).navigate(action);
    }
}