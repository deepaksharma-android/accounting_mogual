package com.berylsystems.buzz.activities.company.administration.master.billsundry;

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
import com.berylsystems.buzz.activities.company.administration.master.item.ExpandableItemListActivity;
import com.berylsystems.buzz.activities.company.transaction.purchase.CreatePurchaseActivity;
import com.berylsystems.buzz.activities.company.transaction.purchase.PurchaseAddBillActivity;
import com.berylsystems.buzz.activities.company.transaction.purchase_return.CreatePurchaseReturnActivity;
import com.berylsystems.buzz.activities.company.transaction.purchase_return.PurchaseReturnAddBillActivity;
import com.berylsystems.buzz.activities.company.transaction.sale.CreateSaleActivity;
import com.berylsystems.buzz.activities.company.transaction.sale.SaleVoucherAddBillActivity;
import com.berylsystems.buzz.activities.company.transaction.sale_return.CreateSaleReturnActivity;
import com.berylsystems.buzz.activities.company.transaction.sale_return.SaleReturnAddBillActivity;
import com.berylsystems.buzz.activities.dashboard.MasterDashboardActivity;
import com.berylsystems.buzz.adapters.BillSundryListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.bill_sundry.DeleteBillSundryResponse;
import com.berylsystems.buzz.networks.api_response.bill_sundry.GetBillSundryListResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventDeleteBillSundry;
import com.berylsystems.buzz.utils.EventSaleAddBill;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class BillSundryListActivity extends AppCompatActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.bill_sundry_list_recycler_view)
    RecyclerView mRecyclerView;
    @Bind(R.id.floating_button)
    FloatingActionButton mFloatingButton;
    RecyclerView.LayoutManager layoutManager;
    BillSundryListAdapter mAdapter;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar  snackbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_sundry_list);
        ButterKnife.bind(this);
        initActionbar();
        mFloatingButton.bringToFront();
        appUser = LocalRepositories.getAppUser(this);
    }
    public void add(View v) {
        Intent intent=new Intent(getApplicationContext(), CreateBillSundryActivity.class);
        intent.putExtra("fromlist",true);
        startActivity(intent);
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
        actionbarTitle.setText("ACCOUNT GROUP LIST");
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                Intent intent = new Intent(this, MasterDashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

 /*   @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MasterDashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(BillSundryListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_BILL_SUNDRY_LIST);
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

    @Subscribe
    public void getUnitList(GetBillSundryListResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200) {
            appUser.arr_billSundryId.clear();
            appUser.arr_billSundryName.clear();
            appUser.billSundryName.clear();
            appUser.billSundryId.clear();
            LocalRepositories.saveAppUser(this, appUser);
            Timber.i("I AM HERE");
            if(response.getBill_sundries().getData().size()==0){
                Snackbar.make(coordinatorLayout,"No Bill Sundry Found!!",Snackbar.LENGTH_LONG).show();
            }
                for (int i = 0; i < response.getBill_sundries().getData().size(); i++) {
                    appUser.arr_billSundryName.add(response.getBill_sundries().getData().get(i).getAttributes().getName());
                    appUser.arr_billSundryId.add(response.getBill_sundries().getData().get(i).getId());
                    if (response.getBill_sundries().getData().get(i).getAttributes().getUndefined() == false) {
                        appUser.billSundryName.add(response.getBill_sundries().getData().get(i).getAttributes().getName());
                        appUser.billSundryId.add(response.getBill_sundries().getData().get(i).getId());
                    }

                    LocalRepositories.saveAppUser(this, appUser);
                }
                mRecyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(layoutManager);
                mAdapter = new BillSundryListAdapter(this, response.getBill_sundries().getData());
                mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Subscribe
    public void deletebillsundry(EventDeleteBillSundry pos){
        appUser.delete_bill_sundry_id= String.valueOf(appUser.arr_billSundryId.get(pos.getPosition()));
        LocalRepositories.saveAppUser(this,appUser);
        new AlertDialog.Builder(BillSundryListActivity.this)
                .setTitle("Delete Bill Sundry")
                .setMessage("Are you sure you want to delete this bill sundry ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if(isConnected) {
                        mProgressDialog = new ProgressDialog(BillSundryListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_BILL_SUNDRY);
                    }
                    else{
                        snackbar = Snackbar
                                .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Boolean isConnected = ConnectivityReceiver.isConnected();
                                        if(isConnected){
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
    public void deletebillsundryresponse(DeleteBillSundryResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){

            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_BILL_SUNDRY_LIST);
        }
        else{
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }


    @Subscribe
    public void ClickEventAddSaleVoucher(EventSaleAddBill pos) {
        String id = pos.getPosition();
        if (ExpandableItemListActivity.comingFrom==0){
            Intent intent = new Intent(getApplicationContext(), SaleVoucherAddBillActivity.class);
            intent.putExtra("id", id);
            intent.putExtra("fromvoucherbilllist",false);
            startActivity(intent);
            finish();
        }
        else if (ExpandableItemListActivity.comingFrom==1){
            Intent intent = new Intent(getApplicationContext(), PurchaseAddBillActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        } else if (ExpandableItemListActivity.comingFrom == 2) {
            Intent intent = new Intent(getApplicationContext(), SaleReturnAddBillActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        } else if (ExpandableItemListActivity.comingFrom == 3) {
            Intent intent = new Intent(getApplicationContext(), PurchaseReturnAddBillActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
            finish();
        }

    }


    @Override
    public void onBackPressed() {
        if (ExpandableItemListActivity.comingFrom==0){
            Intent intent=new Intent(this, CreateSaleActivity.class);
            intent.putExtra("is",true);
            intent.putExtra("fromvoucherbilllist",false);
            startActivity(intent);
            finish();
        } else  if (ExpandableItemListActivity.comingFrom==1){
            Intent intent=new Intent(this, CreatePurchaseActivity.class);
            intent.putExtra("is",true);
            startActivity(intent);
            finish();
        } else if (ExpandableItemListActivity.comingFrom == 2) {
            Intent intent = new Intent(this, CreateSaleReturnActivity.class);
            intent.putExtra("is", true);
            startActivity(intent);
            finish();
        } else if (ExpandableItemListActivity.comingFrom == 3) {
            Intent intent = new Intent(this, CreatePurchaseReturnActivity.class);
            intent.putExtra("is", true);
            startActivity(intent);
            finish();
        }

    }

}