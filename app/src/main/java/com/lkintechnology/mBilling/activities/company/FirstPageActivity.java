package com.lkintechnology.mBilling.activities.company;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.BaseActivityCompany;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.administration.master.item.ItemMaterialCentreListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionBankActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionCashInHandActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionCustomerActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionExpensesActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionSalesActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionStockInHandActivity;
import com.lkintechnology.mBilling.activities.company.transaction.TransactionSupplierActivity;
import com.lkintechnology.mBilling.activities.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.companydashboardinfo.GetCompanyDashboardInfoResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

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
    @Bind(R.id.profit_loss_layout)
    LinearLayout mProfit_loss_layout;
    @Bind(R.id.profit_loss_textview)
    TextView mProfit_loss_textview;
    @Bind(R.id.profit_loss_textview1)
    TextView mProfit_loss_textview1;
    @Bind(R.id.profit_loss_image)
    ImageView profit_loss_image;
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
        setAppBarTitleCompany(1, appUser.company_name);
        ButterKnife.bind(this);
        final Calendar newCalendar = Calendar.getInstance();
        String dayNumberSuffix = getDayNumberSuffix(newCalendar.get(Calendar.DAY_OF_MONTH));
        dateFormatter = new SimpleDateFormat(" d'" + dayNumberSuffix + "' MMM yy", Locale.US);
        String date1 = dateFormatter.format(newCalendar.getTime());
        Timber.i("DATE1"+date1);
        String arr[]=date1.split(" ");
        String apos=arr[2]+"\'";
        String datenew=arr[1]+" "+apos+" "+arr[3];
        mDate.setText("As On "+datenew);

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
                Intent i = new Intent(getApplicationContext(), ItemMaterialCentreListActivity.class);
              /*  FirstPageActivity.isDirectForFirstPage=false;*/
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
            mtextview_cash_in_hand.setText("₹ " +String.format("%.2f",response.getCompany_details().getData().getAttributes().getCash_in_hand()));
            mtextview_bank.setText("₹ " +String.format("%.2f", response.getCompany_details().getData().getAttributes().getBank_account()));
            mtextview_customer.setText("₹ " +String.format("%.2f", response.getCompany_details().getData().getAttributes().getCustomer()));
            mtextview_supplier.setText("₹ " +String.format("%.2f", response.getCompany_details().getData().getAttributes().getSupplier()));
            mtextview_stock_in_hand.setText("₹ " +String.format("%.2f",response.getCompany_details().getData().getAttributes().getStock_in_hand()));
            mtextview_sales.setText("₹ " +String.format("%.2f",response.getCompany_details().getData().getAttributes().getSales()));
            mtextview_expenses.setText("₹ " +String.format("%.2f",response.getCompany_details().getData().getAttributes().getExpenses()));
            mProfit_loss_textview1.setText("₹ " +String.format("%.2f",response.getCompany_details().getData().getAttributes().getProfit_loss()));

            if(response.getCompany_details().getData().getAttributes().getProfit_loss()<0){
                //mProfit_lose_layout.setBackgroundColor(Color.RED);
                mProfit_loss_textview.setText("Loss");
                mProfit_loss_textview.setTextColor(Color.RED);
                mProfit_loss_textview1.setTextColor(Color.RED);
               // mProfit_loss_layout.setBackgroundResource(R.drawable.curve_backgroung_red);
                profit_loss_image.setImageResource(R.drawable.icon_loss);
            }else {
                mProfit_loss_textview.setText("Profit");
                profit_loss_image.setImageResource(R.drawable.icon_profits);
               // mProfit_loss_layout.setBackgroundResource(R.drawable.curve_backgroung_green);
               // mProfit_lose_layout.setBackgroundColor(Color.GREEN);
            }
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
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
