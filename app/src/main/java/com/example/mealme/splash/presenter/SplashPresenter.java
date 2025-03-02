package com.example.mealme.splash.presenter;

import android.os.Handler;

import com.example.mealme.splash.view.SignedInListener;
import com.google.firebase.auth.FirebaseAuth;

public class SplashPresenter {

    SignedInListener signedInListener;
    FirebaseAuth firebaseAuth;

    public SplashPresenter(SignedInListener signedInListener) {
        this.signedInListener = signedInListener;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public void SplashAnimeAction(){

      Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (firebaseAuth.getCurrentUser()!= null){
                    signedInListener.transitionSignedIn();
                }else{
                    signedInListener.transitionNotSignedIn();
                }
            }
        };
        handler.postDelayed(runnable,3200);
    }
}
