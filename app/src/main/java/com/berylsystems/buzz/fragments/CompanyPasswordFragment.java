package com.berylsystems.buzz.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.ConnectivityReceiver;
import com.berylsystems.buzz.activities.LandingPageActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.company.CreateCompanyResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyPasswordFragment extends Fragment {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.username)
    EditText mUserName;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.confirm_password)
    EditText mConfirmPassword;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    public CompanyPasswordFragment() {
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
        View v= inflater.inflate(R.layout.company_fragment_password, container, false);
        ButterKnife.bind(this,v);
        EventBus.getDefault().register(this);
        appUser = LocalRepositories.getAppUser(getActivity());
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if(isConnected) {
                    if(mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
                        appUser.companyUserName = mUserName.getText().toString();
                        appUser.password = mPassword.getText().toString();
                        LocalRepositories.saveAppUser(getActivity(), appUser);
                        mProgressDialog = new ProgressDialog(getActivity());
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getActivity(), appUser);
                        ApiCallsService.action(getActivity(), Cv.ACTION_CREATE_LOGIN);
                    }
                    else{
                        snackbar = Snackbar
                                .make(coordinatorLayout, "Password does not matches", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
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
            startActivity(new Intent(getActivity().getApplicationContext(),LandingPageActivity.class));
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