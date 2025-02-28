package com.example.mealme.signup.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.mealme.main.view.MainActivity;
import com.example.mealme.R;
import com.example.mealme.signup.presenter.SignUpPresenter;
import com.google.android.material.snackbar.Snackbar;

public class SignUpFragment extends Fragment implements SignUpHandler {

    private EditText usernameSignUp,emailSignUp,passwordSignUp,confirmPasswordSignUp;
    private Button signUpBtn;
    private ConstraintLayout constraintSignUp;
    private View view;
    private SignUpPresenter signUpPresenter;

    public SignUpFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ((MainActivity) requireActivity()).showBottomNav(false);
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usernameSignUp = view.findViewById(R.id.usernameSignUp);
        emailSignUp = view.findViewById(R.id.emailSignUp);
        passwordSignUp = view.findViewById(R.id.passwordSignUp);
        signUpBtn = view.findViewById(R.id.loginBtn);
        confirmPasswordSignUp = view.findViewById(R.id.confirmPasswordSignUp);
        constraintSignUp = view.findViewById(R.id.signUpLayout);
        signUpPresenter = new SignUpPresenter(requireActivity(),this);
        this.view = view;



        signUpBtn.setOnClickListener(v->{
            String username = usernameSignUp.getText().toString().trim();
            String email = emailSignUp.getText().toString().trim();
            String password = passwordSignUp.getText().toString().trim();
            String confirmPassword = confirmPasswordSignUp.getText().toString().trim();
            signUpPresenter.createAccount(username,email,password,confirmPassword);
        });
    }
    @Override
    public void onUsernameError(String err) {
        usernameSignUp.setError(err);
    }

    @Override
    public void onEmailError(String err) {
        emailSignUp.setError(err);
    }

    @Override
    public void onPasswordLengthError(String err) {
        passwordSignUp.setError(err);
    }

    @Override
    public void onConfirmPasswordMismatch(String err) {
        confirmPasswordSignUp.setError(err);
    }

    @Override
    public void onSignUpSuccess(String msg) {
        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_homeFragment);
        Snackbar.make(constraintSignUp,msg,Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onSignUpFailure(String err) {
        Snackbar.make(constraintSignUp,err,Snackbar.LENGTH_SHORT).show();
    }
}