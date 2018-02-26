package com.lkintechnology.mBilling.activities.company.transaction.sale;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class SaleVoucherAddBillActivity extends AppCompatActivity {

    @Bind(R.id.bill_courier_charges)
    EditText courier_charges;
    @Bind(R.id.bill_percentage)
    EditText percentage;
    @Bind(R.id.percentage_layout)
    LinearLayout mPercentageLayout;
    @Bind(R.id.bill_amount)
    EditText billAmount;
    @Bind(R.id.totalamt)
    EditText mTotalAmt;
    @Bind(R.id.totallayout)
    LinearLayout mTotalLayout;
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
    String billsundryothername;
    int billSundryId;
    String billSundryFedAs;
    String billSundryFedAsPercentage;
    String billSundryFedAsPercentagePrevious;
    String billSundryType;
    Double billSundryDefaultValue;
    int billSundryNumber;
    String id;
    Boolean billSundryConsolidated;
    Boolean frombillvoucherlist;
    double taxval=0.0;
    private MyInputWatcher watcher1, watcher2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        frombillvoucherlist=getIntent().getExtras().getBoolean("frombillvoucherlist");
        setContentView(R.layout.activity_sale_voucher_add_bill);
        ButterKnife.bind(this);
        initActionbar();
        courier_charges.setEnabled(false);
        appUser=LocalRepositories.getAppUser(this);
        int pos = -1;
        blinkOnClick= AnimationUtils.loadAnimation(this,R.anim.blink_on_click);
        if(frombillvoucherlist){
            pos=getIntent().getExtras().getInt("pos");
            Map map=new HashMap<>();
            map=appUser.mListMapForBillSale.get(pos);
            billSundryFedAsPercentagePrevious=(String)map.get("previous");
            billSundaryPercentage=(String) map.get("percentage");
            billSundryCharges=(String) map.get("courier_charges");
            billSundryFedAs=(String) map.get("fed_as");
            billSundryDefaultValue=Double.parseDouble((String) map.get("default_unit"));
            billSundryFedAsPercentage=(String) map.get("fed_as_percentage");
            billSundryType=(String) map.get("type");
            billSundryNumber=Integer.parseInt((String) map.get("number_of_bill"));
            billSundryConsolidated=Boolean.parseBoolean((String) map.get("consolidated"));
            billSundryId=Integer.parseInt((String) map.get("bill_sundry_id"));
            id=(String) map.get("id");
            if(billSundryFedAsPercentage.equals("valuechange")){
               String changeamount= (String) map.get("changeamount");
                mTotalAmt.setText(changeamount);

            }
        }
        else {
            billSundryFedAsPercentagePrevious=data.getAttributes().getBill_sundry_of_percentage();
            billSundaryPercentage = data.getAttributes().getBill_sundry_percentage_value();
            billSundryCharges = data.getAttributes().getName();
            billSundryFedAs = data.getAttributes().getAmount_of_bill_sundry_fed_as();
            billSundryDefaultValue = data.getAttributes().getDefault_value();
            if(data.getAttributes().getBill_sundry_of_percentage()!=null) {
                billSundryFedAsPercentage = data.getAttributes().getBill_sundry_of_percentage();
            }
            else{
                billSundryFedAsPercentage="";
            }
            billSundryType = data.getAttributes().getBill_sundry_type();
            billSundryNumber = data.getAttributes().getNumber_of_bill_sundry();
            billSundryConsolidated = data.getAttributes().isConsolidate_bill_sundry();
            billSundryId = data.getAttributes().getBill_sundry_id();
            id = data.getId();
        }


        courier_charges.setText(billSundryCharges);

        //percentage.setText(billSundaryPercentage);

        String taxstring= Preferences.getInstance(getApplicationContext()).getSale_type_name();

       // Timber.i("SIZE"+appUser.arr_billSundryId.get(5));

//        Timber.i("SIZE"+appUser.arr_billSundryId.get(1));

        if(/*data.getAttributes().getAmount_of_bill_sundry_fed_as()*/billSundryFedAs.equals("Percentage")){
           // mPercentageLayout.setVisibility(View.VISIBLE);
            mTotalLayout.setVisibility(View.VISIBLE);
          //  percentage.setText(billSundaryPercentage);
        }
        else{
          //  mPercentageLayout.setVisibility(View.GONE);
           // percentage.setText("");
        }
        watcher1 = new MyInputWatcher(billAmount);
        watcher2 = new MyInputWatcher(mTotalAmt);

        billAmount.addTextChangedListener(watcher1);
        mTotalAmt.addTextChangedListener(watcher2);
       /* textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        mTotalAmt.addTextChangedListener(textWatcher);
        mTotalAmt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                billAmount.removeTextChangedListener(textWatcher);
                billAmount.setText("0");
                billSundryFedAsPercentage="valuechange";


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        billAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTotalAmt.setText("0");
               // String previousbillsundry=billSundryFedAsPercentagePrevious;
              //  billSundryFedAsPercentage=;
                return;
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/
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
            billSundryAmount=String.valueOf(/*data.getAttributes().getDefault_value()*/billSundryDefaultValue+taxval);

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
            billSundryAmount=String.valueOf(/*data.getAttributes().getDefault_value()*/billSundryDefaultValue+(taxval/2.0));

        }
        else{

            billSundryAmount=String.valueOf(/*data.getAttributes().getDefault_value()*/billSundryDefaultValue);
        }
        if(billSundryFedAsPercentage.equals("valuechange")){
         billAmount.setText("0");
        }
        else{
            billAmount.setText(billSundryAmount);
        }


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

        final int finalPos = pos;
        final int finalPos1 = pos;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit.startAnimation(blinkOnClick);
                appUser.bill_sundry_fed_as=/*data.getAttributes().getAmount_of_bill_sundry_fed_as()*/billSundryFedAs;
                appUser.bill_sundry_sale_voucher_type=/*data.getAttributes().getBill_sundry_type()*/billSundryType;
                mMap.put("id", /*data.getId()*/id);
                mMap.put("courier_charges",billSundryCharges);
                mMap.put("bill_sundry_id",String.valueOf(billSundryId));
                mMap.put("percentage",billSundaryPercentage);
                mMap.put("percentage_value",billSundaryPercentage);
                mMap.put("default_unit",String.valueOf(billSundryDefaultValue));
                mMap.put("fed_as",/*data.getAttributes().getAmount_of_bill_sundry_fed_as()*/billSundryFedAs);
                mMap.put("fed_as_percentage",/*data.getAttributes().getBill_sundry_of_percentage()*/billSundryFedAsPercentage);
                mMap.put("type",/*data.getAttributes().getBill_sundry_type()*/billSundryType);
                mMap.put("amount",billAmount.getText().toString());
                mMap.put("previous",billSundryFedAsPercentagePrevious);
                if(String.valueOf(/*data.getAttributes().getNumber_of_bill_sundry()*/billSundryNumber)!=null) {
                    mMap.put("number_of_bill", String.valueOf(/*data.getAttributes().getNumber_of_bill_sundry()*/billSundryNumber));
                }
                if(String.valueOf(/*data.getAttributes().isConsolidate_bill_sundry()*/billSundryConsolidated)!=null) {
                    mMap.put("consolidated", String.valueOf(billSundryConsolidated));
                }
                if(billSundryFedAsPercentage!=null){
                if(billSundryFedAsPercentage.equals("valuechange")) {
                    mMap.put("changeamount",/*data.getAttributes().getBill_sundry_type()*/mTotalAmt.getText().toString());
                }
                }

                if(/*data.getAttributes().getBill_sundry_id()*/String.valueOf(billSundryId)!=null) {
                    int size=appUser.arr_billSundryId.size();
                    for(int i=0;i<size;i++){
                        String id=appUser.arr_billSundryId.get(i);
                        if(id.equals(String.valueOf(/*data.getAttributes().getBill_sundry_id()*/billSundryId))){
                             billsundryothername=appUser.arr_billSundryName.get(i);
                            break;
                        }
                    }
                    mMap.put("other", billsundryothername);
                }
                if(!frombillvoucherlist) {
                    appUser.mListMapForBillSale.add(mMap);
                    // appUser.mListMap = mListMap;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
                else{
                    appUser.mListMapForBillSale.remove(finalPos);
                    appUser.mListMapForBillSale.add(finalPos,mMap);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
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

    class MyInputWatcher implements TextWatcher {
        private EditText et;

        private MyInputWatcher(EditText editText) {
            et = editText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {

            switch (et.getId()) {
                case R.id.bill_amount: {
                    mTotalAmt.removeTextChangedListener(watcher2);
                    mTotalAmt.setText("0");
                    billSundryFedAsPercentage=billSundryFedAsPercentagePrevious;
                    mTotalAmt.addTextChangedListener(watcher2);
                    break;
                }
                case R.id.totalamt: {
                    billAmount.removeTextChangedListener(watcher1);
                    billAmount.setText("0");
                    billSundryFedAsPercentage="valuechange";// set whatever text you want to set in editText1
                    billAmount.addTextChangedListener(watcher1);

                    break;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
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
