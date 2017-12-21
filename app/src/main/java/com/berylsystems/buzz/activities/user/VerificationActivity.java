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
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.app.RegisterAbstractActivity;
import com.berylsystems.buzz.activities.company.CompanyListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.otp.OtpResponse;
import com.berylsystems.buzz.networks.api_response.user.UserApiResponse;
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
    @Bind(R.id.resend)
    TextView mResend;
    @Bind(R.id.changeMobile)
    TextView mChangeMobileNumber;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    Boolean fromRegisterPage, fromLoginPage, fromForgotPasswordPage, fromProfilePage, fromUpdateMobileNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initActionbar();
        String mobile = getIntent().getExtras().getString("mobile");
        fromRegisterPage = getIntent().getExtras().getBoolean("fromRegisterPage");
        fromLoginPage = getIntent().getExtras().getBoolean("fromLoginPage");
        fromForgotPasswordPage = getIntent().getExtras().getBoolean("fromForgotPasswordPage");
        fromProfilePage = getIntent().getExtras().getBoolean("fromProfilePage");
        fromUpdateMobileNumber = getIntent().getExtras().getBoolean("fromUpdateMobileNumber");
        if (fromForgotPasswordPage == true) {
            mChangeMobileNumber.setVisibility(View.GONE);
        }
        if (fromProfilePage == true) {
            mChangeMobileNumber.setVisibility(View.GONE);
        }
        appUser = LocalRepositories.getAppUser(this);
        mMobileNumber.setText(mobile);

        mResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    mProgressDialog = new ProgressDialog(VerificationActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_RESEND_OTP);
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
        });

        mChangeMobileNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ChangeMobileActivity.class);
                intent.putExtra("mobile", mMobileNumber.getText().toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("VERIFICATION");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_verification;
    }

    public void resend(View v) {

    }

    public void changeMobileNumber(View v) {

    }

    public void submit(View v) {
        Boolean isConnected = ConnectivityReceiver.isConnected();
        boolean cancel = false;
        View focusView = null;


        if (!mOtp.getText().toString().equals("")) {
            appUser.otp = mOtp.getText().toString();
            appUser.mobile = mMobileNumber.getText().toString();
            LocalRepositories.saveAppUser(this, appUser);
            if (isConnected) {
                mProgressDialog = new ProgressDialog(VerificationActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(this, appUser);
                ApiCallsService.action(this, Cv.ACTION_VERIFICATION);
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
        } else {
            mOtp.setError(getString(R.string.err_otp));
            cancel = true;
            focusView = mOtp;
        }



    }

    @Subscribe
    public void resendOtp(OtpResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Subscribe
    public void verify(UserApiResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            if (fromLoginPage) {
              /*  if (!response.getUser().getData().getAttributes().getUser_plan().equals("")) {*/
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
               /* } else {
                    startActivity(new Intent(getApplicationContext(), PackageActivity.class));
                }*/
            } else if (fromRegisterPage) {
                appUser.fb_id = "";
                LocalRepositories.saveAppUser(this, appUser);
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else if (fromUpdateMobileNumber) {
              /*  if (!response.getUser().getData().getAttributes().getUser_plan().equals("")) {*/
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
               /* } else {
                    startActivity(new Intent(getApplicationContext(), PackageActivity.class));
                }*/
            } else if (fromProfilePage) {
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else if (fromForgotPasswordPage) {
                Intent intent = new Intent(getApplicationContext(), NewPasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                snackbar = Snackbar
                        .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
                snackbar.show();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(fromLoginPage) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else if(fromRegisterPage){
                    Intent intent = new Intent(this, RegisterActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else if(fromUpdateMobileNumber){
                    Intent intent = new Intent(this, ChangeMobileActivity.class);
                    intent.putExtra("fromUpdateMobileNumber",true);
                    intent.putExtra("mobile",mMobileNumber.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else if(fromProfilePage){
                    Intent intent = new Intent(this, UpdateUserActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else if(fromForgotPasswordPage){
                    Intent intent = new Intent(this, ForgotPasswordActivity.class);
                    intent.putExtra("fromForgotPasswordPage",true);
                    intent.putExtra("mobile", mMobileNumber.getText().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if(fromLoginPage) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        else if(fromRegisterPage){
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        else if(fromUpdateMobileNumber){
            Intent intent = new Intent(this, ChangeMobileActivity.class);
            intent.putExtra("fromUpdateMobileNumber",true);
            intent.putExtra("mobile",mMobileNumber.getText().toString());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        else if(fromProfilePage){
            Intent intent = new Intent(this, UpdateUserActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        else if(fromForgotPasswordPage){
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            intent.putExtra("fromForgotPasswordPage",true);
            intent.putExtra("mobile", mMobileNumber.getText().toString());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }


}