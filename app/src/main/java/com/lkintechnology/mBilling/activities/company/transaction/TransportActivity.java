package com.lkintechnology.mBilling.activities.company.transaction;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_response.transport.Transport;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransportActivity extends AppCompatActivity {

    @Bind(R.id.show_hide_layout)
    LinearLayout show_hide_layout;
    @Bind(R.id.spinner_e_way_bill)
    Spinner spinner_e_way_bill;
    @Bind(R.id.spinner_transport)
    Spinner spinner_transport;
    @Bind(R.id.date)
    TextView mDate;
    @Bind(R.id.transport)
    EditText transport;
    @Bind(R.id.gr_rr)
    EditText gr_rr;
    @Bind(R.id.vehicle_no)
    EditText vehicle_no;
    @Bind(R.id.station)
    EditText station;
    @Bind(R.id.pin)
    EditText pin;
    @Bind(R.id.distance)
    EditText distance;
    @Bind(R.id.e_way)
    EditText e_way;
    @Bind(R.id.submit)
    LinearLayout submit;
    AppUser appUser;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public static String voucher_type="";
    public static Transport saledata;
    public static Transport purchasedata;
    public static Transport purchasereturndata;
    public static Transport salereturndata;
    public Boolean fromedit;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transport);
        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        fromedit=getIntent().getExtras().getBoolean("fromedit",false);
        spinner_e_way_bill.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    show_hide_layout.setVisibility(View.VISIBLE);
                } else {
                    show_hide_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(fromedit) {
            if (voucher_type.equals("sale")) {
                transport.setText(saledata.getTransport());
                gr_rr.setText(saledata.getGr_no());
                mDate.setText(saledata.getDate());
                vehicle_no.setText(saledata.getVehicle_number());
                station.setText(saledata.getStation());
                pin.setText(saledata.getPincode());
                if (saledata.getEway_bill_required()) {
                    spinner_e_way_bill.setSelection(0);
                    show_hide_layout.setVisibility(View.VISIBLE);
                    distance.setText(saledata.getDistance());
                    if (saledata.getMode_of_transport().equals("Road")) {
                        spinner_transport.setSelection(0);
                    } else if (saledata.getMode_of_transport().equals("Air")) {
                        spinner_transport.setSelection(1);
                    } else if (saledata.getMode_of_transport().equals("Rail")) {
                        spinner_transport.setSelection(2);
                    } else if (saledata.getMode_of_transport().equals("Ship")) {
                        spinner_transport.setSelection(3);
                    } else {
                        spinner_transport.setSelection(0);
                    }
                    e_way.setText(saledata.getEway_bill_number());
                } else {
                    spinner_e_way_bill.setSelection(1);
                    show_hide_layout.setVisibility(View.GONE);
                }

            } else if (voucher_type.equals("purchase")) {
                transport.setText(purchasedata.getTransport());
                gr_rr.setText(purchasedata.getGr_no());
                mDate.setText(purchasedata.getDate());
                vehicle_no.setText(purchasedata.getVehicle_number());
                station.setText(purchasedata.getStation());
                pin.setText(purchasedata.getPincode());
                if (purchasedata.getEway_bill_required()) {
                    spinner_e_way_bill.setSelection(0);
                    show_hide_layout.setVisibility(View.VISIBLE);
                    distance.setText(purchasedata.getDistance());
                    if (purchasedata.getMode_of_transport().equals("Road")) {
                        spinner_transport.setSelection(0);
                    } else if (purchasedata.getMode_of_transport().equals("Air")) {
                        spinner_transport.setSelection(1);
                    } else if (purchasedata.getMode_of_transport().equals("Rail")) {
                        spinner_transport.setSelection(2);
                    } else if (purchasedata.getMode_of_transport().equals("Ship")) {
                        spinner_transport.setSelection(3);
                    } else {
                        spinner_transport.setSelection(0);
                    }
                    e_way.setText(purchasedata.getEway_bill_number());
                } else {
                    spinner_e_way_bill.setSelection(1);
                    show_hide_layout.setVisibility(View.GONE);
                }

            } else if (voucher_type.equals("sale_return")) {
                transport.setText(salereturndata.getTransport());
                gr_rr.setText(salereturndata.getGr_no());
                mDate.setText(salereturndata.getDate());
                vehicle_no.setText(salereturndata.getVehicle_number());
                station.setText(salereturndata.getStation());
                pin.setText(salereturndata.getPincode());
                if (salereturndata.getEway_bill_required()) {
                    spinner_e_way_bill.setSelection(0);
                    show_hide_layout.setVisibility(View.VISIBLE);
                    distance.setText(salereturndata.getDistance());
                    if (salereturndata.getMode_of_transport().equals("Road")) {
                        spinner_transport.setSelection(0);
                    } else if (salereturndata.getMode_of_transport().equals("Air")) {
                        spinner_transport.setSelection(1);
                    } else if (salereturndata.getMode_of_transport().equals("Rail")) {
                        spinner_transport.setSelection(2);
                    } else if (salereturndata.getMode_of_transport().equals("Ship")) {
                        spinner_transport.setSelection(3);
                    } else {
                        spinner_transport.setSelection(0);
                    }
                    e_way.setText(salereturndata.getEway_bill_number());
                } else {
                    spinner_e_way_bill.setSelection(1);
                    show_hide_layout.setVisibility(View.GONE);
                }
            } else if (voucher_type.equals("purchase_return")) {
                transport.setText(purchasereturndata.getTransport());
                gr_rr.setText(purchasereturndata.getGr_no());
                mDate.setText(purchasereturndata.getDate());
                vehicle_no.setText(purchasereturndata.getVehicle_number());
                station.setText(purchasereturndata.getStation());
                pin.setText(purchasereturndata.getPincode());
                if (purchasereturndata.getEway_bill_required()) {
                    spinner_e_way_bill.setSelection(0);
                    show_hide_layout.setVisibility(View.VISIBLE);
                    distance.setText(purchasereturndata.getDistance());
                    if (purchasereturndata.getMode_of_transport().equals("Road")) {
                        spinner_transport.setSelection(0);
                    } else if (purchasereturndata.getMode_of_transport().equals("Air")) {
                        spinner_transport.setSelection(1);
                    } else if (purchasereturndata.getMode_of_transport().equals("Rail")) {
                        spinner_transport.setSelection(2);
                    } else if (purchasereturndata.getMode_of_transport().equals("Ship")) {
                        spinner_transport.setSelection(3);
                    } else {
                        spinner_transport.setSelection(0);
                    }
                    e_way.setText(purchasereturndata.getEway_bill_number());
                } else {
                    spinner_e_way_bill.setSelection(1);
                    show_hide_layout.setVisibility(View.GONE);
                }
            }
        }
        else {
            spinner_e_way_bill.setSelection(1);
            if (appUser.transport_details.size() > 0) {
                spinner_transport.setSelection(getIndex(spinner_transport, appUser.transport_details.get("mode_of_transport").toString()));
                transport.setText(appUser.transport_details.get("transport").toString());
                gr_rr.setText(appUser.transport_details.get("gr_no").toString());
                mDate.setText(appUser.transport_details.get("date").toString());
                vehicle_no.setText(appUser.transport_details.get("vehicle_number").toString());
                station.setText(appUser.transport_details.get("station").toString());
                pin.setText(appUser.transport_details.get("pincode").toString());
                if (appUser.transport_details.get("eway_bill_required").toString().equals("true")) {
                    spinner_e_way_bill.setSelection(0);
                } else {
                    spinner_e_way_bill.setSelection(1);
                }
//            spinner_e_way_bill.setSelection(getIndex(spinner_e_way_bill, appUser.transport_details.get("eway_bill_required").toString()));
                distance.setText(appUser.transport_details.get("distance").toString());

                e_way.setText(appUser.transport_details.get("eway_bill_number").toString());
            } else {
                final Calendar c = Calendar.getInstance();
                mDate.setText(" " + c.get(Calendar.DAY_OF_MONTH) + " " + Helpers.getMonth(c.get(Calendar.MONTH)) + " " + c.get(Calendar.YEAR));
            }

        }
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog();
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.transport_details.put("voucher_type", voucher_type);
                appUser.transport_details.put("mode_of_transport", (String) spinner_transport.getSelectedItem());
                if (spinner_e_way_bill.getSelectedItem().toString().equals("Yes")) {
                    appUser.transport_details.put("eway_bill_required", "true");
                } else {
                    appUser.transport_details.put("eway_bill_required", "false");
                }
//                appUser.transport_details.put("eway_bill_required", (String) spinner_e_way_bill.getSelectedItem());
                appUser.transport_details.put("date", mDate.getText().toString());
                appUser.transport_details.put("transport", transport.getText().toString());
                appUser.transport_details.put("gr_no", gr_rr.getText().toString());
                appUser.transport_details.put("vehicle_number", vehicle_no.getText().toString());
                appUser.transport_details.put("station", station.getText().toString());
                appUser.transport_details.put("pincode", pin.getText().toString());
                appUser.transport_details.put("distance", distance.getText().toString());
                appUser.transport_details.put("eway_bill_number", e_way.getText().toString());
                LocalRepositories.saveAppUser(TransportActivity.this, appUser);
                finish();
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
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("TRANSPORT ACTIVITY");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }


    private void datePickerDialog() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        mDate.setText(" " + dayOfMonth + " " + Helpers.getMonth(monthOfYear) + " " + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //private method of your class
    private int getIndex(Spinner spinner, String myString) {
        int index = 0;
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

}
