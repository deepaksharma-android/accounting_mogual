package com.lkintechnology.mBilling.activities.company;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.activities.dashboard.CompanyDashboardActivity;
import com.lkintechnology.mBilling.adapters.DefaultItemsExpandableListAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.defaultitems.CreateDefaultItemsResponse;
import com.lkintechnology.mBilling.networks.api_response.defaultitems.GetDefaultItemsResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;
import org.greenrobot.eventbus.Subscribe;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;

public class DefaultItemsExpandableListActivity extends RegisterAbstractActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.lvExp)
    ExpandableListView expListView;
    @Bind(R.id.add_selected_item)
    TextView addSelectedItems;
    AppUser appUser;
    DefaultItemsExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<Integer, List<Integer>> listDataChildId;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    List<String> name;
    List<Integer> id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sync_with_items_expandable_list);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();

        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(DefaultItemsExpandableListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            //LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_DEFAULT_ITEMS);
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

        addSelectedItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(DefaultItemsExpandableListActivity.this, "Selected : "+ DefaultItemsExpandableListAdapter.listData.size(), Toast.LENGTH_SHORT).show();
                if (isConnected) {
                    mProgressDialog = new ProgressDialog(DefaultItemsExpandableListActivity.this);
                    mProgressDialog.setMessage("Info...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(true);
                    mProgressDialog.show();
                    ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_DEFAULT_ITEMS);
                } else {
                    snackbar = Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
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
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_default_items_expandable_list;
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
        actionbarTitle.setText("Mobile Items List");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                DefaultItemsExpandableListAdapter.listData.clear();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        DefaultItemsExpandableListAdapter.listData.clear();
        finish();
    }

    @Subscribe
    public void getDefaultItems(GetDefaultItemsResponse response) {
            mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            listDataHeader = new ArrayList<>();
            listDataChild = new HashMap<String, List<String>>();
            listDataChildId = new HashMap<Integer, List<Integer>>();
            if (response.getDefault_item_group().getData().size() == 0) {
                Snackbar.make(coordinatorLayout, "No Item Found!!", Snackbar.LENGTH_LONG).show();
            }
            for (int i = 0; i < response.getDefault_item_group().getData().size(); i++) {
                listDataHeader.add(response.getDefault_item_group().getData().get(i).getAttributes().getName());
                name = new ArrayList<>();
                id = new ArrayList<>();
               // name.add(0,"Select all"+","+0);
                for (int j = 0; j < response.getDefault_item_group().getData().get(i).getAttributes().getItems().size(); j++) {
                    name.add(response.getDefault_item_group().getData().get(i).getAttributes().getItems().get(j).getName()
                    +","+response.getDefault_item_group().getData().get(i).getAttributes().getItems().get(j).getId());

                    id.add(response.getDefault_item_group().getData().get(i).getAttributes().getItems().get(j).getId());
                }
                listDataChild.put(listDataHeader.get(i), name);
                listDataChildId.put(i, id);
            }
            listAdapter = new DefaultItemsExpandableListAdapter(this, listDataHeader, listDataChild,listDataChildId);

            // setting list adapter
            expListView.setAdapter(listAdapter);
            for (int i = 0; i < listAdapter.getGroupCount(); i++) {
                expListView.expandGroup(i);
            }
        } else {
            //   startActivity(new Intent(getApplicationContext(), MasterDashboardActivity.class));
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void createDefaultItemsResponse(CreateDefaultItemsResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            DefaultItemsExpandableListAdapter.listData.clear();
            LocalRepositories.saveAppUser(getApplicationContext(),appUser);

            Intent intent = new Intent(DefaultItemsExpandableListActivity.this,CompanyDashboardActivity.class);
            startActivity(intent);

        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            mProgressDialog.dismiss();
        }
    }
}
