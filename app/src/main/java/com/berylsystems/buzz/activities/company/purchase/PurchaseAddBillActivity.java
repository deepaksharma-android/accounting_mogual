package com.berylsystems.buzz.activities.company.purchase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.billsundry.BillSundryListActivity;
import com.berylsystems.buzz.activities.company.administration.master.item.ExpandableItemListActivity;
import com.berylsystems.buzz.activities.company.sale.CreateSaleActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.api_response.bill_sundry.BillSundryData;
import com.berylsystems.buzz.utils.LocalRepositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class PurchaseAddBillActivity extends AppCompatActivity {

    @Bind(R.id.bill_courier_charges)
    EditText courier_charges;
    @Bind(R.id.bill_percentage)
    EditText percentage;
    @Bind(R.id.bill_amount)
    EditText billAmount;
    @Bind(R.id.submit)
    LinearLayout submit;
    @Bind(R.id.heading)
    TextView heading;
    public static BillSundryData data = null;
    List<Map<String, String>> mListMap;
    Map<String, String> mMap;
    Animation blinkOnClick;
    boolean b;
    AppUser appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_add_bill);
        appUser=LocalRepositories.getAppUser(this);
        ButterKnife.bind(this);
        blinkOnClick = AnimationUtils.loadAnimation(this, R.anim.blink_on_click);


        Intent intent = getIntent();
        b = intent.getBooleanExtra("bool", false);
        if (b) {
            heading.setText("EDIT BILL SUNDRY");
            Intent i = getIntent();
            Integer position = i.getIntExtra("position", 0);
            data = appUser.billSundryData.get(position);
            String billSundaryPercentage = data.getAttributes().getBill_sundry_of_percentage();
            String billSundryAmount = data.getAttributes().getAmount_of_bill_sundry_fed_as();
            String billSundryCharges = data.getAttributes().getName();

            Timber.i("********"+data.getAttributes().getBill_sundry_of_percentage());
            Timber.i("********"+data.getAttributes().getAmount_of_bill_sundry_fed_as());
            Timber.i("********"+data.getAttributes().getName());


            appUser = LocalRepositories.getAppUser(this);
            //Toast.makeText(this, ""+data.getAttributes().getAmount_of_bill_sundry_fed_as(), Toast.LENGTH_SHORT).show();
            courier_charges.setText(billSundryCharges);
            percentage.setText(billSundaryPercentage);
            billAmount.setText(billSundryAmount);



            mMap = new HashMap<>();

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    submit.startAnimation(blinkOnClick);
                    mMap.put("courier_charges", billSundryCharges);
                    mMap.put("percentage", billSundaryPercentage);
                    mMap.put("amount", billSundryAmount);
                    appUser.mListMapForBillPurchase.add(mMap);
                    Timber.i("************************************" + appUser.mListMapForBillPurchase);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Intent intent = new Intent(getApplicationContext(), CreatePurchaseActivity.class);
                    intent.putExtra("is", true);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            heading.setText("ADD BILL SUNDRY");
            Intent i=getIntent();
            Integer position=Integer.valueOf(i.getIntExtra("id",0));

            //String billSundaryPercentage = data.getAttributes().getBill_sundry_of_percentage();
            String billSundryAmount = data.getAttributes().getAmount_of_bill_sundry_fed_as();
            String billSundryCharges = data.getAttributes().getName();

            Timber.i("********"+data.getAttributes().getBill_sundry_of_percentage());
            Timber.i("********"+data.getAttributes().getAmount_of_bill_sundry_fed_as());
            Timber.i("********"+data.getAttributes().getName());


            appUser = LocalRepositories.getAppUser(this);
            //Toast.makeText(this, ""+data.getAttributes().getAmount_of_bill_sundry_fed_as(), Toast.LENGTH_SHORT).show();
            courier_charges.setText(billSundryCharges);
            //percentage.setText(billSundaryPercentage);
            billAmount.setText(billSundryAmount);



            mMap = new HashMap<>();

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    submit.startAnimation(blinkOnClick);
                    mMap.put("courier_charges", billSundryCharges);
                    //mMap.put("percentage", billSundaryPercentage);
                    mMap.put("amount", billSundryAmount);
                    appUser.mListMapForBillPurchase.add(mMap);
                    Timber.i("************************************" + appUser.mListMapForBillPurchase);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    Intent intent = new Intent(getApplicationContext(), CreatePurchaseActivity.class);
                    intent.putExtra("is", true);
                    startActivity(intent);
                    finish();
                }
            });
        }




    }

    @Override
    public void onBackPressed() {
        if (!b) {
            Intent intent = new Intent(this, BillSundryListActivity.class);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }
}
