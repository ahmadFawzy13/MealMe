package com.example.mealme.signup.view;

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

import com.example.mealme.main.view.MainActivity;
import com.example.mealme.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {

    EditText usernameSignUp,emailSignUp,passwordSignUp,confirmPasswordSignUp;
    Button signUpBtn;

    FirebaseAuth firebaseAuth;

    ConstraintLayout constraintSignUp;

    View view;

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
        firebaseAuth = FirebaseAuth.getInstance();
        this.view = view;
        signUpBtn.setOnClickListener(v->createAccount());

    }

    private void createAccount(){

        String username = usernameSignUp.getText().toString();
        String email = emailSignUp.getText().toString();
        String password = passwordSignUp.getText().toString();
        String confirmPassword = confirmPasswordSignUp.getText().toString();
        boolean isValid = validateCredentials(username,email,password,confirmPassword);
        if(!isValid){
            return;
        }
        createFirebaseAccount(username,email,password);
    }

    boolean validateCredentials(String username, String email, String password, String confirmPassword){

        if(username.isEmpty()){
            usernameSignUp.setError("Please Insert a Username");
            return false;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailSignUp.setError("Invalid Email");
            return false;
        }
        if(password.length() < 6){
            passwordSignUp.setError("Password Too Short");
            return false;
        }
        if(!password.equals(confirmPassword)){
            confirmPasswordSignUp.setError("Password Doesn't Match");
            return false;
        }
        return true;
    }

    private void createFirebaseAccount(String username, String email, String password) {

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        snackBar(task.isSuccessful());
                        Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_homeFragment);
                        //or making email confirmation (de el sheyaka kolaha)
                    }
                });
    }

    private void snackBar(boolean created){
        String snackText;
        if(created) {
            snackText = "Account Created Successfully";
        } else{
            snackText = "Failed to Create Account";
        }
        Snackbar.make(constraintSignUp,snackText,Snackbar.LENGTH_SHORT).show();
    }
}