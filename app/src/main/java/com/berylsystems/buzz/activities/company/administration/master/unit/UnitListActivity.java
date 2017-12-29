package com.berylsystems.buzz.activities.company.administration.master.unit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.company.administration.master.item.CreateNewItemActivity;
import com.berylsystems.buzz.activities.company.administration.master.unitconversion.CreateUnitConversionActivity;
import com.berylsystems.buzz.activities.dashboard.MasterDashboardActivity;
import com.berylsystems.buzz.adapters.UnitListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.unit.DeleteUnitResponse;
import com.berylsystems.buzz.networks.api_response.unit.GetUnitListResponse;
import com.berylsystems.buzz.networks.api_response.unit.GetUqcResponse;
import com.berylsystems.buzz.networks.api_response.unit.ItemUnitList;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventDeleteUnit;
import com.berylsystems.buzz.utils.EventUnitClicked;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.ParameterConstant;
import com.berylsystems.buzz.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class UnitListActivity extends AppCompatActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.unit_list_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.floating_button)
    FloatingActionButton mFloatingButton;
    RecyclerView.LayoutManager layoutManager;
    UnitListAdapter mAdapter;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar snackbar;
    public static Boolean isDirectForUnitList = true;

    public static ItemUnitList data;
    public static ArrayList<String>uqcName;
    public static ArrayList<Integer>uqcId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unit_list);
        ButterKnife.bind(this);
        initActionbar();
        uqcName=new ArrayList<>();
        uqcId=new ArrayList<>();
        appUser = LocalRepositories.getAppUser(this);
        mFloatingButton.bringToFront();

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
        actionbarTitle.setText("UNIT LIST");
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isDirectForUnitList) {
                    Intent intent = new Intent(this, MasterDashboardActivity.class);
                    intent.putExtra("bool",false);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (isDirectForUnitList) {
            Intent intent = new Intent(this, MasterDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(UnitListActivity.this);
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
        Intent intent = new Intent(getApplicationContext(), CreateUnitActivity.class);
        intent.putExtra("fromunitlist", false);
        startActivity(intent);
    }

    @Subscribe
    public void getUnitList(GetUnitListResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(UnitListActivity.this);
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
            //appUser.arr_unitId.clear();
            // appUser.arr_unitName.clear();
            appUser.unitId.clear();
            appUser.unitName.clear();
            LocalRepositories.saveAppUser(this, appUser);
            Timber.i("I AM HERE");
            if (response.getItem_units().getData().size() == 0) {
                Snackbar.make(coordinatorLayout, "No Unit Found!!", Snackbar.LENGTH_LONG).show();
            }

            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new UnitListAdapter(this, response.getItem_units().getData());
            mRecyclerView.setAdapter(mAdapter);

           /* Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    for (int i = 0; i < response.getItem_units().getData().size(); i++) {
                        appUser.arr_unitName.add(response.getItem_units().getData().get(i).getAttributes().getName());
                        appUser.arr_unitId.add(String.valueOf(response.getItem_units().getData().get(i).getId()));
                        if (response.getItem_units().getData().get(i).getAttributes().getUndefined() == false) {
                            appUser.unitName.add(response.getItem_units().getData().get(i).getAttributes().getName());
                            appUser.unitId.add(String.valueOf(response.getItem_units().getData().get(i).getId()));
                        }

                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    }
                }
            }, 1);*/
            data = response.getItem_units();
        }
    }


    @Subscribe
    public void getuqc(GetUqcResponse response) {
        mProgressDialog.dismiss();
        appUser.arr_uqcid.clear();
        appUser.arr_uqcname.clear();
        if (response.getStatus() == 200) {
            for (int i = 0; i < response.getUqc().getData().size(); i++) {
                uqcId.add(response.getUqc().getData().get(i).getAttributes().getId());
                uqcName.add(response.getUqc().getData().get(i).getAttributes().getName());
            }

        }

    }
    @Subscribe
    public void deletematerialcentregroup(EventDeleteUnit pos) {
        appUser.delete_unit_id = String.valueOf(data.getData().get(pos.getPosition()).getId());
        LocalRepositories.saveAppUser(this, appUser);
        new AlertDialog.Builder(UnitListActivity.this)
                .setTitle("Delete Unit")
                .setMessage("Are you sure you want to delete this unit ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(UnitListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_UNIT);
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

    @Subscribe
    public void deleteunitresponse(DeleteUnitResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_UNIT_LIST);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }


    @Subscribe
    public void itemclickedevent(EventUnitClicked pos) {
        Intent intent = getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);

        if (!isDirectForUnitList && bool) {
            Intent intentForward = null;
            if (ParameterConstant.checkStartActivityResultForUnitList == 1) {
                CreateNewItemActivity.context.finish();
                intentForward = new Intent(getApplicationContext(), CreateNewItemActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForUnitList == 2) {
                CreateUnitConversionActivity.context.finish();
                intentForward = new Intent(getApplicationContext(), CreateUnitConversionActivity.class);
            }
            intentForward.putExtra("result", String.valueOf(pos.getPosition()));
            intentForward.putExtra("name", data.getData().get(pos.getPosition()).getAttributes().getName());
            intentForward.putExtra("id", String.valueOf(data.getData().get(pos.getPosition()).getId()));
            intentForward.putExtra("bool", true);
        /*appUser.create_account_group_id = String.valueOf(appUser.arr_account_group_id.get(pos.getPosition()));
          LocalRepositories.saveAppUser(this, appUser);*/
            //setResult(Activity.RESULT_OK, returnIntent);
            startActivity(intentForward);
            finish();
        }
        if (!isDirectForUnitList) {
            Timber.i("POSITION" + pos.getPosition());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", String.valueOf(pos.getPosition()));
            returnIntent.putExtra("name", data.getData().get(pos.getPosition()).getAttributes().getName());
            returnIntent.putExtra("id", String.valueOf(data.getData().get(pos.getPosition()).getId()));
        /*appUser.create_account_group_id = String.valueOf(appUser.arr_account_group_id.get(pos.getPosition()));
          LocalRepositories.saveAppUser(this, appUser);*/
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }

    }
}