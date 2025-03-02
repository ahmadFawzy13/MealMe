package com.example.mealme.profile.presenter;

import androidx.navigation.NavOptions;
import com.example.mealme.R;
import com.example.mealme.model.repo.Repository;
import com.google.firebase.auth.FirebaseAuth;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ProfilePresenter {
    private FirebaseAuth firebaseAuth;
    private Repository repo;

    public ProfilePresenter(Repository repo) {
        firebaseAuth = FirebaseAuth.getInstance();
        this.repo = repo;
    }
    public void onSignOutAction(){
        repo.deleteCalendarTable()
            .subscribeOn(Schedulers.io())
            .subscribe();

        repo.deleteMealsTable()
            .subscribeOn(Schedulers.io())
            .subscribe();

            firebaseAuth.signOut();

    }
}
