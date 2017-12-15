package com.berylsystems.buzz.activities.dashboard;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.company.FirstPageActivity;
import com.berylsystems.buzz.activities.company.administration.master.billsundry.BillSundryListActivity;
import com.berylsystems.buzz.activities.company.administration.master.item.ExpandableItemListActivity;
import com.berylsystems.buzz.activities.user.LoginActivity;
import com.berylsystems.buzz.adapters.MasterDashboardAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MasterDashboardActivity extends BaseActivityCompany {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    MasterDashboardAdapter mAdapter;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    AppUser appUser;
    int[] myImageList = new int[]{R.drawable.master_account, R.drawable.master_account_group, R.drawable.master_item,
            R.drawable.master_item_group, R.drawable.master_materail_center,R.drawable.master_materail_center_group,
            R.drawable.master_unit, R.drawable.master_unit_conversion, R.drawable.master_bill_sundry, R.drawable.master_purchase_type,
            R.drawable.master_sale_type, R.drawable.master_tax_category};
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
            "Purchase Type",
            "Sale Type",
            "Tax Category"
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_grid);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        TypedArray ta = getResources().obtainTypedArray(R.array.rainbow);
        int[] colors = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            colors[i] = ta.getColor(i, 0);
        }
        ExpandableItemListActivity.isDirectForItem=true;
        BillSundryListActivity.isDirectForBill=true;

        ta.recycle();
        setAddCompany(2);
        setAppBarTitleCompany(1,"MASTER");
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MasterDashboardAdapter(this, title, myImageList,colors);
        mRecyclerView.setAdapter(mAdapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(this, FirstPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FirstPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}