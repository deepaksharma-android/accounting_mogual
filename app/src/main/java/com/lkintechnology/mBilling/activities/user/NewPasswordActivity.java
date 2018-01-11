package com.lkintechnology.mBilling.activities.user;

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
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.CompanyListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.user.UserApiResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;

public class NewPasswordActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
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
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
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
        actionbarTitle.setText("NEW PASSWORD");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void submit(View v) {
        Boolean isConnected = ConnectivityReceiver.isConnected();
        boolean cancel = false;
        View focusView = null;


        if (mPassword.getText().toString().matches(mConfirmPassword.getText().toString())) {
            if (mPassword.getText().toString().length() > 6 && mConfirmPassword.getText().toString().length() > 6) {
                appUser.password = mPassword.getText().toString();
                LocalRepositories.saveAppUser(this, appUser);

                if (isConnected) {
                    mProgressDialog = new ProgressDialog(NewPasswordActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(this, appUser);
                    ApiCallsService.action(this, Cv.ACTION_NEW_PASSWORD);
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
                Toast.makeText(getApplicationContext(), "Password length should be more than 6 digits", Toast.LENGTH_LONG).show();
            }
        } else {
            mConfirmPassword.setError(getString(R.string.err_password));
            cancel = true;
            focusView = mConfirmPassword;
        }
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_new_password;
    }

    @Subscribe
    public void newPassword(UserApiResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
           /* if (!response.getUser().getData().getAttributes().getUser_plan().equals("")) {*/
            Preferences.getInstance(getApplicationContext()).setLogin(true);
            Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
           /* } else {
                startActivity(new Intent(getApplicationContext(), PackageActivity.class));
            }*/
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
                Intent intent = new Intent(this, ForgotPasswordActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

}