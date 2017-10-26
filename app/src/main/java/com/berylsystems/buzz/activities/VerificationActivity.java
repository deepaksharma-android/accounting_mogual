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
import android.widget.TextView;
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
import butterknife.ButterKnife;

public class VerificationActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.mobile_number)
    TextView mMobileNumber;
    @Bind(R.id.otp)
    EditText mOtp;
    @Bind(R.id.changeMobile)
    TextView mChangeMobileNumber;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    Boolean fromRegisterPage,fromLoginPage,fromForgotPasswordPage,fromProfilePage,fromUpdateMobileNumber;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        String mobile=getIntent().getExtras().getString("mobile");
        fromRegisterPage = getIntent().getExtras().getBoolean("fromRegisterPage");
        fromLoginPage = getIntent().getExtras().getBoolean("fromLoginPage");
        fromForgotPasswordPage = getIntent().getExtras().getBoolean("fromForgotPasswordPage");
        fromProfilePage = getIntent().getExtras().getBoolean("fromProfilePage");
        fromUpdateMobileNumber = getIntent().getExtras().getBoolean("fromUpdateMobileNumber");
        if(fromForgotPasswordPage==true){
            mChangeMobileNumber.setVisibility(View.GONE);
        }
        if(fromProfilePage==true){
            mChangeMobileNumber.setVisibility(View.GONE);
        }
        appUser = LocalRepositories.getAppUser(this);
        mMobileNumber.setText(mobile);
    }
    @Override
    protected int layoutId() {
        return R.layout.activity_verification;
    }

    public void resend(View v){
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if(isConnected) {
            mProgressDialog = new ProgressDialog(VerificationActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(this, appUser);
            ApiCallsService.action(this, Cv.ACTION_RESEND_OTP);
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
    public void changeMobileNumber(View v){
        Intent intent=new Intent(getApplicationContext(),ChangeMobileActivity.class);
        intent.putExtra("mobile", mMobileNumber.getText().toString());
        startActivity(intent);
    }
    public void submit(View v){
        Boolean isConnected = ConnectivityReceiver.isConnected();
        boolean cancel = false;
        View focusView = null;



        if(!mOtp.getText().toString().equals("")){
            appUser.otp=mOtp.getText().toString();
            LocalRepositories.saveAppUser(this,appUser);
        }
        else {
            mOtp.setError(getString(R.string.err_otp));
            cancel = true;
            focusView = mOtp;
        }
        appUser.mobile = mMobileNumber.getText().toString();
        LocalRepositories.saveAppUser(this, appUser);

        if(isConnected) {
            mProgressDialog = new ProgressDialog(VerificationActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(this, appUser);
            ApiCallsService.action(this, Cv.ACTION_VERIFICATION);
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

    @Subscribe
    public void verify(UserApiResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            if(fromLoginPage){
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                startActivity(intent);
            }
            else if(fromRegisterPage){
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                startActivity(intent);
            }
            else if(fromUpdateMobileNumber){
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                startActivity(intent);
            }
            else if(fromProfilePage){
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                Intent intent = new Intent(getApplicationContext(), LandingPageActivity.class);
                startActivity(intent);
            }
            else if(fromForgotPasswordPage){
                startActivity(new Intent(getApplicationContext(),NewPasswordActivity.class));
            }
            else {
                snackbar = Snackbar
                        .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
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