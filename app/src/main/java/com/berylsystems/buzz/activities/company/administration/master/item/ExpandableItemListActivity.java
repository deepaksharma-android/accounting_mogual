package com.berylsystems.buzz.activities.company.administration.master.item;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.company.administration.master.account.AccountDetailsActivity;
import com.berylsystems.buzz.activities.company.administration.master.account.ExpandableAccountListActivity;
import com.berylsystems.buzz.activities.company.purchase.PurchaseAddItemActivity;
import com.berylsystems.buzz.activities.company.sale.CreateSaleActivity;
import com.berylsystems.buzz.activities.company.sale.SaleVoucherAddItemActivity;
import com.berylsystems.buzz.adapters.ItemExpandableListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.account.DeleteAccountResponse;
import com.berylsystems.buzz.networks.api_response.item.DeleteItemResponse;
import com.berylsystems.buzz.networks.api_response.item.GetItemResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventDeleteAccount;
import com.berylsystems.buzz.utils.EventDeleteItem;
import com.berylsystems.buzz.utils.EventEditAccount;
import com.berylsystems.buzz.utils.EventEditItem;
import com.berylsystems.buzz.utils.EventSaleAddItem;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ExpandableItemListActivity extends BaseActivityCompany {


    public static Integer comingFrom=0;

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.lvExp)
    ExpandableListView expListView;
    @Bind(R.id.floating_button)
    FloatingActionButton floatingActionButton;

    ItemExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<Integer, List<String>> listDataChildId;
    HashMap<Integer, List<String>> listDataChildDesc;
    HashMap<Integer, List<String>> listDataChildUnit;
    HashMap<Integer, List<String>> listDataChildAlternateUnit;
    HashMap<Integer, List<String>> listDataChildSalePriceMain;
    HashMap<Integer, List<String>> listDataChildSalePriceAlternate;
    HashMap<Integer, List<Boolean>> listDataChildSerialWise;
    HashMap<Integer, List<Boolean>> listDataChildBatchWise;

    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar snackbar;
    List<String> name;
    List<String> id;
    List<String> description;
    List<String> unit;
    List<String> alternateUnit;
    List<String> salesPriceMain;
    List<String> salesPriceAlternate;
    List<Boolean> serailWise;
    List<Boolean> batchWise;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_item_list);
        ButterKnife.bind(this);
        setAddCompany(0);
        setAppBarTitleCompany(1, "ITEM LIST");
        appUser = LocalRepositories.getAppUser(this);
        floatingActionButton.bringToFront();
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(this);
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

        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_on_click);
        v.startAnimation(animFadeIn);
        Intent intent = new Intent(getApplicationContext(), CreateNewItemActivity.class);
        intent.putExtra("fromlist",true);
        startActivity(intent);
    }


    @Subscribe
    public void getItem(GetItemResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            listDataHeader = new ArrayList<>();
            listDataChildDesc =new HashMap<Integer, List<String>>();
            listDataChildUnit =new HashMap<Integer, List<String>>();
            listDataChildAlternateUnit =new HashMap<Integer, List<String>>();
            listDataChildSalePriceMain =new HashMap<Integer, List<String>>();
            listDataChildSalePriceAlternate =new HashMap<Integer, List<String>>();
            listDataChildSerialWise =new HashMap<Integer, List<Boolean>>();
            listDataChildBatchWise =new HashMap<Integer, List<Boolean>>();
            listDataChild = new HashMap<String, List<String>>();
            listDataChildId = new HashMap<Integer, List<String>>();
            if(response.getOrdered_items().size()==0){
                Snackbar.make(coordinatorLayout,"No Item Found!!",Snackbar.LENGTH_LONG).show();
            }
                for (int i = 0; i < response.getOrdered_items().size(); i++) {
                    listDataHeader.add(response.getOrdered_items().get(i).getGroup_name());
                    name = new ArrayList<>();
                    description = new ArrayList<>();
                    unit = new ArrayList<>();
                    salesPriceMain = new ArrayList<>();
                    salesPriceAlternate = new ArrayList<>();
                    alternateUnit = new ArrayList<>();
                    serailWise = new ArrayList<>();
                    batchWise = new ArrayList<>();
                    id = new ArrayList<>();
                    for (int j = 0; j < response.getOrdered_items().get(i).getData().size(); j++) {
                        name.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getName());
                        if(response.getOrdered_items().get(i).getData().get(j).getAttributes().getItem_description()!=null) {
                            description.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getItem_description());
                        }
                        else{
                            description.add("");
                        }
                        if(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getSales_price_main())!=null) {
                            salesPriceMain.add(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getSales_price_main()));
                        }
                        else{
                            salesPriceMain.add("");
                        }
                        if(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getSales_price_alternate())!=null) {
                            salesPriceAlternate.add(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getSales_price_alternate()));
                        }
                        else{
                            salesPriceAlternate.add("");
                        }
                        unit.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getItem_unit());
                        if(response.getOrdered_items().get(i).getData().get(j).getAttributes().getAlternate_unit()!=null) {
                            alternateUnit.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getAlternate_unit());
                        }
                        else{
                            alternateUnit.add("");
                        }
                        if(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().isBatch_wise_detail())!=null) {
                            batchWise.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().isBatch_wise_detail());
                        }
                        else{
                            batchWise.add(false);
                        }
                        if(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().isSerial_number_wise_detail())!=null) {
                            serailWise.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().isSerial_number_wise_detail());
                        }
                        else{
                            serailWise.add(false);
                        }
                        id.add(response.getOrdered_items().get(i).getData().get(j).getId());
                    }
                    listDataChild.put(listDataHeader.get(i), name);
                    listDataChildId.put(i, id);
                    listDataChildDesc.put(i, description);
                    listDataChildUnit.put(i, unit);
                    listDataChildAlternateUnit.put(i, alternateUnit);
                    listDataChildSalePriceMain.put(i, salesPriceMain);
                    listDataChildSalePriceAlternate.put(i, salesPriceAlternate);
                    listDataChildSerialWise.put(i, serailWise);
                    listDataChildBatchWise.put(i, batchWise);
                }
                listAdapter = new ItemExpandableListAdapter(this, listDataHeader, listDataChild);

                // setting list adapter
                expListView.setAdapter(listAdapter);




        } else {
            //   startActivity(new Intent(getApplicationContext(), MasterDashboardActivity.class));
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
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
        new AlertDialog.Builder(ExpandableItemListActivity.this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(ExpandableItemListActivity.this);
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
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
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

        startActivity(intent);
    }

    @Subscribe
    public void ClickEventAddSaleVoucher(EventSaleAddItem pos) {
        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        appUser.childId = arrid;
        LocalRepositories.saveAppUser(this, appUser);
 if (ExpandableItemListActivity.comingFrom==0){
        Intent intent = new Intent(getApplicationContext(), SaleVoucherAddItemActivity.class);
        String itemName=listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
        String descr;
        String alternate_unit;
        String sales_price_main;
        String sales_price_alternate;
        Boolean batch,serial;
        descr =listDataChildDesc.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        sales_price_main=listDataChildSalePriceMain.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        sales_price_alternate=listDataChildSalePriceAlternate.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        alternate_unit = listDataChildAlternateUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        batch=listDataChildBatchWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        serial=listDataChildSerialWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        String main_unit=listDataChildUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));;

        intent.putExtra("fromitemlist", true);
        intent.putExtra("id",childid);
        intent.putExtra("name",itemName);
        intent.putExtra("desc",descr);
        intent.putExtra("main_unit",main_unit);
        intent.putExtra("alternate_unit",alternate_unit);
        intent.putExtra("serial_wise",serial);
        intent.putExtra("batch_wise",batch);
        intent.putExtra("sales_price_main",sales_price_main);
        intent.putExtra("sales_price_alternate",sales_price_alternate);

        startActivity(intent);
        finish();
		}
		else if (ExpandableItemListActivity.comingFrom==1){
            Intent intent = new Intent(getApplicationContext(), PurchaseAddItemActivity.class);
            String itemName=name.get(Integer.valueOf(childid)).toString();
            intent.putExtra("fromitemlist", true);
            intent.putExtra("id",childid);
            intent.putExtra("name",itemName);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, CreateSaleActivity.class);
        intent.putExtra("is",true);
        startActivity(intent);
        finish();
    }
}
