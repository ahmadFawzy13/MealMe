package com.example.mealme.splash.presenter;

import android.os.Handler;

import com.example.mealme.splash.view.SplashAnimHandler;
import com.google.firebase.auth.FirebaseAuth;

public class SplashPresenter {

    SplashAnimHandler splashAnimHandler;
    FirebaseAuth firebaseAuth;

    public SplashPresenter(SplashAnimHandler splashAnimHandler) {
        this.splashAnimHandler = splashAnimHandler;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void SplashAnimeAction(){

      Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (firebaseAuth.getCurrentUser()!= null){
                    splashAnimHandler.transitionSignedIn();
                }else{
                    splashAnimHandler.transitionNotSignedIn();
                }
            }
        };
        handler.postDelayed(runnable,3200);
    }
}
