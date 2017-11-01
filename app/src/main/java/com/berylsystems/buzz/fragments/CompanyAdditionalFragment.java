package com.berylsystems.buzz.fragments;


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
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.ConnectivityReceiver;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.company.CreateCompanyResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CompanyAdditionalFragment extends Fragment {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.symbol_spinner)
    Spinner mSymbolSpinner;
    @Bind(R.id.string_spinner)
    Spinner mCurrentcyStringSpinner;
    @Bind(R.id.substring_spinner)
    Spinner mCurrencySubStringSpinner;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    ArrayAdapter<String> spinnerCurrencySymbolAdapter;
    ArrayAdapter<String> spinnerCurrenctStringAdapter;
    ArrayAdapter<String> spinnerCurrencySubStringAdapter;



    public CompanyAdditionalFragment() {
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
        View v= inflater.inflate(R.layout.company_fragment_additional, container, false);
        ButterKnife.bind(this,v);
        appUser = LocalRepositories.getAppUser(getActivity());
        spinnerCurrencySymbolAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.layout_trademark_type_spinner_dropdown_item,getResources().getStringArray(R.array.symbol));
        spinnerCurrencySymbolAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mSymbolSpinner.setAdapter(spinnerCurrencySymbolAdapter);

        spinnerCurrenctStringAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.layout_trademark_type_spinner_dropdown_item,getResources().getStringArray(R.array.currencystring));
        spinnerCurrenctStringAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mCurrentcyStringSpinner.setAdapter(spinnerCurrenctStringAdapter);

        spinnerCurrencySubStringAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.layout_trademark_type_spinner_dropdown_item,getResources().getStringArray(R.array.currencysubstring));
        spinnerCurrencySubStringAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mCurrencySubStringSpinner.setAdapter(spinnerCurrencySubStringAdapter);

        if(!Preferences.getInstance(getActivity()).getCsymbol().equals("")) {
            String symbol = Preferences.getInstance(getActivity()).getCsymbol().trim();// insert code here
            int symbolindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.symbol).length; i++) {
                if (getResources().getStringArray(R.array.symbol)[i].equals(symbol)) {
                    symbolindex = i;
                    break;
                }
            }
            Timber.i("INDEX" + symbolindex);
            mSymbolSpinner.setSelection(symbolindex);
        }
        if(!Preferences.getInstance(getActivity()).getCstring().equals("")) {
            String currencystring = Preferences.getInstance(getActivity()).getCstring().trim();// insert code here
            int currencystringindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.currencystring).length; i++) {
                if (getResources().getStringArray(R.array.currencystring)[i].equals(currencystring)) {
                    currencystringindex = i;
                    break;
                }
            }
            Timber.i("INDEX" + currencystring);
            mCurrentcyStringSpinner.setSelection(currencystringindex);
        }
        if(!Preferences.getInstance(getActivity()).getCsubstring().equals("")) {
            String currencysubstring = Preferences.getInstance(getActivity()).getCsubstring().trim();// insert code here
            int currencysubstringindex = -1;
            for (int i = 0; i < getResources().getStringArray(R.array.currencysubstring).length; i++) {
                if (getResources().getStringArray(R.array.currencysubstring)[i].equals(currencysubstring)) {
                    currencysubstringindex = i;
                    break;
                }
            }
            Timber.i("INDEX" + currencysubstringindex);
            mCurrencySubStringSpinner.setSelection(currencysubstringindex);
        }
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if(isConnected) {
                    appUser.currency_information=mSymbolSpinner.getSelectedItem().toString();
                    appUser.currenyString=mCurrentcyStringSpinner.getSelectedItem().toString();
                    appUser.currenySubString=mCurrencySubStringSpinner.getSelectedItem().toString();
                    LocalRepositories.saveAppUser(getActivity(),appUser);
                    mProgressDialog = new ProgressDialog(getActivity());
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(getActivity(), appUser);
                    ApiCallsService.action(getActivity(), Cv.ACTION_CREATE_ADDITIONAL);
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
    public void createCompany(CreateCompanyResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){

            appUser.cid= String.valueOf(response.getId());
            LocalRepositories.saveAppUser(getActivity(),appUser);
            TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
            tabhost.getTabAt(4).select();
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
    public void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }
}