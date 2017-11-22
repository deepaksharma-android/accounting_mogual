package com.berylsystems.buzz.activities.company.transection.bankcasewithdraw;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.company.transection.bankcasedeposit.CreateBankCaseDepositActivity;

import org.greenrobot.eventbus.Subscribe;

public class BankCaseWithdrawActivity extends BaseActivityCompany {

    Snackbar snackbar;
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_case_withdraw);

        setAddCompany(0);
        setAppBarTitleCompany(1,"BANK CASE WITHDRAW");
    }

    public void add(View v){
        Intent i = new Intent(getApplicationContext(), CreateBankCaseWithdrawActivity.class);
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
