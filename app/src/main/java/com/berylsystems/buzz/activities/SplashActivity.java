package com.berylsystems.buzz.activities;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.Timer;
import java.util.TimerTask;



public class SplashActivity extends Activity {
    long Delay = 2000;
    AppUser appUser;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appUser = LocalRepositories.getAppUser(this);

        if (null == appUser) {
            appUser = new AppUser();
            LocalRepositories.saveAppUser(this, appUser);
        }

        if (appUser != null) {
            Log.e("appUser", appUser.toString());
        }

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion <= 22) {
            // Do something for lollipop and above versions
            Timer RunSplash = new Timer();
            TimerTask ShowSplash = new TimerTask() {
                @Override
                public void run() {
                    if (Preferences.getInstance(getApplicationContext()).getLogin() == true) {
                        Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }

                }

            };

            // Start the timer
            RunSplash.schedule(ShowSplash, Delay);
        } else {
            checkPermissions();
        }


    }


    private void checkPermissions() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA,
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_NETWORK_STATE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,

            }, Cv.PERMISSIONS_BUZZ_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case Cv.PERMISSIONS_BUZZ_REQUEST:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Timer RunSplash = new Timer();
                    TimerTask ShowSplash = new TimerTask() {
                        @Override
                        public void run() {
                            if (Preferences.getInstance(getApplicationContext()).getLogin() == true) {
                                Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                                startActivity(intent);
                            }
                            else {
                                startActivity(new Intent(getApplicationContext(),HomePageActivity.class));
                            }


                        }

                    };
                    // Start the timer
                    RunSplash.schedule(ShowSplash, Delay);


                } else {
                    AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(SplashActivity.this);
                    myAlertDialog.setTitle(getString(R.string.msg_perms_needed));
                    myAlertDialog.setPositiveButton(getString(R.string.btn_quit), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {
                           finish();
                        }
                    });

                    myAlertDialog.setNegativeButton(getString(R.string.btn_accept),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface arg0, int arg1) {
                                    checkPermissions();
                                }
                            });
                    myAlertDialog.show();

                }
                break;
        }
    }



}

