package com.lkintechnology.mBilling.fragments.company;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.EditCompanyActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.CompanyDashboardActivity;
import com.lkintechnology.mBilling.adapters.CompanyLoginAdapter;
import com.lkintechnology.mBilling.adapters.UserFragmentAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.company.CompanyAuthenticateResponse;
import com.lkintechnology.mBilling.networks.api_response.companylogin.CompanyLoginResponse;
import com.lkintechnology.mBilling.networks.api_response.companylogin.CompanyUserResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventEditLogin;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.lkintechnology.mBilling.activities.company.EditCompanyActivity.fragPos;


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
    UserFragmentAdapter userFragmentAdapter;
    @Bind(R.id.fab)
    FloatingActionButton mAddCompanyLogin;
    RecyclerView.LayoutManager layoutManager;
    Dialog dialog;
    public CompanyPasswordFragment() {
        // Required empty public constructor
    }
    private static ArrayList<String> usernameList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onStart() {
        super.onStart();
//        EventBus.getDefault().register(this);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_company_password, container, false);
        ButterKnife.bind(this,v);
        appUser = LocalRepositories.getAppUser(getActivity());
        EventBus.getDefault().register(this);
        mAddCompanyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpopup();
            }
        });

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
    @Override
    public void setMenuVisibility(final boolean visible) {
        super.setMenuVisibility(visible);
        if (visible && isResumed()) {
          //  EventBus.getDefault().register(this);
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
        }

    }
    @Subscribe
    public void getcompanyuser(CompanyUserResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){

           // mRecyclerView.setHasFixedSize(true);
           // Toast.makeText(getActivity(), "Subscribe if", Toast.LENGTH_SHORT).show();
            if(response.getUsers().size()!=0){
                layoutManager = new LinearLayoutManager(getActivity());
                mRecyclerView.setLayoutManager(layoutManager);
                mAdapter=new CompanyLoginAdapter(getActivity(),response.getUsers());
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();



            }else {
                Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        } else {
           // Toast.makeText(getActivity(), "Subscribe else", Toast.LENGTH_SHORT).show();
            Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
        //EventBus.getDefault().unregister(this);
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


    public void hideSoftKeyboard(View v) {
        InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void showpopup(){
        dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_company_login_diolog);
        dialog.setTitle("Users Login");
        dialog.setCancelable(true);
        // set the custom dialog components - text, image and button
        EditText mobile = (EditText) dialog.findViewById(R.id.mobile);
        EditText username = (EditText) dialog.findViewById(R.id.cusername);
        EditText password = (EditText) dialog.findViewById(R.id.cpassword);
        EditText confirmpassword = (EditText) dialog.findViewById(R.id.cconfirmpassword);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                dialog.dismiss();
            }
        });

        // if button is clicked, close the custom dialog
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameList=new ArrayList<>();
                usernameList.add(username.getText().toString());
                if(!mobile.getText().toString().equals("")) {
                    if (!username.getText().toString().equals("")) {
                        if (!password.getText().toString().equals("")) {
                            if (password.getText().toString().equals(confirmpassword.getText().toString())) {
                                appUser.companyUserName = username.getText().toString();
                                appUser.companyUserPassword = password.getText().toString();
                                appUser.companymobile = mobile.getText().toString();
                                appUser.company_user=usernameList;
                                LocalRepositories.saveAppUser(getActivity(), appUser);
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


                            } else {
                                Toast.makeText(getActivity(), "Password does not match", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Ente password", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Enter username", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(), "Enter mobile number", Toast.LENGTH_LONG).show();
                }

            }
        });

        dialog.show();
    }

    @Subscribe
    public void createCompany(CompanyLoginResponse response){

        appUser=LocalRepositories.getAppUser(getActivity());
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            dialog.dismiss();
           // mHeaderViewPager.setCurrentItem(0);
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
               /* mProgressDialog = new ProgressDialog(getActivity());
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();*/
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
          /*  snackbar = Snackbar
                    .make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();*/

        }
        else {
            dialog.dismiss();
            snackbar = Snackbar
                    .make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

 /*   private int getItem(int i) {

            return mHeaderViewPager.getCurrentItem() + i;

    }*/

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
            startActivity(new Intent(getActivity(),CompanyDashboardActivity.class));
        }
        else{

            snackbar = Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
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