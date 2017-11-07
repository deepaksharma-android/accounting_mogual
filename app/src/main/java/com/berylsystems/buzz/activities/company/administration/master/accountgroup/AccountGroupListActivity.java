package com.berylsystems.buzz.activities.company.administration.master.accountgroup;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.dashboard.MasterDashboardActivity;
import com.berylsystems.buzz.adapters.AccountGroupListAdapter;
import com.berylsystems.buzz.adapters.AccountListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.accountgroup.GetAccountGroupResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountGroupListActivity extends BaseActivityCompany {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.account_group_list_recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    AccountGroupListAdapter mAdapter;
    AppUser appUser;
    Snackbar snackbar;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_group_list);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        appUser=LocalRepositories.getAppUser(this);
        setAddCompany(1);
        setAppBarTitleCompany(1,"ACCOUNT GROUP LIST");
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if(isConnected) {
            mProgressDialog = new ProgressDialog(AccountGroupListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ACCOUNT_GROUP);
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
    public void add(View v) {
        Intent intent=new Intent(getApplicationContext(), CreateAccountGroupActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe
    public void getAccountGroup(GetAccountGroupResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            appUser.group_id.clear();
            appUser.group_name.clear();
            for(int i=0;i<response.getAccount_groups().getData().size();i++){
                if(response.getAccount_groups().getData().get(i).getAttributes().getUndefined()==false) {
                    appUser.group_name.add(response.getAccount_groups().getData().get(i).getAttributes().getName());
                    appUser.group_id.add(response.getAccount_groups().getData().get(i).getAttributes().getId());
                    LocalRepositories.saveAppUser(this, appUser);
                }
            }


            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new AccountGroupListAdapter(this, response.getAccount_groups().getData());
            mRecyclerView.setAdapter(mAdapter);
        }
        else{
            Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
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
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MasterDashboardActivity.class));
    }
}