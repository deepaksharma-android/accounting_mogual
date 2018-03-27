package com.lkintechnology.mBilling.activities.company.navigations.administration.masters.materialcentregroup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.materialcentre.CreateMaterialCentreActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.MasterDashboardActivity;
import com.lkintechnology.mBilling.adapters.MaterialCentreGroupListAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.materialcentregroup.DeleteMaterialCentreGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentregroup.GetMaterialCentreGroupListResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventDeleteMaterailCentreGroup;
import com.lkintechnology.mBilling.utils.EventMaterialCentreGroupClicked;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

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
    @Bind(R.id.top_layout)
    RelativeLayout mOverlayLayout;
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
        if (isFirstTime()) {
            mOverlayLayout.setVisibility(View.INVISIBLE);
        }
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        mFloatingButton.bringToFront();


    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        //SharedPreferences preferences1=getP
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
                if (isDirectForMaterialCentreGroup) {
                    Intent intent = new Intent(this, MasterDashboardActivity.class);
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
        finish();
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
            /*appUser.arr_materialCentreGroupId.clear();
            appUser.arr_materialCentreGroupName.clear();
            appUser.materialCentreGroupId.clear();
            appUser.materialCentreGroupName.clear();*/
            LocalRepositories.saveAppUser(this, appUser);
            Timber.i("I AM HERE");
            if (response.getMaterial_center_groups().getData().size() == 0) {
                Snackbar.make(coordinatorLayout, "No Material Centre Group Found!!", Snackbar.LENGTH_LONG).show();
            }

            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new MaterialCentreGroupListAdapter(this, response.getMaterial_center_groups().getData());
            mRecyclerView.setAdapter(mAdapter);

            CreateMaterialCentreGroupActivity.data = response.getMaterial_center_groups();

          /*  Handler handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    for (int i = 0; i < response.getMaterial_center_groups().getData().size(); i++) {
                        appUser.arr_materialCentreGroupName.add(response.getMaterial_center_groups().getData().get(i).getAttributes().getName());
                        appUser.arr_materialCentreGroupId.add(String.valueOf(response.getMaterial_center_groups().getData().get(i).getAttributes().getId()));
                        if (response.getMaterial_center_groups().getData().get(i).getAttributes().getUndefined() == false) {
                            appUser.materialCentreGroupName.add(response.getMaterial_center_groups().getData().get(i).getAttributes().getName());
                            appUser.materialCentreGroupId.add(String.valueOf(response.getMaterial_center_groups().getData().get(i).getAttributes().getId()));
                        }

                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    }
                }
            }, 1);*/
        } else {
            Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void deletematerialcentregroup(EventDeleteMaterailCentreGroup pos) {
        appUser.delete_material_centre_group_id = String.valueOf(CreateMaterialCentreGroupActivity.data.getData().get(pos.getPosition()).getAttributes().getId());
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

        Intent intent = getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);
        //Toast.makeText(this, ""+bool, Toast.LENGTH_SHORT).show();
        if (!isDirectForMaterialCentreGroup && bool) {
            //Toast.makeText(this, "entered", Toast.LENGTH_SHORT).show();
            CreateMaterialCentreActivity.context.finish();
            Intent intentForward = new Intent(getApplicationContext(),CreateMaterialCentreActivity.class);
            intentForward.putExtra("bool",true);
            intentForward.putExtra("result", String.valueOf(pos.getPosition()));
            intentForward.putExtra("name", CreateMaterialCentreGroupActivity.data.getData().get(pos.getPosition()).getAttributes().getName());
            intentForward.putExtra("id", String.valueOf(CreateMaterialCentreGroupActivity.data.getData().get(pos.getPosition()).getAttributes().getId()));
            /*appUser.create_account_group_id = String.valueOf(appUser.arr_account_group_id.get(pos.getPosition()));
            LocalRepositories.saveAppUser(this, appUser);*/
            startActivity(intentForward);
            finish();
        } else if (!isDirectForMaterialCentreGroup) {
            Timber.i("POSITION" + pos.getPosition());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", String.valueOf(pos.getPosition()));
            returnIntent.putExtra("name", CreateMaterialCentreGroupActivity.data.getData().get(pos.getPosition()).getAttributes().getName());
            returnIntent.putExtra("id", String.valueOf(CreateMaterialCentreGroupActivity.data.getData().get(pos.getPosition()).getAttributes().getId()));
            /*appUser.create_account_group_id = String.valueOf(appUser.arr_account_group_id.get(pos.getPosition()));
            LocalRepositories.saveAppUser(this, appUser);*/
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
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