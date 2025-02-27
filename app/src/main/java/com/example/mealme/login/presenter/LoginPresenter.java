package com.example.mealme.login.presenter;

import static androidx.activity.result.ActivityResultCallerKt.registerForActivityResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Patterns;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.example.mealme.R;
import com.example.mealme.utils.GoogleLogin;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPresenter {
    private final FirebaseAuth firebaseAuth;
    private final LoginHandler loginHandler;
    private final Context context;
    GoogleLogin googleLogin;

    public LoginPresenter(LoginHandler loginHandler, Context context, GoogleLogin googleLogin) {
        firebaseAuth = FirebaseAuth.getInstance();
        this.loginHandler = loginHandler;
        this.context = context;
        this.googleLogin = googleLogin;
    }

    public void loginAction(String email, String password ) {
        boolean isValid = validateLogin(email,password);
        if(!isValid){
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.i("TAG", "onComplete: " + firebaseAuth.getCurrentUser());
                           loginHandler.onLoginSuccess("Login Successful");
                        }else{
                            Log.i("TAG", "onComplete: " + firebaseAuth.getCurrentUser());
                           loginHandler.onLoginFailed("Login Failed");
                        }
                    }
                });
    }
    private boolean validateLogin(String email, String password){

        if(email.isEmpty()){
            loginHandler.onEditEmailError("Please Enter User Email");
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginHandler.onEditEmailFormatError("Invalid Email");
            return false;
        }

        if(password.isEmpty()){
            loginHandler.onEditPasswordError("Please Enter User Password");
            return false;
        }
        return true;
    }
    public void loginWithGoogle(){
        googleLogin.signIn();
    }
}



