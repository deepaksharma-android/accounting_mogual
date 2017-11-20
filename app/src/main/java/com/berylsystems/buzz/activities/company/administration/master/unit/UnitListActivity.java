package com.berylsystems.buzz.activities.company.administration.master.unit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.adapters.UnitListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.unit.DeleteUnitResponse;
import com.berylsystems.buzz.networks.api_response.unit.GetUnitListResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventDeleteUnit;
import com.berylsystems.buzz.utils.EventGroupClicked;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class UnitListActivity  extends BaseActivityCompany {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.unit_list_recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    UnitListAdapter mAdapter;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar  snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_list);
        ButterKnife.bind(this);
        setAddCompany(0);
        setAppBarTitleCompany(1,"UNIT LIST");
        appUser=LocalRepositories.getAppUser(this);

    }
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(UnitListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_UNIT_LIST);
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
    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void add(View v) {
        Intent intent=new Intent(getApplicationContext(), CreateUnitActivity.class);
        startActivity(intent);
    }
    @Subscribe
    public void getUnitList(GetUnitListResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            appUser.arr_unitId.clear();
            appUser.arr_unitName.clear();
            appUser.unitId.clear();
            appUser.unitName.clear();
            LocalRepositories.saveAppUser(this,appUser);
            Timber.i("I AM HERE");
            for(int i=0;i<response.getItem_units().getData().size();i++) {
                appUser.arr_unitName.add(response.getItem_units().getData().get(i).getAttributes().getName());
                appUser.arr_unitId.add(String.valueOf(response.getItem_units().getData().get(i).getId()));
                if(response.getItem_units().getData().get(i).getAttributes().getUndefined()==false){
                    appUser.unitName.add(response.getItem_units().getData().get(i).getAttributes().getName());
                    appUser.unitId.add(String.valueOf(response.getItem_units().getData().get(i).getId()));
                }

                LocalRepositories.saveAppUser(this, appUser);
            }
            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new UnitListAdapter(this, response.getItem_units().getData());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Subscribe
    public void deletematerialcentregroup(EventDeleteUnit pos){
        appUser.delete_unit_id= String.valueOf(appUser.arr_unitId.get(pos.getPosition()));
        LocalRepositories.saveAppUser(this,appUser);
        new AlertDialog.Builder(UnitListActivity.this)
                .setTitle("Delete Unit")
                .setMessage("Are you sure you want to delete this unit ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if(isConnected) {
                        mProgressDialog = new ProgressDialog(UnitListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_UNIT);
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
    public void deleteunitresponse(DeleteUnitResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_UNIT_LIST);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }


    @Subscribe
    public void itemclickedevent(EventGroupClicked pos){

        Timber.i("POSITION" + pos.getPosition());
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", String.valueOf(pos.getPosition()));
        returnIntent.putExtra("name", appUser.arr_unitName.get(pos.getPosition()));
        returnIntent.putExtra("id",String.valueOf(appUser.arr_unitId.get(pos.getPosition())));
        Timber.i("PASSSS"+appUser.arr_unitId.get(pos.getPosition()));
        /*appUser.create_account_group_id = String.valueOf(appUser.arr_account_group_id.get(pos.getPosition()));
          LocalRepositories.saveAppUser(this, appUser);*/
        setResult(Activity.RESULT_OK, returnIntent);
        finish();

    }
}