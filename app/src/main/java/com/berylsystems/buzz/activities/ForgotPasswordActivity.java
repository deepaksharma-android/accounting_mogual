package com.berylsystems.buzz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.user.UserApiResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Validation;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.mobile_number)
    EditText mMobileNumber;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
    }

    public void submit(View v){
        Boolean isConnected = ConnectivityReceiver.isConnected();
        boolean cancel = false;
        View focusView = null;


        if (Validation.isPhoneFormatValid(mMobileNumber.getText().toString())) {
            appUser.mobile = mMobileNumber.getText().toString();
            LocalRepositories.saveAppUser(this, appUser);

            if(isConnected) {
                mProgressDialog = new ProgressDialog(ForgotPasswordActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(this, appUser);
                ApiCallsService.action(this, Cv.ACTION_FORGOT_PASSWORD);
            }
            else{
                Toast.makeText(getApplicationContext(),"Not Connected to intenet",Toast.LENGTH_LONG).show();
            }


        } else {
            mMobileNumber.setError(getString(R.string.err_invalid_mobile));
            cancel = true;
            focusView = mMobileNumber;
        }
    }
    @Override
    protected int layoutId() {
        return R.layout.activity_forgot_password;
    }

    @Subscribe
    public void forgotpassword(UserApiResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
           appUser.auth_token=response.getUser().getData().getAttributes().getAuth_token();
            appUser.user_id=response.getUser().getData().getId();
            LocalRepositories.saveAppUser(this,appUser);
            Intent intent=new Intent(getApplicationContext(),VerificationActivity.class);
            intent.putExtra("fromForgotPasswordPage",true);
            intent.putExtra("mobile", mMobileNumber.getText().toString());
            startActivity(intent);

        }
        else{
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
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