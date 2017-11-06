package com.berylsystems.buzz.fragments.account;


import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountGeneralInfoFragment extends Fragment {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.credit_days_layout)
    LinearLayout mCreditDaysLayout;
    @Bind(R.id.bill_by_bill_spinner)
    Spinner mBillByBillSpinner;
    ArrayAdapter<String> mBillByBillAdapter;
    AppUser appUser;

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
        ButterKnife.bind(this, v);
        appUser= LocalRepositories.getAppUser(getActivity());
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
}