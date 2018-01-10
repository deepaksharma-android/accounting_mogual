package com.berylsystems.buzz.activities.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.user.UserApiResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;
import com.berylsystems.buzz.utils.Validation;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UpdateUserActivity extends RegisterAbstractActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.name)
    EditText mName;
    @Bind(R.id.email)
    EditText mEmail;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.zip_code)
    EditText mzip_code;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    AppUser appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_update_user);

        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);

        mName.setText(appUser.name);
        mEmail.setText(appUser.email);
        mPassword.setText("••••••••");
        mzip_code.setText(appUser.zipcode);
        //LocalRepositories.saveAppUser(this,appUser);
    }

    public void update(View v) {

        boolean cancel = false;
        View focusView = null;

        if (!mName.getText().toString().equals("")) {
            if (Validation.isEmailValid(mEmail.getText().toString())) {
                if (!mPassword.getText().toString().equals("")) {
                    if (!mzip_code.getText().toString().equals("")) {
                        appUser.name = mName.getText().toString();
                        appUser.email = mEmail.getText().toString();
                        appUser.password = mPassword.getText().toString();
                        appUser.zipcode = mzip_code.getText().toString();
                        LocalRepositories.saveAppUser(this, appUser);
                        logInRequest();
                    } else {
                        snackbar = Snackbar
                                .make(coordinatorLayout, getString(R.string.zipcode), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } else {
                    snackbar = Snackbar
                            .make(coordinatorLayout, getString(R.string.err_invalid_pwd_format), Snackbar.LENGTH_LONG);
                    snackbar.show();

                }
            } else {
                snackbar = Snackbar
                        .make(coordinatorLayout, getString(R.string.err_invalid_email), Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, getString(R.string.err_invalid_name), Snackbar.LENGTH_LONG);
            snackbar.show();

        }

    }

    private void logInRequest() {
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(UpdateUserActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(this, appUser);
            ApiCallsService.action(this, Cv.ACTION_UPDATE_USER);

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

    @Subscribe
    public void updateuserinfo(UserApiResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.user_id = response.getUser().getData().getId();
            appUser.name = response.getUser().getData().getAttributes().getName();
            appUser.password = response.getUser().getData().getAttributes().getPassword();
            appUser.email = response.getUser().getData().getAttributes().getEmail();
            appUser.zipcode = response.getUser().getData().getAttributes().getPostal_code();
            LocalRepositories.saveAppUser(this, appUser);
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent intent = new Intent(this, CompanyListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
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
        actionbarTitle.setText("USER PROFILE");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_update_user;
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
                Intent intent = new Intent(this, CompanyListActivity.class);
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
        Intent intent = new Intent(this, CompanyListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
