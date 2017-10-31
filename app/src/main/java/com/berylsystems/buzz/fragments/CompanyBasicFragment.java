package com.berylsystems.buzz.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.ConnectivityReceiver;
import com.berylsystems.buzz.activities.LandingPageActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.company.CreateCompanyResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Validation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyBasicFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.company_name)
    EditText mComapnyName;
    @Bind(R.id.print_name)
    EditText mPrintName;
    @Bind(R.id.phone)
    EditText mPhoneNumber;
    @Bind(R.id.short_name)
    EditText mShortName;
    @Bind(R.id.financial_year)
    TextView mFinancialYear;
    @Bind(R.id.book_year)
    TextView mBookYear;
    @Bind(R.id.cin)
    EditText mCin;
    @Bind(R.id.pan)
    EditText mPan;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog1, DatePickerDialog2;
    public CompanyBasicFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.company_fragment_basic, container, false);
        ButterKnife.bind(this,v);
        EventBus.getDefault().register(this);
        appUser = LocalRepositories.getAppUser(getActivity());
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setDateField();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mComapnyName.getText().toString().equals("")){
                    if(Validation.isPhoneFormatValid(mPhoneNumber.getText().toString())){
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if(isConnected) {
                            appUser.company_name=mComapnyName.getText().toString();
                            appUser.print_name=mPrintName.getText().toString();
                            appUser.comapny_phone_number=mPhoneNumber.getText().toString();
                            appUser.short_name=mShortName.getText().toString();
                            appUser.financial_year_from=mFinancialYear.getText().toString();
                            appUser.books_commencing_from=mBookYear.getText().toString();
                            appUser.cin=mCin.getText().toString();
                            appUser.it_pin=mPan.getText().toString();

                            LocalRepositories.saveAppUser(getActivity(),appUser);
                            mProgressDialog = new ProgressDialog(getActivity());
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            LocalRepositories.saveAppUser(getActivity(), appUser);
                            ApiCallsService.action(getActivity(), Cv.ACTION_CREATE_COMPANY);
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
                    }
                    else{
                        snackbar = Snackbar
                                .make(coordinatorLayout, "Enter phone number", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                }
                else{
                    snackbar = Snackbar
                            .make(coordinatorLayout, "Enter company Name", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        return v;

    }



 /*   @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }*/

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void setDateField() {
        mFinancialYear.setOnClickListener(this);
        mBookYear.setOnClickListener(this);
        final Calendar newCalendar = Calendar.getInstance();

        String date1 = dateFormatter.format(newCalendar.getTime());
        mFinancialYear.setText(date1);
        mBookYear.setText(date1);


        DatePickerDialog1 = new DatePickerDialog(getActivity(), new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                mFinancialYear.setText(date1);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog2 = new DatePickerDialog(getActivity(), new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                mBookYear.setText(date1);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        if (view == mFinancialYear) {
            DatePickerDialog1.show();
        } else if (view == mBookYear) {
            DatePickerDialog2.show();
        }
    }


    @Subscribe
    public void createCompany(CreateCompanyResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){

            appUser.cname.add(mComapnyName.getText().toString());
            appUser.cid= String.valueOf(response.getId());
            LocalRepositories.saveAppUser(getActivity(),appUser);
            TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
            tabhost.getTabAt(1).select();
            //startActivity(new Intent(getApplicationContext(),LandingPageActivity.class));
            snackbar = Snackbar
                    .make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();

        }
        else {
            snackbar = Snackbar
                    .make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
    @Subscribe
    public void timout(String msg){
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }
}