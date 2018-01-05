package com.berylsystems.buzz.activities.company.transaction.bankcasewithdraw;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.company.transaction.bankcasedeposit.BankCaseDepositListActivity;
import com.berylsystems.buzz.activities.company.transaction.bankcasedeposit.CreateBankCaseDepositActivity;
import com.berylsystems.buzz.activities.dashboard.MasterDashboardActivity;
import com.berylsystems.buzz.activities.dashboard.TransactionDashboardActivity;
import com.berylsystems.buzz.adapters.BankCashWithdrawListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.bankcashwithdraw.DeleteBankCashWithdrawResponse;
import com.berylsystems.buzz.networks.api_response.bankcashwithdraw.GetBankCashWithdrawResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventClickAlertForBakCashWithDraw;
import com.berylsystems.buzz.utils.EventClickAlertForBankCashDeposite;
import com.berylsystems.buzz.utils.EventDeleteBankCashWithdraw;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BankCaseWithdrawActivity extends AppCompatActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    RecyclerView.LayoutManager layoutManager;
    BankCashWithdrawListAdapter mAdapter;
    @Bind(R.id.sale_type_list_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.dashboard_spinner_layout)
    LinearLayout dashboardSpinnerLayout;
    @Bind(R.id.dashboard_spinner)
    Spinner dashboardSpinner;
    ArrayList<String> cashInHand = new ArrayList<>();
    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    AppUser appUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_case_withdraw);

        ButterKnife.bind(this);
        appUser= LocalRepositories.getAppUser(this);
        initActionbar();
        EventBus.getDefault().register(this);

        String fixMonth = "Apr";
        int inputMonthPosition = inputMonthPosition(fixMonth);
        int currentMonthPosition = currentMonth();
        int currentYear = currentYear();
        cashInHand.add("Today");
        cashInHand.add("Last 7 days");

        if(currentMonthPosition<inputMonthPosition)
        {
            for(int i = currentMonthPosition; i>=0; i--){
                cashInHand.add(monthName[i] + " " + currentYear);
            }
            for(int j=11;j>=inputMonthPosition;j--){
                cashInHand.add(monthName[j] + " " + (currentYear-1));
            }
        }else {
            for (int i = currentMonthPosition; i >=inputMonthPosition; i--) {

                cashInHand.add(monthName[i] + " " + currentYear);
            }
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, cashInHand);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dashboardSpinner.setAdapter(dataAdapter);


        dashboardSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemText = (String) parent.getItemAtPosition(position);
                appUser.bank_cash_withdraw_duration_spinner=selectedItemText;
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);

                Boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    mProgressDialog = new ProgressDialog(BankCaseWithdrawActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_BANK_CASH_WITHDRAW);
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
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("BANK CASH WITHDRAW");
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    String[] monthName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
            "Aug", "Sep", "Oct", "Nov", "Dec"};

    private int currentMonth() {
        int monthPosition = -1;
        Calendar cal = Calendar.getInstance();
        String currentMonth = monthName[cal.get(Calendar.MONTH)];
        //String currentYear = monthName[cal.get(Calendar.YEAR)];

        for (int i = 0; i < 12; i++) {
            if (currentMonth == (monthName[i])) {
                monthPosition = i;
            }
        }
        return monthPosition;
    }

    private int currentYear() {

        Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        return currentYear;
    }

    private int inputMonthPosition(String month) {
        int inputMonth = -1;
        for (int i = 0; i < 12; i++) {
            if (month.equals(monthName[i])) {
                inputMonth = i;
            }
        }
        return inputMonth;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, CreateBankCaseWithdrawActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("fromBankcashWithdraw",false);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, CreateBankCaseWithdrawActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("fromBankcashWithdraw",false);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
    public void getBankCashDeposit(GetBankCashWithdrawResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200) {
            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new BankCashWithdrawListAdapter(this,response.getBank_cash_withdraws().data);
            mRecyclerView.setAdapter(mAdapter);
        }
        else{
            Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void event_click_alert(EventClickAlertForBakCashWithDraw response) {
        mProgressDialog.dismiss();
        response.getPosition();
        Intent intent = new Intent(BankCaseWithdrawActivity.this, CreateBankCaseDepositActivity.class);
        intent.putExtra("fromBankCashDeposit", true);
        intent.putExtra("id", response.getPosition());
        startActivity(intent);
        finish();
    }

    @Subscribe
    public void deletebankcashdeposit(EventDeleteBankCashWithdraw pos){
        //appUser.delete_bank_cash_deposit_id= String.valueOf(appUser.arr_item_group_id.get(pos.getPosition()));
        appUser.delete_bank_cash_withdraw_id= pos.getPosition();
        LocalRepositories.saveAppUser(this,appUser);
        new AlertDialog.Builder(BankCaseWithdrawActivity.this)
                .setTitle("Delete Bank Cash Withdraw Item")
                .setMessage("Are you sure you want to delete this Record ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if(isConnected) {
                        mProgressDialog = new ProgressDialog(BankCaseWithdrawActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_BANK_CASH_WITHDRAW);
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
    public void deletebankcashwithdrawresponse(DeleteBankCashWithdrawResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_BANK_CASH_WITHDRAW);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }


    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }
}
