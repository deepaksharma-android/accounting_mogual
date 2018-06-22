package com.lkintechnology.mBilling.fragments.company;

import android.app.Activity;
import android.app.ProgressDialog;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.EditCompanyActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.company.CreateCompanyResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.facebook.FacebookSdk.getApplicationContext;

public class CompanyBankDetailsFragment extends Fragment {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.bank_name)
    EditText mBankName;
    @Bind(R.id.bank_account_layout)
    LinearLayout mBankAccountLayout;
    @Bind(R.id.bank_account)
    EditText mBankAccount;
    @Bind(R.id.ifsc_code)
    EditText mIfscCode;
    @Bind(R.id.micr_code)
    EditText mMicrCode;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;

    public CompanyBankDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        EventBus.getDefault().register(this);
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_company_bank_details, container, false);
        ButterKnife.bind(this, v);
        appUser = LocalRepositories.getAppUser(getActivity());

        mBankName.setText(Preferences.getInstance(getApplicationContext()).getCbankname());
        mBankAccount.setText(Preferences.getInstance(getApplicationContext()).getCbankaccount());
        mIfscCode.setText(Preferences.getInstance(getApplicationContext()).getCifsccode());
        mMicrCode.setText(Preferences.getInstance(getApplicationContext()).getCmicrcode());

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {
                    appUser.bank_name = mBankName.getText().toString();
                    appUser.bank_account = mBankAccount.getText().toString();
                    appUser.bank_ifsc_code = mIfscCode.getText().toString();
                    appUser.bank_micr_code = mMicrCode.getText().toString();
                    LocalRepositories.saveAppUser(getActivity(), appUser);
                    mProgressDialog = new ProgressDialog(getActivity());
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(getActivity(), appUser);
                    ApiCallsService.action(getActivity(), Cv.ACTION_CREATE_BANK_DETAILS);
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

    @Subscribe
    public void createCompany(CreateCompanyResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
          /*  mBankName.setText("");
            mBankName.setText("");
            mIfscCode.setText("");
            mMicrCode.setText("");*/
            appUser.cid = String.valueOf(response.getId());
            LocalRepositories.saveAppUser(getActivity(), appUser);
            TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
            tabhost.getTabAt(3).select();
            //startActivity(new Intent(getApplicationContext(),CompanyDashboardActivity.class));
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();

        } else {
            // snackbar = Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG);
            // snackbar.show();
            Helpers.dialogMessage(getContext(), response.getMessage());
        }
    }

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }

    public void hideSoftKeyboard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}