package com.berylsystems.buzz.fragments.company.sale_return;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.account.ExpandableAccountListActivity;
import com.berylsystems.buzz.activities.company.administration.master.materialcentre.MaterialCentreListActivity;
import com.berylsystems.buzz.activities.company.administration.master.saletype.SaleTypeListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.purchase.CreatePurchaseResponce;
import com.berylsystems.buzz.networks.api_response.sale_return.CreateSaleReturnResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;

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

public class CreateSaleReturnFragment extends Fragment {
    @Bind(R.id.date)
    TextView mDate;
    @Bind(R.id.series)
    EditText mSeries;
    @Bind(R.id.vch_number)
    EditText mVchNumber;
    @Bind(R.id.sale_type)
    TextView mSaleType;
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

    AppUser appUser;
    private SimpleDateFormat dateFormatter;
    Animation blinkOnClick;


    private static void hideKeyPad(Activity activity){
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sales_return_create_voucher, container, false);
        hideKeyPad(getActivity());
        ButterKnife.bind(this, view);

        appUser = LocalRepositories.getAppUser(getActivity());
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar newCalendar = Calendar.getInstance();
                mDate.setText("01 Apr 2017");
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {

                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        String date = dateFormatter.format(newDate.getTime());
                        mDate.setText(date);
                        appUser.sale_return_date = date;
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
                startActivityForResult(new Intent(getContext(), MaterialCentreListActivity.class), 1);
            }
        });
        mSaleType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), SaleTypeListActivity.class), 2);
            }
        });
        mPartyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), ExpandableAccountListActivity.class), 3);
            }
        });

        cash.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        cash.setTextColor(Color.parseColor("#ffffff"));
        credit.setBackgroundColor(0);

        cash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser.sale_cash_credit =cash.getText().toString();
                LocalRepositories.saveAppUser(getActivity(), appUser);
                cash.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                credit.setBackgroundColor(0);
                cash.setTextColor(Color.parseColor("#ffffff"));//white
                credit.setTextColor(Color.parseColor("#000000"));//black
            }
        });
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser.sale_return_cash_credit =credit.getText().toString();
                LocalRepositories.saveAppUser(getActivity(), appUser);
                credit.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                cash.setBackgroundColor(0);
                credit.setTextColor(Color.parseColor("#ffffff"));//white
                cash.setTextColor(Color.parseColor("#000000"));//black
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.startAnimation(blinkOnClick);
                appUser.sale_return_series = mSeries.getText().toString();
                appUser.sale_return_vchNo = mVchNumber.getText().toString();
                if (appUser.sale_return_cash_credit.equals("")){
                    appUser.sale_return_cash_credit=cash.getText().toString();
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                }
                appUser.sale_return_mobileNumber = mMobileNumber.getText().toString();
                appUser.sale_return_narration = mNarration.getText().toString();
                LocalRepositories.saveAppUser(getActivity(), appUser);
                hideKeyPad(getActivity());
                if (appUser.sale_return_series.equals("")){
                    Snackbar.make(coordinatorLayout, "Series can't be empty", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (appUser.sale_return_date.equals("")){
                    Snackbar.make(coordinatorLayout, "Please select the date", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (appUser.sale_return_vchNo.equals("")){
                    Snackbar.make(coordinatorLayout, "Please enter vch number", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (appUser.sale_return_saleType.equals("")){
                    Snackbar.make(coordinatorLayout, "Please select sale type", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (appUser.sale_return_store.equals("")){
                    Snackbar.make(coordinatorLayout, "Please select store ", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (appUser.sale_return_partyName.equals("")){
                    Snackbar.make(coordinatorLayout, "Please select party name", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (appUser.sale_return_mobileNumber.equals("")){
                    Snackbar.make(coordinatorLayout, "Please enter mobile number", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (appUser.sale_return_narration.equals("")){
                    Snackbar.make(coordinatorLayout, "Please enter narration", Snackbar.LENGTH_LONG).show();
                    return;
                }
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_SALE_RETURN);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.sale_return_store=String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] name=result.split(",");
                mStore.setText(name[0]);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }
        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.sale_return_saleType=String.valueOf(id);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mSaleType.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }

        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.sale_return_partyName=id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                String[] strArr=result.split(",");
                mPartyName.setText(strArr[0]);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }
    }

    @Subscribe
    public void createSaleReturn(CreateSaleReturnResponse response) {
        if (response.getStatus() == 200) {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();

            mSeries.setText("");
            mDate.setText("");
            mVchNumber.setText("");
            mSaleType.setText("");
            mStore.setText("");
            mPartyName.setText("");
            mMobileNumber.setText("");
            mNarration.setText("");

        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }


    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }
    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }



}