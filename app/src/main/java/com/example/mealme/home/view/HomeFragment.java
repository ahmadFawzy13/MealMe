package com.example.mealme.home.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.mealme.IdDelivery;
import com.example.mealme.home.model.HomeMealViewer;
import com.example.mealme.home.model.RandomMealViewer;
import com.example.mealme.home.presenter.HomePresenter;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.R;
import com.example.mealme.home.model.RandomMealPojo;
import com.example.mealme.home.model.HomeMealsPojo;
import com.example.mealme.model.repo.Repository;

import java.util.List;

public class HomeFragment extends Fragment implements HomeMealViewer, RandomMealViewer, IdDelivery {

    ImageView randomMealPhoto;
    TextView randomMealName;
    TextView randomMealDesc;
    RecyclerView recyclerView;
    HomePresenter homePresenter;
    MyHomeMealsAdapter myHomeMealsAdapter;
    View view;

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
        this.view = view;

        homePresenter = setupPresenter();
        homePresenter.getHomeMeals();
        homePresenter.getRandomMeal();

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
    public void showHomeMealErrorMsg(String err) {
        Toast.makeText(requireActivity(), err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRandomMeal(List<RandomMealPojo> listOfMeals) {
        Glide.with(requireActivity()).load(listOfMeals.get(0).getStrMealThumb()).into(randomMealPhoto);
        randomMealName.setText(listOfMeals.get(0).getStrMeal());
        randomMealDesc.setText(listOfMeals.get(0).getStrInstructions());
        randomMealPhoto.setOnClickListener(v->{
            com.example.mealme.home.view.HomeFragmentDirections.ActionHomeFragmentToMealFragment2
                   actionHomeFragmentToMealFragment2 = HomeFragmentDirections.actionHomeFragmentToMealFragment2(listOfMeals.get(0).getIdMeal());
            Navigation.findNavController(view).navigate(actionHomeFragmentToMealFragment2);
        });
    }

    @Override
    public void showRandomMealErrorMsg(String err) {
        Toast.makeText(requireActivity(), err, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void idTransfer(String id) {
        com.example.mealme.home.view.HomeFragmentDirections.ActionHomeFragmentToMealFragment2 actionHomeFragmentToMealFragment2
                = HomeFragmentDirections.actionHomeFragmentToMealFragment2(id);
        Navigation.findNavController(view).navigate(actionHomeFragmentToMealFragment2);

    }
}