package com.lkintechnology.mBilling.activities.company.navigations.administration.masters.materialcentre;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.materialcentregroup.MaterialCentreGroupListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.materialcentre.CreateMaterialCentreResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentre.EditMaterialCentreReponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentre.GetMaterialCentreDetailResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentre.StockResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CreateMaterialCentreActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.centre_name)
    EditText mCentreName;
    @Bind(R.id.centre_address)
    EditText mCentreAddress;
    @Bind(R.id.centre_city)
    EditText mCentreCity;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.update)
    LinearLayout mUpdate;
    @Bind(R.id.group_layout)
    LinearLayout mGroupLayout;
    @Bind(R.id.group_name)
    TextView mGroupName;
    @Bind(R.id.stock_account_spinner)
    Spinner mStockSpinner;
    ArrayAdapter<String> mStockAdapter;
    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Boolean frommaterialcentrelist;
    String title;
    public static CreateMaterialCentreActivity context;
    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        context = this;
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(getApplicationContext());

        title = "CREATE MATERIAL CENTRE";
        frommaterialcentrelist = getIntent().getExtras().getBoolean("frommaterialcentrelist");
        appUser = LocalRepositories.getAppUser(this);
        if (frommaterialcentrelist) {
            title = "EDIT MATERIAL CENTRE";
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            Boolean isaConnected = ConnectivityReceiver.isConnected();
            if (isaConnected) {
                mProgressDialog = new ProgressDialog(CreateMaterialCentreActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_MATERIAL_CENTRE_DETAILS);
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
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateMaterialCentreActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_STOCK);
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
        initActionbar();
        mGroupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialCentreGroupListActivity.isDirectForMaterialCentreGroup = false;
                appUser.material_centre_name = mCentreName.getText().toString();
                appUser.material_centre_address = mCentreAddress.getText().toString();
                appUser.material_centre_city = mCentreCity.getText().toString();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent intent = new Intent(getApplicationContext(), MaterialCentreGroupListActivity.class);
                intent.putExtra("frommaster", false);
                startActivityForResult(intent, 1);
            }
        });

        mCentreName.setText(appUser.material_centre_name);
        mCentreAddress.setText(appUser.material_centre_address);
        mCentreCity.setText(appUser.material_centre_city);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCentreName.getText().toString().equals("")) {
                    if (!mGroupName.getText().toString().equals("")) {
                        appUser.material_centre_name = mCentreName.getText().toString();
                        appUser.material_centre_address = mCentreAddress.getText().toString();
                        appUser.material_centre_city = mCentreCity.getText().toString();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if (isConnected) {
                            mProgressDialog = new ProgressDialog(CreateMaterialCentreActivity.this);
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_MATERIAL_CENTRE);
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
                        Snackbar.make(coordinatorLayout, "Select group name", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Enter the centre name", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCentreName.getText().toString().equals("")) {
                    if (!mGroupName.getText().toString().equals("")) {
                        appUser.material_centre_name = mCentreName.getText().toString();
                        appUser.material_centre_address = mCentreAddress.getText().toString();
                        appUser.material_centre_city = mCentreCity.getText().toString();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if (isConnected) {
                            mProgressDialog = new ProgressDialog(CreateMaterialCentreActivity.this);
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_MATERIAL_CENTRE);
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
                        Snackbar.make(coordinatorLayout, "Select group name", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Enter the centre name", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mStockAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_stock_name);
        mStockAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mStockSpinner.setAdapter(mStockAdapter);
        mStockSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                appUser.stock_id = appUser.arr_stock_id.get(i);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_create_material_centre;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        actionbarTitle.setText(title);
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void createMaterialCentre(CreateMaterialCentreResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "material_center");
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME,appUser.company_name);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            //  MaterialCentreListActivity.isDirectForMaterialCentre=true;
            Intent intent = new Intent(getApplicationContext(), MaterialCentreListActivity.class);
            intent.putExtra("bool", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else {
           // Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
    public void getaccountaccount(GetMaterialCentreDetailResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            mCentreName.setText(response.getMaterial_center().getData().getAttributes().getName());
            mCentreAddress.setText(response.getMaterial_center().getData().getAttributes().getAddress());
            mCentreCity.setText(response.getMaterial_center().getData().getAttributes().getCity());
            mGroupName.setText(response.getMaterial_center().getData().getAttributes().getMaterial_center_group_name());
            String group_type = response.getMaterial_center().getData().getAttributes().getMaterial_centre_stock_name().trim();
            Timber.i("GROUPINDEX" + group_type);
            // insert code here
            int groupindex = -1;
            for (int i = 0; i < appUser.arr_stock_name.size(); i++) {
                Timber.i("GROUPINDEX" + appUser.arr_stock_name);
                if (appUser.arr_stock_name.get(i).equals(group_type)) {
                    groupindex = i;
                    break;
                }
            }
            Timber.i("GROUPINDEX" + groupindex);
            mStockSpinner.setSelection(groupindex);

        } else {
           // Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
    public void editaccount(EditMaterialCentreReponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            // MaterialCentreListActivity.isDirectForMaterialCentre=true;
            Intent intent = new Intent(getApplicationContext(), MaterialCentreListActivity.class);
            intent.putExtra("bool", true);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                boolForGroupName = true;
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.material_centre_group_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mGroupName.setText(result);
                //Toast.makeText(this, "startActivityForResult", Toast.LENGTH_SHORT).show();
                return;
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                //mGroupName.setText("");
            }
        }
    }

    public Boolean boolForGroupName = false;

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);
        //Toast.makeText(this, ""+bool, Toast.LENGTH_SHORT).show();
        if (bool) {

            if (!boolForGroupName) {
                //Toast.makeText(getApplicationContext(), "Resume", Toast.LENGTH_SHORT).show();
                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                appUser.material_centre_group_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mGroupName.setText(result);
            }

        }

    }

    @Subscribe
    public void getstock(StockResponse response) {
        mProgressDialog.dismiss();
        appUser.arr_stock_id.clear();
        appUser.arr_stock_name.clear();
        LocalRepositories.saveAppUser(this, appUser);
        if (response.getStatus() == 200) {
            for (int i = 0; i < response.getCompany_account_groups().getData().size(); i++) {
                appUser.arr_stock_name.add(response.getCompany_account_groups().getData().get(i).getAttributes().getName());
                appUser.arr_stock_id.add(response.getCompany_account_groups().getData().get(i).getId());
                mStockAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_stock_name);
                mStockAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
                mStockSpinner.setAdapter(mStockAdapter);
            }
        }else {
            Helpers.dialogMessage(this,response.getMessage());
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