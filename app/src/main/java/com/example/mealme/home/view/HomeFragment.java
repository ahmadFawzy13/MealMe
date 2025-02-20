package com.example.mealme.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.model.remote.NetworkCallBack;
import com.example.mealme.R;
import com.example.mealme.model.remote.RandomMealNetworkCallBack;
import com.example.mealme.home.model.RandomMealPojo;
import com.example.mealme.home.model.HomeMealsPojo;

import java.util.List;

public class HomeFragment extends Fragment implements NetworkCallBack, RandomMealNetworkCallBack {


    RecyclerView recyclerView;

    ImageView randomMealPhoto;
    TextView randomMealName;
    TextView randomMealDesc;

    MyHomeMealsAdapter myHomeMealsAdapter;

    MealRemoteDataSource mealRemoteDataSource;

    MealRemoteDataSource randomMealClient;

    private static final String TAG = "HomeFragment";

    public HomeFragment() {
    }

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


        mealRemoteDataSource = new MealRemoteDataSource();
        mealRemoteDataSource.makeNetWorkCall(this);

        randomMealClient = new MealRemoteDataSource();
        randomMealClient.makeRandomNetworkCall(this);

    }
    @Override
    public void onSuccessResult(List<HomeMealsPojo> listOfMeals) {

        myHomeMealsAdapter = new MyHomeMealsAdapter(requireActivity(), listOfMeals);
        recyclerView.setAdapter(myHomeMealsAdapter);

    }

    @Override
    public void onFailedResult(String errorMessage) {
        Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRandomMealSuccessResult(List<RandomMealPojo> listOfMeals) {

        Glide.with(requireActivity()).load(listOfMeals.get(0).getStrMealThumb()).into(randomMealPhoto);
        randomMealName.setText(listOfMeals.get(0).getStrMeal());
        randomMealDesc.setText(listOfMeals.get(0).getStrInstructions());

    }

    @Override
    public void onRandomMealFailedResult(String errorMessage) {
        Toast.makeText(requireActivity(), errorMessage, Toast.LENGTH_SHORT).show();
    }
}