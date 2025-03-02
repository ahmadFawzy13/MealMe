package com.example.mealme.splash.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.R;
import com.example.mealme.splash.presenter.SplashPresenter;


public class SplashFragment extends Fragment implements SignedInListener {

    private LottieAnimationView splashScreen;
    View view;

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
        this.view = view;
        splashScreen = view.findViewById(R.id.splashScreen);
        splashScreen.setSpeed(1.8f);
        SplashPresenter splashPresenter = new SplashPresenter(this);
        splashPresenter.SplashAnimeAction();
    }

    @Override
    public void transitionNotSignedIn() {
        Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_loginFragment);
    }

    @Override
    public void transitionSignedIn() {
        Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_homeFragment);
    }
}