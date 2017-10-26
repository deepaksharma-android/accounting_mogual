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
import com.berylsystems.buzz.utils.Validation;

import org.greenrobot.eventbus.Subscribe;



import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.name)
    EditText mName;
    @Bind(R.id.email)
    EditText mEmail;
    @Bind(R.id.mobile)
    EditText mMobile;
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
        ButterKnife.bind(this);
        appUser= LocalRepositories.getAppUser(this);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_register;
    }

    public void register(View v){

        boolean cancel = false;
        View focusView = null;

        if(Validation.isNameFormatValid(mName.getText().toString())){
            if (Validation.isPhoneFormatValid(mMobile.getText().toString())) {
                if(Validation.isEmailValid(mEmail.getText().toString())) {
                    if (Validation.isPwdFormatValid(mPassword.getText().toString())) {
                        if (Validation.isPwdFormatValid(mConfirmPassword.getText().toString())) {
                            if(mPassword.getText().toString().length()==mConfirmPassword.getText().toString().length()) {
                                appUser.name=mName.getText().toString();
                                appUser.email=mEmail.getText().toString();
                                appUser.mobile=mMobile.getText().toString();
                                appUser.password=mPassword.getText().toString();
                                LocalRepositories.saveAppUser(this,appUser);
                                logInRequest();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Password doesnot matches",Toast.LENGTH_LONG).show();
                            }

                        }
                        else {
                            mConfirmPassword.setError(getString(R.string.err_invalid_pwd_format));
                            cancel = true;
                            focusView = mConfirmPassword;
                        }
                    }
                    else {
                        mPassword.setError(getString(R.string.err_invalid_pwd_format));
                        cancel = true;
                        focusView = mPassword;
                    }
                }
                else {
                    mEmail.setError(getString(R.string.err_invalid_email));
                    cancel = true;
                    focusView = mEmail;
                }

            }
            else {
                mMobile.setError(getString(R.string.err_invalid_mobile));
                cancel = true;
                focusView = mMobile;
            }
        }
        else {
            mName.setError(getString(R.string.err_invalid_name));
            cancel = true;
            focusView = mName;
        }
    }


    private void logInRequest() {
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if(isConnected) {
            mProgressDialog = new ProgressDialog(RegisterActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(this, appUser);
            ApiCallsService.action(this, Cv.ACTION_REGISTER_USER);
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
    public void registerResponse(UserApiResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.user_id = response.getUser().getData().getId();
            appUser.name = response.getUser().getData().getAttributes().getName();
            appUser.mobile=response.getUser().getData().getAttributes().getMobile();
            appUser.email = response.getUser().getData().getAttributes().getEmail();
            appUser.auth_token = response.getUser().getData().getAttributes().getAuth_token();
            LocalRepositories.saveAppUser(this, appUser);
            if (response.getUser().getData().getAttributes().getActive()==false) {
                Intent intent=new Intent(getApplicationContext(),VerificationActivity.class);
                intent.putExtra("fromRegisterPage",true);
                intent.putExtra("mobile",mMobile.getText().toString());
                startActivity(intent);
            }
            else{
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                startActivity(new Intent(getApplicationContext(), VerificationActivity.class));
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