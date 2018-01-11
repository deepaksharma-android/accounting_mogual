package com.lkintechnology.mBilling.activities.company.administration.master.item;

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

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemSettingsActivity extends AppCompatActivity {
    @Bind(R.id.settings_set_critical_level_spinner)
    Spinner mSpinnerCriticalLevel;
    @Bind(R.id.serailwiselayout)
    LinearLayout mSerialWiseLayout;
    @Bind(R.id.settings_serial_number_wise_detail_spinner)
    Spinner mSpinnerSerialNumber;
    @Bind(R.id.setting_batch_wise_detail_spinner)
    Spinner mSpinnerBatchWise;
    @Bind(R.id.batchwiselayout)
    LinearLayout mBatchWiseLayout;
    @Bind(R.id.setting_alternate_unit_details_spinner)
    Spinner mSpinnerAlternateUnit;
    @Bind(R.id.setting_pecify_sales_account_spinner)
    Spinner mSpinnerSpecifySales;
    @Bind(R.id.setting_specify_purchase_spinner)
    Spinner mSpinnerSpecifyPurchase;
    @Bind(R.id.setting_dont_maintain_stock_balance_spinner)
    Spinner mSpinnerDontMaintainStockBalance;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    @Bind(R.id.show_more)
    LinearLayout mShowMoreLayout;
    AppUser appUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_settings);
        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        mSpinnerCriticalLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    mShowMoreLayout.setVisibility(View.VISIBLE);

                } else {
                    mShowMoreLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        mShowMoreLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ItemSettingCriticalLevelActivity.class));
            }
        });
        if (mSpinnerCriticalLevel.getSelectedItem().toString().equals("Yes")) {
            mShowMoreLayout.setVisibility(View.VISIBLE);

        } else {
            mShowMoreLayout.setVisibility(View.GONE);
        }
       /* else{
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_min_level_qty("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_recorded_level_qty("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_max_level_qty("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_min_level_days("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_recorded_level_days("");
            Preferences.getInstance(getApplicationContext()).setitem_setting_critical_max_level_days("");
        }*/

        if (Preferences.getInstance(getApplicationContext()).getitem_set_critical_level().equals("Yes")) {
            mSpinnerCriticalLevel.setSelection(1);
        }


        if (Preferences.getInstance(getApplicationContext()).getitem_serial_number_wise_detail().equals("Yes")) {
            mSpinnerSerialNumber.setSelection(0);
            mBatchWiseLayout.setVisibility(View.GONE);
        }


        if (Preferences.getInstance(getApplicationContext()).getitem_batch_wise_detail().equals("Yes")) {
            mSpinnerBatchWise.setSelection(1);
            mSerialWiseLayout.setVisibility(View.GONE);

        }


        if (Preferences.getInstance(getApplicationContext()).getitem_settings_alternate_unit().equals("Yes")) {
            mSpinnerAlternateUnit.setSelection(1);
        }


        if (Preferences.getInstance(getApplicationContext()).getitem_specify_purchase_account().equals("Yes")) {
            mSpinnerSpecifyPurchase.setSelection(1);
        }


        if (Preferences.getInstance(getApplicationContext()).getitem_specify_sales_account().equals("Yes")) {
            mSpinnerSpecifySales.setSelection(1);
        }


        if (Preferences.getInstance(getApplicationContext()).getitem_dont_maintain_stock_balance().equals("Yes")) {
            mSpinnerDontMaintainStockBalance.setSelection(1);
        }
        mSpinnerSerialNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    mBatchWiseLayout.setVisibility(View.GONE);
                } else {
                    mBatchWiseLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerBatchWise.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    mSerialWiseLayout.setVisibility(View.GONE);
                } else {
                    mSerialWiseLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSpinnerBatchWise.getSelectedItem().toString().equals("Yes")) {
                    Preferences.getInstance(getApplicationContext()).setitem_serial_number_wise_detail("No");
                    Preferences.getInstance(getApplicationContext()).setitem_batch_wise_detail(mSpinnerBatchWise.getSelectedItem().toString());
                }
                if (mSpinnerSerialNumber.getSelectedItem().toString().equals("Yes")) {
                    Preferences.getInstance(getApplicationContext()).setitem_serial_number_wise_detail(mSpinnerSerialNumber.getSelectedItem().toString());
                    Preferences.getInstance(getApplicationContext()).setitem_batch_wise_detail("No");
                }
                Preferences.getInstance(getApplicationContext()).setitem_set_critical_level(mSpinnerCriticalLevel.getSelectedItem().toString());
                Preferences.getInstance(getApplicationContext()).setitem_specify_sales_account(mSpinnerSpecifySales.getSelectedItem().toString());
                Preferences.getInstance(getApplicationContext()).setitem_specify_purchase_account(mSpinnerSpecifyPurchase.getSelectedItem().toString());
                Preferences.getInstance(getApplicationContext()).setitem_dont_maintain_stock_balance(mSpinnerDontMaintainStockBalance.getSelectedItem().toString());
                Preferences.getInstance(getApplicationContext()).setitem_settings_alternate_unit(mSpinnerAlternateUnit.getSelectedItem().toString());



              /*  if(mSpinnerCriticalLevel.getSelectedItem().toString().equals("No")){
                    Preferences.getInstance(getApplicationContext()).setitem_setting_critical_min_level_qty("");
                    Preferences.getInstance(getApplicationContext()).setitem_setting_critical_recorded_level_qty("");
                    Preferences.getInstance(getApplicationContext()).setitem_setting_critical_max_level_qty("");
                    Preferences.getInstance(getApplicationContext()).setitem_setting_critical_min_level_days("");
                    Preferences.getInstance(getApplicationContext()).setitem_setting_critical_recorded_level_days("");
                    Preferences.getInstance(getApplicationContext()).setitem_setting_critical_max_level_days("");
                }
*/
                finish();
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("SETTINGS");
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
                finish();
                break;
        }
        return true;
    }
}