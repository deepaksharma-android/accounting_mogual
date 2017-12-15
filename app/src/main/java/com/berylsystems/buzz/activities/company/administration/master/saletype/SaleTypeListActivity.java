package com.berylsystems.buzz.activities.company.administration.master.saletype;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;

import com.berylsystems.buzz.activities.dashboard.MasterDashboardActivity;
import com.berylsystems.buzz.adapters.SaleTypeListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;

import com.berylsystems.buzz.networks.api_response.saletype.GetSaleTypeResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventGroupClicked;
import com.berylsystems.buzz.utils.EventPurchaseClicked;
import com.berylsystems.buzz.utils.EventSaleClicked;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SaleTypeListActivity extends AppCompatActivity {


    AppUser appUser;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    RecyclerView.LayoutManager layoutManager;
    SaleTypeListAdapter mAdapter;
    @Bind(R.id.sale_type_list_recycler_view)
    RecyclerView mRecyclerView;
    Boolean fromGeneral, fromMaster, fromCreateGroup;
    public static Boolean isDirectForSaleType = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_type_list);


        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
        EventBus.getDefault().register(this);

        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(SaleTypeListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_SALE_TYPE);
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
        actionbarTitle.setText("SALE TYPE");
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
        if (isDirectForSaleType) {
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
    public void getItemGroup(GetSaleTypeResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {

            mRecyclerView.setHasFixedSize(true);
            layoutManager = new LinearLayoutManager(getApplicationContext());
            mRecyclerView.setLayoutManager(layoutManager);
            mAdapter = new SaleTypeListAdapter(this, response.getSale_type().data);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void itemclickedevent(EventSaleClicked pos) {

        if (!isDirectForSaleType) {

            Timber.i("POSITION" + pos.getPosition());
            String str = pos.getPosition();
            String[] strAr = str.split(",");
            Intent returnIntent = new Intent();
            returnIntent.putExtra("name", strAr[0]);
            returnIntent.putExtra("id", strAr[1]);
            appUser.sale_type_name = strAr[0];
            LocalRepositories.saveAppUser(this, appUser);
        /*appUser.create_account_group_id = String.valueOf(appUser.arr_account_group_id.get(pos.getPosition()));
          LocalRepositories.saveAppUser(this, appUser);*/
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }

    }


    @Subscribe
    public void itemclickedevent(EventPurchaseClicked pos) {

        if (!isDirectForSaleType) {
            Timber.i("POSITION" + pos.getPosition());
            String str = pos.getPosition();
            String[] strAr = str.split(",");
            Intent returnIntent = new Intent();
            returnIntent.putExtra("name", strAr[0]);
            returnIntent.putExtra("id", strAr[1]);
        /*appUser.create_account_group_id = String.valueOf(appUser.arr_account_group_id.get(pos.getPosition()));
          LocalRepositories.saveAppUser(this, appUser);*/
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

}

