package com.berylsystems.buzz.activities.company.transaction.sale;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.item.ExpandableItemListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;
import com.berylsystems.buzz.utils.TypefaceCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SaleVoucherAddItemActivity extends AppCompatActivity {

    @Bind(R.id.item_name)
    TextView mItemName;
    @Bind(R.id.item_layout)
    LinearLayout mItemLayout;
    @Bind(R.id.description)
    TextView mDescription;
    @Bind(R.id.quantity)
    EditText mQuantity;
    @Bind(R.id.spinner_unit)
    Spinner mSpinnerUnit;
    @Bind(R.id.sr_no)
    TextView mSr_no;
    @Bind(R.id.serail_number_layout)
    LinearLayout mSerialNumberLayout;
    @Bind(R.id.rate)
    EditText mRate;
    @Bind(R.id.discount)
    EditText mDiscount;
    @Bind(R.id.value)
    EditText mValue;
    @Bind(R.id.total)
    EditText mTotal;
    @Bind(R.id.submit)
    LinearLayout mSubmit;

    AppUser appUser;
    List<Map<String, String>> mListMap;
    Map mMap;
    Double first, second, third;
    Intent intent;
    Animation blinkOnClick;
    ArrayList<String> mUnitList;
    ArrayAdapter<String> mUnitAdapter;
    String serial = "";
    String sales_price_applied_on;
    String price_selected_unit;
    String alternate_unit_con_factor;
    String packaging_unit_con_factor;
    String mrp;
    String tax;
    Boolean frombillitemvoucherlist;
    String name;
    String desc;
    String main_unit;
    String alternate_unit;
    String sales_price_main;
    String sales_price_alternate ;
    Boolean serailwise;
    Boolean batchwise ;
    String packaging_unit ;
    String default_unit;
    String packaging_unit_sales_price;
    String sale_type;
    String totalitemprice;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_voucher_add_item);
        appUser = LocalRepositories.getAppUser(this);
        frombillitemvoucherlist=getIntent().getExtras().getBoolean("frombillitemvoucherlist");
        ButterKnife.bind(this);
        initActionbar();
        mListMap = new ArrayList<>();
        mMap = new HashMap<>();
        mUnitList = new ArrayList<>();
        int pos = -1;
        mItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ExpandableItemListActivity.class));
            }
        });
        blinkOnClick = AnimationUtils.loadAnimation(this, R.anim.blink_on_click);

        if(frombillitemvoucherlist){
             pos=getIntent().getExtras().getInt("pos");
            Map map=new HashMap<>();
            map=appUser.mListMapForItemSale.get(pos);
            String itemName= (String) map.get("item_name");
            String description= (String) map.get("description");
            String quantity= (String) map.get("quantity");
            String unit= (String) map.get("unit");
            String srNo= (String) map.get("sr_no");
            String rate= (String) map.get("rate");
            String discount= (String) map.get("discount");
            String value= (String) map.get("value");
            String total= (String) map.get("total");
            String mrpitem= (String) map.get("mrp");
            String applieditem= (String) map.get("applied");
            String priceselectedunititem= (String) map.get("price_selected_unit");
            String alternateunitconfactoritem= (String) map.get("alternate_unit_con_factor");
            String packagingunitconfactoritem= (String) map.get("packaging_unit_con_factor");
            String taxitem= (String) map.get("tax");


            mItemName.setText(itemName);
            mQuantity.setText(quantity);
            mRate.setText(rate);
            mValue.setText(value);
            mTotal.setText(total);
            mDescription.setText(description);
            mrp=mrpitem;
            sales_price_applied_on=applieditem;
            price_selected_unit=priceselectedunititem;
            packaging_unit_con_factor=packagingunitconfactoritem;
            tax=taxitem;
            if(price_selected_unit.equals("main")){
                mSpinnerUnit.setSelection(0);
            }
            else if(price_selected_unit.equals("alternate")){
                mSpinnerUnit.setSelection(1);
            }
            else {
                if (!packaging_unit_con_factor.equals("")) {
                    mSpinnerUnit.setSelection(2);
                }
            }
        }

        else {
            CreateSaleActivity.hideKeyPad(this);
            Intent intent = getIntent();
            id = intent.getStringExtra("id");
            name = intent.getStringExtra("name");
            desc = intent.getStringExtra("desc");
            main_unit = intent.getStringExtra("main_unit");
            alternate_unit = intent.getStringExtra("alternate_unit");
            sales_price_main = intent.getStringExtra("sales_price_main");
            sales_price_alternate = intent.getStringExtra("sales_price_alternate");
            serailwise = intent.getExtras().getBoolean("serial_wise");
            batchwise = intent.getExtras().getBoolean("batch_wise");
            totalitemprice=intent.getStringExtra("total");
            packaging_unit = intent.getStringExtra("packaging_unit");
            default_unit = intent.getStringExtra("default_unit");
            packaging_unit_sales_price = intent.getStringExtra("packaging_unit_sales_price");
            mrp = intent.getStringExtra("mrp");
            packaging_unit_con_factor = intent.getStringExtra("packaging_unit_con_factor");
            sales_price_applied_on = intent.getStringExtra("applied");
            alternate_unit_con_factor = intent.getStringExtra("alternate_unit_con_factor");
            tax = intent.getStringExtra("tax");


            mSerialNumberLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (batchwise && !serailwise) {
                        serial = "1";
                    } else if (!batchwise && serailwise) {
                        if (mQuantity.getText().toString().equals("")) {
                            serial = "0";
                        } else {
                            serial = mQuantity.getText().toString();
                        }

                    } else {
                        serial = "0";
                    }
                    if (!serial.equals("0")) {
                        Dialog dialogbal = new Dialog(SaleVoucherAddItemActivity.this);
                        dialogbal.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialogbal.setContentView(R.layout.dialog_serail);
                        dialogbal.setCancelable(true);
                        LinearLayout serialLayout = (LinearLayout) dialogbal.findViewById(R.id.main_layout);
                        LinearLayout submit = (LinearLayout) dialogbal.findViewById(R.id.submit);
                        int width=getWidth();
                        int height=getHeight();
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
                        lp.setMargins(20,10,20,0);
                        EditText[] pairs = new EditText[Integer.parseInt(serial)];
                        for (int l = 0; l < Integer.parseInt(serial); l++) {
                            pairs[l] = new EditText(getApplicationContext());
                            pairs[l].setPadding(10, 10, 10, 0);
                            pairs[l].setInputType(InputType.TYPE_CLASS_NUMBER);
                            pairs[l].setWidth(width);
                            pairs[l].setHeight(height);
                            pairs[l].setBackgroundResource(R.drawable.grey_stroke_rect);
                            pairs[l].setTextSize(18);
                            if(appUser.serial_arr.size()>0) {
                                pairs[l].setText(appUser.serial_arr.get(l));
                            }
                            pairs[l].setHint("Enter Serial Number"+" "+(l+1));
                            pairs[l].setHintTextColor(Color.GRAY);
                            pairs[l].setTextColor(Color.BLACK);
                            pairs[l].setLayoutParams(lp);
                            pairs[l].setId(l);
                            //pairs[l].setText((l + 1) + ": something");
                            serialLayout.addView(pairs[l]);
                        }
                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                for (int l = 0; l < Integer.parseInt(serial); l++) {
                                    appUser.serial_arr.add(pairs[l].getText().toString());
                                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                                }
                                dialogbal.dismiss();
                            }
                        });
                        dialogbal.show();

                    }
                }

            });
            mItemName.setText(name);
            mDescription.setText(desc);
            appUser.unitlist.add("Main Unit : " + main_unit);
            appUser.unitlist.add("Alternate Unit :" + alternate_unit);

        }
        if (!packaging_unit.equals("")) {
            appUser.unitlist.add("Packaging Unit :" + packaging_unit);
        }




        mItemName.setEnabled(false);
        mValue.setEnabled(false);
        mTotal.setEnabled(false);
        mUnitAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,  appUser.unitlist);
        mUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerUnit.setAdapter(mUnitAdapter);
        if (!packaging_unit.equals("")) {
            if (default_unit.equals("Main Unit")) {
                mSpinnerUnit.setSelection(0);
            } else if (default_unit.equals("Alt. Unit")) {
                mSpinnerUnit.setSelection(1);
            } else if (default_unit.equals("Pckg. Unit")) {
                mSpinnerUnit.setSelection(2);
            } else {
                mSpinnerUnit.setSelection(0);
            }
        } else {
            if (default_unit.equals("Main Unit")) {
                mSpinnerUnit.setSelection(0);
            } else if (default_unit.equals("Alt. Unit")) {
                mSpinnerUnit.setSelection(1);
            } else {
                mSpinnerUnit.setSelection(0);
            }
        }
        if (!packaging_unit.equals("")) {
            mSpinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if (i == 2) {
                        price_selected_unit = "packaging";
                        if (default_unit.equals("Pckg. Unit")) {
                            mRate.setText(String.valueOf(packaging_unit_sales_price));
                        }

                           /* if (sales_price_applied_on.equals("Main Unit")) {
                                Double main_unit_price = Double.parseDouble(packaging_unit_sales_price) / Double.parseDouble(packaging_unit_con_factor);
                                mRate.setText(String.valueOf(main_unit_price));

                            } else if (sales_price_applied_on.equals("Alternate Unit")) {
                                Double main_unit_price = Double.parseDouble(packaging_unit_sales_price) / Double.parseDouble(packaging_unit_con_factor);
                                Double alternate_unit_price = main_unit_price / Double.parseDouble(alternate_unit_con_factor);
                                mRate.setText(String.valueOf(alternate_unit_price));
                            }*/

                    }
                    if (i == 0) {
                        price_selected_unit = "main";
                        if (default_unit.equals("Pckg. Unit")) {
                            Double main_unit_price = Double.parseDouble(packaging_unit_sales_price) / Double.parseDouble(packaging_unit_con_factor);
                            mRate.setText(String.valueOf(main_unit_price));
                        } else {
                            if (sales_price_applied_on.equals("Alternate Unit")) {
                                Double main_unit_price = Double.parseDouble(sales_price_alternate) * Double.parseDouble(alternate_unit_con_factor);
                                mRate.setText(String.valueOf(main_unit_price));
                            } else {
                                mRate.setText(sales_price_main);
                            }
                        }
                    } else if (i == 1) {
                        price_selected_unit = "alternate";
                        if (default_unit.equals("Pckg. Unit")) {
                            Double main_unit_price = Double.parseDouble(packaging_unit_sales_price) / Double.parseDouble(packaging_unit_con_factor);
                            Double alternate_unit_price = main_unit_price / Double.parseDouble(alternate_unit_con_factor);
                            mRate.setText(String.valueOf(alternate_unit_price));
                        } else {
                            if (sales_price_applied_on.equals("Main Unit")) {
                                Double alternate_unit_price = Double.parseDouble(sales_price_main) / Double.parseDouble(alternate_unit_con_factor);
                                mRate.setText(String.valueOf(alternate_unit_price));
                            } else {
                                mRate.setText(sales_price_alternate);
                            }
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        } else {
            mSpinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        price_selected_unit = "main";
                        if (sales_price_applied_on.equals("Alternate Unit")) {
                            Double main_unit_price = Double.parseDouble(sales_price_alternate) * Double.parseDouble(alternate_unit_con_factor);
                            mRate.setText(String.valueOf(main_unit_price));
                        } else {
                            mRate.setText(sales_price_main);
                        }
                    } else if (i == 1) {
                        price_selected_unit = "alternate";
                        if (sales_price_applied_on.equals("Main Unit")) {
                            Double alternate_unit_price = Double.parseDouble(sales_price_main) / Double.parseDouble(alternate_unit_con_factor);
                            mRate.setText(String.valueOf(alternate_unit_price));
                        } else {
                            mRate.setText(sales_price_alternate);
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
            final int finalPos = pos;
            final int finalPos1 = pos;
            mSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSubmit.startAnimation(blinkOnClick);
                    mMap.put("id", id);
                    mMap.put("item_name", mItemName.getText().toString());
                    mMap.put("description", mDescription.getText().toString());
                    mMap.put("quantity", mQuantity.getText().toString());
                    mMap.put("unit", mSpinnerUnit.getSelectedItem().toString());
                    mMap.put("sr_no", mSr_no.getText().toString());
                    mMap.put("rate", mRate.getText().toString());
                    mMap.put("discount", mDiscount.getText().toString());
                    mMap.put("value", mValue.getText().toString());
                    String taxstring= Preferences.getInstance(getApplicationContext()).getSale_type_name();
                    if(taxstring.startsWith("I")||taxstring.startsWith("L")) {
                        String arrtaxstring[] = taxstring.split("-");
                        String taxname = arrtaxstring[0].trim();
                        String taxvalue = arrtaxstring[1].trim();
                        if(taxvalue.equals("ItemWise")) {
                            String total=mTotal.getText().toString();
                            String arr[]=tax.split(" ");
                            String itemtax=arr[1];
                            String taxval[]=itemtax.split("%");
                            String taxpercent=taxval[0];
                            double totalamt=Double.parseDouble(total)*(Double.parseDouble(taxpercent)/100);
                            totalamt=Double.parseDouble(total)+totalamt;
                            mMap.put("total", String.valueOf(totalamt));
                            mMap.put("itemwiseprice",totalitemprice);
                        }
                        else {
                            mMap.put("total", mTotal.getText().toString());
                        }
                    }
                    else{
                        mMap.put("total", mTotal.getText().toString());
                    }

                    mMap.put("applied", sales_price_applied_on);
                    mMap.put("price_selected_unit", price_selected_unit);
                    mMap.put("alternate_unit_con_factor", alternate_unit_con_factor);
                    mMap.put("packaging_unit_con_factor", packaging_unit_con_factor);
                    mMap.put("mrp", mrp);
                    mMap.put("tax", tax);
                    mMap.put("serial_number",appUser.serial_arr);
                    // mListMap.add(mMap);
                    if(!frombillitemvoucherlist) {
                        appUser.mListMapForItemSale.add(mMap);
                        // appUser.mListMap = mListMap;
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    }
                    else{
                        appUser.mListMapForItemSale.remove(finalPos);
                        appUser.mListMapForItemSale.add(finalPos,mMap);
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    }

                    Intent in = new Intent(getApplicationContext(), CreateSaleActivity.class);
                    in.putExtra("is", true);
                    startActivity(in);
                    finish();
                }
            });

            mRate.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!mDiscount.getText().toString().isEmpty()) {
                        if (!mRate.getText().toString().isEmpty()) {
                            second = Double.valueOf(mRate.getText().toString());
                            if (!mDiscount.getText().toString().isEmpty()) {
                                first = Double.valueOf(mDiscount.getText().toString());
                                mValue.setText("" + (first * second));
                            }
                        } else {
                            mValue.setText("");
                        }
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            mDiscount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!mDiscount.getText().toString().isEmpty()) {
                        first = Double.valueOf(mDiscount.getText().toString());
                        if (!mRate.getText().toString().isEmpty()) {
                            second = Double.valueOf(mRate.getText().toString());
                            mValue.setText("" + (first * second) / 100);
                        }
                    } else {
                        mValue.setText("");
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            mQuantity.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(count==0){
                        appUser.serial_arr.clear();
                        LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                    }
                    if (!mValue.getText().toString().isEmpty()) {
                        if (!mQuantity.getText().toString().isEmpty()) {
                            second = Double.valueOf(mQuantity.getText().toString());
                            if (!mValue.getText().toString().isEmpty()) {
                                first = Double.valueOf(mValue.getText().toString());
                                third = Double.valueOf(mRate.getText().toString());
                                mTotal.setText("" + (third - first) * second);
                            }
                        } else {
                            mTotal.setText("");
                        }
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            mValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (!mValue.getText().toString().isEmpty()) {
                        if (!mQuantity.getText().toString().isEmpty()) {
                            second = Double.valueOf(mQuantity.getText().toString());
                            if (!mValue.getText().toString().isEmpty()) {
                                first = Double.valueOf(mValue.getText().toString());
                                third = Double.valueOf(mRate.getText().toString());
                                mTotal.setText("" + (third - first) * second);
                            }
                        } else {
                            mTotal.setText("");
                        }
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

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
        actionbarTitle.setText("SALE VOUCHER ADD ITEM");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        boolean b = intent.getBooleanExtra("bool", false);
        if (b) {
            Intent intent1 = new Intent(this, CreateSaleActivity.class);
            intent1.putExtra("is", true);
            startActivity(intent1);
            finish();
        } else {
            Intent intent2 = new Intent(this, ExpandableItemListActivity.class);
            startActivity(intent2);
            finish();
        }


    }

    private int getWidth(){
        int density= getResources().getDisplayMetrics().densityDpi;
        int size =800;
        switch(density)
        {
            case DisplayMetrics.DENSITY_LOW:
                size = 1200;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                size = 1200;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                size = 1200;
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                size = 1000;
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                size = 1200;
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                size = 1000;
                break;

        }

        return size;
    }
    private int getHeight(){
        int density= getResources().getDisplayMetrics().densityDpi;
        int height =100;
        switch(density)
        {
            case DisplayMetrics.DENSITY_LOW:
                height = 50;
                break;
            case DisplayMetrics.DENSITY_260:
                height = 50;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                height = 50;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                height = 50;
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                height = 100;
                break;
            case DisplayMetrics.DENSITY_XXHIGH:
                height = 250;
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                height = 200;
                break;

        }

        return height;
    }

}
