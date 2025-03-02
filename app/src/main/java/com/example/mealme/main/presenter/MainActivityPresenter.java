package com.example.mealme.main.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import androidx.annotation.NonNull;

import com.example.mealme.main.view.NetworkListener;

public class MainActivityPresenter {
    ConnectivityManager connectivityManager;
    NetworkListener networkListener;
    Context context;
    NetworkCapabilities networkCapabilities;

    public MainActivityPresenter(NetworkListener networkListener, Context context) {
        this.networkListener = networkListener;
        this.context = context;
        connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
    public void monitorConnection() {

        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                .build();
        ConnectivityManager.NetworkCallback networkCallback
                = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onLost(@NonNull Network network) {
                networkListener.onNetworkLost();
            }

            @Override
            public void onAvailable(@NonNull Network network) {
                networkListener.onNetworkAvailable();
            }
        };
        connectivityManager.registerNetworkCallback(networkRequest,networkCallback);
    }

    public boolean isNetworkConnectedOnStart() {
        networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
    }
}
