package com.berylsystems.buzz.activities.company.transaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.company.FirstPageActivity;

import com.berylsystems.buzz.adapters.TransactionSalesAdapter;
import com.berylsystems.buzz.adapters.TransactionStockInHandAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.item.GetItemResponse;
import com.berylsystems.buzz.networks.api_response.salereport.GetSaleReportResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class TransactionSalesActivity extends AppCompatActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.dashboard_spinner_layout)
    LinearLayout dashboardSpinnerLayout;
    @Bind(R.id.dashboard_spinner)
    Spinner dashboardSpinner;
    ArrayList<String> cashInHand = new ArrayList<>();
    @Bind(R.id.lvExp)
    ExpandableListView expListView;
    TransactionSalesAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<Integer, List<String>> listDataChildId;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar snackbar;
    List<String> name;
    List<String> id;
    ArrayList amountList=new ArrayList();
    ArrayList quantityList=new ArrayList();
    String title;
    public static Boolean isDirectForFirstPage = true;
    Boolean fromStockReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_month_year);

        ButterKnife.bind(this);
        initActionbar();
        appUser=LocalRepositories.getAppUser(this);

        String fixMonth = "Apr";
        int inputMonthPosition = inputMonthPosition(fixMonth);
        int currentMonthPosition = currentMonth();
        int currentYear = currentYear();

        cashInHand.add("Today");
        cashInHand.add("7 Days");

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
                appUser.duration_spinner=selectedItemText;
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                //Toast.makeText(getApplicationContext(),appUser.date_spinner, Toast.LENGTH_SHORT).show();

                Boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    mProgressDialog = new ProgressDialog(TransactionSalesActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_SALE_REPORT);
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
        //Spinner spinner = (Spinner) viewActionBar.findViewById(R.id.dashboard_spinner1);
       // spinner.setVisibility(View.VISIBLE);
        actionbarTitle.setText("Sales");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
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
                Intent intent = new Intent(this, FirstPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



    @Subscribe
    public void getSaleReport(GetSaleReportResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            listDataHeader = new ArrayList<>();
            listDataChild = new HashMap<String, List<String>>();
            // listDataChildAmount = new HashMap<Integer, List<String>>();
            listDataChildId = new HashMap<Integer, List<String>>();
            if (response.getSale_voucher_items().size() == 0) {
                Snackbar.make(coordinatorLayout, "No Item Found!!", Snackbar.LENGTH_LONG).show();
            }
            for (int i = 0; i < response.getSale_voucher_items().size(); i++) {
                listDataHeader.add(response.getSale_voucher_items().get(i).getGroup_name());
                name = new ArrayList<>();
                //amount = new ArrayList<>();
                id = new ArrayList<>();
                Double addAmount=0.0;
                int addQuantity=0;
                for (int j = 0; j < response.getSale_voucher_items().get(i).getData().size(); j++) {
                    name.add(response.getSale_voucher_items().get(i).getData().get(j).getAttributes().getName()
                            + "," + String.valueOf(response.getSale_voucher_items().get(i).getData().get(j).getAttributes().getPrice())
                            + "," + String.valueOf(response.getSale_voucher_items().get(i).getData().get(j).getAttributes().getQuantity()));
                    id.add(response.getSale_voucher_items().get(i).getData().get(j).getId());
                    Double addprize = response.getSale_voucher_items().get(i).getData().get(j).getAttributes().getPrice();
                    int addquantity = response.getSale_voucher_items().get(i).getData().get(j).getAttributes().getQuantity();
                    addAmount = addAmount+addprize;
                    addQuantity = addQuantity+addquantity;
                }
                amountList.add(Double.valueOf(String.format("%.2f",addAmount)));
                quantityList.add(addQuantity);
                listDataChild.put(listDataHeader.get(i), name);
                listDataChildId.put(i, id);
                Timber.i("1111111111111111111"+addAmount);
                Timber.i("1111111111111111111"+addQuantity);
               // Timber.i("1111111111111111111"+listDataHeader);
            }
            Timber.i("1111111111111111111"+listDataHeader);

            listAdapter = new TransactionSalesAdapter(this, listDataHeader, listDataChild,amountList,quantityList);

            // setting list adapter
            expListView.setAdapter(listAdapter);

        } else {
            //   startActivity(new Intent(getApplicationContext(), MasterDashboardActivity.class));
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

}
