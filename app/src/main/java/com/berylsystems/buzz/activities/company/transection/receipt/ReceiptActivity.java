package com.berylsystems.buzz.activities.company.transection.receipt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.company.transection.income.CreateIncomeActivity;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;

public class ReceiptActivity extends BaseActivityCompany {

    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);

        setAddCompany(0);
        setAppBarTitleCompany(1,"RECEIPT");
    }

    public void add(View v) {
        Intent i=new Intent(getApplicationContext(),CreateReceiptActivity.class);
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
