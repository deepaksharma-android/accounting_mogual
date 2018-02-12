package com.lkintechnology.mBilling.activities.company.navigation.reports.account_group;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.navigation.reports.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.transaction.payment.CreatePaymentActivity;
import com.lkintechnology.mBilling.activities.company.transaction.receiptvoucher.CreateReceiptVoucherActivity;
import com.lkintechnology.mBilling.adapters.PdcPaymentAdapter;
import com.lkintechnology.mBilling.adapters.PdcReceiptAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.payment.DeletePaymentResponse;
import com.lkintechnology.mBilling.networks.api_response.pdc.Attribute;
import com.lkintechnology.mBilling.networks.api_response.pdc.GetPdcResponse;
import com.lkintechnology.mBilling.networks.api_response.receiptvoucher.DeleteReceiptVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.transactionpdfresponse.GetTransactionPdfResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventClickAlertForPayment;
import com.lkintechnology.mBilling.utils.EventClickAlertForReceipt;
import com.lkintechnology.mBilling.utils.EventDeletePaymentPdcDetails;
import com.lkintechnology.mBilling.utils.EventDeleteReceiptPdcDetails;
import com.lkintechnology.mBilling.utils.ListHeight;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PdcActivity extends RegisterAbstractActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
   /* @Bind(R.id.account_group_layout)
    LinearLayout mAccount_group_layout;
    @Bind(R.id.start_date_layout)
    LinearLayout mStart_date_layout;
    @Bind(R.id.end_date_layout)
    LinearLayout mEnd_date_layout;
    @Bind(R.id.account_group_textview)
    TextView mAccount_group_textview;
    @Bind(R.id.start_date)
    TextView mStart_date;
    @Bind(R.id.end_date)
    TextView mEnd_date;
    @Bind(R.id.submit)
    LinearLayout mSubmit;*/

    @Bind(R.id.list_view_receipt)
    ListView listViewReceipt;
    @Bind(R.id.list_view_payment)
    ListView listViewPayment;


    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    AppUser appUser;
    ArrayList<String> arrayList = new ArrayList<>();
    @Bind(R.id.dashboard_spinner)
    Spinner spinner;
    ArrayList<Attribute> receiptList;
    ArrayList<Attribute> paymentList;
    Boolean pdcdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initActionbar();
        appUser=LocalRepositories.getAppUser(this);
        String fixMonth = "Apr";
        int inputMonthPosition = inputMonthPosition(fixMonth);
        int currentMonthPosition = currentMonth();
        int currentYear = currentYear();
        arrayList.add("Today");
        arrayList.add("Last 7 days");

        if(currentMonthPosition<inputMonthPosition)
        {
            for(int i = currentMonthPosition; i>=0; i--){
                arrayList.add(monthName[i] + " " + currentYear);
            }
            for(int j=11;j>=inputMonthPosition;j--){
                arrayList.add(monthName[j] + " " + (currentYear-1));
            }
        }else {
            for (int i = currentMonthPosition; i >=inputMonthPosition; i--) {

                arrayList.add(monthName[i] + " " + currentYear);
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                R.layout.layout_date_spinner_textview, arrayList);
        dataAdapter.setDropDownViewResource(R.layout.layout_date_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String selectedItemText = (String) parent.getItemAtPosition(position);
                appUser.receipt_duration_spinner=selectedItemText;
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);

                Boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    mProgressDialog = new ProgressDialog(PdcActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_PDC);
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





       /* appUser = LocalRepositories.getAppUser(this);
        dateFormatter = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
        setDateField();

        long date = System.currentTimeMillis();
       // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");
        String dateString = dateFormatter.format(date);
        mStart_date.setText(dateString);
        mEnd_date.setText(dateString);

        mAccount_group_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group = "Bank Accounts";
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ExpandableAccountListActivity.isDirectForAccount=false;
                Intent i = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                startActivityForResult(i, 2);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //appUser.pdf_account_id = mAccount_group_textview.getText().toString();
                appUser.pdf_start_date = mStart_date.getText().toString();
                appUser.pdf_end_date = mEnd_date.getText().toString();
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);

                Boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    mProgressDialog = new ProgressDialog(PdcActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_TRANSACTION_PDF);
                } else {
                    snackbar = Snackbar
                            .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Boolean isConnected = ConnectivityReceiver.isConnected();
                                    if (isConnected) {
                                    }
                                }
                            });
                    snackbar.show();
                }
            }
        });*/
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_pdc;
    }

    @Subscribe
    public void getPdcResponse(GetPdcResponse response) {
        mProgressDialog.dismiss();
        receiptList=new ArrayList();
        paymentList=new ArrayList();
        if (response.getStatus() == 200) {

            for(int i=0;i<response.getPdc_details().getData().size();i++){
                if (response.getPdc_details().getData().get(i).getType().equals("receipt-vouchers")){
                    receiptList.add(response.getPdc_details().getData().get(i).getAttributes());
                }else {
                    paymentList.add(response.getPdc_details().getData().get(i).getAttributes());
                }
            }
            listViewReceipt.setAdapter(new PdcReceiptAdapter(this,receiptList));
            ListHeight.setListViewHeightBasedOnChildren(listViewReceipt);
            ListHeight.setListViewHeightBasedOnChildren(listViewReceipt);
            listViewPayment.setAdapter(new PdcPaymentAdapter(this,paymentList));
            ListHeight.setListViewHeightBasedOnChildren(listViewPayment);
            ListHeight.setListViewHeightBasedOnChildren(listViewPayment);
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            receiptList.clear();
            paymentList.clear();
            listViewReceipt.setAdapter(new PdcReceiptAdapter(this,receiptList));
            ListHeight.setListViewHeightBasedOnChildren(listViewReceipt);
            ListHeight.setListViewHeightBasedOnChildren(listViewReceipt);
            listViewPayment.setAdapter(new PdcPaymentAdapter(this,paymentList));
            ListHeight.setListViewHeightBasedOnChildren(listViewPayment);
            ListHeight.setListViewHeightBasedOnChildren(listViewPayment);
        }
    }

    @Subscribe
    public void deletereceiptvoucher(EventDeleteReceiptPdcDetails pos){
        appUser.delete_receipt_id= pos.getPosition();
        LocalRepositories.saveAppUser(this,appUser);

        new AlertDialog.Builder(PdcActivity.this)
                .setTitle("Delete Expence Item")
                .setMessage("Are you sure you want to delete this Record ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if(isConnected) {
                        mProgressDialog = new ProgressDialog(PdcActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_RECEIPT_VOUCHER);
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
    public void deletereceiptvoucherresponse(DeleteReceiptVoucherResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_PDC);
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void deletepayment(EventDeletePaymentPdcDetails pos){
        appUser.delete_payment_id= pos.getPosition();
        LocalRepositories.saveAppUser(this,appUser);
        new AlertDialog.Builder(PdcActivity.this)
                .setTitle("Delete Expence Item")
                .setMessage("Are you sure you want to delete this Record ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if(isConnected) {
                        mProgressDialog = new ProgressDialog(PdcActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_PAYMENT);
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
    public void deletepaymentresponse(DeletePaymentResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_PDC);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void event_click_alert(EventClickAlertForReceipt response) {
        mProgressDialog.dismiss();
        response.getPosition();
        Intent intent = new Intent(PdcActivity.this, CreateReceiptVoucherActivity.class);
        intent.putExtra("from", "pdcDetailsReceipt");
        intent.putExtra("fromReceipt", true);
        intent.putExtra("id", response.getPosition());
        //Toast.makeText(this, "" + response.getPosition(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();

    }
    @Subscribe
    public void event_click_alert(EventClickAlertForPayment response) {
        mProgressDialog.dismiss();
        response.getPosition();
        Intent intent = new Intent(PdcActivity.this, CreatePaymentActivity.class);
        intent.putExtra("from", "pdcDetailsPayment");
        intent.putExtra("fromPayment", true);
        intent.putExtra("id", response.getPosition());
        //Toast.makeText(this, "" + response.getPosition(), Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }

/*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                String[] name = result.split(",");
                mAccount_group_textview.setText(name[0]);
                appUser.pdf_account_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
            }
        }
    }*/

   /* private void setDateField() {
        mStart_date.setOnClickListener(this);
        mEnd_date.setOnClickListener(this);

        final Calendar newCalendar = Calendar.getInstance();

        DatePickerDialog1 = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                mStart_date.setText(date1);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog2 = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                mEnd_date.setText(date1);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        if (view == mStart_date) {
            DatePickerDialog1.show();
        }
        else if(view == mEnd_date){
            DatePickerDialog2.show();
        }
    }*/

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
        actionbarTitle.setText("PDC");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void getTransactionPdf(GetTransactionPdfResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Intent i = new Intent(this, TransactionPdfActivity.class);
            String company_report = response.getCompany_report();
            i.putExtra("company_report", company_report);
            startActivity(i);
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
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

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }

}
