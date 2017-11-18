package com.berylsystems.buzz.activities.company.administration.master.unitconversion;

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
import com.berylsystems.buzz.adapters.UnitConversionListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.unitconversion.DeleteUnitConversionResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.GetUnitConversionListResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventDeleteConversionUnit;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class UnitConversionListActivity extends BaseActivityCompany {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.unit_conversion_list_recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    UnitConversionListAdapter mAdapter;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar  snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_conversion_list);
        ButterKnife.bind(this);
        setAddCompany(0);
        setAppBarTitleCompany(1,"UNIT CONVERSION LIST");
        appUser=LocalRepositories.getAppUser(this);
/*        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new UnitConversionListAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);*/
    }
    public void add(View v) {
        Intent intent=new Intent(getApplicationContext(), CreateUnitConversionActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(UnitConversionListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_UNIT_CONVERSION_LIST);
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

    @Subscribe
    public void getUnitConversionList(GetUnitConversionListResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            appUser.arr_unitlistConversionId.clear();
            LocalRepositories.saveAppUser(this,appUser);
            Timber.i("I AM HERE");
            for(int i=0;i<response.getUnit_conversions().getData().size();i++) {
                appUser.arr_unitlistConversionId.add(String.valueOf(response.getUnit_conversions().getData().get(i).getAttributes().getId()));
                LocalRepositories.saveAppUser(this, appUser);
            }
            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new UnitConversionListAdapter(this, response.getUnit_conversions().getData());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Subscribe
    public void deleteconversionunit(EventDeleteConversionUnit pos){
        appUser.delete_unit_conversion_id= String.valueOf(appUser.arr_unitlistConversionId.get(pos.getPosition()));
        LocalRepositories.saveAppUser(this,appUser);
        new AlertDialog.Builder(UnitConversionListActivity.this)
                .setTitle("Delete Unit Conversion")
                .setMessage("Are you sure you want to delete this unit conversion ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if(isConnected) {
                        mProgressDialog = new ProgressDialog(UnitConversionListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_UNIT_CONVERSION);
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
    public void deleteunitconversionresponse(DeleteUnitConversionResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_UNIT_CONVERSION_LIST);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }
}