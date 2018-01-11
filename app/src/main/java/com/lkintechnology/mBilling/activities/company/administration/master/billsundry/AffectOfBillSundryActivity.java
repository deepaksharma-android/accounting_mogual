package com.lkintechnology.mBilling.activities.company.administration.master.billsundry;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
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

public class AffectOfBillSundryActivity extends AppCompatActivity {
    @Bind(R.id.spinner_goods_in_sale)
    Spinner mSpinnerGoodsInSale;
    @Bind(R.id.spinner_goods_in_purchase)
    Spinner mSpinnerGoodsInPurchase;
    @Bind(R.id.spinner_material_issue)
    Spinner mSpinnerMaterialIssue;
    @Bind(R.id.spinner_material_receipt)
    Spinner mSpinnerMaterialReceipt;
    @Bind(R.id.spinner_stock_transfer)
    Spinner mSpinnerStockTransfer;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    AppUser appUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affect_of_bill_sundry);
        ButterKnife.bind(this);
        initActionbar();
        appUser= LocalRepositories.getAppUser(this);
        if (Preferences.getInstance(getApplicationContext()).getcost_goods_in_sale().equals("Yes")) {
            mSpinnerGoodsInSale.setSelection(1);
        }
        if (Preferences.getInstance(getApplicationContext()).getcost_goods_in_purchase().equals("Yes")) {
            mSpinnerGoodsInPurchase.setSelection(1);
        }
        if (Preferences.getInstance(getApplicationContext()).getcost_material_issue().equals("Yes")) {
            mSpinnerMaterialIssue.setSelection(1);
        }
        if (Preferences.getInstance(getApplicationContext()).getcost_material_receipt().equals("Yes")) {
            mSpinnerMaterialReceipt.setSelection(1);
        }
        if (Preferences.getInstance(getApplicationContext()).getcost_stock_transfer().equals("Yes")) {
            mSpinnerStockTransfer.setSelection(1);
        }
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.getInstance(getApplicationContext()).setcost_goods_in_sale(mSpinnerGoodsInSale.getSelectedItem().toString());
                Preferences.getInstance(getApplicationContext()).setcost_goods_in_purchase(mSpinnerGoodsInPurchase.getSelectedItem().toString());
                Preferences.getInstance(getApplicationContext()).setcost_material_issue(mSpinnerMaterialIssue.getSelectedItem().toString());
                Preferences.getInstance(getApplicationContext()).setcost_material_receipt(mSpinnerMaterialReceipt.getSelectedItem().toString());
                Preferences.getInstance(getApplicationContext()).setcost_stock_transfer(mSpinnerStockTransfer.getSelectedItem().toString());
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
        actionbarTitle.setText("AFFECT OF BILL SUNDRY");
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