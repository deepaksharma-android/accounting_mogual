package com.lkintechnology.mBilling.activities.company.navigations.reports.outstanding;

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
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.AccountDetailsActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.reports.ReportsActivity;
import com.lkintechnology.mBilling.adapters.AmountReceivableAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.account.GetAccountResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventAmountReceivable;
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

public class AmountReceivablesListActivity extends AppCompatActivity {
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
    AmountReceivableAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<Integer, List<String>> listDataChildId;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar snackbar;
    List<String> name;
    List<String> id;
    ArrayList amountList=new ArrayList();
    Boolean amountreceivable;
    Boolean amounReceivabletForDirect,amountPaybleForDirect;
    String title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount_receivables_list);

        ButterKnife.bind(this);
        mFloatingButton.bringToFront();
         amounReceivabletForDirect=getIntent().getBooleanExtra("amounReceivabletForDirect",false);
         amountPaybleForDirect=getIntent().getBooleanExtra("amountPaybleForDirect",false);
         if(amounReceivabletForDirect){
             title="AMOUNT RECEIVABLE";
         }
         else{
             title="AMOUNT PAYABLE";
         }
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
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
        actionbarTitle.setText(title);
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
            mProgressDialog = new ProgressDialog(AmountReceivablesListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ACCOUNT);
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
        Intent intent = new Intent(getApplicationContext(), AccountDetailsActivity.class);
        intent.putExtra("fromaccountlist", false);
        startActivity(intent);
    }

    @Subscribe
    public void getAccountList(GetAccountResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            listDataHeader = new ArrayList<>();
            listDataChild = new HashMap<String, List<String>>();
            // listDataChildAmount = new HashMap<Integer, List<String>>();
            listDataChildId = new HashMap<Integer, List<String>>();
            if (response.getOrdered_accounts().size() == 0) {
                //Snackbar.make(coordinatorLayout, "No Account Found!!", Snackbar.LENGTH_LONG).show();
                main_layout.setVisibility(View.GONE);
                error_layout.setVisibility(View.VISIBLE);
            }else {
                main_layout.setVisibility(View.VISIBLE);
                error_layout.setVisibility(View.GONE);
            }
            for (int i = 0; i < response.getOrdered_accounts().size(); i++) {
                listDataHeader.add(response.getOrdered_accounts().get(i).getGroup_name());
                name = new ArrayList<>();
                //amount = new ArrayList<>();
                id = new ArrayList<>();
                Double addAmount=0.0;
                for (int j = 0; j < response.getOrdered_accounts().get(i).getData().size(); j++) {
                    name.add(response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getName() + "," + String.valueOf(response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getUndefined()) + "," + String.valueOf(response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getAmount()));
                    id.add(response.getOrdered_accounts().get(i).getData().get(j).getId());
                    Double addprize = response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getAmount();
                    addAmount = addAmount+addprize;
                }
                amountList.add(addAmount);
                listDataChild.put(listDataHeader.get(i), name);
                listDataChildId.put(i, id);
            }
            listAdapter = new AmountReceivableAdapter(this, listDataHeader, listDataChild,amountList);

            // setting list adapter
            expListView.setAdapter(listAdapter);

        } else {
            //   startActivity(new Intent(getApplicationContext(), MasterDashboardActivity.class));
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
    public void ClickEventAccountList(EventAmountReceivable pos) {

        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        String name = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
        appUser.childId = arrid;
        LocalRepositories.saveAppUser(this, appUser);
        Intent getIntent=getIntent();

        if(amounReceivabletForDirect){
            Intent intent = new Intent(getApplicationContext(), AmountReceivableActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("id",arrid);
            ExpandableAccountListActivity.isDirectForAccount=false;
            startActivity(intent);
        }if(amountPaybleForDirect) {
            Intent intent = new Intent(getApplicationContext(), AmountPaybleActivity.class);
            intent.putExtra("name",name);
            intent.putExtra("id",arrid);
            ExpandableAccountListActivity.isDirectForAccount=false;
            startActivity(intent);
        }
    }

  /*  @Subscribe
    public void deletegroup(EventDeleteAccount pos) {
        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        appUser.delete_account_id = arrid;
        LocalRepositories.saveAppUser(this, appUser);
        new AlertDialog.Builder(TransactionCustomerActivity.this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete this account ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(TransactionCustomerActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_ACCOUNT);
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
    public void deletegroupresponse(DeleteAccountResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ACCOUNT);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void editgroup(EventEditAccount pos) {
        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        appUser.edit_account_id = arrid;
        LocalRepositories.saveAppUser(this, appUser);
        Intent intent = new Intent(getApplicationContext(), AccountDetailsActivity.class);
        intent.putExtra("fromaccountlist", true);
        startActivity(intent);

    }
*/

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
