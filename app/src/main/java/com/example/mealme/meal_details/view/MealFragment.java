package com.example.mealme.meal_details.view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mealme.R;
import com.example.mealme.Reflection;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.meal_details.model.Meal;
import com.example.mealme.meal_details.model.MealDetailViewer;
import com.example.mealme.meal_details.presenter.MealPresenter;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.model.repo.Repository;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MealFragment extends Fragment implements MealDetailViewer, Reflection {


    MealPresenter mealPresenter;
    MealAdapter mealAdapter;
    RecyclerView recyclerView;
    private Button calendarBtn;
    private Button saveFavBtn;
    private Calendar calendar;
    int year;
    int month;
    int day;
    String receivedId;

    ImageView mealDetailsImg;
    TextView detailsMealName;

    TextView mealType;
    TextView detailsCountryName;

    TextView mealInstructions;

    private YouTubePlayerView youTubePlayerView;

    public MealFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).showBottomNav(true);
        return inflater.inflate(R.layout.fragment_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);

        calendarBtn = view.findViewById(R.id.calendarBtn);
        saveFavBtn = view.findViewById(R.id.saveFavBtn);
        mealDetailsImg = view.findViewById(R.id.mealDetailsImg);
        detailsMealName = view.findViewById(R.id.detailsMealName);
        mealType = view.findViewById(R.id.mealType);
        detailsCountryName = view.findViewById(R.id.detailsCountryName);
        mealInstructions = view.findViewById(R.id.mealInstructions);

        recyclerView = view.findViewById(R.id.recycler_meal_page);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(requireActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        mealAdapter = new MealAdapter(requireActivity(),new ArrayList<>(),new ArrayList<>());
        recyclerView.setAdapter(mealAdapter);

        youTubePlayerView = view.findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youTubePlayerView);


        calendarBtn.setOnClickListener(v->{
            datePicker();
        });

        receivedId = MealFragmentArgs.fromBundle(getArguments()).getIdMeal();
        mealPresenter = setUpPresenter();
        mealPresenter.getMealDetails(receivedId);
    }

    private MealPresenter setUpPresenter() {
        MealRemoteDataSource mealRemoteDataSource = new MealRemoteDataSource();
        MealLocalDataSource mealLocalDataSource = new MealLocalDataSource(requireActivity());
        Repository repo = Repository.getInstance(mealRemoteDataSource,mealLocalDataSource);
        return new MealPresenter(repo,this,this);
    }

    @Override
    public void showMealDetails(List<Meal> listOfMeals) {

        Glide.with(requireActivity()).load(listOfMeals.get(0).getStrMealThumb()).into(mealDetailsImg);
        detailsMealName.setText(listOfMeals.get(0).getStrMeal());
        mealType.setText(listOfMeals.get(0).getStrCategory());
        detailsCountryName.setText(listOfMeals.get(0).getStrArea());
        mealInstructions.setText(listOfMeals.get(0).getStrInstructions());
        String youtubeId = youtubeCut(listOfMeals.get(0).getStrYoutube());
        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                super.onReady(youTubePlayer);
                youTubePlayer.cueVideo(youtubeId,0);
            }
        });


    }

    @Override
    public void showMealDetailsErrorMsg(String err) {
        Toast.makeText(requireActivity(), err, Toast.LENGTH_SHORT).show();
    }

    private String youtubeCut(String url){
        return url.substring(url.indexOf("v=") + 2);
    }

    private void datePicker(){
        DatePickerDialog dialog = new DatePickerDialog(requireActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                Toast.makeText(requireActivity(), String.valueOf(year)+"/"+String.valueOf(month)+"/"+String.valueOf(dayOfMonth), Toast.LENGTH_SHORT).show();
                
            }   
        }, year, month, day);
        dialog.getDatePicker().setMinDate(System.currentTimeMillis());
        dialog.show();
    }

    @Override
    public void reflectedLists(List<String> ingredientsList, List<String> measuresList) {
        mealAdapter.setList(ingredientsList,measuresList);
    }
}