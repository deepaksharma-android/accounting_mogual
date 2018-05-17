package com.lkintechnology.mBilling.activities.company.navigations.reports.account_group;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.navigations.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.item_wise_report.ItemWiseReportResponse;
import com.lkintechnology.mBilling.networks.api_response.transactionpdfresponse.GetTransactionPdfResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemWiseReportActicity extends RegisterAbstractActivity implements View.OnClickListener {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.item_layout)
    LinearLayout mItem_layout;
    @Bind(R.id.start_date_layout)
    LinearLayout mStart_date_layout;
    @Bind(R.id.end_date_layout)
    LinearLayout mEnd_date_layout;
    @Bind(R.id.item_txtView)
    TextView mItem_textView;
    @Bind(R.id.start_date)
    TextView mStart_date;
    @Bind(R.id.end_date)
    TextView mEnd_date;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.calender_icon)
    ImageView mCalender_Icon;
    @Bind(R.id.calender_icon1)
    ImageView mCalender_Icon1;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    AppUser appUser;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog1,DatePickerDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
       // dateFormatter = new SimpleDateFormat("yyyy-MMM-dd", Locale.US);
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        long date = System.currentTimeMillis();
        String dateString = dateFormatter.format(date);
        mStart_date.setText(dateString);
        mEnd_date.setText(dateString);
        setDateField();
        mItem_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ExpandableItemListActivity.comingFrom = 5;
                ExpandableItemListActivity.isDirectForItem = false;
                Intent i = new Intent(getApplicationContext(), ExpandableItemListActivity.class);
                startActivityForResult(i, 2);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String start = mStart_date.getText().toString();
                String end = mEnd_date.getText().toString();

                if(!mItem_textView.getText().toString().equals("")){
                    appUser.pdf_start_date = mStart_date.getText().toString();
                    appUser.pdf_end_date = mEnd_date.getText().toString();
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(ItemWiseReportActicity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ITEM_WISE_REPORT);
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
                }else {
                    Snackbar.make(coordinatorLayout, "Please select Item", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_item_wise_report;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 2) {
                   String result = data.getStringExtra("name");
                   String id = data.getStringExtra("item_id");
                   String[] name = result.split(",");
                   mItem_textView.setText(name[0]);
                   appUser.pdf_account_id = id;
                   LocalRepositories.saveAppUser(getApplicationContext(),appUser);
            }
        }
    }

    private void setDateField() {
        mStart_date.setOnClickListener(this);
        mEnd_date.setOnClickListener(this);
        mCalender_Icon.setOnClickListener(this);
        mCalender_Icon1.setOnClickListener(this);

        final Calendar newCalendar = Calendar.getInstance();

        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        String dateString = sdf.format(date);

        DatePickerDialog1 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year,  int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                String endDate = mEnd_date.getText().toString();
                String startDate = mStart_date.getText().toString();
                mStart_date.setText(date1);
                  /*  if (date1.compareTo(endDate) <= 0){
                        //System.out.println("Date1 is before endDate");
                        mStart_date.setText(date1);
                    }else{
                        Snackbar.make(coordinatorLayout, "Please select valid date ", Snackbar.LENGTH_LONG).show();
                    }*/

               // Or

               /* if(monthOfYear>=03 && year==2017){
                    mStart_date.setText(date1);
                }
                else if((current_month == "Jan" || current_month == "Feb" || current_month == "Mar") && current_year>2017){
                    mStart_date.setText(date1);
                }
                else{
                    Snackbar.make(coordinatorLayout, "Please select valid date ", Snackbar.LENGTH_LONG).show();
                }*/
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog2 = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //view.setMaxDate(System.currentTimeMillis());
                String start_date = mStart_date.getText().toString();
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                mEnd_date.setText(date1);

             /*   if (date1.compareTo(start_date) >= 0 )*//*&& date1.compareTo(dateString) <= 0)*//*{
                    //System.out.println("Date1 is after Date2");
                    mEnd_date.setText(date1);
                }else{
                    Snackbar.make(coordinatorLayout, "Please select valid date ", Snackbar.LENGTH_LONG).show();
                }*/
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }

    @Override
    public void onClick(View view) {
        if (view == mStart_date || view==mCalender_Icon) {
            DatePickerDialog1.show();
        }
        else if(view == mEnd_date || view==mCalender_Icon1){
            DatePickerDialog2.show();
        }
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
        actionbarTitle.setText("Item Wise Report");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void getItemWiseReport(ItemWiseReportResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200) {
            //Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent i = new Intent(this, TransactionPdfActivity.class);
            String company_report = response.getHtml();
            i.putExtra("company_report",company_report);
            startActivity(i);
            finish();
        }
        else{
            Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();
    }
}
