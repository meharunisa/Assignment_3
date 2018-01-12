package com.example.madamkinza.assignment_3;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.LocaleList;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {
    private static  final String IS_BATTERY_LOW = "isBatteryLow";
    private static  final String BATTERY_LOW_ACTION = "BatteryLow";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");

        Intent i = new Intent(BATTERY_LOW_ACTION);

        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,-1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        float batteryPercentage = level/(float) scale;

        if (batteryPercentage <50){
            Toast.makeText(context, "Battery Low", Toast.LENGTH_LONG).show();
        }
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);
    }

