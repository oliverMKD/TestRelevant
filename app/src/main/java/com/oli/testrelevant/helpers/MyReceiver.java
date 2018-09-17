package com.oli.testrelevant.helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "NetworkChangeReceiver";
    private boolean isConnected = false;
    public AlertDialog.Builder builder;

    @Override
    public void onReceive(Context context, Intent intent) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("ARE YOU CONNECTED?");
        builder.setMessage(isNetworkAvailable(context));

        if (isConnected){

        }else {
            builder.show();
            Toast.makeText(context, ""+isNetworkAvailable(context), Toast.LENGTH_SHORT).show();
        }

    }
    public String isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo network = connectivity.getActiveNetworkInfo();
            if (network != null) {
                if (network.getState() == NetworkInfo.State.CONNECTED) {

                    if (!isConnected) {
                        isConnected = true;


                    }

                    return "Your internet connection is fine!";
                }
            }


        }

        isConnected = false;
        return "You are not connected";


    }
}
