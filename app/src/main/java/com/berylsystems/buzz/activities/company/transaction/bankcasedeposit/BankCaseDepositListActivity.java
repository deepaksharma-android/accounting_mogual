package com.berylsystems.buzz.activities.company.transaction.bankcasedeposit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;

public class BankCaseDepositListActivity extends BaseActivityCompany {

    @Bind(R.id.add_button)
    FloatingActionButton add_button;
    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_case_deposit_list);
        //add_button.bringToFront();
        setAddCompany(0);
        setAppBarTitleCompany(1,"BANK CASE DEPOSIT");
    }

    public void add(View v) {
        Intent i=new Intent(getApplicationContext(),CreateBankCaseDepositActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onPause() {
      //  EventBus.getDefault().unregister(this);
        //mProgressDialog.dismiss();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
      //  EventBus.getDefault().unregister(this);
       // mProgressDialog.dismiss();
    }

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        //mProgressDialog.dismiss();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}