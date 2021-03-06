package com.lkintechnology.mBilling.activities.company.navigations.administration.masters.accountgroup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.AccountDetailsActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.MasterDashboardActivity;
import com.lkintechnology.mBilling.adapters.AccountGroupListAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.accountgroup.DeleteAccountGroupResponse;
import com.lkintechnology.mBilling.networks.api_response.accountgroup.GetAccountGroupResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventDeleteGroup;
import com.lkintechnology.mBilling.utils.EventGroupClicked;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountGroupListActivity extends AppCompatActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.account_group_list_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.floating_button)
    FloatingActionButton mFloatingButton;
    @Bind(R.id.top_layout)
    RelativeLayout mOverlayLayout;
    @Bind(R.id.error_layout)
    LinearLayout error_layout;
    RecyclerView.LayoutManager layoutManager;
    AccountGroupListAdapter mAdapter;
    AppUser appUser;
    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    Boolean fromGeneral, fromMaster, fromCreateGroup;
    public static Boolean isDirectForAccountGroup = true;
    Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_group_list);
        fromMaster = getIntent().getExtras().getBoolean("frommaster");
        fromCreateGroup = getIntent().getExtras().getBoolean("fromcreategroup");
        ButterKnife.bind(this);
   /*     if (isFirstTime()) {
            mOverlayLayout.setVisibility(View.INVISIBLE);
        }*/
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
        mFloatingButton.bringToFront();
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(AccountGroupListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ACCOUNT_GROUP);
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
        actionbarTitle.setText("ACCOUNT GROUP LIST");
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
                if (isDirectForAccountGroup) {
                    Intent intent = new Intent(this, MasterDashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else if (!isDirectForAccountGroup) {
                   /* Intent intent = new Intent(this, AccountDetailsActivity.class);
                    intent.putExtra("fromaccountlist", false);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);*/
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (isDirectForAccountGroup) {
            Intent intent = new Intent(this, MasterDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else if (!isDirectForAccountGroup) {
            /*Intent intent = new Intent(this, AccountDetailsActivity.class);
            intent.putExtra("fromaccountlist", false);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);*/
            finish();
        }
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
        super.onResume();
    }

    public void add(View v) {
        Intent intent = new Intent(getApplicationContext(), CreateAccountGroupActivity.class);
        intent.putExtra("fromaccountgrouplist", false);
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        mProgressDialog.dismiss();
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        mProgressDialog.dismiss();
    }

    @Subscribe
    public void getAccountGroup(GetAccountGroupResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.group_id.clear();
            appUser.group_name.clear();
            appUser.arr_account_group_id.clear();
            appUser.arr_account_group_name.clear();
            LocalRepositories.saveAppUser(this, appUser);
            if (response.getAccount_groups().getData().size() == 0) {
                //Snackbar.make(coordinatorLayout, "No Account Group Found!!", Snackbar.LENGTH_LONG).show();
                mRecyclerView.setVisibility(View.GONE);
                error_layout.setVisibility(View.VISIBLE);
            }else {
                mRecyclerView.setVisibility(View.VISIBLE);
                error_layout.setVisibility(View.GONE);
            }

           /* handler = new Handler();
            handler.postDelayed(new Runnable(){
                @Override
                public void run(){
                    for (int i = 0; i < response.getAccount_groups().getData().size(); i++) {
                        if (response.getAccount_groups().getData().get(i).getAttributes().getUndefined() == false) {
                            appUser.group_name.add(response.getAccount_groups().getData().get(i).getAttributes().getName());
                            appUser.group_id.add(response.getAccount_groups().getData().get(i).getAttributes().getId());
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        }
                        appUser.arr_account_group_name.add(response.getAccount_groups().getData().get(i).getAttributes().getName());
                        appUser.arr_account_group_id.add(response.getAccount_groups().getData().get(i).getAttributes().getId());
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    }
                }
            }, 1);
*/
            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new AccountGroupListAdapter(this, response.getAccount_groups().getData());
            mRecyclerView.setAdapter(mAdapter);
            CreateAccountGroupActivity.data = response.getAccount_groups();

        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
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


    @Subscribe
    public void groupclickedevent(EventGroupClicked pos) {
        Intent intent = getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);

        if (!isDirectForAccountGroup && bool) {
            String data = pos.getPosition();
            String arr[] = data.split(",");
            String id = arr[0];
            String name = arr[1];
            Intent intentForward = null;
            if (ParameterConstant.checkStartActivityResultForAccountGroup == 0) {
                AccountDetailsActivity.context.finish();
                intentForward = new Intent(getApplicationContext(), AccountDetailsActivity.class);
            }
            intentForward.putExtra("result", String.valueOf(pos.getPosition()));
            intentForward.putExtra("name", name);
            intentForward.putExtra("id", id);
            intentForward.putExtra("bool",true);
            startActivity(intentForward);
            finish();
        }
        if (!isDirectForAccountGroup) {

            String data = pos.getPosition();
            String arr[] = data.split(",");
            String id = arr[0];
            String name = arr[1];
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", String.valueOf(pos.getPosition()));
            returnIntent.putExtra("name", name);
            returnIntent.putExtra("id", id);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }

    }

    @Subscribe
    public void deletegroup(EventDeleteGroup pos) {
        appUser.delete_group_id = String.valueOf(pos.getPosition());
        LocalRepositories.saveAppUser(this, appUser);
        new AlertDialog.Builder(AccountGroupListActivity.this)
                .setTitle("Delete Account Group")
                .setMessage("Are you sure you want to delete this account group ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(AccountGroupListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_ACCOUNT_GROUP);
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
    public void deletegroupresponse(DeleteAccountGroupResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ACCOUNT_GROUP);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
           // Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

}