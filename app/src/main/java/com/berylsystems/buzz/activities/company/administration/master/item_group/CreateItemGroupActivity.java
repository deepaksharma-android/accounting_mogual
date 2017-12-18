package com.berylsystems.buzz.activities.company.administration.master.item_group;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.app.RegisterAbstractActivity;
import com.berylsystems.buzz.activities.company.administration.master.accountgroup.AccountGroupListActivity;
import com.berylsystems.buzz.activities.company.administration.master.accountgroup.CreateAccountGroupActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.accountgroup.CreateAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.EditAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.GetAccountGroupDetailsResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.GetAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.CreateItemGroupResponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.EditItemGroupResponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.GetItemGroupDetailsResponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.GetItemGroupResponse;
import com.berylsystems.buzz.networks.api_response.itemgroup.ItemGroups;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Timer;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class CreateItemGroupActivity extends RegisterAbstractActivity {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.under_group_spinner)
    Spinner mSpinnerUnderGroup;
    @Bind(R.id.under_group_layout)
    LinearLayout mUnderGroupLayout;
    @Bind(R.id.primary_spinner)
    Spinner mSpinnerPrimary;
    @Bind(R.id.group_name)
    EditText mGroupName;
    @Bind(R.id.submitLayout)
    LinearLayout mSubmit;
    @Bind(R.id.update)
    LinearLayout mUpdate;
    ArrayAdapter<String> mPrimaryGroupAdapter;
    ArrayAdapter<String> mUnderGroupAdapter;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Boolean fromItemGroupList;
    Snackbar snackbar;
    String title;
    public static ItemGroups data;
    ArrayList<String> grouplistname;
    ArrayList<String> grouplistid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);

        appUser = LocalRepositories.getAppUser(this);
        title="CREATE ITEM GROUP";
        fromItemGroupList = getIntent().getExtras().getBoolean("fromitemgrouplist");
        if (fromItemGroupList == true) {
            Timber.i("ItemGrop");
            title="EDIT ITEM GROUP";
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            appUser.edit_group_id1 = getIntent().getExtras().getString("id");
            LocalRepositories.saveAppUser(this, appUser);
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateItemGroupActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ITEM_GROUP_DETAILS);
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
        initActionbar();


        grouplistname=new ArrayList<>();
        grouplistid=new ArrayList<>();
        for(int i=0;i<data.getData().size();i++){
            if (data.getData().get(i).getAttributes().getUndefined() == false) {
                grouplistname.add(data.getData().get(i).getAttributes().getName());
                grouplistid.add(String.valueOf(data.getData().get(i).getAttributes().getId()));
            }
           /* if (data.getData().get(i).getAttributes().getUndefined() == false) {
                appUser.group_name.add(data.getData().get(i).getAttributes().getName());
                appUser.group_id.add(data.getData().get(i).getAttributes().getId());
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            }
            appUser.arr_account_group_name.add(data.getData().get(i).getAttributes().getName());
            appUser.arr_account_group_id.add(data.getData().get(i).getAttributes().getId());
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);*/
        }
        mPrimaryGroupAdapter = new ArrayAdapter<String>(this,
                R.layout.layout_trademark_type_spinner_dropdown_item, getResources().getStringArray(R.array.primary_group));
        mPrimaryGroupAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mSpinnerPrimary.setAdapter(mPrimaryGroupAdapter);
        mUnderGroupAdapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.layout_trademark_type_spinner_dropdown_item, grouplistname);
        mUnderGroupAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
        mSpinnerUnderGroup.setAdapter(mUnderGroupAdapter);
        mSpinnerPrimary.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    mUnderGroupLayout.setVisibility(View.VISIBLE);
                    mSpinnerUnderGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            appUser.item_group_id = String.valueOf(grouplistid.get(i));
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } else {
                    appUser.item_group_id = "";
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    mUnderGroupLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!mGroupName.getText().toString().equals("")){
                    appUser.item_group_name=mGroupName.getText().toString();
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if(isConnected) {
                        mProgressDialog = new ProgressDialog(CreateItemGroupActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_ITEM_GROUP);
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
                }
            }
        });

        mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mGroupName.getText().toString().equals("")){
                    appUser.item_group_name=mGroupName.getText().toString();
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if(isConnected) {
                        mProgressDialog = new ProgressDialog(CreateItemGroupActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_ITEM_GROUP);
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
                }
            }
        });

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_create_item_group;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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
        actionbarTitle.setText(title);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void createItemGroup(CreateItemGroupResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Boolean isConnected = ConnectivityReceiver.isConnected();

            Intent intent = new Intent(this, ItemGroupListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("fromcreateitem",true);
            startActivity(intent);
           /* if(isConnected) {
                mProgressDialog = new ProgressDialog(CreateItemGroupActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ITEM_GROUP);
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
            }*/

        }
        else{
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

   /* @Override
    public void onBackPressed() {
        ItemGroupListActivity.isDirectForItemGroup=false;
    /*    Intent intent = new Intent(this, ItemGroupListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("fromcreategroup",true);
        startActivity(intent);*/
       /* finish();
    }

    @Subscribe
    public void getItemGroup(GetItemGroupResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.group_id1.clear();
            appUser.group_name1.clear();
            appUser.arr_item_group_id.clear();
            appUser.arr_item_group_name.clear();
            LocalRepositories.saveAppUser(this, appUser);
            for (int i = 0; i < response.getItem_groups().getData().size(); i++) {
                appUser.arr_item_group_name.add(response.getItem_groups().getData().get(i).getAttributes().getName());
                appUser.arr_item_group_id.add(response.getItem_groups().getData().get(i).getAttributes().getId());
                LocalRepositories.saveAppUser(this, appUser);
                ItemGroupListActivity.isDirectForItemGroup=false;
                Intent intent = new Intent(this, ItemGroupListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("fromcreategroup",true);
                startActivity(intent);
            }
        }
    }*/

    @Subscribe
    public void getItemGroupDetails(GetItemGroupDetailsResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            mGroupName.setText(response.getItem_group().getData().getAttributes().getName());
                if(!response.getItem_group().getData().getAttributes().getName().equals("")){
                    mSpinnerPrimary.setSelection(1);
                    mSpinnerUnderGroup.setVisibility(View.VISIBLE);
                    String group_type = response.getItem_group().getData().getAttributes().getItem_groups().trim();
                    Timber.i("GROUPINDEX"+group_type);
                    // insert code here
                    int groupindex = -1;
                    for (int i = 0; i<appUser.group_name1.size(); i++) {
                        Timber.i("GROUPINDEX"+appUser.group_name1);
                        if (appUser.group_name1.get(i).equals(group_type)) {
                            groupindex = i;
                            break;
                        }
                    Timber.i("GROUPINDEX"+groupindex);
                    mSpinnerUnderGroup.setSelection(groupindex);
                }
            }
        }
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void editItemGroupDetails(EditItemGroupResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            ItemGroupListActivity.isDirectForItemGroup=false;
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Intent intent = new Intent(this, ItemGroupListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("fromcreategroup",true);
            startActivity(intent);
        }
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }
}
