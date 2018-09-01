package com.lkintechnology.mBilling.activities.company.pos;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.FirstPageActivity;
import com.lkintechnology.mBilling.activities.company.navigations.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.materialcentre.MaterialCentreListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.saletype.SaleTypeListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.CreateSaleVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.voucherseries.VoucherSeriesResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PosSettingActivity extends AppCompatActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.date)
    TextView mDate;
    @Bind(R.id.series)
    Spinner mSeries;
    @Bind(R.id.sale_type_layout)
    LinearLayout mSaleTypeLayout;
    @Bind(R.id.sale_type)
    TextView mSaleType;
    @Bind(R.id.store)
    TextView mStore;
    @Bind(R.id.party_name)
    TextView mPartyName;
    @Bind(R.id.mobile_number)
    EditText mMobileNumber;
    @Bind(R.id.vch_number)
    EditText mVchNumber;
    @Bind(R.id.submit)
    LinearLayout submit;
    AppUser appUser;
    private SimpleDateFormat dateFormatter;
    Animation blinkOnClick;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    ArrayList<String> arr_series;
    ArrayAdapter<String> mVoucherAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_setting);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(getApplicationContext());
        initActionbar();
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        final Calendar newCalendar = Calendar.getInstance();
        String date1 = dateFormatter.format(newCalendar.getTime());
        if (Preferences.getInstance(getApplicationContext()).getPos_date().equals("")){
            Preferences.getInstance(getApplicationContext()).setPos_date(date1);
        }
        FirstPageActivity.posSetting = true;
        FirstPageActivity.posNotifyAdapter = false;
        blinkOnClick = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.blink_on_click);

        mSaleType.setText(Preferences.getInstance(getApplicationContext()).getPos_sale_type());
        mDate.setText(Preferences.getInstance(getApplicationContext()).getPos_date());
        mStore.setText(Preferences.getInstance(getApplicationContext()).getPos_store());
        mPartyName.setText(Preferences.getInstance(getApplicationContext()).getPos_party_name());
        mMobileNumber.setText(Preferences.getInstance(getApplicationContext()).getPos_mobile());

        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(PosSettingActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_VOUCHER_SERIES);
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
        if (Preferences.getInstance(getApplicationContext()).getAuto_increment()!=null){
            if (Preferences.getInstance(getApplicationContext()).getAuto_increment().equals("true")){
                mVchNumber.setEnabled(false);
               // mSeries.setEnabled(false);
            }else {
                mVchNumber.setEnabled(true);
              //  mSeries.setEnabled(true);
            }
        }

        mVoucherAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_spinner_item, appUser.arr_series);
        mVoucherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSeries.setAdapter(mVoucherAdapter);
        String group_type = Preferences.getInstance(getApplicationContext()).getVoucherSeries();
        int groupindex = -1;
        for (int i = 0; i < appUser.arr_series.size(); i++) {
            if (appUser.arr_series.get(i).equals(group_type)) {
                groupindex = i;
                break;
            }
        }
        mSeries.setSelection(groupindex);
        mSeries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Map map = new HashMap();
                if (appUser.series_details.size() > 0) {
                    map = appUser.series_details.get(position);
                    String auto_increament = (String) map.get("auto_increment");
                    String vch_number = (String) map.get("voucher_number");
                    if (auto_increament.equals("false")) {
                        mVchNumber.setEnabled(true);
                    } else {
                        mVchNumber.setEnabled(false);
                    }
                    mVchNumber.setText(vch_number);
                    if (Preferences.getInstance(getApplicationContext()).getVoucherSeries().equals(mSeries.getSelectedItem().toString())) {
                        mVchNumber.setText(Preferences.getInstance(getApplicationContext()).getVoucher_number());
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser = LocalRepositories.getAppUser(getApplicationContext());
                DatePickerDialog datePickerDialog = new DatePickerDialog(PosSettingActivity.this, new android.app.DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String date = dateFormatter.format(newDate.getTime());
                        mDate.setText(date);
                        Preferences.getInstance(getApplicationContext()).setPos_date(date);
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });


        mStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser = LocalRepositories.getAppUser(getApplicationContext());
                ParameterConstant.checkStartActivityResultForAccount = 0;
                ParameterConstant.checkStartActivityResultForMaterialCenter = 1;
                MaterialCentreListActivity.isDirectForMaterialCentre = false;
                startActivityForResult(new Intent(getApplicationContext(), MaterialCentreListActivity.class), 1);
            }
        });
        mSaleType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser = LocalRepositories.getAppUser(getApplicationContext());
                ParameterConstant.checkStartActivityResultForAccount = 0;
                SaleTypeListActivity.isDirectForSaleType = false;
                startActivityForResult(new Intent(getApplicationContext(), SaleTypeListActivity.class), 2);
            }
        });
        mPartyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser = LocalRepositories.getAppUser(getApplicationContext());
                ParameterConstant.forAccountIntentBool = false;
                appUser.account_master_group = "Sundry Debtors,Sundry Creditors,Cash-in-hand";
                ExpandableAccountListActivity.isDirectForAccount = false;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ParameterConstant.handleAutoCompleteTextView = 0;
                Intent intent = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                //intent.putExtra("bool",true);
                startActivityForResult(intent, 3);
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSeries.getSelectedItem().toString().equals("")) {
                    if (!mDate.getText().toString().equals("")) {
                            if (!mSaleType.getText().toString().equals("")) {
                                if (!mStore.getText().toString().equals("")) {
                                    if (!mPartyName.getText().toString().equals("")) {
                                        Preferences.getInstance(getApplicationContext()).setPos_mobile(mMobileNumber.getText().toString());
                                        Preferences.getInstance(getApplicationContext()).setVoucherSeries(mSeries.getSelectedItem().toString());
                                        Preferences.getInstance(getApplicationContext()).setVoucher_number(mVchNumber.getText().toString());
                                        finish();
                                    } else {
                                        Snackbar.make(coordinatorLayout, "Please select party name", Snackbar.LENGTH_LONG).show();
                                    }
                                } else {
                                    Snackbar.make(coordinatorLayout, "Please select store ", Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(coordinatorLayout, "Please select sale type", Snackbar.LENGTH_LONG).show();
                            }

                    } else {
                        Snackbar.make(coordinatorLayout, "Please select the date", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please select the series", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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
        actionbarTitle.setText("POS SETTING");
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        appUser = LocalRepositories.getAppUser(getApplicationContext());
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                String[] name = result.split(",");
                mStore.setText(name[0]);
                Preferences.getInstance(getApplicationContext()).setPos_store(name[0]);
                Preferences.getInstance(getApplicationContext()).setPos_store_id(id);
                return;
            }
        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                appUser.billsundrytotal.clear();
                appUser.mListMapForItemSale.clear();
                ExpandableItemListActivity.mListMapForItemSale.clear();
                PosItemAddActivity.mListMapForBillSale.clear();
                appUser.mListMapForBillSale.clear();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                mSaleType.setText(result);
                Preferences.getInstance(getApplicationContext()).setPos_sale_type(result);
                Preferences.getInstance(getApplicationContext()).setPos_sale_type_id(id);
            }
        }

        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {

                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    mPartyName.setText(ParameterConstant.name);
                    mMobileNumber.setText(ParameterConstant.mobile);
                    appUser.sale_partyName = ParameterConstant.id;
                    appUser.sale_partyEmail = ParameterConstant.email;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Preferences.getInstance(getApplicationContext()).setPos_party_id(ParameterConstant.id);
                    Preferences.getInstance(getApplicationContext()).setPos_party_name(ParameterConstant.name);
                    Preferences.getInstance(getApplicationContext()).setPos_mobile(ParameterConstant.mobile);
                    appUser.sale_partyEmail = ParameterConstant.email;
                } else {
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    String[] strArr = result.split(",");
                    mPartyName.setText(strArr[0]);
                    mMobileNumber.setText(mobile);
                    appUser.sale_partyEmail = strArr[3];
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                    Preferences.getInstance(getApplicationContext()).setPos_party_id(id);
                    Preferences.getInstance(getApplicationContext()).setPos_party_name(strArr[0]);
                    Preferences.getInstance(getApplicationContext()).setPos_mobile(mobile);
                    return;
                }
            }
        }
    }

    @Subscribe
    public void getVoucherNumber(VoucherSeriesResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            arr_series = new ArrayList<>();
            int pos = -1;
            for (int i = 0; i < response.getVoucher_series().getData().size(); i++) {
                Map map = new HashMap();
                map.put("id", response.getVoucher_series().getData().get(i).getId());
                map.put("name", response.getVoucher_series().getData().get(i).getAttributes().getName());
                if (response.getVoucher_series().getData().get(i).getAttributes().isDefaults()) {
                    pos = i;
                }
                map.put("default", String.valueOf(response.getVoucher_series().getData().get(i).getAttributes().isDefaults()));
                map.put("auto_increment", String.valueOf(response.getVoucher_series().getData().get(i).getAttributes().isAuto_increment()));
                map.put("voucher_number", response.getVoucher_series().getData().get(i).getAttributes().getVoucher_number());
                appUser.arr_series.add(response.getVoucher_series().getData().get(i).getAttributes().getName());
                appUser.series_details.add(map);

            }
            LocalRepositories.saveAppUser(PosSettingActivity.this, appUser);
            mVoucherAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, appUser.arr_series);
            mVoucherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSeries.setAdapter(mVoucherAdapter);
            mSeries.setSelection(pos);
            mSeries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Map map = new HashMap();
                    map = appUser.series_details.get(position);
                    String auto_increament = (String) map.get("auto_increment");
                    String vch_number = (String) map.get("voucher_number");
                    if (auto_increament.equals("false")) {
                        mVchNumber.setEnabled(true);
                    } else {
                        mVchNumber.setEnabled(false);
                    }
                    mVchNumber.setText(vch_number);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });


            // mVchNumber.setText(response.get());

        } else {
            // Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            // set_date.setOnClickListener(this);
            Helpers.dialogMessage(PosSettingActivity.this, response.getMessage());
        }
    }
}
