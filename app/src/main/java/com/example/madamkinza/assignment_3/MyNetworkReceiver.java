package com.example.madamkinza.assignment_3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.LocalBroadcastManager;

public class MyNetworkReceiver extends BroadcastReceiver {
    public static final String NETWORK_AVAILABLE_ACTION = "NetworkAvailable";
    public static final String IS_NETWORK_AVAILABLE = "isNetworkAvailable";

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(NETWORK_AVAILABLE_ACTION);
        i.putExtra(IS_NETWORK_AVAILABLE, isConnected(context));
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);
    }

    private boolean isConnected(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnected();
        }
        return false;
    }
}