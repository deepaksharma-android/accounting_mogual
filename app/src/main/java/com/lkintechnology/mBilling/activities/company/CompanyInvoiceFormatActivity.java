package com.lkintechnology.mBilling.activities.company;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.SplashActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.CompanyDashboardActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.company.CreateCompanyResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyInvoiceFormatActivity extends AppCompatActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.submit)
    RelativeLayout mSubmit;
    @Bind(R.id.radio_button1)
    RadioButton radio_button1;
    @Bind(R.id.radio_button2)
    RadioButton radio_button2;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_invoice_format);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        EventBus.getDefault().register(this);
        initActionbar();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radio_button1.isChecked()==true || radio_button2.isChecked()==true){
                    if (radio_button1.isChecked()==true){
                        appUser.invoice_format = radio_button1.getText().toString();
                    }else {
                        appUser.invoice_format = radio_button2.getText().toString();
                    }
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if(isConnected) {
                        mProgressDialog = new ProgressDialog(CompanyInvoiceFormatActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_INVOICE_FORMAT);
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
                }else {
                    Helpers.dialogMessage(CompanyInvoiceFormatActivity.this,"Please select invoice format!!!");
                }
            }
        });

        if (CompanyListActivity.boolForInvoiceFormat){
            radio_button2.setChecked(true);
        }else {
            radio_button1.setChecked(true);
        }
    }

    public void onRadioButtonClicked(View view) {
       /* boolean checked = ((RadioButton) view).isChecked();
        switch(view.getId()) {
            case R.id.radio_button1:
                if (checked)
                    CompanyListActivity.boolForInvoiceFormat = false;
                    break;
            case R.id.radio_button2:
                if (checked)
                    CompanyListActivity.boolForInvoiceFormat = true;
                    break;
        }*/
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
        actionbarTitle.setText("Invoice Format");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
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

    @Subscribe
    public void createInvoice(CreateCompanyResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            if (radio_button1.isChecked()==true){
                CompanyListActivity.boolForInvoiceFormat = false;
            }else {
                CompanyListActivity.boolForInvoiceFormat = true;
            }
            finish();
        }
        else {
            Helpers.dialogMessage(getApplicationContext(),response.getMessage());
        }
    }

}
