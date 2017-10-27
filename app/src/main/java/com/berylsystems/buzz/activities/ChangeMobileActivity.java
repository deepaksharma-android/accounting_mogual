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
import com.berylsystems.buzz.utils.Validation;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChangeMobileActivity extends RegisterAbstractActivity {
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
        String mobile=getIntent().getExtras().getString("mobile");
        mMobileNumber.setText(mobile);
        appUser = LocalRepositories.getAppUser(this);
    }
    @Override
    protected int layoutId() {
        return R.layout.activity_change_mobile_number;
    }

    public void submit(View v) {
        Boolean isConnected = ConnectivityReceiver.isConnected();
        boolean cancel = false;
        View focusView = null;


        if (Validation.isPhoneFormatValid(mMobileNumber.getText().toString())) {
            appUser.mobile = mMobileNumber.getText().toString();
            LocalRepositories.saveAppUser(this, appUser);

            if(isConnected) {
                mProgressDialog = new ProgressDialog(ChangeMobileActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(this, appUser);
                ApiCallsService.action(this, Cv.ACTION_UPDATE_MOBILE_NUMBER);
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


        } else {
            mMobileNumber.setError(getString(R.string.err_invalid_mobile));
            cancel = true;
            focusView = mMobileNumber;
        }
    }

    @Subscribe
    public void changemobilenumber(UserApiResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
            Intent intent=new Intent(getApplicationContext(),VerificationActivity.class);
            intent.putExtra("fromUpdateMobileNumber",true);
            intent.putExtra("mobile",mMobileNumber.getText().toString());
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