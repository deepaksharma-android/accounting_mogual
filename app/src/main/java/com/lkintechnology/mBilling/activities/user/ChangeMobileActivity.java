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

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.user.UserApiResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;
import com.lkintechnology.mBilling.utils.Validation;

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
        initActionbar();
        String mobile=getIntent().getExtras().getString("mobile");
        mMobileNumber.setText(mobile);
        appUser = LocalRepositories.getAppUser(this);

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
        actionbarTitle.setText("CHANGE MOBILE");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
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
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }
        else{
           // snackbar = Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            // snackbar.show();
            Helpers.dialogMessage(this,response.getMessage());
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, VerificationActivity.class);
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
        Intent intent = new Intent(this, VerificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}