package com.example.mealme.home.view;

import android.content.Context;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mealme.common.onMealClickListener;
import com.example.mealme.home.presenter.HomePresenter;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.R;
import com.example.mealme.home.model.RandomMealPojo;
import com.example.mealme.home.model.HomeMealsPojo;
import com.example.mealme.model.repo.Repository;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class HomeFragment extends Fragment implements HomeMealViewer, RandomMealViewer, onMealClickListener {

    private ImageView randomMealPhoto;
    private TextView randomMealName,randomMealDesc;
    private RecyclerView recyclerView;
    private HomePresenter homePresenter;
    private MyHomeMealsAdapter myHomeMealsAdapter;
    private View view;
    private ConstraintLayout constraintLayout;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).showBottomNav(true);
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        randomMealDesc = view.findViewById(R.id.randomMealDesc);
        randomMealName = view.findViewById(R.id.randomMealName);
        randomMealPhoto = view.findViewById(R.id.randomMealPhoto);
        constraintLayout = view.findViewById(R.id.constraintHome_layout);
        this.view = view;

        firebaseAuth = FirebaseAuth.getInstance();
        SharedPreferences sp = getActivity().getSharedPreferences("randomMealSp", Context.MODE_PRIVATE);

            homePresenter = setupPresenter();
            homePresenter.getHomeMeals();
            homePresenter.getEditorsPick(sp);

        if(firebaseAuth.getCurrentUser() != null) {
            homePresenter.syncFavouritesWithFirebase();
            homePresenter.syncLocalCalendarWithFirebase();
        }
    }

    private HomePresenter setupPresenter(){
        MealRemoteDataSource mealRemoteDataSource = new MealRemoteDataSource();
        MealLocalDataSource mealLocalDataSource = new MealLocalDataSource(requireActivity());
        Repository repo = Repository.getInstance(mealRemoteDataSource,mealLocalDataSource);
        return new HomePresenter(repo, this, this);
    }

    @Override
    public void showHomeMeal(List<HomeMealsPojo> listOfMeals) {
        myHomeMealsAdapter = new MyHomeMealsAdapter(requireActivity(), listOfMeals,this);
        recyclerView.setAdapter(myHomeMealsAdapter);
    }

    @Override
    public void onMealClicked(String idMeal) {
        HomeFragmentDirections.ActionHomeFragmentToMealFragment2 action
                = HomeFragmentDirections.actionHomeFragmentToMealFragment2(idMeal);
        Navigation.findNavController(view).navigate(action);

    }
    @Override
    public void showHomeMealErrorMsg(String err) {
        Snackbar.make(constraintLayout,err,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onRandomMealSuccess(List<RandomMealPojo> listOfMeals) {
        Glide.with(requireActivity()).load(listOfMeals.get(0).getStrMealThumb()).into(randomMealPhoto);
        randomMealName.setText(listOfMeals.get(0).getStrMeal());
        randomMealDesc.setText(listOfMeals.get(0).getStrInstructions());
        randomMealPhoto.setOnClickListener(v->{
            HomeFragmentDirections.ActionHomeFragmentToMealFragment2
                   action = HomeFragmentDirections.actionHomeFragmentToMealFragment2(listOfMeals.get(0).getIdMeal());
            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    public void onRandomMealError(String err) {
        Snackbar.make(constraintLayout,err,Snackbar.LENGTH_SHORT).show();
    }

}