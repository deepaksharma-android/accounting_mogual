package com.lkintechnology.mBilling.activities.company.pos;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.billsundry.BillSundryListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale.CreateSaleActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_response.bill_sundry.BillSundryData;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class PosAddBillActivity extends AppCompatActivity {

    @Bind(R.id.bill_courier_charges)
    EditText courier_charges;

    @Bind(R.id.bill_amount)
    EditText billAmount;
    @Bind(R.id.totalamt)
    EditText mTotalAmt;
    @Bind(R.id.total)
    TextView mTotal;
    @Bind(R.id.totallayout)
    LinearLayout mTotalLayout;
    @Bind(R.id.totalAmountLayout)
    LinearLayout mTotalAmountLayout;
    @Bind(R.id.defaultvaluetext)
    TextView mDefaulttext;
    @Bind(R.id.submit)
    LinearLayout submit;
    public static BillSundryData data = null;

    AppUser appUser;
    List<Map<String, String>> mListMap;
    Map<String, String> mMap;
    Boolean fromSaleVoucherBillList;
    Animation blinkOnClick;
    public Double totalitemamount = 0.0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_voucher_add_bill);
        ButterKnife.bind(this);
        initActionbar();
        courier_charges.setEnabled(false);
        appUser = LocalRepositories.getAppUser(this);
        int pos = -1;
        blinkOnClick = AnimationUtils.loadAnimation(this, R.anim.blink_on_click);


        mMap = new HashMap<>();

        final int finalPos = pos;
        final int finalPos1 = pos;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit.startAnimation(blinkOnClick);
                appUser.bill_sundry_fed_as ="";
                appUser.bill_sundry_sale_voucher_type ="";
                mMap.put("id", /*data.getId()*/"");
                mMap.put("courier_charges", "");
                mMap.put("bill_sundry_id", "");
                mMap.put("percentage", billAmount.getText().toString());
                mMap.put("percentage_value", "");
                mMap.put("default_unit", String.valueOf(""));
                mMap.put("fed_as","");
                mMap.put("fed_as_percentage","");
                mMap.put("type","");
                mMap.put("amount", billAmount.getText().toString());
                mMap.put("previous", "");
                if (String.valueOf("") != null) {
                    mMap.put("number_of_bill", "");
                }
                if (String.valueOf("") != null) {
                    mMap.put("consolidated", String.valueOf(""));
                }
               /* if (billSundryFedAsPercentage != null) {
                    if (billSundryFedAsPercentage.equals("valuechange")) {
                        mMap.put("changeamount",*//*data.getAttributes().getBill_sundry_type()*//*mTotalAmt.getText().toString());
                    }
                }*/

                if (/*data.getAttributes().getBill_sundry_id()*/String.valueOf("") != null) {
                    int size = appUser.arr_billSundryId.size();
                    for (int i = 0; i < size; i++) {
                        String id = appUser.arr_billSundryId.get(i);
                        if (id.equals(String.valueOf(/*data.getAttributes().getBill_sundry_id()*/""))) {
                          //  billsundryothername = appUser.arr_billSundryName.get(i);
                            break;
                        }
                    }
                    mMap.put("other", "");
                }
                appUser.mListMapForBillSale.add(mMap);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                /*if (!frombillvoucherlist) {
                    appUser.mListMapForBillSale.add(mMap);
                    // appUser.mListMap = mListMap;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                } else {
                    appUser.mListMapForBillSale.remove(finalPos);
                    appUser.mListMapForBillSale.add(finalPos, mMap);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }*/
                Intent intent = new Intent(getApplicationContext(), CreateSaleActivity.class);
                intent.putExtra("is", true);
                startActivity(intent);
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
        actionbarTitle.setText("SALE VOUCHER ADD BILL SUNDRY");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, BillSundryListActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, BillSundryListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
