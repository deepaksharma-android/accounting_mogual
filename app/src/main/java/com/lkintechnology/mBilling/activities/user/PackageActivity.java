package com.lkintechnology.mBilling.activities.user;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.CompanyListActivity;
import com.lkintechnology.mBilling.adapters.PackagesItemAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.packages.GetPackageResponse;
import com.lkintechnology.mBilling.networks.api_response.packages.PlanResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PackageActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    ArrayList<String> mSpinnerPackageList;
    ArrayList<String> mSpinnerPackageListId;
    ArrayList<String> arrPackagePrice;
    ArrayList<String> arrItemsMessage;
    ArrayList<String> arrItemsName;
    ArrayList<ArrayList<String>> arrTotalItemsName;
    ArrayList<ArrayList<String>> arrTotalItemsMessage;
    ArrayAdapter<String> spinnerAdapter;
    @Bind(R.id.layout_popular)
    LinearLayout mPopularLayout;
    @Bind(R.id.spinner_package)
    Spinner mSpinnerPackage;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar snackbar;
    PackagesItemAdapter mAdapter;
    @Bind(R.id.list_view_item)
    ListView mItemList;
    @Bind(R.id.packageAmount)
    TextView mPackageAmount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(PackageActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            ApiCallsService.action(this, Cv.ACTION_GET_PACKAGES);
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

    public void submit(View v){
        Dialog dialogbal = new Dialog(PackageActivity.this);
        dialogbal.setContentView(R.layout.dialog_package_serial_number);
        dialogbal.setCancelable(true);
        EditText serial = (EditText) dialogbal.findViewById(R.id.serial);
        RadioGroup radiogrp = (RadioGroup) dialogbal.findViewById(R.id.radioGroup);
        RadioButton cash = (RadioButton) dialogbal.findViewById(R.id.radioButtonCash);
        RadioButton cheque = (RadioButton) dialogbal.findViewById(R.id.radioButtonCheque);
        LinearLayout submit = (LinearLayout) dialogbal.findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.package_serail_number = serial.getText().toString();
                appUser.package_mode = String.valueOf(radiogrp.getCheckedRadioButtonId());
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    mProgressDialog = new ProgressDialog(PackageActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_PLANS);
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
                dialogbal.dismiss();
            }
        });
        dialogbal.show();

    }


    @Override
    protected int layoutId() {
        return R.layout.activity_package;
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
        actionbarTitle.setText("CHOOSE PACKAGE");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void getpackage(GetPackageResponse response) {
        mProgressDialog.dismiss();
        mSpinnerPackageList = new ArrayList<String>();
        mSpinnerPackageListId = new ArrayList<String>();
        arrPackagePrice = new ArrayList<>();
        arrTotalItemsName = new ArrayList<>();
        arrTotalItemsMessage = new ArrayList<>();
        if (response.getStatus() == 200) {
            for (int i = 0; i < response.getPlan().getData().size(); i++) {
                mSpinnerPackageListId.add(response.getPlan().getData().get(i).getId());
                mSpinnerPackageList.add(response.getPlan().getData().get(i).getAttributes().getName());
                arrPackagePrice.add(String.valueOf(response.getPlan().getData().get(i).getAttributes().getAmount()));
                arrItemsName = new ArrayList<>();
                arrItemsMessage = new ArrayList<>();
                for (int j = 0; j < response.getPlan().getData().get(i).getAttributes().getFeatures().size(); j++) {
                    arrItemsName.add(response.getPlan().getData().get(i).getAttributes().getFeatures().get(j).getName());
                    arrItemsMessage.add(response.getPlan().getData().get(i).getAttributes().getFeatures().get(j).getDescription());

                }
                arrTotalItemsName.add(arrItemsName);
                arrTotalItemsMessage.add(arrItemsMessage);


            }


            spinnerAdapter = new ArrayAdapter<String>(this,
                    R.layout.layout_trademark_type_spinner_dropdown_item, mSpinnerPackageList);
            spinnerAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mSpinnerPackage.setAdapter(spinnerAdapter);

            mSpinnerPackage.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    appUser.package_id=mSpinnerPackageListId.get(i);
                    appUser.package_amount=arrPackagePrice.get(i);
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                    mPackageAmount.setText(arrPackagePrice.get(i));
                    mAdapter = new PackagesItemAdapter(PackageActivity.this, response.getPlan().getData().get(i).getAttributes().getFeatures());
                    mItemList.setAdapter(mAdapter);


                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
    }

    @Subscribe
    public void plans(PlanResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Preferences.getInstance(getApplicationContext()).setLogin(true);
            Intent intent = new Intent(getApplicationContext(), CompanyListActivity.class);
            startActivity(intent);
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