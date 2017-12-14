package com.berylsystems.buzz.activities.company.transaction.purchase;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.billsundry.BillSundryListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.api_response.bill_sundry.BillSundryData;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;
import com.berylsystems.buzz.utils.TypefaceCache;

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
    @Bind(R.id.percentage_layout)
    LinearLayout mPercentageLayout;
    @Bind(R.id.bill_amount)
    EditText billAmount;
    @Bind(R.id.submit)
    LinearLayout submit;
    public static BillSundryData data = null;

    AppUser appUser;
    List<Map<String, String>> mListMap;
    Map<String, String> mMap;
    Boolean fromSaleVoucherBillList;
    Animation blinkOnClick;
    String billSundaryPercentage;
    String billSundryAmount;
    String billSundryCharges;
    String billsundryothername;
    double taxval=0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_add_bill);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        ButterKnife.bind(this);
        blinkOnClick = AnimationUtils.loadAnimation(this, R.anim.blink_on_click);
        billSundaryPercentage = data.getAttributes().getBill_sundry_percentage_value();
        billSundryAmount = String.valueOf(data.getAttributes().getDefault_value());
        billSundryCharges = data.getAttributes().getName();
        courier_charges.setText(billSundryCharges);
        //percentage.setText(billSundaryPercentage);

        String taxstring= Preferences.getInstance(getApplicationContext()).getPurchase_type_name();
        Timber.i("ID++++" + data.getAttributes().getBill_sundry_id());
        //Timber.i("SIZE"+appUser.arr_billSundryId.get(5));
        if (data.getAttributes().getAmount_of_bill_sundry_fed_as().equals("Percentage")) {
            mPercentageLayout.setVisibility(View.VISIBLE);
            percentage.setText(billSundaryPercentage);
        } else {
            mPercentageLayout.setVisibility(View.GONE);
            percentage.setText("");
        }
        if(billSundryCharges.equals("IGST")){
            if(taxstring.startsWith("I")) {
                String arrtaxstring[] = taxstring.split("-");
                String taxname = arrtaxstring[0].trim();
                String taxvalue = arrtaxstring[1].trim();
                if(taxvalue.contains("%")) {
                    String taxvalpercent[] = taxvalue.split("%");
                    String taxvalpercentval = taxvalpercent[0];
                    Timber.i("TAXNAME" + taxname);
                    Timber.i("TAXVAL" + taxvalpercentval);
                    taxval=Double.parseDouble(taxvalpercentval);
                }
            }
            else{
                taxval=0.0;
            }
            billSundryAmount=String.valueOf(data.getAttributes().getDefault_value()+taxval);

        }
        else if(billSundryCharges.equals("CGST")||billSundryCharges.equals("SGST")){
            if(taxstring.startsWith("L")) {
                String arrtaxstring[] = taxstring.split("-");
                String taxname = arrtaxstring[0].trim();
                String taxvalue = arrtaxstring[1].trim();
                if(taxvalue.contains("%")) {
                    String taxvalpercent[] = taxvalue.split("%");
                    String taxvalpercentval = taxvalpercent[0];
                    Timber.i("TAXNAME" + taxname);
                    Timber.i("TAXVAL" + taxvalpercentval);
                    taxval=Double.parseDouble(taxvalpercentval);
                }
            }
            else{
                taxval=0.0;
            }
            billSundryAmount=String.valueOf(data.getAttributes().getDefault_value()+(taxval/2.0));

        }
        else{
            billSundryAmount=String.valueOf(data.getAttributes().getDefault_value());
        }

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
                appUser.bill_sundry_fed_as = data.getAttributes().getAmount_of_bill_sundry_fed_as();
                appUser.bill_sundry_sale_voucher_type = data.getAttributes().getBill_sundry_type();
                mMap.put("id", data.getId());
                mMap.put("courier_charges", billSundryCharges);
                mMap.put("percentage", billSundaryPercentage);
                mMap.put("fed_as", data.getAttributes().getAmount_of_bill_sundry_fed_as());
                mMap.put("fed_as_percentage", data.getAttributes().getBill_sundry_of_percentage());
                mMap.put("percentage_value", data.getAttributes().getBill_sundry_percentage_value());
                mMap.put("type", data.getAttributes().getBill_sundry_type());
                mMap.put("amount", billAmount.getText().toString());
                if (String.valueOf(data.getAttributes().getNumber_of_bill_sundry()) != null) {
                    mMap.put("number_of_bill", String.valueOf(data.getAttributes().getNumber_of_bill_sundry()));
                }
                if (String.valueOf(data.getAttributes().isConsolidate_bill_sundry()) != null) {
                    mMap.put("consolidated", String.valueOf(data.getAttributes().isConsolidate_bill_sundry()));
                }

                if (data.getAttributes().getBill_sundry_id() != null) {
                    int size = appUser.arr_billSundryId.size();
                    for (int i = 0; i < size; i++) {
                        String id = appUser.arr_billSundryId.get(i);
                        if (id.equals(String.valueOf(data.getAttributes().getBill_sundry_id()))) {
                            billsundryothername = appUser.arr_billSundryName.get(i);
                            break;
                        }
                    }
                    mMap.put("other", billsundryothername);
                }
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
        actionbarTitle.setText("PURCHASE ADD BILL SUNDRY");
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
}
