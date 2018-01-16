package com.lkintechnology.mBilling.activities.dashboard;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.CompanyListActivity;
import com.lkintechnology.mBilling.activities.company.DefaultItemsExpandableListActivity;
import com.lkintechnology.mBilling.activities.company.EditCompanyActivity;
import com.lkintechnology.mBilling.activities.company.FirstPageActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.company.CompanyAuthenticateResponse;
import com.lkintechnology.mBilling.networks.api_response.company.DeleteCompanyResponse;
import com.lkintechnology.mBilling.networks.api_response.company.IndustryTypeResponse;
import com.lkintechnology.mBilling.networks.api_response.getcompany.CompanyResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyDashboardActivity extends AppCompatActivity {
    //public CompanyData data;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
   /* @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;*/
   // RecyclerView.LayoutManager layoutManager;
   // CompanyDashboardAdapter mAdapter;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    AppUser appUser;
  /*  @Bind(R.id.layout_open)
    LinearLayout mOpen;*/
    @Bind(R.id.deleteCompanyLayout)
    LinearLayout mDeleteCompanyLayout;
    @Bind(R.id.editCompanyLayout)
    LinearLayout mEditCompanyLayout;
    @Bind(R.id.syncTallyCompanyLayout)
    LinearLayout mSyncTallyCompanyLayout;
    @Bind(R.id.syncBusyCompanyLayout)
    LinearLayout mSyncBusyCompanyLayout;
    @Bind(R.id.syncItemsCompanyLayout)
    LinearLayout mSyncItemsCompanyLayout;
   /* @Bind(R.id.layout_edit)
    LinearLayout mEdit;
    @Bind(R.id.layout_delete)
    LinearLayout mDelete;
    @Bind(R.id.layout_backup)
    LinearLayout mBackup;*/
   /* @Bind(R.id.openImage)
    ImageView mOPenImage;
    @Bind(R.id.openText)
    TextView mOpenText;
    @Bind(R.id.editImage)
    ImageView mEditImage;
    @Bind(R.id.editText)
    TextView mEditText;
    @Bind(R.id.deleteImage)
    ImageView mDeleteImage;
    @Bind(R.id.deleteText)
    TextView mDeleteText;*/
    Dialog dialog;


   /* int[] myImageList = new int[]{R.drawable.icon_administration, R.drawable.icon_transaction, R.drawable.icon_display, R.drawable.icon_printer, R.drawable.icon_favorites};
    private String[] title = {
            "ADMINISTRATION",
            "TRANSACTION",
            "DISPLAY",
            "PRINT/EMAIL/SMS",
            "FAVOURITES"
    };
*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
/*        mOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
        mEditCompanyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditCompanyActivity.class);
                //EditCompanyActivity.data = data;
                startActivity(intent);
            }
        });
        mDeleteCompanyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showpopup();
            }
        });
      /*  mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new CompanyDashboardAdapter(this, title, myImageList);
        mRecyclerView.setAdapter(mAdapter);*/

        //get a company details api

        mSyncBusyCompanyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(CompanyDashboardActivity.this)
                        .setTitle("m-Billing")
                        .setMessage("Coming soon")
                        .setPositiveButton(null, null)
                        .setNegativeButton(R.string.btn_ok,null)
                        .show();

            }
        });

        mSyncTallyCompanyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(CompanyDashboardActivity.this)
                        .setTitle("m-Billing")
                        .setMessage("Coming soon")
                        .setPositiveButton(null, null)
                        .setNegativeButton(R.string.btn_ok,null)
                        .show();

            }
        });

        mSyncItemsCompanyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DefaultItemsExpandableListActivity.class);
                startActivity(intent);
            }
        });


    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText(appUser.company_name);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(CompanyDashboardActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            ApiCallsService.action(this, Cv.ACTION_GET_COMPANY);
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


    @Subscribe
    public void getcompanydetail(CompanyResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            LocalRepositories.saveAppUser(this, appUser);
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
            Preferences.getInstance(getApplicationContext()).setCcity(Helpers.mystring(response.getCompany().getData().getAttributes().getCity()));
            Preferences.getInstance(getApplicationContext()).setCindustrytype(Helpers.mystring(response.getCompany().getData().getAttributes().getIndustry_type()));
            Preferences.getInstance(getApplicationContext()).setCward(Helpers.mystring(response.getCompany().getData().getAttributes().getWard()));
            Preferences.getInstance(getApplicationContext()).setCfax(Helpers.mystring(response.getCompany().getData().getAttributes().getFax()));
            Preferences.getInstance(getApplicationContext()).setCemail(Helpers.mystring(response.getCompany().getData().getAttributes().getEmail()));
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
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }

        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(CompanyDashboardActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            ApiCallsService.action(this, Cv.ACTION_GET_INDUSTRY);
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
    public void getIndustryType(IndustryTypeResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.industry_type.clear();
            appUser.industry_id.clear();
            for(int i=0;i<response.getIndustry().getData().size();i++){
                appUser.industry_type.add(response.getIndustry().getData().get(i).getAttributes().getName());
                appUser.industry_id.add(response.getIndustry().getData().get(i).getAttributes().getId());
                LocalRepositories.saveAppUser(this,appUser);
            }

        } else {
            Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
        }


    }





   /* @Override
    public void onBackPressed() {
      /*  new AlertDialog.Builder(CompanyDashboardActivity.this)
                .setTitle("Exit Company")
                .setMessage("Do you want to exit this company ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    startActivity(new Intent(getApplicationContext(), CompanyListActivity.class));

                })
                .setNegativeButton(R.string.btn_cancel, null)

                .show();*/








    @Subscribe
    public void timout(String msg){
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }
    public void showpopup(){
        dialog = new Dialog(CompanyDashboardActivity.this);
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
                InputMethodManager inputManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                dialog.dismiss();
            }
        });

        // if button is clicked, close the custom dialog
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!username.getText().toString().equals("")){
                    if(!password.getText().toString().equals("")){
                        appUser.cusername=username.getText().toString();
                        appUser.cpassword=password.getText().toString();
                        LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if (isConnected) {
                            mProgressDialog = new ProgressDialog(CompanyDashboardActivity.this);
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_COMPANY_AUTHENTICATE);
                            dialog.dismiss();
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
                        Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Enter username",Toast.LENGTH_LONG).show();
                }

            }
        });

        dialog.show();
    }

    @Subscribe
    public void authenticate(CompanyAuthenticateResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            new AlertDialog.Builder(CompanyDashboardActivity.this)
                    .setTitle("Delete Company")
                    .setMessage("Are you sure you want to delete this company ?")
                    .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if (isConnected) {
                            mProgressDialog = new ProgressDialog(CompanyDashboardActivity.this);
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_COMPANY);
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

                    })
                    .setNegativeButton(R.string.btn_cancel, null)
                    .show();

        }
        else{

            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();

        }
    }

    @Subscribe
    public void deletecompany(DeleteCompanyResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),CompanyListActivity.class));
            finish();
        }
        else{
            Snackbar.make(coordinatorLayout,response.getMessage(), Snackbar.LENGTH_LONG).show();
            startActivity(new Intent(getApplicationContext(),CompanyListActivity.class));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(this, FirstPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FirstPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}
