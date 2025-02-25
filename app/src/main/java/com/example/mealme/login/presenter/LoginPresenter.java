package com.example.mealme.login.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPresenter {
    private final FirebaseAuth firebaseAuth;
    private final LoginHandler loginHandler;
    Context context;

    public LoginPresenter(Context context, LoginHandler loginHandler) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.loginHandler = loginHandler;
        this.context = context;
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
                           loginHandler.onLoginSuccess("Login Successful");
                        }else{
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
}
