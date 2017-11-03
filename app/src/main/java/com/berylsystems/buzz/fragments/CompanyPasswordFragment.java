package com.berylsystems.buzz.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.ComapanyListActivity;
import com.berylsystems.buzz.activities.ConnectivityReceiver;
import com.berylsystems.buzz.activities.LandingPageActivity;
import com.berylsystems.buzz.adapters.CompanyListAdapter;
import com.berylsystems.buzz.adapters.CompanyLoginAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.company.CompanyAuthenticateResponse;
import com.berylsystems.buzz.networks.api_response.company.CreateCompanyResponse;
import com.berylsystems.buzz.networks.api_response.companylogin.CompanyLoginResponse;
import com.berylsystems.buzz.networks.api_response.companylogin.CompanyUserResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventEditLogin;
import com.berylsystems.buzz.utils.EventOpenCompany;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CompanyPasswordFragment extends Fragment {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    /*@Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.username)
    EditText mUserName;
    @Bind(R.id.password)
    EditText mPassword;
    @Bind(R.id.confirm_password)
    EditText mConfirmPassword;*/
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    @Bind(R.id.company_user_recycler_view)
    RecyclerView mRecyclerView;
    CompanyLoginAdapter mAdapter;
    @Bind(R.id.fab)
    FloatingActionButton mAddCompanyLogin;
    RecyclerView.LayoutManager layoutManager;
    Dialog dialog;
    public CompanyPasswordFragment() {
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
        View v= inflater.inflate(R.layout.company_fragment_password, container, false);
        ButterKnife.bind(this,v);
        appUser = LocalRepositories.getAppUser(getActivity());

        mAddCompanyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpopup();
            }
        });
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            ApiCallsService.action(getActivity(), Cv.ACTION_GET_COMPANY_USER);
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
       /* mUserName.setText(Preferences.getInstance(getActivity()).getCusername());
        if(!Preferences.getInstance(getActivity()).getCusername().equals("")){
            mPassword.setText("••••••••");
            mConfirmPassword.setText("••••••••");
        }
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard();
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if(isConnected) {
                    if (!mUserName.getText().toString().equals("")) {
                        if (!mPassword.getText().toString().equals("")) {
                            if (mPassword.getText().toString().equals(mConfirmPassword.getText().toString())) {
                                appUser.companyUserName = mUserName.getText().toString();
                                appUser.companyUserPassword = mPassword.getText().toString();
                                LocalRepositories.saveAppUser(getActivity(), appUser);
                                mProgressDialog = new ProgressDialog(getActivity());
                                mProgressDialog.setMessage("Info...");
                                mProgressDialog.setIndeterminate(false);
                                mProgressDialog.setCancelable(true);
                                mProgressDialog.show();
                                LocalRepositories.saveAppUser(getActivity(), appUser);
                                ApiCallsService.action(getActivity(), Cv.ACTION_CREATE_LOGIN);
                            } else {
                                snackbar = Snackbar
                                        .make(coordinatorLayout, "Password does not matches", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        } else {
                            snackbar = Snackbar
                                    .make(coordinatorLayout, "Please enter the password", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }
                    }
                    else{
                        snackbar = Snackbar
                                .make(coordinatorLayout, "Password can't be blank", Snackbar.LENGTH_LONG);
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

        });*/
        return v;
    }

    @Subscribe
    public void getcompanyuser(CompanyUserResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
           // mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter=new CompanyLoginAdapter(getActivity(),response.getCompany().getData().getAttributes().getUsername());
            mRecyclerView.setAdapter(mAdapter);
        }

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


    public void hideSoftKeyboard() {
        if(getActivity().getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showpopup(){
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.layout_company_login_diolog);
        dialog.setTitle("Company Login");
        dialog.setCancelable(true);
        // set the custom dialog components - text, image and button
        EditText username = (EditText) dialog.findViewById(R.id.cusername);
        EditText password = (EditText) dialog.findViewById(R.id.cpassword);
        EditText confirmpassword = (EditText) dialog.findViewById(R.id.cconfirmpassword);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);

        // if button is clicked, close the custom dialog
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!username.getText().toString().equals("")){
                    if(!password.getText().toString().equals("")){
                        if(password.getText().toString().equals(confirmpassword.getText().toString())){
                        appUser.companyUserName=username.getText().toString();
                        appUser.companyUserPassword=password.getText().toString();
                        LocalRepositories.saveAppUser(getActivity(),appUser);
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if (isConnected) {
                            mProgressDialog = new ProgressDialog(getActivity());
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            ApiCallsService.action(getActivity(), Cv.ACTION_CREATE_LOGIN);
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
                        else{
                            Toast.makeText(getActivity(),"Password does not match",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(getActivity(),"Ente password",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Enter username",Toast.LENGTH_LONG).show();
                }

            }
        });

        dialog.show();
    }

    @Subscribe
    public void createCompany(CompanyLoginResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            dialog.dismiss();
            startActivity(new Intent(getActivity().getApplicationContext(),LandingPageActivity.class));
            snackbar = Snackbar
                    .make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();

        }
        else {
            dialog.dismiss();
            snackbar = Snackbar
                    .make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    @Subscribe
    public void opencompany(EventEditLogin pos){
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(getActivity());
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            ApiCallsService.action(getActivity(), Cv.ACTION_EDIT_LOGIN);
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
    @Subscribe
    public void authenticate(CompanyAuthenticateResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            startActivity(new Intent(getActivity(),LandingPageActivity.class));
        }
        else{

            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }


}