package com.berylsystems.buzz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.adapters.LandingPageGridAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.events.EventFbAuthResponse;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.user.UserApiResponse;
import com.berylsystems.buzz.networks.api_response.userexist.UserExistResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.Helpers;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;
import com.berylsystems.buzz.utils.Validation;
import com.facebook.CallbackManager;
import com.facebook.login.LoginManager;

import org.greenrobot.eventbus.Subscribe;

import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.mobile)
    EditText mMobileText;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.fb_login_button)
    LinearLayout fbLoginBtn;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appUser=LocalRepositories.getAppUser(this);
        ButterKnife.bind(this);
        callbackManager = CallbackManager.Factory.create();
        fbLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email"));
                Helpers.facebookLogin(callbackManager, fbLoginBtn);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    @Subscribe
    public void fbAuthResponse(EventFbAuthResponse resp) {
        appUser.fb_id=resp.id;
        appUser.name=resp.first_name+" "+resp.last_name;
        appUser.email=resp.email;
        appUser.password="qwopaskl23";
        LocalRepositories.saveAppUser(this,appUser);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if(isConnected) {
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();

            ApiCallsService.action(this, Cv.ACTION_FACEBOOK_CHECK);
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
    public void userexists(UserExistResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            if(response.is_present.equals("true")){
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                startActivity(new Intent(getApplicationContext(),LandingPageActivity.class));
            }
            else {
                startActivity(new Intent(getApplicationContext(),FacebookHandlerActivity.class));
            }
        }
        else {
            snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG);
            snackbar.show();
        }

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