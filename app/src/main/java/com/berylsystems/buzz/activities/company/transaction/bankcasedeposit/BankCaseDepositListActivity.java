package com.berylsystems.buzz.activities.company.transaction.bankcasedeposit;

import android.app.ProgressDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.adapters.BankCashDepositListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.bankcashdeposit.DeleteBankCashDepositResponse;
import com.berylsystems.buzz.networks.api_response.bankcashdeposit.GetBankCashDepositResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventDeleteBankCashDeposit;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BankCaseDepositListActivity extends BaseActivityCompany {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    RecyclerView.LayoutManager layoutManager;
    BankCashDepositListAdapter mAdapter;
    @Bind(R.id.sale_type_list_recycler_view)
    RecyclerView mRecyclerView;
    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    AppUser appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_case_deposit_list);
        //add_button.bringToFront();
        ButterKnife.bind(this);
        appUser= LocalRepositories.getAppUser(this);
        setAddCompany(0);
        setAppBarTitleCompany(1,"BANK CASH DEPOSIT");
        EventBus.getDefault().register(this);

        Boolean isConnected = ConnectivityReceiver.isConnected();
        if(isConnected) {
            mProgressDialog = new ProgressDialog(BankCaseDepositListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_BANK_CASH_DEPOSIT);
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
    }

   /* public void add(View v) {
        Intent i=new Intent(getApplicationContext(),CreateBankCaseDepositActivity.class);
        startActivity(i);
    }*/

    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        mProgressDialog.dismiss();
        super.onPause();
    }
    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        mProgressDialog.dismiss();
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

    @Subscribe
    public void getBankCashDeposit(GetBankCashDepositResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200) {
            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new BankCashDepositListAdapter(this,response.getBank_cash_deposits().data);
            mRecyclerView.setAdapter(mAdapter);
        }
        else{
            Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void deletebankcashdeposit(EventDeleteBankCashDeposit pos){
        //appUser.delete_bank_cash_deposit_id= String.valueOf(appUser.arr_item_group_id.get(pos.getPosition()));
        appUser.delete_bank_cash_deposit_id= pos.getPosition();
        LocalRepositories.saveAppUser(this,appUser);
        new AlertDialog.Builder(BankCaseDepositListActivity.this)
                .setTitle("Delete Bank Cash Deposit Item")
                .setMessage("Are you sure you want to delete this Record ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if(isConnected) {
                        mProgressDialog = new ProgressDialog(BankCaseDepositListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_BANK_CASH_DEPOSIT);
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
                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }

    @Subscribe
    public void deletebankcashdepositresponse(DeleteBankCashDepositResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_BANK_CASH_DEPOSIT);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }
}