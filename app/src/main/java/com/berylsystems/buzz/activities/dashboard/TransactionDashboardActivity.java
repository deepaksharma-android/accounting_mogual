package com.berylsystems.buzz.activities.dashboard;

import android.content.Intent;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.company.FirstPageActivity;
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

    int[] myImageList = new int[]{R.drawable.transaction_sale, R.drawable.transaction_reciept,
            R.drawable.transaction_purchase, R.drawable.transaction_payment,
            R.drawable.transaction_payment, R.drawable.transaction_bank_cash_deposit,
            R.drawable.transaction_bank_cash_withdrwal,R.drawable.transaction_income,
            R.drawable.transaction_expence, R.drawable.transaction_sale_return,
            R.drawable.transaction_purchase_return, R.drawable.transaction_journal_voucher,
            R.drawable.transaction_debit_note, R.drawable.transaction_credit_note};
    private String[] title={
            "Sales",
            "Receipt",
            "Purchase",
            "Payment",
            "Bank Cash Deposit",
            "Bank Cash Withdraw ",
            "Income",
            "Expense",
            "Sale Return",
            "Purchase Return",
            "Journal Voucher",
            "Debit Note W/O Item",
            "Credit Note W/O Item"
    };

    //int[] viewcolor = new int[]{getResources().getColor(R.color.red),getResources().getColor(R.color.blue),getResources().getColor(R.color.green),getResources().getColor(R.color.yellow),getResources().getColor(R.color.orange),getResources().getColor(R.color.red),getResources().getColor(R.color.purple),getResources().getColor(R.color.bright_pink),getResources().getColor(R.color.light_blue),getResources().getColor(R.color.grey),getResources().getColor(R.color.brown),getResources().getColor(R.color.premiumcolor),getResources().getColor(R.color.splashText1)};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_grid);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        TypedArray ta = getResources().obtainTypedArray(R.array.rainbow);
        int[] colors = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            colors[i] = ta.getColor(i, 0);
        }
        ta.recycle();
        setAddCompany(2);
        setAppBarTitleCompany(1,"TRANSACTION");

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new TransactionDashboardAdapter(this, title, myImageList,colors);
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
