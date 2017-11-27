package com.berylsystems.buzz.activities.company.administration.master.item;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemSettingsActivity extends AppCompatActivity {
    @Bind(R.id.settings_set_critical_level_spinner)
    Spinner mSpinnerCriticalLevel;
    @Bind(R.id.settings_serial_number_wise_detail_spinner)
    Spinner mSpinnerSerialNumber;
    @Bind(R.id.setting_batch_wise_detail_spinner)
    Spinner mSpinnerBatchWise;
    @Bind(R.id.setting_alternate_unit_details_spinner)
    Spinner mSpinnerAlternateUnit;
    @Bind(R.id.setting_pecify_sales_account_spinner)
    Spinner mSpinnerSpecifySales;
    @Bind(R.id.settings_mrp_spinner)
    Spinner mSpinnerMrp;
    @Bind(R.id.setting_specify_purchase_spinner)
    Spinner mSpinnerSpecifyPurchase;
    @Bind(R.id.setting_dont_maintain_stock_balance_spinner)
    Spinner mSpinnerDontMaintainStockBalance;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    AppUser appUser;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_settings);
        ButterKnife.bind(this);
        initActionbar();
        appUser=LocalRepositories.getAppUser(this);
        mSpinnerCriticalLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    startActivity(new Intent(getApplicationContext(),ItemSettingCriticalLevelActivity.class));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if(mSpinnerCriticalLevel.getSelectedItem().toString().equals("Yes")){
            startActivity(new Intent(getApplicationContext(),ItemSettingCriticalLevelActivity.class));
        }
        else{
            appUser.item_setting_critical_min_level_qty="";
            appUser.item_setting_critical_recorded_level_qty="";
            appUser.item_setting_critical_max_level_qty="";
            appUser.item_setting_critical_min_level_days="";
            appUser.item_setting_critical_recorded_level_days="";
            appUser.item_setting_critical_max_level_days="";
            LocalRepositories.saveAppUser(getApplicationContext(),appUser);
        }
        if(appUser.item_set_critical_level!=null){
            if (appUser.item_set_critical_level.equals("Yes")) {
                mSpinnerCriticalLevel.setSelection(1);
            }
        }
        if(appUser.item_serial_number_wise_detail!=null){
            if (appUser.item_serial_number_wise_detail.equals("Yes")) {
                mSpinnerSerialNumber.setSelection(1);
            }
        }
        if(appUser.item_batch_wise_detail!=null){
            if (appUser.item_batch_wise_detail.equals("Yes")) {
                mSpinnerBatchWise.setSelection(1);
            }
        }
        if(appUser.item_settings_alternate_unit!=null){
            if (appUser.item_settings_alternate_unit.equals("Yes")) {
                mSpinnerAlternateUnit.setSelection(1);
            }
        }
        if(appUser.item_settings_mrp!=null){
            if (appUser.item_settings_mrp.equals("Yes")) {
                mSpinnerMrp.setSelection(1);
            }
        }
        if(appUser.item_settings_specify_purchase_account!=null){
            if (appUser.item_settings_specify_purchase_account.equals("Yes")) {
                mSpinnerSpecifyPurchase.setSelection(1);
            }
        }
        if(appUser.item_specify_sales_account!=null){
            if (appUser.item_specify_sales_account.equals("Yes")) {
                mSpinnerSpecifySales.setSelection(1);
            }
        }
        if(appUser.item_dont_maintain_stock_balance!=null){
            if (appUser.item_dont_maintain_stock_balance.equals("Yes")) {
                mSpinnerDontMaintainStockBalance.setSelection(1);
            }

        }
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mSpinnerCriticalLevel.getSelectedItem().toString().equals("No")){
                    appUser.item_setting_critical_min_level_qty="";
                    appUser.item_setting_critical_recorded_level_qty="";
                    appUser.item_setting_critical_max_level_qty="";
                    appUser.item_setting_critical_min_level_days="";
                    appUser.item_setting_critical_recorded_level_days="";
                    appUser.item_setting_critical_max_level_days="";
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                }
                appUser.item_set_critical_level = mSpinnerCriticalLevel.getSelectedItem().toString();
                appUser.item_serial_number_wise_detail = mSpinnerSerialNumber.getSelectedItem().toString();
                appUser.item_batch_wise_detail=mSpinnerBatchWise.getSelectedItem().toString();
                appUser.item_settings_alternate_unit = mSpinnerAlternateUnit.getSelectedItem().toString();
                appUser.item_settings_mrp=mSpinnerMrp.getSelectedItem().toString();
                appUser.item_settings_specify_purchase_account=mSpinnerSpecifyPurchase.getSelectedItem().toString();
                appUser.item_specify_sales_account = mSpinnerSpecifySales.getSelectedItem().toString();
                appUser.item_dont_maintain_stock_balance = mSpinnerDontMaintainStockBalance.getSelectedItem().toString();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            }
        });
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
        actionbarTitle.setText("SETTINGS");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }
}