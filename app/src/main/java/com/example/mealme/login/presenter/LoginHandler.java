package com.example.mealme.login.presenter;

public interface LoginHandler {
    void onLoginFailed(String msg);
    void onLoginSuccess(String msg);
    void onEditEmailError(String err);
    void onEditEmailFormatError(String err);
    void onEditPasswordError(String err);
    void onSignInWithGoogleSuccess(String msg);
    void onSignInWithGoogleFailure(String err);
}
