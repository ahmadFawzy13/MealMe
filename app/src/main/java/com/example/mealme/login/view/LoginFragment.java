package com.example.mealme.login.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mealme.main.view.MainActivity;
import com.example.mealme.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {

    private EditText emailLogin,passwordLogin;
    private Button loginBtn;
    private TextView txtSignUp;
    private Button guestBtn;
    View view;
    ConstraintLayout constraintLogin;
    FirebaseAuth firebaseAuth;
    private Button googleBtn;

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
        firebaseAuth = FirebaseAuth.getInstance();
        this.view = view;

        loginBtn.setOnClickListener(v->loginAction());

        txtSignUp.setOnClickListener(v->{
          Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_signUpFragment);
        });

        guestBtn.setOnClickListener(v->{
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
        });
    }

    private void loginAction() {
        String email = emailLogin.getText().toString();
        String password = passwordLogin.getText().toString();
        boolean isValid = validateLogin(email,password);
        if(!isValid){
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Snackbar.make(constraintLogin,"Login Successful",Snackbar.LENGTH_SHORT).show();
                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_homeFragment);
                            //code for navigation to home fragment and then removing the
                            //login fragment from backStack (the equivalent of finish())
                        }else{
                            Snackbar.make(constraintLogin,"Wrong Email or Password",Snackbar.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean validateLogin(String email, String password){

        if(email.isEmpty()){
            emailLogin.setError("Please Enter User Email");
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailLogin.setError("Invalid Email");
            return false;
        }

        if(password.isEmpty()){
            passwordLogin.setError("Please Enter User Password");
            return false;
        }
        return true;
    }
}