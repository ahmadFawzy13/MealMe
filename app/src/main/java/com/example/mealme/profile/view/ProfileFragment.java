package com.example.mealme.profile.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mealme.R;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.model.repo.Repository;
import com.example.mealme.profile.presenter.ProfilePresenter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    Button wklyPlnBtn;
    Button fvrtBtn;
    Button signOutBtn;
    ProfilePresenter profilePresenter;

    public ProfileFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         ((MainActivity) requireActivity()).showBottomNav(true);
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        wklyPlnBtn = view.findViewById(R.id.wklyPlnBtn);
        fvrtBtn = view.findViewById(R.id.fvrtBtn);
        signOutBtn = view.findViewById(R.id.signOutBtn);
        profilePresenter = setUpPresenter();

        signOutBtn.setOnClickListener(v -> {
            new MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Sign Out")
                    .setMessage("Are you sure you want to sign out?")
                    .setPositiveButton("Sign Out", (dialog, which) -> {

                        profilePresenter.onSignOutAction();
                        NavOptions navOptions = new NavOptions.Builder()
                                .setPopUpTo(R.id.nav_graph, true)
                                .build();
                        Navigation.findNavController(v).navigate(R.id.action_profileFragment_to_loginFragment, null, navOptions);
                        Snackbar.make(view, "Signed out", Snackbar.LENGTH_SHORT).show();

                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                    })
                    .show();
        });
        
        fvrtBtn.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_favouriteFragment);
        });
        wklyPlnBtn.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_calendarFragment);
        });
    }
    private ProfilePresenter setUpPresenter(){
        MealLocalDataSource mealLocalDataSource = new MealLocalDataSource(requireActivity());
        MealRemoteDataSource mealRemoteDataSource = new MealRemoteDataSource();
        Repository repository = Repository.getInstance(mealRemoteDataSource,mealLocalDataSource);
        return new ProfilePresenter(repository);
    }
}