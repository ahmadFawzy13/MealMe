package com.example.mealme.favourite.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mealme.R;
import com.example.mealme.favourite.presenter.FavouritePresenter;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.model.remote.Meal;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.model.repo.Repository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class FavouriteFragment extends Fragment implements DeleteFavMeal, FavMealObjectTransfer, FavouriteMealViewer {
    private FavouritePresenter favouritePresenter;
    private MyFavouriteAdapter myFavouriteAdapter;
    private RecyclerView recyclerView;
    private ConstraintLayout constraintLayout;
    private View view;

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

        favouritePresenter.getFavMealLocal();
    }

    private FavouritePresenter setFavouritePresenter(){
        MealLocalDataSource mealLocalDataSource = new MealLocalDataSource(requireActivity());
        MealRemoteDataSource mealRemoteDataSource = new MealRemoteDataSource();
        Repository repo = Repository.getInstance(mealRemoteDataSource,mealLocalDataSource);
        return new FavouritePresenter(repo,this);
    }

    @Override
    public void mealToDelete(Meal meal) {
        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Delete Meal")
                .setMessage("Are you sure you want to delete this item?")
                .setPositiveButton("Delete", (dialog, which) -> {
                        favouritePresenter.deleteFavMeal(meal);
                        favouritePresenter.deleteFavMealFirebase(meal);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .show();
    }

    @Override
    public void favMealObjectTransfer(Meal meal) {
        FavouriteFragmentDirections.ActionFavouriteFragmentToFavouriteMealDetailsFragment action =
            FavouriteFragmentDirections.actionFavouriteFragmentToFavouriteMealDetailsFragment(meal);
                Navigation.findNavController(view).navigate(action);


    }

    @Override
    public void onFavouriteMealSuccess(List<Meal> favouriteMealsList) {
        myFavouriteAdapter.setList(favouriteMealsList);
    }

    @Override
    public void onFavouriteMealFailure(String error) {
        Snackbar.make(constraintLayout,error,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onFavMealDeletionSuccess(String success) {
        Snackbar.make(constraintLayout,success,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onFavMealDeletionFailure(String err) {
        Snackbar.make(constraintLayout,err,Snackbar.LENGTH_SHORT).show();
    }
}