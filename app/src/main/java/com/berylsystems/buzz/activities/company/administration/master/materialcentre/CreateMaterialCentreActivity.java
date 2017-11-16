package com.berylsystems.buzz.activities.company.administration.master.materialcentre;

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
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.app.RegisterAbstractActivity;
import com.berylsystems.buzz.activities.company.administration.master.account.ExpandableAccountListActivity;
import com.berylsystems.buzz.activities.company.administration.master.accountgroup.AccountGroupListActivity;
import com.berylsystems.buzz.activities.company.administration.master.materialcentregroup.MaterialCentreGroupListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.account.EditAccountResponse;
import com.berylsystems.buzz.networks.api_response.account.GetAccountDetailsResponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.CreateMaterialCentreResponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.EditMaterialCentreReponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.GetMaterialCentreDetailResponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.StockResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.Helpers;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initActionbar();


        frommaterialcentrelist = getIntent().getExtras().getBoolean("frommaterialcentrelist");
        appUser = LocalRepositories.getAppUser(this);
        if (frommaterialcentrelist) {
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
        }
        else{
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
        mGroupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MaterialCentreGroupListActivity.class);
                intent.putExtra("frommaster", false);
                startActivityForResult(intent, 1);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCentreName.getText().toString().equals("")) {
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
                    Snackbar.make(coordinatorLayout, "Enter the centre name", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mCentreName.getText().toString().equals("")) {
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

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009DE0")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("CREATE MATERIAL CENTRE");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void createMaterialCentre(CreateMaterialCentreResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),MaterialCentreListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);

        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
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
            Timber.i("GROUPINDEX"+group_type);
            // insert code here
            int groupindex = -1;
            for (int i = 0; i<appUser.arr_stock_name.size(); i++) {
                Timber.i("GROUPINDEX"+appUser.arr_stock_name);
                if (appUser.arr_stock_name.get(i).equals(group_type)) {
                    groupindex = i;
                    break;
                }
            }
            Timber.i("GROUPINDEX"+groupindex);
            mStockSpinner.setSelection(groupindex);

        } else {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void editaccount(EditMaterialCentreReponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent intent=new Intent(getApplicationContext(),MaterialCentreListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
           startActivity(intent);
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                appUser.material_centre_group_id = id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mGroupName.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                mGroupName.setText("");
            }
        }
    }

    @Subscribe
    public void getstock(StockResponse response){
        mProgressDialog.dismiss();
        appUser.arr_stock_id.clear();
        appUser.arr_stock_name.clear();
        LocalRepositories.saveAppUser(this,appUser);
        if(response.getStatus()==200){
            for(int i=0;i<response.getCompany_account_groups().getData().size();i++){
                appUser.arr_stock_name.add(response.getCompany_account_groups().getData().get(i).getAttributes().getName());
                appUser.arr_stock_id.add(response.getCompany_account_groups().getData().get(i).getId());
                mStockAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_stock_name);
                mStockAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
                mStockSpinner.setAdapter(mStockAdapter);
            }

        }
    }


}