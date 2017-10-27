package com.berylsystems.buzz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.user.UserApiResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;
import com.berylsystems.buzz.utils.Validation;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.mobile)
    EditText mMobileText;
    @Bind(R.id.password)
    EditText mPassword;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appUser=LocalRepositories.getAppUser(this);
        ButterKnife.bind(this);

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    public void login(View v){

        boolean cancel = false;
        View focusView = null;

        if (Validation.isPhoneFormatValid(mMobileText.getText().toString())) {

            if (!Validation.isPwdFormatValid(mPassword.getText().toString())) {

                mPassword.setError(getString(R.string.err_invalid_pwd_format));
                cancel = true;
                focusView = mPassword;
            }
        } else {
            mMobileText.setError(getString(R.string.err_invalid_mobile));
            cancel = true;
            focusView = mMobileText;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            appUser.mobile = mMobileText.getText().toString();
            appUser.password = mPassword.getText().toString();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            logInRequest();
        }

    }
    private void logInRequest() {
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if(isConnected) {
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();

            ApiCallsService.action(this, Cv.ACTION_LOGIN);
        }
        else{
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
    }
    public void register(View v){
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }
    public void forgot(View v){
        startActivity(new Intent(getApplicationContext(),ForgotPasswordActivity.class));
    }

    @Subscribe
    public void loginResponse(UserApiResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.user_id = response.getUser().getData().getId();
            appUser.name = response.getUser().getData().getAttributes().getName();
            appUser.mobile=response.getUser().getData().getAttributes().getMobile();
            appUser.email = response.getUser().getData().getAttributes().getEmail();
            appUser.auth_token = response.getUser().getData().getAttributes().getAuth_token();
            LocalRepositories.saveAppUser(this, appUser);
            Preferences.getInstance(getApplicationContext()).setLogin(true);
            if(!response.getUser().getData().getAttributes().getActive()){
                Intent intent=new Intent(getApplicationContext(),VerificationActivity.class);
                intent.putExtra("fromLoginPage",true);
                intent.putExtra("mobile",response.getUser().getData().getAttributes().getMobile());
                startActivity(intent);

            }
            else{
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                startActivity(new Intent(getApplicationContext(), LandingPageActivity.class));
            }



        } else {
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