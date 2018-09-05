package com.lkintechnology.mBilling.activities.company;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;


import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.BaseActivityCompany;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.fragments.dashboard.DashBoardReportsFragment;
import com.lkintechnology.mBilling.fragments.dashboard.DashboardAccountFragment;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.companydashboardinfo.GetCompanyDashboardInfoResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FirstPageActivity extends BaseActivityCompany {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    // public static CompanyData data;
    @Bind(R.id.viewpager)
    ViewPager mHeaderViewPager;
    @Bind(R.id.tabs)
    TabLayout mTabLayout;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    AppUser appUser;
    public static Boolean posSetting = false;
    public static Boolean posNotifyAdapter = false;
    public static Boolean pos = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        appUser = LocalRepositories.getAppUser(this);
        setAddCompany(0);
        setAppBarTitleCompany(1, appUser.company_name);
        ButterKnife.bind(this);
        //initActionbar();
        posSetting = false;
        pos = false;
    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#67309c")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText(appUser.company_name);
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new DashboardAccountFragment(), "ACCOUNTS");
        adapter.addFragment(new DashBoardReportsFragment(), "REPORTS");
        viewPager.setAdapter(adapter);
    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(FirstPageActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_COMPANY_DASHBOARD_INFO);
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
        super.onResume();
    }

    @Subscribe
    public void getCompanyDashboardInfo(GetCompanyDashboardInfoResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            DashboardAccountFragment.data = response.getCompany_details();
            appUser.invoice_format = response.getCompany_details().getData().getAttributes().getInvoice_format();
            if (response.getCompany_details().getData().getAttributes().getInvoice_format() != null) {
                if (response.getCompany_details().getData().getAttributes().getInvoice_format().equals("4 inch Invoice")) {
                    CompanyListActivity.boolForInvoiceFormat = true;
                } else {
                    CompanyListActivity.boolForInvoiceFormat = false;
                }
            } else {
                CompanyListActivity.boolForInvoiceFormat = false;
            }
            if (response.getCompany_details().getData().getAttributes().getTnc() != null) {
                appUser.tnc = new String[response.getCompany_details().getData().getAttributes().getTnc().length];
                for (int i = 0; i < response.getCompany_details().getData().getAttributes().getTnc().length; i++) {
                    appUser.tnc[i] = response.getCompany_details().getData().getAttributes().getTnc()[i];
                }
            } else {
                appUser.tnc = null;
            }
            LocalRepositories.saveAppUser(this, appUser);
            setupViewPager(mHeaderViewPager);
            mTabLayout.setupWithViewPager(mHeaderViewPager);
            mHeaderViewPager.setOffscreenPageLimit(1);
        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this, response.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        new android.support.v7.app.AlertDialog.Builder(FirstPageActivity.this)
                .setTitle("Exit Users")
                .setMessage("Do you want to exit this company ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
                    startActivity(intent);
                    finish();

                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }
}