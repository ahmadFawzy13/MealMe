package com.example.mealme.favourite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mealme.DeleteMeal;
import com.example.mealme.ObjectTransfer;
import com.example.mealme.R;
import com.example.mealme.favourite.presenter.FavouritePresenter;
import com.example.mealme.favourite_details.view.FavouriteMealDetailsFragment;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.meal_details.model.Meal;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.model.repo.Repository;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment implements DeleteMeal, ObjectTransfer {

    LiveData<List<Meal>>favMeals;
    FavouritePresenter favouritePresenter;
    MyFavouriteAdapter myFavouriteAdapter;
    RecyclerView recyclerView;
    Button deleteFavBtn;
    ConstraintLayout constraintLayout;

    View view;
    public FavouriteFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).showBottomNav(true);
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        deleteFavBtn = view.findViewById(R.id.deleteFavBtn);
        recyclerView = view.findViewById(R.id.recycler_frag);
        constraintLayout = view.findViewById(R.id.favouriteLayout);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        this.view = view;

        favouritePresenter = setFavouritePresenter();


        myFavouriteAdapter = new MyFavouriteAdapter(requireActivity(),new ArrayList<>(),this,this);
        recyclerView.setAdapter(myFavouriteAdapter);

        favMeals = favouritePresenter.getFavMealLocal();
        Observer<List<Meal>> favMealsObserver = new Observer<List<Meal>>() {
            @Override
            public void onChanged(List<Meal> meals) {
                myFavouriteAdapter.setList(meals);
            }
        };
        favMeals.observe(getViewLifecycleOwner(),favMealsObserver);

    }


    private FavouritePresenter setFavouritePresenter(){
        MealLocalDataSource mealLocalDataSource = new MealLocalDataSource(requireActivity());
        MealRemoteDataSource mealRemoteDataSource = new MealRemoteDataSource();
        Repository repo = Repository.getInstance(mealRemoteDataSource,mealLocalDataSource);
        return new FavouritePresenter(repo);
    }

    @Override
    public void mealToDelete(Meal meal) {
        favouritePresenter.deleteFavMeal(meal);
        Snackbar.make(constraintLayout,"Removed From Favourites",Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void mealObjectTransfer(Meal meal) {
        FavouriteFragmentDirections.ActionFavouriteFragmentToFavouriteMealDetailsFragment action =
            FavouriteFragmentDirections.actionFavouriteFragmentToFavouriteMealDetailsFragment(meal);
                Navigation.findNavController(view).navigate(action);
    }
}