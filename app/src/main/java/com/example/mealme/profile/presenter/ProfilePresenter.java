package com.example.mealme.profile.presenter;

import androidx.navigation.NavOptions;

import com.example.mealme.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfilePresenter {

    FirebaseAuth firebaseAuth;

    public ProfilePresenter() {
        firebaseAuth = FirebaseAuth.getInstance();
    }
    public void onSignOutAction(){
        firebaseAuth.signOut();
    }
}
