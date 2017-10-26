package com.berylsystems.buzz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.UserResponse.UserApiResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;

public class NewPasswordActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.confirm_password)
    EditText mConfirmPassword;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
    }
    public void submit(View v) {
        Boolean isConnected = ConnectivityReceiver.isConnected();
        boolean cancel = false;
        View focusView = null;


        if (mPassword.getText().toString().matches(mConfirmPassword.getText().toString())) {
            if (mPassword.getText().toString().length() > 6 && mConfirmPassword.getText().toString().length() > 6) {
                appUser.password = mPassword.getText().toString();
                LocalRepositories.saveAppUser(this, appUser);

                if (isConnected) {
                    mProgressDialog = new ProgressDialog(NewPasswordActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(this, appUser);
                    ApiCallsService.action(this, Cv.ACTION_NEW_PASSWORD);
                } else {
                    snackbar = Snackbar
                            .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    if(isConnected){
                                        snackbar.dismiss();
                                    }
                                }
                            });
                    snackbar.show();
                }


            } else {
                Toast.makeText(getApplicationContext(), "Password length should be more than 6 digits", Toast.LENGTH_LONG).show();
            }
        }
        else {
            mConfirmPassword.setError(getString(R.string.err_password));
            cancel = true;
            focusView = mConfirmPassword;
        }
    }
    @Override
    protected int layoutId() {
        return R.layout.activity_new_password;
    }

    @Subscribe
    public void newPassword(UserApiResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200) {
            Preferences.getInstance(getApplicationContext()).setLogin(true);
            Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
            startActivity(intent);
        }
        else{
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Subscribe
    public void timout(String msg){
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }

}