package com.example.mealme.splash.presenter;

import android.os.Handler;

import androidx.navigation.Navigation;

import com.example.mealme.R;
import com.example.mealme.SplashAnimHandler;

public class SplashPresenter {

    SplashAnimHandler splashAnimHandler;

    public SplashPresenter(SplashAnimHandler splashAnimHandler) {
        this.splashAnimHandler = splashAnimHandler;
    }

    public void SplashAnimeAction(){

      Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                splashAnimHandler.animHandler();
            }
        };
        handler.postDelayed(runnable,3200);
    }
}
