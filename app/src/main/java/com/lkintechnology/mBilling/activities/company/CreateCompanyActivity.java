package com.lkintechnology.mBilling.activities.company;

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
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.company.CreateCompanyResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;
import com.lkintechnology.mBilling.utils.Validation;

import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateCompanyActivity extends RegisterAbstractActivity implements View.OnClickListener {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.company_name)
    EditText mComapnyName;
    @Bind(R.id.phone)
    EditText mPhoneNumber;
    @Bind(R.id.financial_year)
    TextView mFinancialYear;
    @Bind(R.id.book_year)
    TextView mBookYear;
    @Bind(R.id.cin)
    EditText mCin;
    @Bind(R.id.pan)
    EditText mPan;
    @Bind(R.id.gst)
    EditText mGst;
    @Bind(R.id.address)
    EditText mAddress;
    @Bind(R.id.city)
    EditText mCity;
    @Bind(R.id.zip_code)
    EditText mzip_code;
    @Bind(R.id.state_spinner)
    Spinner mStateSpinner;
    @Bind(R.id.username)
    EditText mUserName;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.confirm_password)
    EditText mConfirmPassword;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    ArrayAdapter<String> spinnerStateAdapter;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog DatePickerDialog1, DatePickerDialog2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initActionbar();
        spinnerStateAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.layout_trademark_type_spinner_dropdown_item, getResources().getStringArray(R.array.state));
        spinnerStateAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mStateSpinner.setAdapter(spinnerStateAdapter);
        mStateSpinner.setSelection(12);
        appUser = LocalRepositories.getAppUser(getApplicationContext());
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setDateField();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mComapnyName.getText().toString().equals("")) {
                    if (Validation.isPhoneFormatValid(mPhoneNumber.getText().toString())) {
                        if (mCin.getText().toString().equals("") | mCin.getText().toString().length() == 21) {
                            if (mPan.getText().toString().equals("") | mPan.getText().toString().length() == 10) {
                                if (mGst.getText().toString().equals("") | mGst.getText().toString().length() == 15) {
                                    if (!mAddress.getText().toString().equals("")) {
                                        if (!mCity.getText().toString().equals("")) {
                                            if (!mUserName.getText().toString().equals("")) {
                                                if (!mPassword.getText().toString().equals("")) {
                                                    if (!mConfirmPassword.getText().toString().equals("")) {
                                                        if (mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
                                                            if (!mzip_code.getText().toString().equals("")) {
                                                                Boolean isConnected = ConnectivityReceiver.isConnected();
                                                                if (isConnected) {
                                                                    appUser.company_name = mComapnyName.getText().toString();
                                                                    appUser.comapny_phone_number = mPhoneNumber.getText().toString();
                                                                    appUser.financial_year_from = mFinancialYear.getText().toString();
                                                                    appUser.books_commencing_from = mBookYear.getText().toString();
                                                                    appUser.cin = mCin.getText().toString();
                                                                    appUser.it_pin = mPan.getText().toString();
                                                                    appUser.gst = mGst.getText().toString();
                                                                    appUser.address = mAddress.getText().toString();
                                                                    appUser.city = mCity.getText().toString();
                                                                    appUser.state = mStateSpinner.getSelectedItem().toString();
                                                                    appUser.companyUserName = mUserName.getText().toString();
                                                                    appUser.companyUserPassword = mPassword.getText().toString();
                                                                    appUser.companyzipcode=mzip_code.getText().toString();
                                                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                                                    mProgressDialog = new ProgressDialog(CreateCompanyActivity.this);
                                                                    mProgressDialog.setMessage("Info...");
                                                                    mProgressDialog.setIndeterminate(false);
                                                                    mProgressDialog.setCancelable(true);
                                                                    mProgressDialog.show();
                                                                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_COMPANY);
                                                                } else {
                                                                    snackbar = Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
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
                                                            else{
                                                                snackbar = Snackbar
                                                                        .make(coordinatorLayout, "Please enter pincode", Snackbar.LENGTH_LONG);
                                                                snackbar.show();
                                                            }
                                                        }else {
                                                            snackbar = Snackbar
                                                                    .make(coordinatorLayout, "Password does not match", Snackbar.LENGTH_LONG);
                                                            snackbar.show();
                                                        }
                                                    } else {
                                                        snackbar = Snackbar
                                                                .make(coordinatorLayout, "Please confirm the password", Snackbar.LENGTH_LONG);
                                                        snackbar.show();
                                                    }
                                                } else {
                                                    snackbar = Snackbar
                                                            .make(coordinatorLayout, "Enter Users password", Snackbar.LENGTH_LONG);
                                                    snackbar.show();
                                                }
                                            } else {
                                                snackbar = Snackbar
                                                        .make(coordinatorLayout, "Enter Users Username", Snackbar.LENGTH_LONG);
                                                snackbar.show();
                                            }
                                        } else {
                                            snackbar = Snackbar
                                                    .make(coordinatorLayout, "Enter City name", Snackbar.LENGTH_LONG);
                                            snackbar.show();
                                        }
                                    } else {
                                        snackbar = Snackbar
                                                .make(coordinatorLayout, "Enter address", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }

                                } else {
                                    snackbar = Snackbar
                                            .make(coordinatorLayout, "Enter valid gstin", Snackbar.LENGTH_LONG);
                                    snackbar.show();
                                }
                            } else {
                                snackbar = Snackbar
                                        .make(coordinatorLayout, "Enter valid pan number", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        } else {
                            snackbar = Snackbar
                                    .make(coordinatorLayout, "Enter valid cin number", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    } else {
                        snackbar = Snackbar
                                .make(coordinatorLayout, "Enter valid phone number", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } else {
                    snackbar = Snackbar
                            .make(coordinatorLayout, "Enter company Name", Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });


    }

    @Override
    protected int layoutId() {
        return R.layout.activity_create_company;
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
        actionbarTitle.setText("CREATE COMPANY");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void setDateField() {
        mFinancialYear.setOnClickListener(this);
        mBookYear.setOnClickListener(this);
        final Calendar newCalendar = Calendar.getInstance();
        String fixMonth = "Apr";
        int inputMonthPosition = inputMonthPosition(fixMonth);
        int currentMonthPosition = currentMonth();
        int currentYear = currentYear();

        if(inputMonthPosition>currentMonthPosition){
            mFinancialYear.setText("01 "+monthName[inputMonthPosition]+" "+(currentYear-1));
        }else {
            mFinancialYear.setText("01 "+monthName[inputMonthPosition]+" "+currentYear);
        }

        mBookYear.setText("01 "+monthName[inputMonthPosition]+" "+currentYear);


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
    public void onClick(View view) {
        if (view == mFinancialYear) {
            DatePickerDialog1.show();
        } else if (view == mBookYear) {
            DatePickerDialog2.show();
        }
    }

    @Subscribe
    public void createCompany(CreateCompanyResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {

            Preferences.getInstance(getApplicationContext()).setCid(String.valueOf(response.getId()));
            startActivity(new Intent(getApplicationContext(), FirstPageActivity.class));
            snackbar = Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
            finish();
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
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