package com.example.madamkinza.assignment_3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {

    Switch airplaneonoff;
    EditText sharedPreferenceName;
    TextView batteryPercentage;
    private BroadcastReceiver mReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences sp = getSharedPreferences("mysettings", MODE_PRIVATE);
        sharedPreferenceName = (EditText) findViewById(R.id.etName);
        batteryPercentage = (TextView) findViewById(R.id.tvBatteryStatus);


        //Wifi On Off and Notification
        final Switch switchNetwork = (Switch) findViewById(R.id.wifiOnOff);


        final Boolean state = sp.getBoolean("state", false);
        switchNetwork.setChecked(state);


        IntentFilter intentFilter = new IntentFilter(NetworkReceiver.NETWORK_AVAILABLE_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isNetworkAvailable = intent.getBooleanExtra(IS_NETWORK_AVAILABLE, false);
                if (isNetworkAvailable) {
                    NotificationManager mNotificationManager =

                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    Intent notifyintent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                    notifyintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, notifyintent, 0);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(MainActivity.this).
                                    setSmallIcon(R.drawable.icon).
                                    setContentIntent(pendingIntent).
                                    setContentTitle("Wifi Connection").
                                    setContentText("Wifi is turned On");

                    Notification notification = mBuilder.build();
                    mNotificationManager.notify(0, notification);
                    Toast.makeText(context, "Network Status: " + isNetworkAvailable, Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putBoolean("state", isNetworkAvailable);
                    switchNetwork.setChecked(true);
                } else {
                    NotificationManager mNotificationManager =

                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    Intent notifyintent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                    notifyintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, notifyintent, 0);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(MainActivity.this).
                                    setSmallIcon(R.drawable.icon).
                                    setContentIntent(pendingIntent).
                                    setContentTitle("Wifi Connection").
                                    setContentText("Wifi is turned Off");

                    Notification notification = mBuilder.build();
                    mNotificationManager.notify(0, notification);
                    Toast.makeText(context, "Network Status: " + isNetworkAvailable, Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putBoolean("state", isNetworkAvailable);
                    switchNetwork.setChecked(false);
                }
                String networkState = isNetworkAvailable ? "Connected" : "Disconnected";
                // Toast.makeText(context, "Network Status: " + networkState, Toast.LENGTH_LONG).show();
            }
        }, intentFilter);


        switchNetwork.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferences.Editor editor = sp.edit();

                editor.putBoolean("state", b);

                editor.apply();
                if (b) {
                    WifiManager onwifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    onwifi.setWifiEnabled(true);
                } else {
                    WifiManager offwifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
                    offwifi.setWifiEnabled(false);
                }


            }
        });


        //AirPlane Mode On Off and Notification
        airplaneonoff = (Switch) findViewById(R.id.airplane);
        AirplaneModeReceiver receiver = new AirplaneModeReceiver();

        IntentFilter intentFilter2 = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);

        LocalBroadcastManager.getInstance(this).registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean airplanemode = intent.getBooleanExtra("state", false);
                if (airplanemode) {
                    NotificationManager mNotificationManager =

                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    Intent notifyintent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                    notifyintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 1, notifyintent, 0);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(MainActivity.this).
                                    setSmallIcon(R.drawable.icon).
                                    setContentIntent(pendingIntent).
                                    setContentTitle("AirPlane Connection").
                                    setContentText("Airplane is turned On");

                    Notification notification = mBuilder.build();
                    mNotificationManager.notify(1, notification);
                    airplaneonoff.setChecked(true);
                    Toast.makeText(getApplicationContext(), "AirPlane Mode is On", Toast.LENGTH_LONG).show();
                } else {
                    NotificationManager mNotificationManager =

                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    Intent notifyintent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                    notifyintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 1, notifyintent, 0);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(MainActivity.this).
                                    setSmallIcon(R.drawable.icon).
                                    setContentIntent(pendingIntent).
                                    setContentTitle("AirPlane Connection").
                                    setContentText("Airplane is turned Off");

                    Notification notification = mBuilder.build();
                    mNotificationManager.notify(1, notification);
                    airplaneonoff.setChecked(false);
                    Toast.makeText(getApplicationContext(), "AirPlane Mode is Off", Toast.LENGTH_LONG).show();
                }

            }
        }, intentFilter2);
        this.registerReceiver(receiver, intentFilter2);


        //Battery Status
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = getApplicationContext().registerReceiver(null, ifilter);


        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        String bateria = String.valueOf(status);

        int battery = Integer.parseInt(bateria);
        if (battery < 50) {
            batteryPercentage.setText("Battery is Low");
        } else {
            batteryPercentage.setText("Battery is OK");
        }

        Log.d("batteryChecked", bateria);
        //Toast.makeText(MainActivity.this, bateria, Toast.LENGTH_LONG).show();


        //Shared Preference Name save
        sharedPreferenceName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                SharedPreferences.Editor editor = sp.edit();
                editor = sp.edit();
                editor.putString("SP_NAMES", sharedPreferenceName.getText().toString());
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }


        });


    }

