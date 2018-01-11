package com.lkintechnology.mBilling.activities.company.administration.master.materialcentre;

import android.app.Activity;
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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.transaction.sale.CreateSaleActivity;
import com.lkintechnology.mBilling.activities.dashboard.MasterDashboardActivity;
import com.lkintechnology.mBilling.adapters.MaterialCentreListAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.materialcentre.DeleteMaterialCentreResponse;
import com.lkintechnology.mBilling.networks.api_response.materialcentre.GetMaterialCentreListResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventDeleteMaterialCentre;
import com.lkintechnology.mBilling.utils.EventEditMaterialCentre;
import com.lkintechnology.mBilling.utils.EventSelectPurchase;
import com.lkintechnology.mBilling.utils.EventSelectSaleVoucher;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MaterialCentreListActivity extends AppCompatActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.lvExp)
    ExpandableListView expListView;
    @Bind(R.id.floating_button)
    FloatingActionButton mFloatingButton;
    MaterialCentreListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<Integer, List<String>> listDataChildId;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar snackbar;
    List<String> name;
    List<String> id;
    public static Boolean isDirectForMaterialCentre = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_centre_list);
        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        mFloatingButton.bringToFront();
        appUser.material_centre_name = "";
        appUser.material_centre_address ="";
        appUser.material_centre_city ="";
        LocalRepositories.saveAppUser(getApplication(),appUser);
       /* mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new MaterialCentreListAdapter(this, null);
        mRecyclerView.setAdapter(mAdapter);*/
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
        actionbarTitle.setText("MATERIAL CENTRE LIST");
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
                if (isDirectForMaterialCentre) {
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
        if (isDirectForMaterialCentre) {
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
    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();

    }
    public void add(View v) {
        Intent intent = new Intent(getApplicationContext(), CreateMaterialCentreActivity.class);
        intent.putExtra("frommaterialcentrelist", false);
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
            if (response.getOrdered_material_centers().size() == 0) {
                Snackbar.make(coordinatorLayout, "No Material Centre Found!!", Snackbar.LENGTH_LONG).show();
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

    @Subscribe
    public void clickEvent(EventSelectSaleVoucher pos) {

        Intent intent = getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);
       // Toast.makeText(this, ""+bool, Toast.LENGTH_SHORT).show();
        if (!isDirectForMaterialCentre && bool) {
            //Toast.makeText(this, "if", Toast.LENGTH_SHORT).show();
            String id = pos.getPosition();
            String[] arr = id.split(",");
            String groupid = arr[0];
            String childid = arr[1];
            String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
            String name = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
            Intent intentForward = new Intent(getApplicationContext(), CreateSaleActivity.class);
            intentForward.putExtra("bool", true);
            intentForward.putExtra("name", name);
            intentForward.putExtra("id", arrid);
            startActivity(intentForward);
            //setResult(Activity.RESULT_OK, intentForward);
            finish();
        }
        else if (!isDirectForMaterialCentre) {
          //  Toast.makeText(this, "else", Toast.LENGTH_SHORT).show();
            String id = pos.getPosition();
            String[] arr = id.split(",");
            String groupid = arr[0];
            String childid = arr[1];
            String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
            String name = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
            Intent returnIntent = new Intent();
            returnIntent.putExtra("name", name);
            returnIntent.putExtra("id", arrid);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }


    @Subscribe
    public void clickEventForPurchase(EventSelectPurchase pos) {
        if (!isDirectForMaterialCentre) {
            String id = pos.getPosition();
            String[] arr = id.split(",");
            String groupid = arr[0];
            String childid = arr[1];
            String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
            String name = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
            Intent returnIntent = new Intent();
            returnIntent.putExtra("name", name);
            returnIntent.putExtra("id", arrid);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }
}