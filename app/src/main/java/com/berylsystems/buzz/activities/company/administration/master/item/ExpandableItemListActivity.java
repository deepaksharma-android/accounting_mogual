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
    HashMap<Integer, List<String>> listDataChildasdfs;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar snackbar;
    List<String> name;
    List<String> id;


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
            listDataChild = new HashMap<String, List<String>>();
            listDataChildId = new HashMap<Integer, List<String>>();
            if(response.getOrdered_items().size()==0){
                Snackbar.make(coordinatorLayout,"No Item Found!!",Snackbar.LENGTH_LONG).show();
            }
                for (int i = 0; i < response.getOrdered_items().size(); i++) {
                    listDataHeader.add(response.getOrdered_items().get(i).getGroup_name());
                    name = new ArrayList<>();
                    id = new ArrayList<>();
                    for (int j = 0; j < response.getOrdered_items().get(i).getData().size(); j++) {
                        name.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getName());
                        id.add(response.getOrdered_items().get(i).getData().get(j).getId());
                    }
                    listDataChild.put(listDataHeader.get(i), name);
                    listDataChildId.put(i, id);
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
        Intent intent = new Intent(getApplicationContext(), SaleVoucherAddItemActivity.class);
        String itemName=name.get(Integer.valueOf(childid)).toString();
        intent.putExtra("fromitemlist", true);
        intent.putExtra("id",childid);
        intent.putExtra("name",itemName);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, CreateSaleActivity.class);
        intent.putExtra("is",true);
        startActivity(intent);
        finish();
    }
}
