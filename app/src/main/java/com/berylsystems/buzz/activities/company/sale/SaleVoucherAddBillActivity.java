package com.berylsystems.buzz.activities.company.sale;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.billsundry.BillSundryListActivity;
import com.berylsystems.buzz.activities.company.administration.master.item.ExpandableItemListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.api_response.bill_sundry.BillSundryData;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SaleVoucherAddBillActivity extends AppCompatActivity {

    @Bind(R.id.bill_courier_charges)
    EditText courier_charges;
  /*  @Bind(R.id.bill_percentage)
    EditText percentage;*/
    @Bind(R.id.bill_amount)
    EditText billAmount;
    @Bind(R.id.submit)
    LinearLayout submit;
    public static BillSundryData data=null;

    AppUser appUser;
    List<Map<String, String>> mListMap;
    Map<String, String> mMap;
    Boolean fromSaleVoucherBillList;
    Animation blinkOnClick;
    String billSundaryPercentage;
    String billSundryAmount;
    String billSundryCharges;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_voucher_add_bill);
        ButterKnife.bind(this);
        initActionbar();
        appUser=LocalRepositories.getAppUser(this);
        blinkOnClick= AnimationUtils.loadAnimation(this,R.anim.blink_on_click);
        billSundaryPercentage=data.getAttributes().getBill_sundry_of_percentage();
        billSundryAmount= String.valueOf(data.getAttributes().getDefault_value());
        billSundryCharges=data.getAttributes().getName();
        courier_charges.setText(billSundryCharges);
        //percentage.setText(billSundaryPercentage);
        billAmount.setText(billSundryAmount);
       /* fromSaleVoucherBillList=getIntent().getExtras().getBoolean("fromvoucherbilllist");
        if(fromSaleVoucherBillList){

            billAmount.setText(getIntent().getStringExtra("amount"));
            courier_charges.setText(getIntent().getStringExtra("unit"));
        }
        else{
             billSundaryPercentage=data.getAttributes().getBill_sundry_of_percentage();
             billSundryAmount= String.valueOf(data.getAttributes().getDefault_value());
             billSundryCharges=data.getAttributes().getName();
            courier_charges.setText(billSundryCharges);
            percentage.setText(billSundaryPercentage);
            billAmount.setText(billSundryAmount);
        }*/


        mMap = new HashMap<>();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit.startAnimation(blinkOnClick);
                appUser.bill_sundry_fed_as=data.getAttributes().getAmount_of_bill_sundry_fed_as();
                appUser.bill_sundry_sale_voucher_type=data.getAttributes().getBill_sundry_type();
                mMap.put("courier_charges",billSundryCharges);
                mMap.put("percentage",billSundaryPercentage);
                mMap.put("fed_as",data.getAttributes().getAmount_of_bill_sundry_fed_as());
                mMap.put("type",data.getAttributes().getBill_sundry_type());
                mMap.put("amount",billAmount.getText().toString());
                appUser.mListMapForBillSale.add(mMap);
                Timber.i("************************************"+appUser.mListMapForBillSale);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent intent =new Intent(getApplicationContext(),CreateSaleActivity.class);
                intent.putExtra("is",true);
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009DE0")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("SALE VOUCHER ADD BILL SUNDRY");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, BillSundryListActivity.class);
        startActivity(intent);
        finish();
    }
}
