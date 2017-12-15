package com.berylsystems.buzz.activities.company.administration.master.item_group;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivity;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.company.administration.master.accountgroup.CreateAccountGroupActivity;
import com.berylsystems.buzz.activities.dashboard.MasterDashboardActivity;
import com.berylsystems.buzz.adapters.AccountGroupListAdapter;
import com.berylsystems.buzz.adapters.ItemGroupListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.accountgroup.DeleteAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.GetAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.DeleteItemGroupReponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.GetItemGroupResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventDeleteGroup;
import com.berylsystems.buzz.utils.EventDeleteItemGroup;
import com.berylsystems.buzz.utils.EventGroupClicked;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ItemGroupListActivity extends AppCompatActivity {

    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.floating_button)
    FloatingActionButton mFloatingButton;
    RecyclerView.LayoutManager layoutManager;
    ItemGroupListAdapter mAdapter;
    @Bind(R.id.item_group_list_recycler_view)
    RecyclerView mRecyclerView;
    Boolean fromGeneral, fromMaster, fromCreateGroup;
    public static Boolean isDirectForItemGroup = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_group_list);

        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
        mFloatingButton.bringToFront();
        EventBus.getDefault().register(this);

        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(ItemGroupListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ITEM_GROUP);
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
        actionbarTitle.setText("ITEM GROUP LIST");
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
        if (isDirectForItemGroup) {
            Intent intent = new Intent(this, MasterDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    public void add(View v) {
        Intent intent = new Intent(getApplicationContext(), CreateItemGroupActivity.class);
        intent.putExtra("fromitemgrouplist", false);
        startActivity(intent);
    }

    @Override
    protected void onResume() {

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
        super.onStop();
        EventBus.getDefault().unregister(this);
        mProgressDialog.dismiss();
    }

    @Subscribe
    public void getItemGroup(GetItemGroupResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.group_name1.clear();
            appUser.group_id1.clear();
            appUser.arr_item_group_name.clear();
            appUser.arr_item_group_id.clear();
            LocalRepositories.saveAppUser(this, appUser);
            if (response.getItem_groups().getData().size() == 0) {
                Snackbar.make(coordinatorLayout, "No Item Group Found!!", Snackbar.LENGTH_LONG).show();
            }
            for (int i = 0; i < response.getItem_groups().getData().size(); i++) {
                appUser.arr_item_group_name.add(response.getItem_groups().getData().get(i).getAttributes().getName());
                appUser.arr_item_group_id.add(response.getItem_groups().getData().get(i).getAttributes().getId());
                LocalRepositories.saveAppUser(this, appUser);

                if (response.getItem_groups().getData().get(i).getAttributes().getUndefined() == false) {
                    appUser.group_name1.add(response.getItem_groups().getData().get(i).getAttributes().getName());
                    appUser.group_id1.add(response.getItem_groups().getData().get(i).getAttributes().getId());
                    LocalRepositories.saveAppUser(this, appUser);
                }
            }
            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new ItemGroupListAdapter(this, response.getItem_groups().data);
            mRecyclerView.setAdapter(mAdapter);

        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
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
    public void deletegroup(EventDeleteItemGroup pos) {
        appUser.delete_group_id = String.valueOf(appUser.arr_item_group_id.get(pos.getPosition()));
        LocalRepositories.saveAppUser(this, appUser);
        new AlertDialog.Builder(ItemGroupListActivity.this)
                .setTitle("Delete Item Group")
                .setMessage("Are you sure you want to delete this item group ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(ItemGroupListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_ITEM_GROUP);
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
    public void deletegroupresponse(DeleteItemGroupReponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ITEM_GROUP);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void itemclickedevent(EventGroupClicked pos) {

        if (!isDirectForItemGroup) {
            Timber.i("POSITION" + pos.getPosition());
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result", String.valueOf(pos.getPosition()));
            returnIntent.putExtra("name", appUser.arr_item_group_name.get(pos.getPosition()));
            returnIntent.putExtra("id", String.valueOf(appUser.arr_item_group_id.get(pos.getPosition())));
            Timber.i("PASSSS" + appUser.arr_item_group_id.get(pos.getPosition()));
            /*appUser.create_account_group_id = String.valueOf(appUser.arr_account_group_id.get(pos.getPosition()));
            LocalRepositories.saveAppUser(this, appUser);*/
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }

    }
}
