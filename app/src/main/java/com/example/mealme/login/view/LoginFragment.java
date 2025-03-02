package com.example.mealme.login.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mealme.login.presenter.LoginHandler;
import com.example.mealme.login.presenter.LoginPresenter;
import com.example.mealme.main.view.MainActivity;
import com.example.mealme.R;
import com.example.mealme.model.local.MealLocalDataSource;
import com.example.mealme.model.remote.MealRemoteDataSource;
import com.example.mealme.model.repo.Repository;
import com.example.mealme.utils.GoogleLogin;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment implements LoginHandler {

    private EditText emailLogin,passwordLogin;
    private Button loginBtn;
    private TextView txtSignUp;
    private Button guestBtn;
    View view;
    private LoginPresenter loginPresenter;
    ConstraintLayout constraintLogin;
    FirebaseAuth firebaseAuth;
    private Button googleBtn;
    GoogleLogin googleLogin;

    public LoginFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).showBottomNav(false);
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailLogin = view.findViewById(R.id.emailLogin);
        passwordLogin = view.findViewById(R.id.passwordLogin);
        loginBtn = view.findViewById(R.id.loginBtn);
        txtSignUp = view.findViewById(R.id.txtSignUp);
        constraintLogin = view.findViewById(R.id.loginLayout);
        guestBtn = view.findViewById(R.id.guestBtn);
        googleBtn = view.findViewById(R.id.google_btn);
        this.view = view;

        firebaseAuth = FirebaseAuth.getInstance();
        loginPresenter = setUpPresenter();
         txtSignUp.setOnClickListener(v->{
          Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
        });

        guestBtn.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
        });

        loginBtn.setOnClickListener(v->{
            loginPresenter.loginAction(emailLogin.getText().toString(),passwordLogin.getText().toString());
        });

        googleBtn.setOnClickListener(v->{
            loginPresenter.loginWithGoogle();
        });


    }


    public LoginPresenter setUpPresenter(){
        MealLocalDataSource mealLocalDataSource = new MealLocalDataSource(requireActivity());
        MealRemoteDataSource mealRemoteDataSource = new MealRemoteDataSource();
        Repository repo = Repository.getInstance(mealRemoteDataSource,mealLocalDataSource);
        return new LoginPresenter(this,requireActivity(),new GoogleLogin(this,this),repo);
    }
    @Override
    public void onLoginFailed(String msg) {
        Snackbar.make(constraintLogin,msg,Snackbar.LENGTH_SHORT).show();
    }
    @Override
    public void onLoginSuccess(String msg) {
        Snackbar.make(constraintLogin,msg,Snackbar.LENGTH_SHORT).show();
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
    }
    @Override
    public void onEditEmailError(String err) {
        emailLogin.setError(err);
    }
    @Override
    public void onEditEmailFormatError(String err) {
        emailLogin.setError(err);
    }
    @Override
    public void onEditPasswordError(String err) {
        passwordLogin.setError(err);
    }
    @Override
    public void onSignInWithGoogleSuccess(String msg) {
        Snackbar.make(constraintLogin,msg,Snackbar.LENGTH_SHORT).show();
        Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
    }
    @Override
    public void onSignInWithGoogleFailure(String err) {
        Snackbar.make(constraintLogin,err,Snackbar.LENGTH_SHORT).show();
    }
}