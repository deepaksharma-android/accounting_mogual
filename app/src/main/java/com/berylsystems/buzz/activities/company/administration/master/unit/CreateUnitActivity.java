package com.berylsystems.buzz.activities.company.administration.master.unit;

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
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.unit.CreateUnitResponse;
import com.berylsystems.buzz.networks.api_response.unit.EditUnitResponse;
import com.berylsystems.buzz.networks.api_response.unit.GetUnitDetailsResponse;
import com.berylsystems.buzz.networks.api_response.unit.GetUqcResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CreateUnitActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.uqc_spinner)
    Spinner mSpinnerUqc;
    @Bind(R.id.unit_name)
    EditText mUnitName;
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.update)
    LinearLayout mUpdate;
    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Boolean frommunitlist;
    ArrayAdapter<String> muqcAdapter;
    public String title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        title="CREATE ITEM UNIT";

        frommunitlist = getIntent().getExtras().getBoolean("fromunitlist");
        if (frommunitlist) {
            title="EDIT ITEM UNIT";
            muqcAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_uqcname);
            muqcAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mSpinnerUqc.setAdapter(muqcAdapter);
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            appUser.edit_unit_id = getIntent().getExtras().getString("id");
            LocalRepositories.saveAppUser(this, appUser);
            Timber.i("I am here");
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateUnitActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_UNIT_DETAILS);
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
                mProgressDialog = new ProgressDialog(CreateUnitActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_UQC);
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
        muqcAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_uqcname);
        muqcAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mSpinnerUqc.setAdapter(muqcAdapter);

        mSpinnerUqc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(appUser.arr_uqcid!=null) {
                    appUser.uqc = String.valueOf(appUser.arr_uqcid.get(i));
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mUnitName.getText().toString().equals("")) {
                    appUser.unit_name = mUnitName.getText().toString();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateUnitActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_UNIT);
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
        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mUnitName.getText().toString().equals("")) {
                    appUser.unit_name = mUnitName.getText().toString();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(CreateUnitActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_UNIT);
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
        return R.layout.activity_create_unit;
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
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void createunit(CreateUnitResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            UnitListActivity.isDirectForUnitList=false;
            Intent intent = new Intent(getApplicationContext(), UnitListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void editunit(EditUnitResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            UnitListActivity.isDirectForUnitList=false;
            Intent intent = new Intent(getApplicationContext(), UnitListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void getunitdetails(GetUnitDetailsResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            mUnitName.setText(response.getItem_unit().getData().getAttributes().getName());
            String group_type = response.getItem_unit().getData().getAttributes().getUqc().trim();
            Timber.i("GROUPINDEX" + group_type);
            // insert code here
            int groupindex = -1;
            for (int i = 0; i < appUser.arr_uqcname.size(); i++) {
                Timber.i("GROUPINDEX" + appUser.arr_stock_name);
                if (appUser.arr_uqcname.get(i).equals(group_type)) {
                    groupindex = i;
                    break;
                }
            }
            Timber.i("GROUPINDEX" + groupindex);
            mSpinnerUqc.setSelection(groupindex);

        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void getuqc(GetUqcResponse response) {
        mProgressDialog.dismiss();
        appUser.arr_uqcid.clear();
        appUser.arr_uqcname.clear();
        if (response.getStatus() == 200) {
            for (int i = 0; i < response.getUqc().getData().size(); i++) {
                appUser.arr_uqcid.add(response.getUqc().getData().get(i).getAttributes().getId());
                appUser.arr_uqcname.add(response.getUqc().getData().get(i).getAttributes().getName());
                LocalRepositories.saveAppUser(this, appUser);
            }

            muqcAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_trademark_type_spinner_dropdown_item, appUser.arr_uqcname);
            muqcAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mSpinnerUqc.setAdapter(muqcAdapter);


        }

    }
}