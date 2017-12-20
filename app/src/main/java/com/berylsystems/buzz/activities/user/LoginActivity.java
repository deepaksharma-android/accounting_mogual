package com.berylsystems.buzz.activities.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.app.HomePageActivity;
import com.berylsystems.buzz.activities.app.RegisterAbstractActivity;
import com.berylsystems.buzz.activities.company.CompanyListActivity;
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
        appUser = LocalRepositories.getAppUser(this);
        ButterKnife.bind(this);
        initActionbar();
        callbackManager = CallbackManager.Factory.create();
        fbLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("email"));
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#3F51B5")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("SIGNIN");
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

    @Subscribe
    public void fbAuthResponse(EventFbAuthResponse resp) {
        appUser.fb_id = resp.id;
        appUser.name = resp.first_name + " " + resp.last_name;
        appUser.email = resp.email;
        appUser.password = "qwopaskl23";
        LocalRepositories.saveAppUser(this, appUser);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            ApiCallsService.action(this, Cv.ACTION_FACEBOOK_CHECK);
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Boolean isConnected = ConnectivityReceiver.isConnected();
                            if (isConnected) {
                                snackbar.dismiss();
                            }
                        }
                    });
            snackbar.show();
        }


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(this, HomePageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Subscribe
    public void userexists(UserExistResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            if (response.is_present.equals("true")) {
                appUser.user_id = response.getUser().getData().getId();
                appUser.name = response.getUser().getData().getAttributes().getName();
                appUser.mobile = response.getUser().getData().getAttributes().getMobile();
                appUser.email = response.getUser().getData().getAttributes().getEmail();
                appUser.auth_token = response.getUser().getData().getAttributes().getAuth_token();
                LocalRepositories.saveAppUser(this, appUser);
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(getApplicationContext(), FacebookHandlerActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("fromloginpage",true);
                startActivity(intent);
                finish();
            }
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }

    }


    @Override
    protected int layoutId() {
        return R.layout.activity_login;
    }

    public void login(View v) {

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
        if (isConnected) {
            mProgressDialog = new ProgressDialog(LoginActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();

            ApiCallsService.action(this, Cv.ACTION_LOGIN);
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Boolean isConnected = ConnectivityReceiver.isConnected();
                            if (isConnected) {
                                snackbar.dismiss();
                            }
                        }
                    });
            snackbar.show();
        }
    }

    public void register(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
        startActivity(intent);
        finish();
    }

    public void forgot(View v) {
        Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
        startActivity(intent);
        finish();
    }

    @Subscribe
    public void loginResponse(UserApiResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.user_id = response.getUser().getData().getId();
            appUser.name = response.getUser().getData().getAttributes().getName();
            appUser.mobile = response.getUser().getData().getAttributes().getMobile();
            appUser.email = response.getUser().getData().getAttributes().getEmail();
            appUser.auth_token = response.getUser().getData().getAttributes().getAuth_token();
            appUser.zipcode = response.getUser().getData().getAttributes().getPostal_code();
            LocalRepositories.saveAppUser(this, appUser);
            if (!response.getUser().getData().getAttributes().getActive()) {
                Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
                intent.putExtra("fromLoginPage", true);
                intent.putExtra("mobile", response.getUser().getData().getAttributes().getMobile());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

            } else {
                /*if (!response.getUser().getData().getAttributes().getUser_plan().equals("")) {*/
                    Preferences.getInstance(getApplicationContext()).setLogin(true);
                Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
                startActivity(intent);
                finish();
               /* } else {
                    startActivity(new Intent(getApplicationContext(), PackageActivity.class));
                }*/


            }
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();


        }
    }

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}