package com.berylsystems.buzz.activities.company.administration.master.materialcentre;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ExpandableListView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.adapters.MaterialCentreListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.materialcentre.DeleteMaterialCentreResponse;
import com.berylsystems.buzz.networks.api_response.materialcentre.GetMaterialCentreListResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventDeleteMaterialCentre;
import com.berylsystems.buzz.utils.EventEditMaterialCentre;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MaterialCentreListActivity extends BaseActivityCompany {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.lvExp)
    ExpandableListView expListView;
    MaterialCentreListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<Integer, List<String>> listDataChildId;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar snackbar;
    List<String> name;
    List<String> id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_centre_list);
        ButterKnife.bind(this);
        setAddCompany(0);
        setAppBarTitleCompany(1, "MATERIAL CENTRE LIST");
        appUser = LocalRepositories.getAppUser(this);


       /* mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MaterialCentreListAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(MaterialCentreListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_MATERIAL_CENTRE_LIST);
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
        Intent intent = new Intent(getApplicationContext(), CreateMaterialCentreActivity.class);
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
    public void getCreateMaterialList(GetMaterialCentreListResponse response) {
        mProgressDialog.dismiss();

        if (response.getStatus() == 200) {
            listDataHeader = new ArrayList<>();
            listDataChild = new HashMap<String, List<String>>();
            listDataChildId = new HashMap<Integer, List<String>>();
            if(response.getOrdered_material_centers().size()==0){
                Snackbar.make(coordinatorLayout,"No Material Centre Found!!",Snackbar.LENGTH_LONG).show();
            }
                for (int i = 0; i < response.getOrdered_material_centers().size(); i++) {
                    listDataHeader.add(response.getOrdered_material_centers().get(i).getGroup_name());
                    name = new ArrayList<>();
                    id = new ArrayList<>();
                    for (int j = 0; j < response.getOrdered_material_centers().get(i).getData().size(); j++) {
                        name.add(response.getOrdered_material_centers().get(i).getData().get(j).getAttributes().getName() + "," + String.valueOf(response.getOrdered_material_centers().get(i).getData().get(j).getAttributes().getUndefined()));
                        id.add(response.getOrdered_material_centers().get(i).getData().get(j).getId());
                    }
                    listDataChild.put(listDataHeader.get(i), name);
                    listDataChildId.put(i, id);
                }
                listAdapter = new MaterialCentreListAdapter(this, listDataHeader, listDataChild);

                // setting list adapter
                expListView.setAdapter(listAdapter);

        } else {
            //   startActivity(new Intent(getApplicationContext(), MasterDashboardActivity.class));
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
      /*  expListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount
                    , int totalItemCount) {
                if(firstVisibleItem+visibleItemCount>=totalItemCount){
                    //the last item is visible
                }

            }
        });*/
    }

    @Subscribe
    public void deletematerialcentre(EventDeleteMaterialCentre pos) {
        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        appUser.delete_material_centre_id = arrid;
        LocalRepositories.saveAppUser(this, appUser);
        new AlertDialog.Builder(MaterialCentreListActivity.this)
                .setTitle("Delete Company")
                .setMessage("Are you sure you want to delete this account ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(MaterialCentreListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_MATERIAL_CENTRE);
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
    public void deletematerialcentreresponse(DeleteMaterialCentreResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_MATERIAL_CENTRE_LIST);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void editmaterialcentre(EventEditMaterialCentre pos) {
        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        appUser.edit_material_centre_id = arrid;
        LocalRepositories.saveAppUser(this, appUser);
        Intent intent = new Intent(getApplicationContext(), CreateMaterialCentreActivity.class);
        intent.putExtra("frommaterialcentrelist", true);
        startActivity(intent);

    }
}