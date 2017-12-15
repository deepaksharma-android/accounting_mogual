package com.berylsystems.buzz.activities.company.administration.master.materialcentregroup;

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
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.company.administration.master.accountgroup.CreateAccountGroupActivity;
import com.berylsystems.buzz.activities.company.administration.master.materialcentre.CreateMaterialCentreActivity;
import com.berylsystems.buzz.activities.dashboard.MasterDashboardActivity;
import com.berylsystems.buzz.adapters.MaterialCentreGroupListAdapter;
import com.berylsystems.buzz.adapters.MaterialCentreListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.accountgroup.DeleteAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.DeleteMaterialCentreGroupResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.GetMaterialCentreGroupListResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventDeleteGroup;
import com.berylsystems.buzz.utils.EventDeleteMaterailCentreGroup;
import com.berylsystems.buzz.utils.EventMaterialCentreGroupClicked;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MaterialCentreGroupListActivity extends AppCompatActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.material_centre_group_list_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.floating_button)
    FloatingActionButton mFloatingButton;
    RecyclerView.LayoutManager layoutManager;
    MaterialCentreGroupListAdapter mAdapter;
    AppUser appUser;
    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    public static Boolean isDirectForMaterialCentreGroup = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_centre_group_list);
        ButterKnife.bind(this);
        initActionbar();
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009DE0")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("MATERIAL GROUP LIST");
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
                Intent intent = new Intent(this, MasterDashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (isDirectForMaterialCentreGroup) {
            Intent intent = new Intent(this, MasterDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    public void add(View v) {
        Intent intent = new Intent(getApplicationContext(), CreateMaterialCentreGroupActivity.class);
        intent.putExtra("frommaterialcentregrouplist", false);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(MaterialCentreGroupListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_MATERIAL_CENTRE_GROUP_LIST);
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
        mProgressDialog.dismiss();
        super.onPause();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();

    }

    @Subscribe
    public void getmaterialcentregrouplist(GetMaterialCentreGroupListResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.arr_materialCentreGroupId.clear();
            appUser.arr_materialCentreGroupName.clear();
            appUser.materialCentreGroupId.clear();
            appUser.materialCentreGroupName.clear();
            LocalRepositories.saveAppUser(this, appUser);
            Timber.i("I AM HERE");
            if (response.getMaterial_center_groups().getData().size() == 0) {
                Snackbar.make(coordinatorLayout, "No Material Centre Group Found!!", Snackbar.LENGTH_LONG).show();
            }
            for (int i = 0; i < response.getMaterial_center_groups().getData().size(); i++) {
                appUser.arr_materialCentreGroupName.add(response.getMaterial_center_groups().getData().get(i).getAttributes().getName());
                appUser.arr_materialCentreGroupId.add(String.valueOf(response.getMaterial_center_groups().getData().get(i).getAttributes().getId()));
                if (response.getMaterial_center_groups().getData().get(i).getAttributes().getUndefined() == false) {
                    appUser.materialCentreGroupName.add(response.getMaterial_center_groups().getData().get(i).getAttributes().getName());
                    appUser.materialCentreGroupId.add(String.valueOf(response.getMaterial_center_groups().getData().get(i).getAttributes().getId()));
                }

                LocalRepositories.saveAppUser(this, appUser);
            }
            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new MaterialCentreGroupListAdapter(this, response.getMaterial_center_groups().getData());
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void deletematerialcentregroup(EventDeleteMaterailCentreGroup pos) {
        appUser.delete_material_centre_group_id = String.valueOf(appUser.arr_materialCentreGroupId.get(pos.getPosition()));
        LocalRepositories.saveAppUser(this, appUser);
        new AlertDialog.Builder(MaterialCentreGroupListActivity.this)
                .setTitle("Delete Material Group")
                .setMessage("Are you sure you want to delete this material group ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(MaterialCentreGroupListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_MATERIAL_CENTRE_GROUP);
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
    public void deletematerialcentregroupresponse(DeleteMaterialCentreGroupResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_MATERIAL_CENTRE_GROUP_LIST);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void materialcentregroupclickedevent(EventMaterialCentreGroupClicked pos) {
        if (!isDirectForMaterialCentreGroup) {
            Timber.i("POSITION" + pos.getPosition());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", String.valueOf(pos.getPosition()));
            returnIntent.putExtra("name", appUser.arr_materialCentreGroupName.get(pos.getPosition()));
            returnIntent.putExtra("id", String.valueOf(appUser.arr_materialCentreGroupId.get(pos.getPosition())));
            /*appUser.create_account_group_id = String.valueOf(appUser.arr_account_group_id.get(pos.getPosition()));
            LocalRepositories.saveAppUser(this, appUser);*/
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }


}