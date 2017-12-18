package com.berylsystems.buzz.activities.company.administration.master.materialcentregroup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.app.RegisterAbstractActivity;
import com.berylsystems.buzz.activities.company.administration.master.accountgroup.AccountGroupListActivity;
import com.berylsystems.buzz.activities.company.administration.master.materialcentre.CreateMaterialCentreActivity;
import com.berylsystems.buzz.adapters.MaterialCentreGroupListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.accountgroup.CreateAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.EditAccountGroupResponse;
import com.berylsystems.buzz.networks.api_response.accountgroup.GetAccountGroupDetailsResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.CreateMaterialCentreGroupResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.EditMaterialCentreGroupResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.GetMaterialCentreGroupDetailResponse;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.GetMaterialCentreGroupListResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventGroupClicked;
import com.berylsystems.buzz.utils.EventMaterialCentreGroupClicked;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.berylsystems.buzz.activities.company.administration.master.materialcentregroup.MaterialCentreGroupListActivity.isDirectForMaterialCentreGroup;

public class CreateMaterialCentreGroupActivity extends RegisterAbstractActivity {
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
    @Bind(R.id.submit)
    LinearLayout mSubmit;
    @Bind(R.id.update)
    LinearLayout mUpdate;
    ArrayAdapter<String> mPrimaryGroupAdapter;
    ArrayAdapter<String> mUnderGroupAdapter;
    Snackbar snackbar;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Boolean fromMaterailCentreGroupList;
    public String title;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        title="CREATE MATERIAL CENTRE GROUP";

        fromMaterailCentreGroupList = getIntent().getExtras().getBoolean("frommaterialcentregrouplist");
        if (fromMaterailCentreGroupList == true) {
            title="EDIT MATERIAL CENTRE GROUP";
            mSubmit.setVisibility(View.GONE);
            mUpdate.setVisibility(View.VISIBLE);
            appUser.edit_material_centre_group_id = getIntent().getExtras().getString("id");
            LocalRepositories.saveAppUser(this, appUser);
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(CreateMaterialCentreGroupActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_MATERIAL_CENTRE_GROUP_DETAILS);
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
            mPrimaryGroupAdapter = new ArrayAdapter<String>(this,
                    R.layout.layout_trademark_type_spinner_dropdown_item, getResources().getStringArray(R.array.primary_group));
            mPrimaryGroupAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
            mSpinnerPrimary.setAdapter(mPrimaryGroupAdapter);
            mUnderGroupAdapter = new ArrayAdapter<String>(getApplicationContext(),
                    R.layout.layout_trademark_type_spinner_dropdown_item, appUser.materialCentreGroupName);
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
                                appUser.material_centre_group_id = String.valueOf(appUser.materialCentreGroupId.get(i));
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    } else {
                        appUser.material_centre_group_id = "";
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
                    if (!mGroupName.getText().toString().equals("")) {
                        appUser.material_centre_group_name = mGroupName.getText().toString();
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if (isConnected) {
                            mProgressDialog = new ProgressDialog(CreateMaterialCentreGroupActivity.this);
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_MATERIAL_CENTRE_GROUP);
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
                }
            });

            mUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!mGroupName.getText().toString().equals("")) {
                        appUser.material_centre_group_name = mGroupName.getText().toString();
                        Boolean isConnected = ConnectivityReceiver.isConnected();
                        if (isConnected) {
                            mProgressDialog = new ProgressDialog(CreateMaterialCentreGroupActivity.this);
                            mProgressDialog.setMessage("Info...");
                            mProgressDialog.setIndeterminate(false);
                            mProgressDialog.setCancelable(true);
                            mProgressDialog.show();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            ApiCallsService.action(getApplicationContext(), Cv.ACTION_EDIT_MATERIAL_CENTRE_GROUP);
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
                }
            });
        }

    @Override
    protected int layoutId() {
        return R.layout.activity_create_material_centre_group;
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
        actionbarTitle.setTextSize(14);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Subscribe
    public void getMaterialCentreGroupDetails(GetMaterialCentreGroupDetailResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            mGroupName.setText(response.getMaterial_center_group().getData().getAttributes().getName());
            if(response.getMaterial_center_group().getData().getAttributes().getMaterial_center_group()!=null){

                mSpinnerPrimary.setSelection(1);
                mSpinnerUnderGroup.setVisibility(View.VISIBLE);
                String group_type = response.getMaterial_center_group().getData().getAttributes().getMaterial_center_group().trim();
                // insert code here
                int groupindex = -1;
                for (int i = 0; i<appUser.materialCentreGroupName.size(); i++) {
                    if (appUser.materialCentreGroupName.get(i).equals(group_type)) {
                        groupindex = i;
                        break;
                    }
                }
                Timber.i("GROUPINDEX"+groupindex);
                mSpinnerUnderGroup.setSelection(groupindex);

            }
            else{
                mSpinnerPrimary.setSelection(0);
                mSpinnerUnderGroup.setVisibility(View.GONE);
            }


        }
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void createAccountGroup(CreateMaterialCentreGroupResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if(isConnected) {
                mProgressDialog = new ProgressDialog(CreateMaterialCentreGroupActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_MATERIAL_CENTRE_GROUP_LIST);
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
            MaterialCentreGroupListActivity.isDirectForMaterialCentreGroup=false;
            startActivity(new Intent(getApplicationContext(),MaterialCentreGroupListActivity.class));
        }
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }

    }

    @Subscribe
    public void editAccountGroupDetails(EditMaterialCentreGroupResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            MaterialCentreGroupListActivity.isDirectForMaterialCentreGroup=false;
            Intent intent = new Intent(this, MaterialCentreGroupListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("fromcreategroup",true);
            startActivity(intent);
        }
        else{
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    @Subscribe
    public void getmaterialcentregrouplist(GetMaterialCentreGroupListResponse response){
        mProgressDialog.dismiss();
        if(response.getStatus()==200){
            appUser.arr_materialCentreGroupId.clear();
            appUser.arr_materialCentreGroupName.clear();
            LocalRepositories.saveAppUser(this,appUser);
            Timber.i("I AM HERE");
            for(int i=0;i<response.getMaterial_center_groups().getData().size();i++) {
                appUser.arr_materialCentreGroupName.add(response.getMaterial_center_groups().getData().get(i).getAttributes().getName());
                appUser.arr_materialCentreGroupId.add(String.valueOf(response.getMaterial_center_groups().getData().get(i).getAttributes().getId()));
                LocalRepositories.saveAppUser(this, appUser);
            }
            MaterialCentreGroupListActivity.isDirectForMaterialCentreGroup=false;
            Intent intent = new Intent(this, MaterialCentreGroupListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("fromcreatematerialcentregroup",true);
            startActivity(intent);

        }
        else{
            Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG).show();
        }
    }

}