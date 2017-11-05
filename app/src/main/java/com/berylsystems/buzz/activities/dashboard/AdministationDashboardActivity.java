package com.berylsystems.buzz.activities.dashboard;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.adapters.AdministrationDashboardAdapter;
import com.berylsystems.buzz.adapters.CompanyDashboardAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdministationDashboardActivity extends BaseActivityCompany {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    AdministrationDashboardAdapter mAdapter;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    AppUser appUser;
    int[] myImageList = new int[]{R.drawable.icon_administration, R.drawable.icon_transaction, R.drawable.icon_display, R.drawable.icon_printer, R.drawable.icon_favorites,R.drawable.icon_administration, R.drawable.icon_transaction, R.drawable.icon_display, R.drawable.icon_printer, R.drawable.icon_favorites};
    private String[] title = {
            "Account",
            "Account Group",
            "Item",
            "Item Group",
            "Material Center",
            "Material Center Group",
            "Unit",
            "Unit Conversion",
            "Bill Sundry",
            "Bill Of Materila"
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administration_dashboard);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        setAddCompany(2);
        setAppBarTitleCompany(1,"ADMINISTRATION");
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new AdministrationDashboardAdapter(this, title, myImageList);
        mRecyclerView.setAdapter(mAdapter);
    }
}