package com.example.mealme.signup.view;

public interface SignUpHandler {
    void onEmailError(String err);
    void onPasswordLengthError(String err);
    void onConfirmPasswordMismatch(String err);
    void onSignUpSuccess(String msg);
    void onSignUpFailure(String err);
}
