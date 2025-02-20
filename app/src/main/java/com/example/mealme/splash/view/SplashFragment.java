package com.example.mealme.splash.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.R;


public class SplashFragment extends Fragment {

    private Handler handler;
    private Runnable runnable;

    private LottieAnimationView splashScreen;


    public SplashFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).showBottomNav(false);
        return inflater.inflate(R.layout.fragment_splash, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        splashScreen = view.findViewById(R.id.splashScreen);
        splashScreen.setSpeed(1.8f);
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {

                Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment);

            }
        };
        handler.postDelayed(runnable,3200);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }
}