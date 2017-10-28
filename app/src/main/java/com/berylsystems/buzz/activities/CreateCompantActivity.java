package com.berylsystems.buzz.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.company.CreateCompanyResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Validation;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateCompantActivity extends RegisterAbstractActivity implements View.OnClickListener {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.company_name)
    EditText mComapnyName;
    @Bind(R.id.print_name)
    EditText mPrintName;
    @Bind(R.id.phone)
    EditText mPhoneNumber;
    @Bind(R.id.fax)
    EditText mFax;
    @Bind(R.id.email)
    EditText mEmail;
    @Bind(R.id.short_name)
    EditText mShortName;
    @Bind(R.id.address)
    EditText mAddress;
    @Bind(R.id.country_spinner)
    Spinner mCountrySpinner;
    @Bind(R.id.state_spinner)
    Spinner mStateSpinner;
    @Bind(R.id.financial_year)
    EditText mFinancialYear;
    @Bind(R.id.book_year)
    EditText mBookYear;
    @Bind(R.id.cin)
    EditText mCin;
    @Bind(R.id.pan)
    EditText mPan;
    @Bind(R.id.ward)
    EditText mWard;
    @Bind(R.id.symbol_spinner)
    Spinner mSymbolSpinner;
    @Bind(R.id.string_spinner)
    Spinner mCurrentcyStringSpinner;
    @Bind(R.id.substring_spinner)
    Spinner mCurrencySubStringSpinner;
    @Bind(R.id.gst)
    EditText mGst;
    @Bind(R.id.dealer_spinner)
    Spinner mDealerSpinner;
    @Bind(R.id.default1)
    EditText mDefaultTax1;
    @Bind(R.id.default2)
    EditText mDefaultTax2;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog1, DatePickerDialog2, DatePickerDialog3;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        mFinancialYear.setInputType(InputType.TYPE_NULL);
        mBookYear.setInputType(InputType.TYPE_NULL);
        setDateField();

    }

    private void setDateField() {
        mFinancialYear.setOnClickListener(this);
        mBookYear.setOnClickListener(this);
        final Calendar newCalendar = Calendar.getInstance();

        String date1 = dateFormatter.format(newCalendar.getTime());
        mFinancialYear.setText(date1);
        mBookYear.setText(date1);


        DatePickerDialog1 = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String date1 = dateFormatter.format(newDate.getTime());
                mFinancialYear.setText(date1);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog2 = new DatePickerDialog(this, new android.app.DatePickerDialog.OnDateSetListener() {

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
            DatePickerDialog1.getDatePicker().setMaxDate(System.currentTimeMillis());
            DatePickerDialog1.show();
        } else if (view == mBookYear) {
            DatePickerDialog2.getDatePicker().setMaxDate(System.currentTimeMillis());
            DatePickerDialog2.show();
        }
    }


    @Override
    protected int layoutId() {
        return R.layout.activity_create_company;
    }

    public void submit(View v){

        if(!mComapnyName.getText().toString().equals("")){
            if(Validation.isPhoneFormatValid(mPhoneNumber.getText().toString())){
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if(isConnected) {
                    appUser.company_name=mComapnyName.getText().toString();
                    appUser.print_name=mPrintName.getText().toString();
                    appUser.comapny_phone_number=mPhoneNumber.getText().toString();
                    appUser.fax=mFax.getText().toString();
                    appUser.company_email=mEmail.getText().toString();
                    appUser.short_name=mShortName.getText().toString();
                    appUser.address=mAddress.getText().toString();
                    appUser.country=mCountrySpinner.getSelectedItem().toString();
                    appUser.state=mStateSpinner.getSelectedItem().toString();
                    appUser.financial_year_from=mFinancialYear.getText().toString();
                    appUser.books_commencing_from=mBookYear.getText().toString();
                    appUser.cin=mCin.getText().toString();
                    appUser.it_pin=mPan.getText().toString();
                    appUser.ward=mWard.getText().toString();
                    appUser.currency_information=mSymbolSpinner.getSelectedItem().toString();
                    appUser.currenyString=mCurrentcyStringSpinner.getSelectedItem().toString();
                    appUser.currenySubString=mCurrencySubStringSpinner.getSelectedItem().toString();
                    appUser.gst=mGst.getText().toString();
                    appUser.type_of_dealer=mDealerSpinner.getSelectedItem().toString();
                    appUser.default_tax_rate1=mDefaultTax1.getText().toString();
                    appUser.default_tax_rate2=mDefaultTax2.getText().toString();
                    LocalRepositories.saveAppUser(this,appUser);
                    mProgressDialog = new ProgressDialog(CreateCompantActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(this, appUser);
                    ApiCallsService.action(this, Cv.ACTION_CREATE_COMPANY);
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

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009DE0")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("CREATE COMPANY");
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void createCompany(CreateCompanyResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){

            appUser.cname.add(mComapnyName.getText().toString());
            LocalRepositories.saveAppUser(this,appUser);
            startActivity(new Intent(getApplicationContext(),LandingPageActivity.class));
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
}