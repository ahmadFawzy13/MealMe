package com.example.mealme.main.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.mealme.R;
import com.example.mealme.home.view.HomeFragment;
import com.example.mealme.profile.view.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

   BottomNavigationView bottomNav;
    FirebaseAuth firebaseAuth;
    FragmentTransaction fragmentTransaction;

    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNav = findViewById(R.id.bottom_nav);
      /*  bottomNav.setOnItemSelectedListener(item -> {

            if(item.getItemId() == R.id.nav_home){
                replaceFragment(new HomeFragment());
                return true;
            }else if(item.getItemId() == R.id.nav_profile){
                replaceFragment(new ProfileFragment());
                return true;
            }else{
                Toast.makeText(this, "navBar", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/

    }


    @Override
    protected void onStart() {
        super.onStart();
        navController = Navigation.findNavController(this, R.id.fragmentContainerView);
        NavigationUI.setupWithNavController(bottomNav, navController);
    }

   /* private void replaceFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }*/

    public void showBottomNav(boolean show) {
        bottomNav.setVisibility(show ? View.VISIBLE : View.GONE);
    }

}