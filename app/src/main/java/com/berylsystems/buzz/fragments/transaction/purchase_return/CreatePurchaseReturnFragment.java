package com.berylsystems.buzz.fragments.transaction.purchase_return;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.company.administration.master.account.ExpandableAccountListActivity;
import com.berylsystems.buzz.activities.company.administration.master.materialcentre.MaterialCentreListActivity;
import com.berylsystems.buzz.activities.company.administration.master.saletype.SaleTypeListActivity;
import com.berylsystems.buzz.activities.dashboard.TransactionDashboardActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.GetVoucherNumbersResponse;
import com.berylsystems.buzz.networks.api_response.purchase_return.CreatePurchaseReturnResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.ParameterConstant;
import com.berylsystems.buzz.utils.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by BerylSystems on 11/22/2017.
 */

public class CreatePurchaseReturnFragment extends Fragment {
    @Bind(R.id.date)
    TextView mDate;
    @Bind(R.id.series)
    Spinner mSeries;
    @Bind(R.id.vch_number)
    TextView mVchNumber;
    @Bind(R.id.purchase_type)
    TextView mPurchaseType;
    @Bind(R.id.store)
    TextView mStore;
    @Bind(R.id.party_name)
    TextView mPartyName;
    @Bind(R.id.mobile_number)
    EditText mMobileNumber;
    @Bind(R.id.cash)
    Button cash;
    @Bind(R.id.credit)
    Button credit;
    @Bind(R.id.submit)
    LinearLayout submit;
    @Bind(R.id.narration)
    EditText mNarration;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    private SimpleDateFormat dateFormatter;
    Animation blinkOnClick;
    Snackbar snackbar;
    public static int intStartActivityForResult=0;
    public Boolean boolForPartyName=false;
    public Boolean boolForStore=false;

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_purchase_return_create, container, false);
        ButterKnife.bind(this, view);

        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_VOUCHER_NUMBERS);
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


        appUser = LocalRepositories.getAppUser(getActivity());
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        final Calendar newCalendar = Calendar.getInstance();
        String date1 = dateFormatter.format(newCalendar.getTime());
        Preferences.getInstance(getContext()).setVoucher_date(date1);
        mPurchaseType.setText(Preferences.getInstance(getContext()).getPurchase_return_type_name());
        mDate.setText(Preferences.getInstance(getContext()).getVoucher_date());
        mStore.setText(Preferences.getInstance(getContext()).getStore());
        mPartyName.setText(Preferences.getInstance(getContext()).getParty_name());
        mVchNumber.setText(Preferences.getInstance(getContext()).getVoucher_number());
        mMobileNumber.setText(Preferences.getInstance(getContext()).getMobile());
        mNarration.setText(Preferences.getInstance(getContext()).getNarration());
        if(Preferences.getInstance(getContext()).getCash_credit().equals("CASH")){
            cash.setBackgroundColor(Color.parseColor("#ababab"));
            cash.setTextColor(Color.parseColor("#ffffff"));
            credit.setBackgroundColor(0);
            credit.setTextColor(Color.parseColor("#000000"));
        }
        else if(Preferences.getInstance(getContext()).getCash_credit().equals("Credit")){
            credit.setBackgroundColor(Color.parseColor("#ababab"));
            cash.setBackgroundColor(0);
            credit.setTextColor(Color.parseColor("#ffffff"));//white
            cash.setTextColor(Color.parseColor("#000000"));//black
        }
        else{
            cash.setBackgroundColor(Color.parseColor("#ababab"));
            cash.setTextColor(Color.parseColor("#ffffff"));
            credit.setBackgroundColor(0);
            credit.setTextColor(Color.parseColor("#000000"));
        }
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String date = dateFormatter.format(newDate.getTime());
                        mDate.setText(date);
                        Preferences.getInstance(getContext()).setVoucher_date(date);
                        appUser.purchase_date = date;
                        LocalRepositories.saveAppUser(getActivity(), appUser);
                    }
                }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        blinkOnClick = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_on_click);
        mStore .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intStartActivityForResult=1;
                ParameterConstant.checkStartActivityResultForAccount =9;
                MaterialCentreListActivity.isDirectForMaterialCentre=false;
                startActivityForResult(new Intent(getContext(), MaterialCentreListActivity.class), 11);
            }
        });
        mPurchaseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaleTypeListActivity.isDirectForSaleType=false;
                startActivityForResult(new Intent(getContext(), SaleTypeListActivity.class), 22);
            }
        });
        mPartyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intStartActivityForResult=2;
                ParameterConstant.checkStartActivityResultForAccount =9;
                appUser.account_master_group = "";
                ExpandableAccountListActivity.isDirectForAccount=false;
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                startActivityForResult(new Intent(getContext(), ExpandableAccountListActivity.class), 33);
            }
        });
        appUser.purchase_payment_type=cash.getText().toString();
        LocalRepositories.saveAppUser(getApplicationContext(),appUser);

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Preferences.getInstance(getContext()).setCash_credit(cash.getText().toString());
                appUser.sale_cash_credit = cash.getText().toString();
                LocalRepositories.saveAppUser(getActivity(), appUser);
                cash.setBackgroundColor(Color.parseColor("#ababab"));
                credit.setBackgroundColor(0);
                cash.setTextColor(Color.parseColor("#ffffff"));//white
                credit.setTextColor(Color.parseColor("#000000"));//black
            }
        });
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preferences.getInstance(getContext()).setCash_credit(credit.getText().toString());
                appUser.sale_cash_credit = credit.getText().toString();
                LocalRepositories.saveAppUser(getActivity(), appUser);
                credit.setBackgroundColor(Color.parseColor("#ababab"));
                cash.setBackgroundColor(0);
                credit.setTextColor(Color.parseColor("#ffffff"));//white
                cash.setTextColor(Color.parseColor("#000000"));//black
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.startAnimation(blinkOnClick);
                if(appUser.mListMapForItemPurchaseReturn.size()>0) {
                    if (!mSeries.getSelectedItem().toString().equals("")) {
                        if (!mDate.getText().toString().equals("")) {
                            if (!mVchNumber.getText().toString().equals("")) {
                                if (!mPurchaseType.getText().toString().equals("")) {
                                    if (!mStore.getText().toString().equals("")) {
                                        if (!mPartyName.getText().toString().equals("")) {
                                          /*  if (!mMobileNumber.getText().toString().equals("")) {*/
                                                appUser.purchase_voucher_series = mSeries.getSelectedItem().toString();
                                                appUser.purchase_voucher_number = mVchNumber.getText().toString();
                                                appUser.purchase_mobile_number = mMobileNumber.getText().toString();
                                                appUser.purchase_narration = mNarration.getText().toString();
                                                LocalRepositories.saveAppUser(getActivity(), appUser);
                                                mProgressDialog = new ProgressDialog(getActivity());
                                                mProgressDialog.setMessage("Info...");
                                                mProgressDialog.setIndeterminate(false);
                                                mProgressDialog.setCancelable(true);
                                                mProgressDialog.show();
                                                ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_PURCHASE_RETURN);
                                                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_VOUCHER_NUMBERS);

                                           /* } else {
                                                Snackbar.make(coordinatorLayout, "Please enter mobile number", Snackbar.LENGTH_LONG).show();
                                            }*/
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
                                Snackbar.make(coordinatorLayout, "Please enter vch number", Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            Snackbar.make(coordinatorLayout, "Please select the date", Snackbar.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Snackbar.make(coordinatorLayout, "Please select the series", Snackbar.LENGTH_LONG).show();
                    }
                }
                else{
                    Snackbar.make(coordinatorLayout, "Please select the item", Snackbar.LENGTH_LONG).show();
                }


            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.purchase_material_center_id=String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name=result.split(",");
                mStore.setText(name[0]);
                boolForStore=true;
                Preferences.getInstance(getContext()).setStore(name[0]);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }
        if (requestCode == 22) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.purchase_puchase_type_id=String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mPurchaseType.setText(result);
                Preferences.getInstance(getContext()).setPurchase_return_type_name(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }

        if (requestCode == 33) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                String mobile = data.getStringExtra("mobile");
                appUser.purchase_account_master_id=id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] strArr=result.split(",");
                mPartyName.setText(strArr[0]);
                mMobileNumber.setText(mobile);
                boolForPartyName=true;
                Preferences.getInstance(getContext()).setMobile(mobile);
                Preferences.getInstance(getContext()).setParty_name(strArr[0]);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mPartyName.setText("");
            }
        }
    }

    @Subscribe
    public void createpurchase(CreatePurchaseReturnResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            mPartyName.setText("");
            mMobileNumber.setText("");
            mNarration.setText("");
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            //startActivity(new Intent(getApplicationContext(), TransactionDashboardActivity.class));
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }
    
    @Subscribe
    public void getVoucherNumber(GetVoucherNumbersResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            mVchNumber.setText(response.getVoucher_number());

        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            // set_date.setOnClickListener(this);
        }
    }



    public void onPause() {
        Preferences.getInstance(getContext()).setVoucher_number(mVchNumber.getText().toString());
        Preferences.getInstance(getContext()).setNarration(mNarration.getText().toString());
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getActivity().getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);
        if (bool) {

            if (intStartActivityForResult==1){
                boolForPartyName=true;
            }else if (intStartActivityForResult==2){
                boolForStore=true;
            }
            if (!boolForPartyName) {
                // Toast.makeText(getContext(), "Resume Party", Toast.LENGTH_SHORT).show();
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                String mobile = intent.getStringExtra("mobile");
                appUser.sale_partyName = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] strArr = result.split(",");
                mPartyName.setText(strArr[0]);
                mMobileNumber.setText(mobile);
                Preferences.getInstance(getContext()).setMobile(mobile);
                Preferences.getInstance(getContext()).setParty_name(strArr[0]);

            }
            if (!boolForStore) {
                //Toast.makeText(getContext(), "Resume Store", Toast.LENGTH_SHORT).show();
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                appUser.sale_store = String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name = result.split(",");
                mStore.setText(name[0]);
                Preferences.getInstance(getContext()).setStore(name[0]);

            }
        }

    }
}
