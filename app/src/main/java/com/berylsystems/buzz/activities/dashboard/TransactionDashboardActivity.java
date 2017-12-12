package com.berylsystems.buzz.activities.dashboard;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.adapters.AdministrationDashboardAdapter;
import com.berylsystems.buzz.adapters.TransactionDashboardAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransactionDashboardActivity extends BaseActivityCompany {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    AppUser appUser;
    RecyclerView.LayoutManager layoutManager;
    TransactionDashboardAdapter mAdapter;

    int[] myImageList = new int[]{R.drawable.icon_administration, R.drawable.icon_transaction, R.drawable.icon_display, R.drawable.icon_printer, R.drawable.icon_administration, R.drawable.icon_transaction, R.drawable.icon_display, R.drawable.icon_printer,R.drawable.icon_administration, R.drawable.icon_transaction, R.drawable.icon_display,R.drawable.icon_administration, R.drawable.icon_transaction};
    private String[] title={
            "Sales",
            "Purchase",
            "Sale Return",
            "Purchase Return",
            "Payment",
            "Receipt",
            "Bank Cash Deposit",
            "Bank Cash Withdraw ",
            "Income",
            "Expense",
            "Journal Voucher",
            "Debit Note W/O Item",
            "Credit Note W/O Item",
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_grid);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        setAddCompany(2);
        setAppBarTitleCompany(1,"TRANSACTION");
        Preferences.getInstance(getApplicationContext()).setSale_type_name("");
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new TransactionDashboardAdapter(this, title, myImageList);
        mRecyclerView.setAdapter(mAdapter);

    }
    @Override
    public void onBackPressed() {
        //startActivity(new Intent(getApplicationContext(),CompanyDashboardActivity.class));
        finish();
    }
}
