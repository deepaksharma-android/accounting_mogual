package com.lkintechnology.mBilling.activities.company;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.companylogin.CreateAuthorizationSettingsResponse;
import com.lkintechnology.mBilling.networks.api_response.companylogin.Users;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyAuthrizationActivity extends AppCompatActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.checkbox_enable)
    CheckBox checkbox_enable;
    @Bind(R.id.checkbox_delete_voucher)
    CheckBox checkbox_delete_voucher;
    @Bind(R.id.checkbox_edit_voucher)
    CheckBox checkbox_edit_voucher;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    public static Users data;
    AppUser appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_authrization);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
        appUser.allow_user_to_delete_voucher = "";
        appUser.enable_user = "";
        appUser.allow_user_to_edit_voucher = "";
        LocalRepositories.saveAppUser(this, appUser);

        if (data.getEnable() != null) {
            if (data.getEnable()) {
                checkbox_enable.setChecked(true);
                appUser.enable_user = "true";
                LocalRepositories.saveAppUser(this, appUser);
            } else {
                checkbox_enable.setChecked(false);
                appUser.enable_user = "false";
                LocalRepositories.saveAppUser(this, appUser);
            }
        }
        if (data.getEnable() != null) {
            if (data.getAllow_delete_voucher()) {
                checkbox_delete_voucher.setChecked(true);
                appUser.allow_user_to_delete_voucher = "true";
                LocalRepositories.saveAppUser(this, appUser);
            } else {
                checkbox_delete_voucher.setChecked(false);
                appUser.allow_user_to_delete_voucher = "false";
                LocalRepositories.saveAppUser(this, appUser);
            }
        }

        if (data.getAllow_edit_voucher() != null) {
            if (data.getAllow_edit_voucher()) {
                checkbox_edit_voucher.setChecked(true);
                appUser.allow_user_to_edit_voucher = "true";
                LocalRepositories.saveAppUser(this, appUser);
            } else {
                checkbox_edit_voucher.setChecked(false);
                appUser.allow_user_to_edit_voucher = "false";
                LocalRepositories.saveAppUser(this, appUser);
            }
        }

        checkbox_enable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    appUser.enable_user = "true";
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                } else {

                    appUser.enable_user = "false";
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
            }
        });

        checkbox_delete_voucher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    appUser.allow_user_to_delete_voucher = "true";
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                } else {
                    appUser.allow_user_to_delete_voucher = "false";
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
            }
        });

        checkbox_edit_voucher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    appUser.allow_user_to_edit_voucher = "true";
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                } else {
                    appUser.allow_user_to_edit_voucher = "false";
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
            }
        });


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = new ProgressDialog(CompanyAuthrizationActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_AUTHORIZATION_SETTINGS);
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
        actionbarTitle.setText("AUTHORIZATION SETTINGS");
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void createAuthorizationSettings(CreateAuthorizationSettingsResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.allow_user_to_delete_voucher = "";
            appUser.enable_user = "";
            appUser.allow_user_to_edit_voucher = "";
            LocalRepositories.saveAppUser(this, appUser);
            Intent intent = new Intent(getApplicationContext(), EditCompanyActivity.class);
            startActivity(intent);
            finish();
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        //EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        // EventBus.getDefault().register(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        //  EventBus.getDefault().register(this);
        super.onStop();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
