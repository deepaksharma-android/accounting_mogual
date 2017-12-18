package com.berylsystems.buzz.activities.company.administration.master.unitconversion;

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
import com.berylsystems.buzz.activities.company.administration.master.unit.UnitListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.unit.GetUnitListResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.CreateUnitConversionResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.EditUnitConversionResponse;
import com.berylsystems.buzz.networks.api_response.unitconversion.GetUnitConversionDetailsResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CreateUnitConversionActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.update)
    LinearLayout mUpdate;
    @Bind(R.id.confactor)
    EditText mConfactor;
    @Bind(R.id.mainUnitLayout)
    LinearLayout mainUnitLayout;
    @Bind(R.id.subUnitLayout)
    LinearLayout subUnitLayout;
    @Bind(R.id.mainUnitText)
    TextView mainUnitText;
    @Bind(R.id.subUnitText)
    TextView subUnitText;

    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Boolean frommunitconversionlist;
    String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        title = "CREATE ITEM UNIT CONVERSION";

        mainUnitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnitListActivity.isDirectForUnitList=false;
                startActivityForResult(new Intent(getApplicationContext(), UnitListActivity.class),1);
            }
        });

        subUnitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnitListActivity.isDirectForUnitList=false;
                startActivityForResult(new Intent(getApplicationContext(), UnitListActivity.class),2);
            }
        });

        frommunitconversionlist = getIntent().getExtras().getBoolean("fromunitconversionlist");
        if (frommunitconversionlist) {
            title = "EDIT ITEM UNIT CONVERSION";
            /*mMainUnitAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_unitConversionUnitName);
            mMainUnitAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mMainUnitSpinner.setAdapter(mMainUnitAdapter);

            mSubUnitAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_unitConversionUnitName);
            mSubUnitAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mSubUnitSpinner.setAdapter(mSubUnitAdapter);*/
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            appUser.edit_unit_conversion_id = String.valueOf(getIntent().getExtras().getInt("id"));
            LocalRepositories.saveAppUser(this, appUser);
            Timber.i("I am here" + String.valueOf(getIntent().getExtras().getInt("id")));
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateUnitConversionActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_UNIT_CONVERSION_DETAILS);
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
                mProgressDialog = new ProgressDialog(CreateUnitConversionActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_UNIT_LIST);
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
        /*mMainUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                appUser.main_unit_id=appUser.arr_unitConversionId.get(i);
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        /*mSubUnitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                appUser.sub_unit_id=appUser.arr_unitConversionId.get(i);
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mConfactor.getText().toString().equals("")) {
                    appUser.confactor = mConfactor.getText().toString();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateUnitConversionActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_UNIT_CONVERSION);
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
                    Snackbar.make(coordinatorLayout, "Enter the conversion factor", Snackbar.LENGTH_LONG).show();
                }
            }

        });
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mConfactor.getText().toString().equals("")) {
                    appUser.confactor = mConfactor.getText().toString();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateUnitConversionActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_UNIT_CONVERSION);
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
                    Snackbar.make(coordinatorLayout, "Enter the unit name", Snackbar.LENGTH_LONG).show();
                }
            }

        });


    }

    @Override
    protected int layoutId() {
        return R.layout.activity_create_unit_conversion;
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
        actionbarTitle.setText(title);
        actionbarTitle.setTextSize(14);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void getUnitList(GetUnitListResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.arr_unitConversionUnitName.clear();
            appUser.arr_unitConversionId.clear();
            LocalRepositories.saveAppUser(this, appUser);
            Timber.i("I AM HERE");
            for (int i = 0; i < response.getItem_units().getData().size(); i++) {
                appUser.arr_unitConversionUnitName.add(response.getItem_units().getData().get(i).getAttributes().getName());
                appUser.arr_unitConversionId.add(response.getItem_units().getData().get(i).getId());
             /*   if(response.getItem_units().getData().get(i).getAttributes().getUndefined()==false){
                    appUser.unitConversionUnitName.add(response.getItem_units().getData().get(i).getAttributes().getName());
                    appUser.unitConversionId.add(response.getItem_units().getData().get(i).getId());
                }*/

                LocalRepositories.saveAppUser(this, appUser);

              /*  mMainUnitAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_unitConversionUnitName);
                mMainUnitAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
                mMainUnitSpinner.setAdapter(mMainUnitAdapter);

                mSubUnitAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_unitConversionUnitName);
                mSubUnitAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
                mSubUnitSpinner.setAdapter(mSubUnitAdapter);*/
            }

        }
    }

    @Subscribe
    public void createunit(CreateUnitConversionResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), UnitConversionListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void editunit(EditUnitConversionResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), UnitConversionListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void getunitconversiondetails(GetUnitConversionDetailsResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            mConfactor.setText(String.valueOf(response.getUnit_conversion_detail().getData().getAttributes().getConversion_factor()));
            String group_type = response.getUnit_conversion_detail().getData().getAttributes().getMain_unit().trim();
            // insert code here
            int groupindex = -1;
            for (int i = 0; i < appUser.arr_unitConversionUnitName.size(); i++) {
                if (appUser.arr_unitConversionUnitName.get(i).equals(group_type)) {
                    groupindex = i;
                    break;
                }
            }
            Timber.i("GROUPINDEX" + groupindex);
            //mMainUnitSpinner.setSelection(groupindex);
            String sub_unit = response.getUnit_conversion_detail().getData().getAttributes().getSub_unit().trim();
            // insert code here
            int sub_unit_index = -1;
            for (int i = 0; i < appUser.arr_unitConversionUnitName.size(); i++) {
                if (appUser.arr_unitConversionUnitName.get(i).equals(sub_unit)) {
                    sub_unit_index = i;
                    break;
                }
            }

            //mSubUnitSpinner.setSelection(sub_unit_index);

        } else {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mainUnitText.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                subUnitText.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {

            }
        }

    }
}