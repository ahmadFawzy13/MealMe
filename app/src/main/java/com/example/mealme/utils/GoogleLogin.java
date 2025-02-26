package com.example.mealme.utils;

import android.app.Activity;
import android.content.Intent;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.mealme.R;
import com.example.mealme.login.presenter.LoginHandler;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class GoogleLogin {

    private final FirebaseAuth firebaseAuth;
    private final GoogleSignInClient googleSignInClient;
    private final Fragment fragment;
    private LoginHandler loginHandler;
    private ActivityResultLauncher<Intent> launcher;

    public GoogleLogin(Fragment fragment, LoginHandler loginHandler) {
        firebaseAuth = FirebaseAuth.getInstance();
        this.fragment = fragment;
        this.loginHandler = loginHandler;

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(fragment.getString(R.string.client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(fragment.requireContext(), googleSignInOptions);
        launcher = fragment.registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == fragment.getActivity().RESULT_OK && result.getData() != null) {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                try {
                    GoogleSignInAccount account = task.getResult(ApiException.class);
                    firebaseAuthWithGoogle(account.getIdToken());
                } catch (ApiException e) {
                    loginHandler.onSignInWithGoogleFailure(e.getMessage());
                }
            }
        });

    }

    public void signIn() {
        googleSignInClient.signOut().addOnCompleteListener(task -> {
            Intent signInIntent = googleSignInClient.getSignInIntent();
            launcher.launch(signInIntent);
        });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener((Activity) fragment.requireContext(), task -> {
                    if (task.isSuccessful()) {
                        loginHandler.onSignInWithGoogleSuccess("Signed In");
                    } else {
                        loginHandler.onSignInWithGoogleFailure("Can't Sign In Right Now");
                    }
                });
    }
}