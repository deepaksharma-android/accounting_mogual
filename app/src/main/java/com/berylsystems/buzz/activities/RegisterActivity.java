package com.berylsystems.buzz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
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
import timber.log.Timber;

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
    @Bind(R.id.fb_login_button)
    LinearLayout fbLoginBtn;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    private CallbackManager callbackManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initActionbar();
        appUser= LocalRepositories.getAppUser(this);
        callbackManager = CallbackManager.Factory.create();
        fbLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(RegisterActivity.this, Arrays.asList("email"));
                Helpers.facebookLogin(callbackManager, fbLoginBtn);
            }
        });

    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009DE0")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("SIGNUP");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    protected int layoutId() {
        return R.layout.activity_register;
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
            mProgressDialog = new ProgressDialog(RegisterActivity.this);
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
                    .make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }

    public void register(View v){

        boolean cancel = false;
        View focusView = null;

        if(!mName.getText().toString().equals("")){
            if (Validation.isPhoneFormatValid(mMobile.getText().toString())) {
                    if (Validation.isPwdFormatValid(mPassword.getText().toString())) {
                        if (Validation.isPwdFormatValid(mConfirmPassword.getText().toString())) {
                            if(mPassword.getText().toString().matches(mConfirmPassword.getText().toString())) {
                                appUser.name=mName.getText().toString();
                                appUser.email=mEmail.getText().toString();
                                appUser.mobile=mMobile.getText().toString();
                                appUser.password=mPassword.getText().toString();
                                LocalRepositories.saveAppUser(this,appUser);
                                logInRequest();
                            }
                            else{
                                snackbar = Snackbar
                                        .make(coordinatorLayout, "Password does not match", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }

                        }
                        else {
                            snackbar = Snackbar
                                    .make(coordinatorLayout, getString(R.string.err_invalid_pwd_format), Snackbar.LENGTH_LONG);
                            snackbar.show();

                        }
                    }
                    else {
                        snackbar = Snackbar
                                .make(coordinatorLayout, getString(R.string.err_invalid_pwd_format), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
            else {
                snackbar = Snackbar
                        .make(coordinatorLayout, getString(R.string.err_invalid_mobile), Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }
        else {
            snackbar = Snackbar
                    .make(coordinatorLayout, getString(R.string.err_invalid_name), Snackbar.LENGTH_LONG);
            snackbar.show();

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