package com.example.mealme.main.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mealme.R;
import com.example.mealme.main.presenter.MainActivityPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NetworkListener {
    private BottomNavigationView bottomNav;
    private FirebaseAuth firebaseAuth;
    private NavController navController;
    private TextView noConnectionTxt;
    private ImageView noConnectionImg;
    private MainActivityPresenter mainActivityPresenter;
    private TextView offlineTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_nav);
        offlineTxt = findViewById(R.id.offlineTxt);
        /*FirebaseApp.initializeApp(this);*/
        firebaseAuth = FirebaseAuth.getInstance();

        mainActivityPresenter = new MainActivityPresenter(this,this);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);
        navController=navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNav, navController);

        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (firebaseAuth.getCurrentUser() == null) {
                if ((itemId == R.id.profileFragment || itemId == R.id.calendarFragment || itemId == R.id.favouriteFragment)) {
                    showSignUpDialog();
                    return false;
                } else if (itemId == R.id.homeFragment) {
                    navController.navigate(R.id.action_global_homeFragment2);
                    return true;
                } else if (itemId == R.id.searchFragment) {
                    navController.navigate(R.id.action_global_searchFragment2);
                    return true;
                }
            } else {
                if (itemId == R.id.homeFragment) {
                    navController.navigate(R.id.action_global_homeFragment2);
                }else if(itemId == R.id.searchFragment){
                    navController.navigate(R.id.action_global_searchFragment2);
                }else if(itemId == R.id.favouriteFragment){
                    navController.navigate(R.id.action_global_favouriteFragment);
                }else if(itemId == R.id.calendarFragment){
                    navController.navigate(R.id.action_global_calendarFragment);
                }else if(itemId == R.id.profileFragment){
                    navController.navigate(R.id.action_global_profileFragment);
                }
               return true;
            }
            return true;
        });

        mainActivityPresenter.monitorConnection();
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    public void showBottomNav(boolean show) {
        bottomNav.setVisibility(show ? View.VISIBLE : View.GONE);
    }


    public void showSignUpDialog(){
        new MaterialAlertDialogBuilder(this)
                .setTitle("Login")
                .setMessage("Please Login To Access This Feature")
                .setPositiveButton("Login", (dialog, which) -> {
                    navController.navigate(R.id.action_global_loginFragment);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {

                })
                .show();
    }

    @Override
    public void onNetworkAvailable() {
        runOnUiThread(()->{
            offlineTxt.setVisibility(View.GONE);
        });
    }

    @Override
    public void onNetworkLost() {
        runOnUiThread(()->{
            offlineTxt.setVisibility(View.VISIBLE);
        });
    }

}