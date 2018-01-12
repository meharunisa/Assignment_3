package com.example.madamkinza.assignment_3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

    public class ReceiverAirplaneMode extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {

            boolean state = intent.getBooleanExtra("state", false);
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);


        }




}


