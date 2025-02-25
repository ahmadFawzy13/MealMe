package com.example.mealme.signup.presenter;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import com.example.mealme.R;
import com.example.mealme.SignUpHandler;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpPresenter {
    FirebaseAuth firebaseAuth;
    Context context;
    SignUpHandler signUpHandler;

    public SignUpPresenter(Context context, SignUpHandler signUpHandler) {
        firebaseAuth = FirebaseAuth.getInstance();
        this.context = context;
        this.signUpHandler = signUpHandler;
    }

    public void createAccount(String username, String email, String password, String confirmPassword){
        boolean isValid = validateCredentials(username,email,password,confirmPassword);
        if(!isValid){
            return;
        }
        createFirebaseAccount(email,password);
    }

    private boolean validateCredentials(String username, String email, String password, String confirmPassword){
        if(username.isEmpty()){
            signUpHandler.onUsernameError("Please Insert a Username");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            signUpHandler.onEmailError("Invalid Email");
            return false;
        }
        if(password.length() < 6){
            signUpHandler.onPasswordLengthError("Password Too Short");
            return false;
        }
        if(!password.equals(confirmPassword)){
            signUpHandler.onConfirmPasswordMismatch("Password Doesn't Match");
            return false;
        }
        return true;
    }

    private void createFirebaseAccount(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            signUpHandler.onSignUpSuccess("Sign Up Successful");
                        }else{
                            signUpHandler.onSignUpFailure("Sign Up Failed");
                        }
                    }
                });
    }
}
