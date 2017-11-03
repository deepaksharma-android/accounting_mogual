package com.berylsystems.buzz.activities;

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
import com.berylsystems.buzz.adapters.CompanyListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.company.CompanyAuthenticateResponse;
import com.berylsystems.buzz.networks.api_response.company.CompanyListResponse;
import com.berylsystems.buzz.networks.api_response.company.CreateCompanyResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventOpenCompany;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ComapanyListActivity extends BaseActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.company_list_recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    CompanyListAdapter mAdapter;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        ButterKnife.bind(this);
        appUser=LocalRepositories.getAppUser(this);
        setNavigation(1);
        setAdd(1);
        setAppBarTitle(1, "COMPANY LIST");
    }

    @Override
    protected void onResume() {
        Preferences.getInstance(getApplicationContext()).setCname("");
        Preferences.getInstance(getApplicationContext()).setCprintname("");
        Preferences.getInstance(getApplicationContext()).setCshortname("");
        Preferences.getInstance(getApplicationContext()).setCphonenumber("");
        Preferences.getInstance(getApplicationContext()).setCfinancialyear("");
        Preferences.getInstance(getApplicationContext()).setCbookyear("");
        Preferences.getInstance(getApplicationContext()).setCcin("");
        Preferences.getInstance(getApplicationContext()).setCpan("");
        Preferences.getInstance(getApplicationContext()).setCaddress("");
        Preferences.getInstance(getApplicationContext()).setCcity("");
        Preferences.getInstance(getApplicationContext()).setCcountry("");
        Preferences.getInstance(getApplicationContext()).setCstate("");
        Preferences.getInstance(getApplicationContext()).setCindustrytype("");
        Preferences.getInstance(getApplicationContext()).setCward("");
        Preferences.getInstance(getApplicationContext()).setCfax("");
        Preferences.getInstance(getApplicationContext()).setCemail("");
        Preferences.getInstance(getApplicationContext()).setCgst("");
        Preferences.getInstance(getApplicationContext()).setCdealer("");
        Preferences.getInstance(getApplicationContext()).setCtax1("");
        Preferences.getInstance(getApplicationContext()).setCtax2("");
        Preferences.getInstance(getApplicationContext()).setCsymbol("");
        Preferences.getInstance(getApplicationContext()).setCstring("");
        Preferences.getInstance(getApplicationContext()).setCsubstring("");
        Preferences.getInstance(getApplicationContext()).setClogo("");
        Preferences.getInstance(getApplicationContext()).setCsign("");
        Preferences.getInstance(getApplicationContext()).setCusername("");
        EventBus.getDefault().register(this);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if(isConnected) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(this, appUser);
            ApiCallsService.action(this, Cv.ACTION_COMPANY_LIST);
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
        super.onResume();
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
        Intent intent=new Intent(getApplicationContext(), CreateCompanyActivity.class);
        //EditCompanyActivity.data = null;
        Preferences.getInstance(getApplicationContext()).setCid("");
        appUser.logo="";
        appUser.signature="";
        LocalRepositories.saveAppUser(this,appUser);
        startActivity(intent);
    }




    @Subscribe
    public void getCompnayList(CompanyListResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter=new CompanyListAdapter(this,response.getCompanies().getData());
            mRecyclerView.setAdapter(mAdapter);
        }
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }
    @Subscribe
    public void timout(String msg){
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }

    @Subscribe
    public void opencompany(EventOpenCompany pos){
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(ComapanyListActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_COMPANY_AUTHENTICATE);
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
    @Subscribe
    public void authenticate(CompanyAuthenticateResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            startActivity(new Intent(getApplicationContext(),LandingPageActivity.class));
        }
        else{

            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }
    @Override
    public void onBackPressed() {

    }
}