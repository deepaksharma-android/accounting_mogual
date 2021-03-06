package com.lkintechnology.mBilling.activities.company.navigations.reports.stock_status;

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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.CreateNewItemActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.ReportsActivity;
import com.lkintechnology.mBilling.adapters.TransactionStockInHandAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.item.DeleteItemResponse;
import com.lkintechnology.mBilling.networks.api_response.item.GetItemResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventDeleteItem;
import com.lkintechnology.mBilling.utils.EventEditItem;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class StocksReportsActivity extends AppCompatActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.lvExp)
    ExpandableListView expListView;
    @Bind(R.id.floating_button)
    FloatingActionButton mFloatingButton;
    @Bind(R.id.main_layout)
    RelativeLayout main_layout;
    @Bind(R.id.error_layout)
    LinearLayout error_layout;
    TransactionStockInHandAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<Integer, List<String>> listDataChildId;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar snackbar;
    List<String> name;
    List<String> id;
    ArrayList amountList=new ArrayList();
    ArrayList quantityList=new ArrayList();
    String title;
    public static Boolean isDirectForFirstPage = true;
    Boolean fromStockReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stocks_reports);

        ButterKnife.bind(this);
        mFloatingButton.bringToFront();

        appUser = LocalRepositories.getAppUser(this);

        initActionbar();
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
        actionbarTitle.setText("Stock Reports");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                    Intent intent = new Intent(this, ReportsActivity.class);
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
        Intent intent = new Intent(this, ReportsActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(StocksReportsActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ITEM);
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

    public void add(View v) {
        Intent intent = new Intent(getApplicationContext(), CreateNewItemActivity.class);
        intent.putExtra("fromlist", false);
        startActivity(intent);
    }

    @Subscribe
    public void getstocksreports(GetItemResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            listDataHeader = new ArrayList<>();
            listDataChild = new HashMap<String, List<String>>();
            // listDataChildAmount = new HashMap<Integer, List<String>>();
            listDataChildId = new HashMap<Integer, List<String>>();
            if (response.getOrdered_items().size() == 0) {
                //Snackbar.make(coordinatorLayout, "No Item Found!!", Snackbar.LENGTH_LONG).show();
                main_layout.setVisibility(View.GONE);
                error_layout.setVisibility(View.VISIBLE);
            }else {
                main_layout.setVisibility(View.VISIBLE);
                error_layout.setVisibility(View.GONE);
            }
            for (int i = 0; i < response.getOrdered_items().size(); i++) {
                listDataHeader.add(response.getOrdered_items().get(i).getGroup_name());
                name = new ArrayList<>();
                //amount = new ArrayList<>();
                id = new ArrayList<>();
                Double addAmount=0.0;
                int addQuantity=0;
                Double totalstockprice;
                int totalstockquantity;
                for (int j = 0; j < response.getOrdered_items().get(i).getData().size(); j++) {
                    if(response.getOrdered_items().get(i).getData().get(j).getAttributes().getTotal_stock_price()!=null){
                        totalstockprice=response.getOrdered_items().get(i).getData().get(j).getAttributes().getTotal_stock_price();
                    }
                    else{
                        totalstockprice=0.0;
                    }
                    if(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getTotal_stock_quantity())!=null){
                        totalstockquantity=response.getOrdered_items().get(i).getData().get(j).getAttributes().getTotal_stock_quantity();
                    }
                    else{
                        totalstockquantity=0;
                    }
                    if(totalstockquantity!=0)
                    {
                        name.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getName()
                            /*+ "," + String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getUndefined())*/
                                + "," + String.valueOf(totalstockprice)
                                + "," + String.valueOf(totalstockquantity)
                                + "," + String.valueOf(response.getOrdered_items().get(i).getData().get(j).getId()));
                    }
                    id.add(response.getOrdered_items().get(i).getData().get(j).getId());
                    Double addprize;
                    int addquantity;
                    if(!Helpers.mystring(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getTotal_stock_price())).equals("")){
                        addprize = response.getOrdered_items().get(i).getData().get(j).getAttributes().getTotal_stock_price();
                    }
                    else{
                        addprize =0.0;
                    }
                    if(!Helpers.mystring(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getTotal_stock_quantity())).equals("")) {
                        addquantity = response.getOrdered_items().get(i).getData().get(j).getAttributes().getTotal_stock_quantity();
                    }
                    else{
                        addquantity=0;
                    }
                    addAmount = addAmount+addprize;
                    addQuantity = addQuantity+addquantity;
                }
                amountList.add(Double.valueOf(String.format("%.2f",addAmount)));
                quantityList.add(addQuantity);
                listDataChild.put(listDataHeader.get(i), name);
                listDataChildId.put(i, id);
            }
            listAdapter = new TransactionStockInHandAdapter(this, listDataHeader, listDataChild,amountList,quantityList);

            // setting list adapter
            expListView.setAdapter(listAdapter);

        } else {
            //   startActivity(new Intent(getApplicationContext(), MasterDashboardActivity.class));
           // Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
    public void delete_item(EventDeleteItem pos) {
        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        appUser.delete_item_id = arrid;
        LocalRepositories.saveAppUser(this, appUser);
        new AlertDialog.Builder(StocksReportsActivity.this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(StocksReportsActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_ITEM);
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
    public void deleteitemresponse(DeleteItemResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ITEM);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
    public void editgroup(EventEditItem pos) {
        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        appUser.edit_item_id = arrid;
        LocalRepositories.saveAppUser(this, appUser);
        Intent intent = new Intent(getApplicationContext(), CreateNewItemActivity.class);
        intent.putExtra("fromitemlist", true);
        intent.putExtra("fromstockinhand", true);
        startActivity(intent);
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
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }
}
