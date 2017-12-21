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
import com.berylsystems.buzz.activities.app.HomePageActivity;
import com.berylsystems.buzz.activities.app.RegisterAbstractActivity;
import com.berylsystems.buzz.activities.company.CompanyListActivity;
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

public class FacebookHandlerActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.mobile_number)
    EditText mMobileNumber;
    @Bind(R.id.zip_code)
    EditText mzip_code;
    @Bind(R.id.salesman_mobile)
    EditText mSalesman_mobile;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    Boolean fromLogin,fromRegister;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
        fromLogin=getIntent().getExtras().getBoolean("fromloginpage");
        fromRegister=getIntent().getExtras().getBoolean("fromregisterpage");

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
        actionbarTitle.setText("MOBILE NUMBER");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
    @Override
    protected int layoutId() {
        return R.layout.activity_facebook_handler;
    }

    public void submit(View v){
        Boolean isConnected = ConnectivityReceiver.isConnected();
        boolean cancel = false;
        View focusView = null;


        if (Validation.isPhoneFormatValid(mMobileNumber.getText().toString())) {
            appUser.mobile = mMobileNumber.getText().toString();
            appUser.zipcode = mzip_code.getText().toString();
            appUser.salesmanmobile = mSalesman_mobile.getText().toString();
            LocalRepositories.saveAppUser(this, appUser);

            if(isConnected) {
                mProgressDialog = new ProgressDialog(FacebookHandlerActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(this, appUser);
                ApiCallsService.action(this, Cv.ACTION_REGISTER_USER);
            }
            else{
                snackbar = Snackbar
                        .make(coordinatorLayout,"Not Connected to internet", Snackbar.LENGTH_LONG);
            }


        } else {
            mMobileNumber.setError(getString(R.string.err_invalid_mobile));
            cancel = true;
            focusView = mMobileNumber;
        }
    }

    @Subscribe
    public void faacebookhandler(UserApiResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            appUser.auth_token=response.getUser().getData().getAttributes().getAuth_token();
            appUser.user_id=response.getUser().getData().getId();
            LocalRepositories.saveAppUser(this,appUser);
            if(response.getUser().getData().getAttributes().getActive()==false) {
                Intent intent = new Intent(getApplicationContext(), VerificationActivity.class);
                intent.putExtra("fromRegisterPage", true);
                intent.putExtra("mobile", mMobileNumber.getText().toString());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            else{
                appUser.fb_id="";
                LocalRepositories.saveAppUser(this,appUser);
                Preferences.getInstance(getApplicationContext()).setLogin(true);
                Intent intent = new Intent(this, CompanyListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                if(fromLogin) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                if(fromRegister){
                    Intent intent = new Intent(this, RegisterActivity.class);
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
        if(fromLogin) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if(fromRegister){
            Intent intent = new Intent(this, RegisterActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}