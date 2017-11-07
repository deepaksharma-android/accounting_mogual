package com.berylsystems.buzz.fragments.account;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.company.administration.master.accountgroup.AccountGroupListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.account.CreateAccountResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.CreateAccountGroupResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountGeneralInfoFragment extends Fragment {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.credit_days_layout)
    LinearLayout mCreditDaysLayout;
    @Bind(R.id.credit_days_sale)
    EditText mCreditDaysSale;
    @Bind(R.id.credit_days_purchase)
    EditText mCreditDaysPurchase;
    @Bind(R.id.bill_by_bill_spinner)
    Spinner mBillByBillSpinner;
   /* @Bind(R.id.group_spinner)
    Spinner mSpinnerGroup;*/
    @Bind(R.id.groupLayout)
    LinearLayout mGroupLayout;
    @Bind(R.id.group_text)
    TextView mGroupText;
    @Bind(R.id.account_name)
    EditText mAccountName;
    @Bind(R.id.type_of_dealer_spinner)
    Spinner mSpinnerDealer;
    @Bind(R.id.opening_balance)
    EditText mOpeningBalance;
    @Bind(R.id.prev_year_balance)
    EditText mPreviousYearBalance;
    @Bind(R.id.radio_group_opening)
    RadioGroup mOpeningRadioGroup;
    @Bind(R.id.radio_group_prev_year)
    RadioGroup mPrevYearRadioGroup;
    @Bind(R.id.radio_opening_debit)
    RadioButton mRadioOpeningDebit;
    @Bind(R.id.radio_opening_credit)
    RadioButton mRadioOpeningCredit;
    @Bind(R.id.radio_prev_year_debit)
    RadioButton mRadioPrevYearDebit;
    @Bind(R.id.radio_prev_year_credit)
    RadioButton mRadioPrevYearCredit;
    @Bind(R.id.bank_account_number)
    EditText mBankAccountNumber;
    @Bind(R.id.ifsc_code)
    EditText mIfscCode;
    @Bind(R.id.bank_name)
    EditText mBankName;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    ArrayAdapter<String> mBillByBillAdapter;
    ArrayAdapter<String> mGroupAdapter;
    AppUser appUser;
    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    Boolean fromList;
    public AccountGeneralInfoFragment() {
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
        View v = inflater.inflate(R.layout.fragment_account_general_info, container, false);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this, v);
        appUser= LocalRepositories.getAppUser(getActivity());
        mGroupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), AccountGroupListActivity.class);
                intent.putExtra("frommaster",false);
                 startActivityForResult(intent,1);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mAccountName.getText().toString().equals("")){
                    if(!mGroupText.getText().toString().equals("")){
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if (isConnected) {
                            appUser.account_name=mAccountName.getText().toString();
                            appUser.account_type_of_dealer=mSpinnerDealer.getSelectedItem().toString();
                            appUser.acount_opening_balance=mOpeningBalance.getText().toString();
                            if(mRadioOpeningCredit.isActivated()){
                                appUser.acount_opening_balance_type="credit";
                            }
                           if(mRadioOpeningDebit.isActivated()){
                               appUser.acount_opening_balance_type="debit";
                           }
                            appUser.prev_year_balance=mPreviousYearBalance.getText().toString();
                            if(mRadioPrevYearCredit.isActivated()){
                                appUser.prev_year_balance_type="credit";
                            }
                            if(mRadioPrevYearDebit.isActivated()){
                                appUser.prev_year_balance_type="debit";
                            }
                            appUser.credit_days_for_sale=mCreditDaysSale.getText().toString();
                            appUser.credit_days_for_purchase= mCreditDaysPurchase.getText().toString();
                            appUser.bank_account_number=mBankAccountNumber.getText().toString();
                            appUser.bank_ifsc_code=mIfscCode.getText().toString();
                            appUser.bank_name=mBankName.getText().toString();
                            LocalRepositories.saveAppUser(getActivity(),appUser);
                            mProgressDialog = new ProgressDialog(getActivity());
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            ApiCallsService.action(getActivity(), Cv.ACTION_CREATE_ACCOUNT);
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
                }
            }
        });


       /* mGroupAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.layout_trademark_type_spinner_dropdown_item, appUser.group_name);
        mGroupAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mSpinnerGroup.setAdapter(mGroupAdapter);
        mSpinnerGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        mBillByBillAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.layout_trademark_type_spinner_dropdown_item, getResources().getStringArray(R.array.bill_by_bill_balancing));
        mBillByBillAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mBillByBillSpinner.setAdapter(mBillByBillAdapter);
        mBillByBillSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    mCreditDaysLayout.setVisibility(View.VISIBLE);
                } else {
                    mCreditDaysLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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


    @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (requestCode == 1) {
                if(resultCode == Activity.RESULT_OK){
                    String result=data.getStringExtra("name");
                    String id=data.getStringExtra("id");
                    appUser.create_account_group_id=id;
                    LocalRepositories.saveAppUser(getActivity(),appUser);
                    mGroupText.setText(result+id);
                    //ppUser.create_account_group_id= String.valueOf(appUser.arr_account_group_id.get(Integer.parseInt(result)));
                    //LocalRepositories.saveAppUser(getActivity(),appUser);
                }
                if (resultCode == Activity.RESULT_CANCELED) {
                    //Write your code if there's no result
                    mGroupText.setText("");
                }
            }
        }
    @Subscribe
    public void createaccount(CreateAccountResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            appUser.account_id= String.valueOf(response.getId());
            LocalRepositories.saveAppUser(getActivity(),appUser);
            TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
            tabhost.getTabAt(1).select();
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();

        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }
}