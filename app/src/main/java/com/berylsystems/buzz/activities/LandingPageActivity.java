package com.berylsystems.buzz.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.adapters.LandingPageGridAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.company.IndustryTypeResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LandingPageActivity extends BaseActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    LandingPageGridAdapter mAdapter;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    AppUser appUser;
    int[] myImageList = new int[]{R.drawable.icn_open_company, R.drawable.icon_administration, R.drawable.icon_transaction, R.drawable.icon_display, R.drawable.icon_printer, R.drawable.icon_favorites};
    private String[] title = {
            "Company",
            "Administation",
            "Transaction",
            "Display",
            "Print/Email/SMS",
            "Favourites"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setHeading(2);
        setNavigation(1);
        setAdd(1);
        setAppBarTitle(1, "BAHI KHATA");
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        appUser = LocalRepositories.getAppUser(this);
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new LandingPageGridAdapter(this, title, myImageList);
        mRecyclerView.setAdapter(mAdapter);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(LandingPageActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            ApiCallsService.action(this, Cv.ACTION_GET_INDUSTRY);
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

    public void add(View v) {
        startActivity(new Intent(getApplicationContext(), AddCompanyActivity.class));
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
    public void getIndustryType(IndustryTypeResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            for(int i=0;i<response.getIndustry().getData().size();i++){
                appUser.industry_type.add(response.getIndustry().getData().get(i).getAttributes().getName());
                appUser.industry_id.add(response.getIndustry().getData().get(i).getAttributes().getId());
                LocalRepositories.saveAppUser(this,appUser);
            }

        } else {
            Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG).show();
        }


    }

    @Override
    public void onBackPressed() {

    }
}
