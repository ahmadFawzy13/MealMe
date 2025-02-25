package com.example.mealme;

public interface SignUpHandler {

    void onUsernameError(String err);
    void onEmailError(String err);
    void onPasswordLengthError(String err);
    void onConfirmPasswordMismatch(String err);
    void onSignUpSuccess(String msg);
    void onSignUpFailure(String err);

}
