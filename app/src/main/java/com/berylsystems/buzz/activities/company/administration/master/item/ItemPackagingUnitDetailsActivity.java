package com.berylsystems.buzz.activities.company.administration.master.item;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.company.administration.master.unit.UnitListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;
import com.berylsystems.buzz.utils.TypefaceCache;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ItemPackagingUnitDetailsActivity extends AppCompatActivity {
    @Bind(R.id.packaging_unit_layout)
    LinearLayout mPackagingUnitLayout;
    @Bind(R.id.packaging_unit)
    TextView mPackagingUnit;
    @Bind(R.id.purchase_price)
    EditText mPurchasePrice;
    @Bind(R.id.sales_price)
    EditText mSalesPrice;
    @Bind(R.id.con_factor)
    EditText mConFactor;
    @Bind(R.id.default_unit_for_sales)
    Spinner mSpinnerDefaultUnitForSale;
    @Bind(R.id.default_unit_for_purchase)
    Spinner mSpinnerDefaultUnitForPurchase;
    @Bind(R.id.layout)
    LinearLayout mLayout;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    AppUser appUser;
    String item_unit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packaging_unit_details);
        ButterKnife.bind(this);
        item_unit=getIntent().getStringExtra("unit");
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        mPackagingUnitLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UnitListActivity.isDirectForUnitList=false;
                Intent intent = new Intent(getApplicationContext(), UnitListActivity.class);
                intent.putExtra("frommaster", false);
                startActivityForResult(intent, 4);
            }
        });
        ;

        if (!Preferences.getInstance(getApplicationContext()).getitem_conversion_factor_pkg_unit().equals("")) {
            mConFactor.setText(Preferences.getInstance(getApplicationContext()).getitem_conversion_factor_pkg_unit());
        }
        if (!Preferences.getInstance(getApplicationContext()).getitem_salse_price().equals("")) {
            mSalesPrice.setText(Preferences.getInstance(getApplicationContext()).getitem_salse_price());
        }
        if (Preferences.getInstance(getApplicationContext()).getitem_package_unit_detail_name().equals("")) {
            mPackagingUnit.setText(Preferences.getInstance(getApplicationContext()).getitem_package_unit_detail_name());
        }
        if (!Preferences.getInstance(getApplicationContext()).getitem_specify_purchase_account().equals("")) {
            mPurchasePrice.setText(Preferences.getInstance(getApplicationContext()).getitem_specify_purchase_account());
        }
        if (!Preferences.getInstance(getApplicationContext()).getitem_default_unit_for_purchase().equals("")) {
            if (Preferences.getInstance(getApplicationContext()).getitem_default_unit_for_purchase().equals("Not Req.")) {
                mSpinnerDefaultUnitForPurchase.setSelection(0);
            } else if (Preferences.getInstance(getApplicationContext()).getitem_default_unit_for_purchase().equals("Main Unit")) {
                mSpinnerDefaultUnitForPurchase.setSelection(1);
            } else if (Preferences.getInstance(getApplicationContext()).getitem_default_unit_for_purchase().equals("Alt. Unit")) {
                mSpinnerDefaultUnitForPurchase.setSelection(2);
            } else {
                mSpinnerDefaultUnitForPurchase.setSelection(3);
            }
        }

        if (!Preferences.getInstance(getApplicationContext()).getitem_default_unit_for_sales().equals("")) {
            if (Preferences.getInstance(getApplicationContext()).getitem_default_unit_for_sales().equals("Not Req.")) {
                mSpinnerDefaultUnitForSale.setSelection(0);
            } else if (Preferences.getInstance(getApplicationContext()).getitem_default_unit_for_sales().equals("Main Unit")) {
                mSpinnerDefaultUnitForSale.setSelection(1);
            } else if (Preferences.getInstance(getApplicationContext()).getitem_default_unit_for_sales().equals("Alt. Unit")) {
                mSpinnerDefaultUnitForSale.setSelection(2);
            } else {
                mSpinnerDefaultUnitForSale.setSelection(3);
            }
        }

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Preferences.getInstance(getApplicationContext()).setitem_purchase_price(mPurchasePrice.getText().toString());
                Preferences.getInstance(getApplicationContext()).setitem_conversion_factor_pkg_unit(mConFactor.getText().toString());
                Preferences.getInstance(getApplicationContext()).setitem_salse_price(mSalesPrice.getText().toString());
                Preferences.getInstance(getApplicationContext()).setitem_default_unit_for_sales(mSpinnerDefaultUnitForSale.getSelectedItem().toString());
                Preferences.getInstance(getApplicationContext()).setitem_default_unit_for_purchase(mSpinnerDefaultUnitForPurchase.getSelectedItem().toString());
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009DE0")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("PACKAGING UNIT DETAILS");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                Timber.i("MYID" + id);
                if(result.equals(item_unit)){
                    mLayout.setVisibility(View.GONE);
                }
                else{
                    mLayout.setVisibility(View.VISIBLE);
                }
                 if(result.equals(Preferences.getInstance(getApplicationContext()).getitem_alternate_unit_name())){
                    new AlertDialog.Builder(ItemPackagingUnitDetailsActivity.this)
                            .setTitle("Invalid Data")
                            .setMessage("Alternate Unit and Packaging Unit cannot be the same !")
                            .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                                mPackagingUnit.setText("");
                                return;

                            }).show();
                }
                Preferences.getInstance(getApplicationContext()).setitem_package_unit_detail_id(id);
                Preferences.getInstance(getApplicationContext()).setitem_package_unit_detail_name(result);
                mPackagingUnit.setText(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                mPackagingUnit.setText("");
            }
        }
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