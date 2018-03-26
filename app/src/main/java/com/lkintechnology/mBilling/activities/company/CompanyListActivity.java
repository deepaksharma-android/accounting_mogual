package com.lkintechnology.mBilling.activities.company;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.BaseActivity;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.adapters.CompanyListAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.company.CompanyAuthenticateResponse;
import com.lkintechnology.mBilling.networks.api_response.company.CompanyListResponse;
import com.lkintechnology.mBilling.networks.api_response.getcompany.CompanyResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventOpenCompany;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyListActivity extends BaseActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.company_list_recycler_view)
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    @Bind(R.id.search)
    LinearLayout mSearchButton;
    @Bind(R.id.floating_button)
    FloatingActionButton mFloatingButton;
    @Bind(R.id.searchtext)
    EditText mSearchText;
    @Bind(R.id.company_name)
    TextView mCompanyName;
    @Bind(R.id.company_address)
    TextView mCompany_address;
    @Bind(R.id.mainLayout)
    LinearLayout mMainLayout;
    @Bind(R.id.reset)
    TextView mReset;
    @Bind(R.id.top_layout)
    RelativeLayout mOverlayLayout;

    public Dialog dialog;
    CompanyListAdapter mAdapter;
    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_list);
        ButterKnife.bind(this);

        appUser=LocalRepositories.getAppUser(this);
        setNavigation(1);
        setAdd(0);
        setAppBarTitle(1, "COMPANY LIST");
        mFloatingButton.bringToFront();
        if (isFirstTime()) {
            mOverlayLayout.setVisibility(View.INVISIBLE);
        }
        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                if(!mSearchText.getText().toString().equals("")){
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if(isConnected) {
                        appUser.search_company_id=mSearchText.getText().toString();
                        mProgressDialog = new ProgressDialog(CompanyListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_SEARCH_COMPANY);
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
            }
        });

        mReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideSoftKeyboard(view);
                mReset.setVisibility(View.GONE);
                mMainLayout.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mSearchText.setText("");
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if(isConnected) {
                    mProgressDialog = new ProgressDialog(CompanyListActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_COMPANY_LIST);
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
    }
    @Subscribe
    public void getcompanydetail(CompanyResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            mReset.setVisibility(View.VISIBLE);
            mMainLayout.setVisibility(View.VISIBLE);
            mRecyclerView.setVisibility(View.GONE);
            appUser.company_name=response.getCompany().getData().getAttributes().getName();
            appUser.company_id=response.getCompany().getData().getId();
            LocalRepositories.saveAppUser(this,appUser);
            mCompanyName.setText(response.getCompany().getData().getAttributes().getName()+" "+"("+response.getCompany().getData().getAttributes().getUnique_id()+")");
            mCompany_address.setText(response.getCompany().getData().getAttributes().getAddress());
            mMainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showpopup();
                   /* if(response.getCompany().getData().getAttributes().getAuthorization()==true){
                        showpopup();
                    }
                    else{
                        startActivity(new Intent(getApplicationContext(),CompanyDashboardActivity.class));
                    }*/
                }
            });

            Preferences.getInstance(getApplicationContext()).setCid(response.getCompany().getData().getId());
            Preferences.getInstance(getApplicationContext()).setCname(Helpers.mystring(response.getCompany().getData().getAttributes().getName()));
            Preferences.getInstance(getApplicationContext()).setCprintname(Helpers.mystring(response.getCompany().getData().getAttributes().getPrint_name()));
            Preferences.getInstance(getApplicationContext()).setCshortname(Helpers.mystring(response.getCompany().getData().getAttributes().getShort_name()));
            Preferences.getInstance(getApplicationContext()).setCphonenumber(Helpers.mystring(response.getCompany().getData().getAttributes().getPhone_number()));
            Preferences.getInstance(getApplicationContext()).setCfinancialyear(Helpers.mystring(response.getCompany().getData().getAttributes().getFinancial_year_from()));
            Preferences.getInstance(getApplicationContext()).setCbookyear(Helpers.mystring(response.getCompany().getData().getAttributes().getBooks_commencing_from()));
            Preferences.getInstance(getApplicationContext()).setCcin(Helpers.mystring(response.getCompany().getData().getAttributes().getCin()));
            Preferences.getInstance(getApplicationContext()).setCpan(Helpers.mystring(response.getCompany().getData().getAttributes().getIt_pin()));
            Preferences.getInstance(getApplicationContext()).setCaddress(Helpers.mystring(response.getCompany().getData().getAttributes().getAddress()));
            Preferences.getInstance(getApplicationContext()).setCcountry(Helpers.mystring(response.getCompany().getData().getAttributes().getCountry()));
            Preferences.getInstance(getApplicationContext()).setCstate(Helpers.mystring(response.getCompany().getData().getAttributes().getState()));
            Preferences.getInstance(getApplicationContext()).setCindustrytype(Helpers.mystring(response.getCompany().getData().getAttributes().getIndustry_type()));
            Preferences.getInstance(getApplicationContext()).setCcity(Helpers.mystring(response.getCompany().getData().getAttributes().getCity()));
            Preferences.getInstance(getApplicationContext()).setCZipcode(Helpers.mystring(response.getCompany().getData().getAttributes().getZipcode()));
            Preferences.getInstance(getApplicationContext()).setCward(Helpers.mystring(response.getCompany().getData().getAttributes().getWard()));
            Preferences.getInstance(getApplicationContext()).setCfax(Helpers.mystring(response.getCompany().getData().getAttributes().getFax()));
            Preferences.getInstance(getApplicationContext()).setCemail(Helpers.mystring(response.getCompany().getData().getAttributes().getEmail()));
            Preferences.getInstance(getApplicationContext()).setCgst(Helpers.mystring(response.getCompany().getData().getAttributes().getGst()));
            Preferences.getInstance(getApplicationContext()).setCdealer(Helpers.mystring(response.getCompany().getData().getAttributes().getType_of_dealer()));
            Preferences.getInstance(getApplicationContext()).setCtax1(Helpers.mystring(response.getCompany().getData().getAttributes().getDefault_tax_rate1()));
            Preferences.getInstance(getApplicationContext()).setCtax2(Helpers.mystring(response.getCompany().getData().getAttributes().getDefault_tax_rate2()));
            Preferences.getInstance(getApplicationContext()).setCsymbol(Helpers.mystring(response.getCompany().getData().getAttributes().getCurrency_symbol()));
            Preferences.getInstance(getApplicationContext()).setCstring(Helpers.mystring(response.getCompany().getData().getAttributes().getCurrency_string()));
            Preferences.getInstance(getApplicationContext()).setCsubstring(Helpers.mystring(response.getCompany().getData().getAttributes().getCurrency_sub_string()));
            Preferences.getInstance(getApplicationContext()).setClogo(Helpers.mystring(response.getCompany().getData().getAttributes().getLogo()));
            Preferences.getInstance(getApplicationContext()).setCsign(Helpers.mystring(response.getCompany().getData().getAttributes().getSignature()));
            Preferences.getInstance(getApplicationContext()).setCusername(Helpers.mystring(response.getCompany().getData().getAttributes().getUsername()));
        }
        else{
            Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    public void showpopup(){
        dialog = new Dialog(CompanyListActivity.this);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login);
        dialog.setCancelable(true);
        // set the custom dialog components - text, image and button
        EditText username = (EditText) dialog.findViewById(R.id.cusername);
        EditText password = (EditText) dialog.findViewById(R.id.cpassword);
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
                hideSoftKeyboard(v);
                if(!username.getText().toString().equals("")){
                    if(!password.getText().toString().equals("")){
                        appUser.cusername=username.getText().toString();
                        appUser.cpassword=password.getText().toString();
                        LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if (isConnected) {
                            mProgressDialog = new ProgressDialog(CompanyListActivity.this);
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_COMPANY_AUTHENTICATE);
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
                        Toast.makeText(getApplicationContext(),"Ente password",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter username",Toast.LENGTH_LONG).show();
                }

            }
        });

        dialog.show();
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
        Preferences.getInstance(getApplicationContext()).setCname("");
        Preferences.getInstance(getApplicationContext()).setCprintname("");
        Preferences.getInstance(getApplicationContext()).setCshortname("");
        Preferences.getInstance(getApplicationContext()).setCphonenumber("");
        Preferences.getInstance(getApplicationContext()).setCfinancialyear("");
        Preferences.getInstance(getApplicationContext()).setCbookyear("");
        Preferences.getInstance(getApplicationContext()).setCcin("");
        Preferences.getInstance(getApplicationContext()).setCpan("");
        Preferences.getInstance(getApplicationContext()).setCaddress("");
        Preferences.getInstance(getApplicationContext()).setCcity("");
        Preferences.getInstance(getApplicationContext()).setCZipcode("");
        Preferences.getInstance(getApplicationContext()).setCcountry("");
        Preferences.getInstance(getApplicationContext()).setCstate("");
        Preferences.getInstance(getApplicationContext()).setCindustrytype("");
        Preferences.getInstance(getApplicationContext()).setCward("");
        Preferences.getInstance(getApplicationContext()).setCfax("");
        Preferences.getInstance(getApplicationContext()).setCemail("");
        Preferences.getInstance(getApplicationContext()).setCgst("");
        Preferences.getInstance(getApplicationContext()).setCdealer("");
        Preferences.getInstance(getApplicationContext()).setCtax1("");
        Preferences.getInstance(getApplicationContext()).setCtax2("");
        Preferences.getInstance(getApplicationContext()).setCsymbol("");
        Preferences.getInstance(getApplicationContext()).setCstring("");
        Preferences.getInstance(getApplicationContext()).setCsubstring("");
        Preferences.getInstance(getApplicationContext()).setClogo("");
        Preferences.getInstance(getApplicationContext()).setCsign("");
        Preferences.getInstance(getApplicationContext()).setCusername("");
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if(isConnected) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(this, appUser);
            ApiCallsService.action(this, Cv.ACTION_COMPANY_LIST);
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
        super.onResume();
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void add(View v) {
        Intent intent=new Intent(getApplicationContext(), CreateCompanyActivity.class);
        //EditCompanyActivity.data = null;

        Preferences.getInstance(getApplicationContext()).setCid("");
        appUser.logo="";
        appUser.signature="";
        LocalRepositories.saveAppUser(this,appUser);
        startActivity(intent);
    }




    @Subscribe
    public void getCompnayList(CompanyListResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            if(response.getCompanies().getData().size()>0) {
                mRecyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(layoutManager);
                mAdapter = new CompanyListAdapter(this, response.getCompanies().getData());
                mRecyclerView.setAdapter(mAdapter);
            }
            else{
                Snackbar.make(coordinatorLayout,"No Company Found!",Snackbar.LENGTH_LONG).show();
            }
        }
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }
    @Subscribe
    public void timout(String msg){
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }

    @Subscribe
    public void opencompany(EventOpenCompany pos){
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CompanyListActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_COMPANY_AUTHENTICATE);
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
            startActivity(new Intent(getApplicationContext(),FirstPageActivity.class));
            finish();
        }
        else{

            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    public void hideSoftKeyboard(View v) {
        InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private boolean isFirstTime()
    {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
            mOverlayLayout.setVisibility(View.VISIBLE);
            mOverlayLayout.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mOverlayLayout.setVisibility(View.INVISIBLE);
                    return false;
                }

            });


        }
        return ranBefore;

    }

}