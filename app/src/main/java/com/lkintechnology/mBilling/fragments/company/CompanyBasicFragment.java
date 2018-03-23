package com.lkintechnology.mBilling.fragments.company;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.company.CreateCompanyResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.Validation;

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
    public void onStart() {

        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_company_basic, container, false);
        ButterKnife.bind(this,v);
        EventBus.getDefault().register(this);
        mComapnyName.setText(Preferences.getInstance(getActivity()).getCname());
        mPrintName.setText(Preferences.getInstance(getActivity()).getCprintname());
        mShortName.setText(Preferences.getInstance(getActivity()).getCshortname());
        mPhoneNumber.setText(Preferences.getInstance(getActivity()).getCphonenumber());
        mFinancialYear.setText(Preferences.getInstance(getActivity()).getCfinancialyear());
        mBookYear.setText(Preferences.getInstance(getActivity()).getCbookyear());
        mCin.setText(Preferences.getInstance(getActivity()).getCcin());
        mPan.setText(Preferences.getInstance(getActivity()).getCpan());
        appUser = LocalRepositories.getAppUser(getActivity());
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        setDateField();
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
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
                            ApiCallsService.action(getActivity(), Cv.ACTION_CREATE_BASIC);
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



    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

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
    public void createCompany(CreateCompanyResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Preferences.getInstance(getActivity()).setCid(String.valueOf(response.getId()));
            TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
            tabhost.getTabAt(1).select();
            //startActivity(new Intent(getApplicationContext(),CompanyDashboardActivity.class));
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
    public void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}