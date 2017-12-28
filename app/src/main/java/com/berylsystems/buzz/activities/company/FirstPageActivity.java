package com.berylsystems.buzz.activities.company;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.company.transaction.TransactionBankActivity;
import com.berylsystems.buzz.activities.company.transaction.TransactionCashInHandActivity;
import com.berylsystems.buzz.activities.company.transaction.TransactionCustomerActivity;
import com.berylsystems.buzz.activities.company.transaction.TransactionExpensesActivity;
import com.berylsystems.buzz.activities.company.transaction.TransactionSalesActivity;
import com.berylsystems.buzz.activities.company.transaction.TransactionStockInHandActivity;
import com.berylsystems.buzz.activities.company.transaction.TransactionSupplierActivity;
import com.berylsystems.buzz.activities.company.transaction.sale.CreateSaleActivity;
import com.berylsystems.buzz.activities.dashboard.CompanyDashboardActivity;
import com.berylsystems.buzz.activities.dashboard.TransactionDashboardActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.companydashboardinfo.GetCompanyDashboardInfoResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FirstPageActivity extends BaseActivityCompany {

    @Bind(R.id.transaction_button)
    LinearLayout transactionButton;
    @Bind(R.id.layout_cash_in_hand)
    LinearLayout mlayout_cash_in_hand;
    @Bind(R.id.layout_bank)
    LinearLayout mlayout_bank;
    @Bind(R.id.layout_customer)
    LinearLayout mlayout_customer;
    @Bind(R.id.layout_supplier)
    LinearLayout mlayout_supplier;
    @Bind(R.id.layout_stock_in_hand)
    LinearLayout mlayout_stock_in_hand;
    @Bind(R.id.layout_sale)
    LinearLayout mSales_layout;
    @Bind(R.id.layout_expense)
    LinearLayout mExpenses_layout;

    @Bind(R.id.textview_cash_in_hand)
    TextView mtextview_cash_in_hand;
    @Bind(R.id.textview_bank)
    TextView mtextview_bank;
    @Bind(R.id.textview_customer)
    TextView mtextview_customer;
    @Bind(R.id.textview_supplier)
    TextView mtextview_supplier;
    @Bind(R.id.textview_stock_in_hand)
    TextView mtextview_stock_in_hand;
    @Bind(R.id.textview_sale)
    TextView mtextview_sales;
    @Bind(R.id.textview_expenses)
    TextView mtextview_expenses;
    @Bind(R.id.date)
    TextView mDate;
    ProgressDialog mProgressDialog;
    CoordinatorLayout coordinatorLayout;
    Snackbar snackbar;
    AppUser appUser;
    private SimpleDateFormat dateFormatter;
    public static Boolean isDirectForFirstPage = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        appUser = LocalRepositories.getAppUser(this);
        setAddCompany(0);
        setAppBarTitleCompany(1, appUser.company_name.toUpperCase());
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        final Calendar newCalendar = Calendar.getInstance();
        String dayNumberSuffix = getDayNumberSuffix(newCalendar.get(Calendar.DAY_OF_MONTH));
        dateFormatter = new SimpleDateFormat(" dd'" + dayNumberSuffix + "' MMM yy", Locale.US);
        String date1 = dateFormatter.format(newCalendar.getTime());
        mDate.setText("As On "+date1);

        mlayout_cash_in_hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Cash-in-hand";
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                Intent i = new Intent(getApplicationContext(), TransactionCashInHandActivity.class);
                FirstPageActivity.isDirectForFirstPage=false;
                startActivity(i);
            }
        });

        mlayout_bank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Bank Accounts";
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                Intent i = new Intent(getApplicationContext(), TransactionBankActivity.class);
                FirstPageActivity.isDirectForFirstPage=false;
                startActivity(i);
            }
        });

        mlayout_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Sundry Debtors";
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                Intent i = new Intent(getApplicationContext(), TransactionCustomerActivity.class);
                FirstPageActivity.isDirectForFirstPage=false;
                startActivity(i);
            }
        });

        mlayout_supplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Sundry Creditors";
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                Intent i = new Intent(getApplicationContext(), TransactionSupplierActivity.class);
                FirstPageActivity.isDirectForFirstPage=false;
                startActivity(i);
            }
        });
        mlayout_stock_in_hand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group ="";
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                Intent i = new Intent(getApplicationContext(), TransactionStockInHandActivity.class);
                FirstPageActivity.isDirectForFirstPage=false;
                startActivity(i);
            }
        });

        transactionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TransactionDashboardActivity.class);
                startActivity(intent);
            }
        });

        mSales_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //appUser.duration_spinner ="Today";
                Intent intent = new Intent(getApplicationContext(), TransactionSalesActivity.class);
                FirstPageActivity.isDirectForFirstPage=false;
                startActivity(intent);
            }
        });
        mExpenses_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TransactionExpensesActivity.class);
                FirstPageActivity.isDirectForFirstPage=false;
                startActivity(intent);
            }
        });

    }

    @Subscribe
    public void getCompanyDashboardInfo(GetCompanyDashboardInfoResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            mtextview_cash_in_hand.setText("₹ " + response.getCompany_details().getData().getAttributes().getCash_in_hand());
            mtextview_bank.setText("₹ " + response.getCompany_details().getData().getAttributes().getBank_account());
            mtextview_customer.setText("₹ " + response.getCompany_details().getData().getAttributes().getCustomer());
            mtextview_supplier.setText("₹ " + response.getCompany_details().getData().getAttributes().getSupplier());
            mtextview_stock_in_hand.setText("₹ " + response.getCompany_details().getData().getAttributes().getStock_in_hand());
            mtextview_sales.setText("₹ " +response.getCompany_details().getData().getAttributes().getSales());
            mtextview_expenses.setText("₹ " +response.getCompany_details().getData().getAttributes().getExpenses());
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

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
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        mProgressDialog.dismiss();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        mProgressDialog.dismiss();
    }

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        //mProgressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        new android.support.v7.app.AlertDialog.Builder(FirstPageActivity.this)
                .setTitle("Exit Company")
                .setMessage("Do you want to exit this company ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Intent intent=new Intent(getApplicationContext(),CompanyListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);//***Change Here***
                    startActivity(intent);
                    finish();

                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();
    }

    private String getDayNumberSuffix(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }
}
