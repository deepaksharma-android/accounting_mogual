package com.lkintechnology.mBilling.activities.app;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.CompanyListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.version.VersionResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

import timber.log.Timber;


public class SplashActivity extends Activity {
    long Delay = 2000;
    AppUser appUser;
    int versionCode = -1;
    String versionName = "";


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
                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_VERSION);
                   /* if (Preferences.getInstance(getApplicationContext()).getLogin() == true) {
                        Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);/*//***Change Here***
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);/*//***Change Here***
                        startActivity(intent);
                        finish();
                    }*/

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
                    Manifest.permission.READ_CONTACTS,

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
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_VERSION);
                           /* if (Preferences.getInstance(getApplicationContext()).getLogin() == true) {
                                Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);/*//***Change Here***
                                startActivity(intent);
                                finish();

                            }
                            else {
                                Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);/*//***Change Here***
                                startActivity(intent);
                                finish();
                            }*/


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

    @Subscribe
    public void version(VersionResponse versionResponse) {
        if (versionResponse.getStatus() == 200) {
            try {
                PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                versionName = packageInfo.versionName;
                versionCode = packageInfo.versionCode;
                Timber.i("VERSION NAME"+versionName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            if (Double.parseDouble(versionName)!=(Double.parseDouble(versionResponse.getVersion_number()))) {
                android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(SplashActivity.this);
                alert.setCancelable(false);
                alert.setMessage("A new version of m-Billing is available.Please update to version " + versionResponse.getVersion_number() + " now.");
                alert.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
                        i.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.berylsystems.buzz"));
                        startActivity(i);
                    }
                });

                alert.show();
            } else {
                if (Preferences.getInstance(getApplicationContext()).getLogin() == true) {
                    Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }
                else {
                    Intent intent = new Intent(getApplicationContext(), HomePageActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

        }
        else{
            Toast.makeText(getApplicationContext(), versionResponse.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void timeout(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }



}

