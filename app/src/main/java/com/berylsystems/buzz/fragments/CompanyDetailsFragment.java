package com.berylsystems.buzz.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.media.MediaMetadataCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.ConnectivityReceiver;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.company.CreateCompanyResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Validation;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyDetailsFragment extends Fragment {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.address)
    EditText mAddress;
    @Bind(R.id.city)
    EditText mCity;
    @Bind(R.id.country_spinner)
    Spinner mCountrySpinner;
    @Bind(R.id.state_spinner)
    Spinner mStateSpinner;
    @Bind(R.id.industry_spinner)
    Spinner mIndustrySpinner;
    @Bind(R.id.fax)
    EditText mFax;
    @Bind(R.id.email)
    EditText mEmail;
    @Bind(R.id.ward)
    EditText mWard;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    int pos;
    ArrayAdapter<String> spinnerAdapter;

    public CompanyDetailsFragment() {
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
        View v= inflater.inflate(R.layout.company_fragment_details, container, false);
        ButterKnife.bind(this,v);
        appUser = LocalRepositories.getAppUser(getActivity());
        spinnerAdapter = new ArrayAdapter<String>(getActivity(),
                R.layout.layout_trademark_type_spinner_dropdown_item,appUser.industry_type);
        spinnerAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mIndustrySpinner.setAdapter(spinnerAdapter);
        mIndustrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pos=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if(isConnected) {
                            appUser.fax=mFax.getText().toString();
                            appUser.company_email=mEmail.getText().toString();
                            appUser.address=mAddress.getText().toString();
                            appUser.country=mCountrySpinner.getSelectedItem().toString();
                            appUser.state=mStateSpinner.getSelectedItem().toString();
                            appUser.industryId= String.valueOf(appUser.industry_id.get(pos));
                            appUser.city=mCity.getText().toString();
                            appUser.ward=mWard.getText().toString();
                            LocalRepositories.saveAppUser(getActivity(),appUser);
                            mProgressDialog = new ProgressDialog(getActivity());
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            LocalRepositories.saveAppUser(getActivity(), appUser);
                            ApiCallsService.action(getActivity(), Cv.ACTION_CREATE_DETAILS);
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

  /*  @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }*/

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
            tabhost.getTabAt(2).select();
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
   /* @Subscribe
    public void timout(String msg){
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }*/
}