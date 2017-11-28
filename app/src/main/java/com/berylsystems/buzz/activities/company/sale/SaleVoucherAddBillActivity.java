package com.berylsystems.buzz.activities.company.sale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.billsundry.BillSundryListActivity;
import com.berylsystems.buzz.activities.company.administration.master.item.ExpandableItemListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.api_response.bill_sundry.BillSundryData;
import com.berylsystems.buzz.utils.LocalRepositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SaleVoucherAddBillActivity extends AppCompatActivity {

    @Bind(R.id.bill_courier_charges)
    EditText courier_charges;
    @Bind(R.id.bill_percentage)
    EditText percentage;
    @Bind(R.id.bill_amount)
    EditText billAmount;
    @Bind(R.id.submit)
    LinearLayout submit;
    public static BillSundryData data=null;

    AppUser appUser;
    List<Map<String, String>> mListMap;
    Map<String, String> mMap;

    Animation blinkOnClick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_voucher_add_bill);
        ButterKnife.bind(this);
        blinkOnClick= AnimationUtils.loadAnimation(this,R.anim.blink_on_click);
        String billSundaryPercentage=data.getAttributes().getBill_sundry_of_percentage();
        String billSundryAmount=data.getAttributes().getAmount_of_bill_sundry_fed_as();
        String billSundryCharges=data.getAttributes().getName();

        appUser=LocalRepositories.getAppUser(this);
        //Toast.makeText(this, ""+data.getAttributes().getAmount_of_bill_sundry_fed_as(), Toast.LENGTH_SHORT).show();
        courier_charges.setText(billSundryCharges);
        percentage.setText(billSundaryPercentage);
        billAmount.setText(billSundryAmount);
        mMap = new HashMap<>();


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit.startAnimation(blinkOnClick);
                mMap.put("courier_charges",billSundryCharges);
                mMap.put("percentage",billSundaryPercentage);
                mMap.put("amount", billSundryAmount);
                appUser.mListMapForBill.add(mMap);
                Timber.i("************************************"+appUser.mListMapForBill);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent intent =new Intent(getApplicationContext(),CreateSaleActivity.class);
                intent.putExtra("is",true);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(this, BillSundryListActivity.class);
        startActivity(intent);
        finish();
    }
}
