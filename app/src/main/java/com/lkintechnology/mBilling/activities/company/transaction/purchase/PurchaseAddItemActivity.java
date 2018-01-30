package com.lkintechnology.mBilling.activities.company.transaction.purchase;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.administration.master.item.ExpandableItemListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale.CreateSaleActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import java.security.cert.PolicyQualifierInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import timber.log.Timber;

public class PurchaseAddItemActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
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
    List<Map<String, String>> mListMapForItemPurchase;

    AppUser appUser;
    List<Map<String, String>> mListMap;
    Map mMap;
    Double first, second, third;
    Intent intent;
    Animation blinkOnClick;
    ArrayList<String> mUnitList;
    ArrayAdapter<String> mUnitAdapter;
    String purchase_price_applied_on;

    String serial = "";
    String sales_price_applied_on;
    String price_selected_unit;
    String alternate_unit_con_factor;
    String packaging_unit_con_factor;
    String mrp;
    String tax;
    String barcode;
    Boolean frombillitemvoucherlist;
    String name;
    String desc;
    String main_unit;
    String alternate_unit;
    String purchase_price_main;
    String purchase_price_alternate ;
    Boolean serailwise;
    Boolean batchwise ;
    String packaging_unit ;
    String default_unit;
    String packaging_unit_purchase_price;
    String sale_type;
    String totalitemprice;
    String purchase_unit;
    String id;
    ArrayAdapter<String> spinnerAdapter;
    ArrayList arr_barcode;
    private ZBarScannerView mScannerView;
    @Bind(R.id.mainLayout)
    LinearLayout mMainLayout;
    @Bind(R.id.scan_item)
    LinearLayout mScanItem;
    @Bind(R.id.add_item)
    LinearLayout mAddItem;
    @Bind(R.id.scanLayout)
    RelativeLayout mScanLayout;
    @Bind(R.id.scanning_content_frame)
    FrameLayout scanning_content_frame;
    @Bind(R.id.cancel)
    ImageView mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_add_item);
        frombillitemvoucherlist=getIntent().getExtras().getBoolean("frombillitemvoucherlist");
        appUser = LocalRepositories.getAppUser(this);
        ButterKnife.bind(this);
        initActionbar();
        appUser.serial_arr.clear();
        appUser.purchase_item_serail_arr.clear();
        LocalRepositories.saveAppUser(getApplicationContext(),appUser);
        mListMap = new ArrayList<>();
        mMap = new HashMap<>();
        mScannerView = new ZBarScannerView(this);
        mUnitList = new ArrayList<>();
        int pos = -1;
        blinkOnClick = AnimationUtils.loadAnimation(this, R.anim.blink_on_click);
        if(frombillitemvoucherlist){
            pos=getIntent().getExtras().getInt("pos");
            Map map=new HashMap<>();
            map=appUser.mListMapForItemPurchase.get(pos);
            String itemName= (String) map.get("item_name");
            String iid=(String)map.get("id");

            String description= (String) map.get("description");
            String quantity= (String) map.get("quantity");
            String unit= (String) map.get("unit");
            String srNo= (String) map.get("sr_no");
            String rate= (String) map.get("rate");
            String discount= (String) map.get("discount");
            String value= (String) map.get("value");
            String total= (String) map.get("total");
            String mrpitem= (String) map.get("mrp");
            String purchaseunit= (String) map.get("purchase_unit");
            String applieditem= (String) map.get("applied");
            String priceselectedunititem= (String) map.get("price_selected_unit");
            String alternateunitconfactoritem= (String) map.get("alternate_unit_con_factor");
            String packagingunitconfactoritem= (String) map.get("packaging_unit_con_factor");
            String taxitem= (String) map.get("tax");
            String batch_wise=(String) map.get("batch_wise").toString();
            String serial_wise= (String) map.get("serial_wise").toString();
            String packagingunit=(String) map.get("packaging_unit");
            String defaultunit=(String) map.get("default_unit");
            String mainunit=(String) map.get("main_unit");
            String alternateunit=(String) map.get("alternate_unit");
            String purchasepricemain=(String) map.get("purchase_price_main");

            String purchasepricealternate=(String) map.get("purchase_price_alternate");
            String unit_list=(String) map.get("unit_list").toString().replace("[","").replace("]","");
            List<String> myList = new ArrayList<String>(Arrays.asList(unit_list.split(",")));
            for(int i=0;i<myList.size();i++){
                mUnitList.add(myList.get(i));
            }
            if((String) map.get("serial_number")!=null) {
                String serialnumber = (String) map.get("serial_number").toString().replace("[", "").replace("]", "");
                List<String> serialList = new ArrayList<String>(Arrays.asList(serialnumber.split(",")));
                for (int i = 0; i < serialList.size(); i++) {
                    appUser.serial_arr.add(serialList.get(i));
                    LocalRepositories.saveAppUser(this, appUser);
                }

                Timber.i("MYSERAILNUMBER" + serialnumber);
            }

       /*     mUnitAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, mUnitList);
            mUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerUnit.setAdapter(mUnitAdapter);*/
            id=iid;
            mItemName.setText(itemName);
            mQuantity.setText(quantity);
            mRate.setText(rate);
            mValue.setText(value);
            mDiscount.setText(discount);
            mTotal.setText(total);
            mDescription.setText(description);
            default_unit=defaultunit;
            mrp=mrpitem;
            purchase_unit=purchaseunit;
            main_unit=mainunit;
            alternate_unit=alternateunit;
            purchase_price_applied_on=applieditem;
            purchase_price_main=purchasepricemain;
            alternate_unit_con_factor=alternateunitconfactoritem;
            packaging_unit_con_factor=packagingunitconfactoritem;
            purchase_price_alternate=purchasepricealternate;
            price_selected_unit=priceselectedunititem;
            serailwise=Boolean.valueOf(serial_wise);
            batchwise=Boolean.valueOf(batch_wise);
            Timber.i("PRICESELECTED"+price_selected_unit);
         /*   if(packagingunit==null){
                packaging_unit="";
            }
            else{*/
            packaging_unit = packagingunit;
            // }
            packaging_unit_con_factor=packagingunitconfactoritem;
            tax=taxitem;
           /* if(price_selected_unit.equals("main")){
                mSpinnerUnit.setSelection(0);
            }
            else if(price_selected_unit.equals("alternate")){
                mSpinnerUnit.setSelection(1);
            }
            else {
                if (!packaging_unit_con_factor.equals("")) {
                    mSpinnerUnit.setSelection(2);
                }
            }*/

            mUnitAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, mUnitList);
            mUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerUnit.setAdapter(mUnitAdapter);
            if (!packaging_unit.equals("")) {
                if (price_selected_unit.equals("main")) {
                    mSpinnerUnit.setSelection(0);
                    purchase_unit=main_unit;
                } else if (price_selected_unit.equals("alternate")) {
                    mSpinnerUnit.setSelection(1);
                    purchase_unit=alternate_unit;
                } else if (price_selected_unit.equals("packaging")) {
                    mSpinnerUnit.setSelection(2);
                    purchase_unit=packaging_unit;
                } else {
                    mSpinnerUnit.setSelection(0);
                    purchase_unit=main_unit;
                }
            } else {
                if (price_selected_unit.equals("main")) {
                    mSpinnerUnit.setSelection(0);
                    purchase_unit=main_unit;
                } else if (price_selected_unit.equals("alternate")) {
                    mSpinnerUnit.setSelection(1);
                    purchase_unit=alternate_unit;
                } else {
                    mSpinnerUnit.setSelection(0);
                    purchase_unit=main_unit;
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
            purchase_price_main = intent.getStringExtra("purchase_price_main");
            if(purchase_price_main.equals("null")){
                purchase_price_main="0.0";
            }
             purchase_price_alternate = intent.getStringExtra("purchase_price_alternate");
             serailwise = intent.getExtras().getBoolean("serial_wise");
            batchwise = intent.getExtras().getBoolean("batch_wise");
             packaging_unit = intent.getStringExtra("packaging_unit");
             default_unit = intent.getStringExtra("default_unit");
            packaging_unit_purchase_price = intent.getStringExtra("packaging_unit_purchase_price");
            mrp = intent.getStringExtra("mrp");
            packaging_unit_con_factor = intent.getStringExtra("packaging_unit_con_factor");
            purchase_price_applied_on = intent.getStringExtra("applied");
            alternate_unit_con_factor = intent.getStringExtra("alternate_unit_con_factor");
            tax = intent.getStringExtra("tax");
            mItemName.setText(name);
            mDescription.setText(desc);
            mUnitList.add("Main Unit : " + main_unit);
            mUnitList.add("Alternate Unit :" + alternate_unit);
            if (!packaging_unit.equals("")) {
                mUnitList.add("Packaging Unit :" + packaging_unit);
            }
            mItemName.setEnabled(false);
            mValue.setEnabled(true);
            mTotal.setEnabled(false);

            mUnitAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, mUnitList);
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
        }

        mAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mQuantity.getText().toString().equals("")) {
                    Dialog dialogbal = new Dialog(PurchaseAddItemActivity.this);
                    dialogbal.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialogbal.setContentView(R.layout.dialog_add_item_code);
                    dialogbal.setCancelable(true);
                    LinearLayout mClose = (LinearLayout) dialogbal.findViewById(R.id.close);
                    LinearLayout mSubmit = (LinearLayout) dialogbal.findViewById(R.id.submit);
                    EditText mSerialNumber = (EditText) dialogbal.findViewById(R.id.serial);
                    mClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialogbal.dismiss();
                        }
                    });
                    mSubmit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!mSerialNumber.getText().toString().equals("")) {
                                String listString = "";
                                int qty = Integer.parseInt(mQuantity.getText().toString());
                                if (qty > appUser.serial_arr.size()) {
                                    // mScannerView.stopCamera();
                                    if (appUser.serial_arr.contains(mSerialNumber.getText().toString())) {
               /* appUser.serial_arr.add("");
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);*/
                                        Toast.makeText(PurchaseAddItemActivity.this, mSerialNumber.getText().toString() + "already added", Toast.LENGTH_SHORT).show();
                                    } else {
                                        appUser.serial_arr.add(mSerialNumber.getText().toString());
                                       // appUser.purchase_item_serail_arr.add(mSerialNumber.getText().toString());
                                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                        for (String s : appUser.serial_arr) {
                                            listString += s + ",";
                                        }
                                        mSr_no.setText(listString);
                                        Toast.makeText(PurchaseAddItemActivity.this, mSerialNumber.getText().toString() + "added successfully", Toast.LENGTH_SHORT).show();
                                        mSerialNumber.setText("");
                                    }
                                }
                                else{
                                    Toast.makeText(getApplicationContext()," Quantity is less.",Toast.LENGTH_LONG).show();
                                    dialogbal.dismiss();
                                }

                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Enter Serial number",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dialogbal.show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Add quantity",Toast.LENGTH_LONG).show();
                }
            }

        });

        mScanItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mQuantity.getText().toString().equals("")) {
                    mMainLayout.setVisibility(View.GONE);
                    mScanLayout.setVisibility(View.VISIBLE);
                    mScannerView.setResultHandler(PurchaseAddItemActivity.this);
                    scanning_content_frame.addView(mScannerView);
                    mScannerView.startCamera();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Add quantity",Toast.LENGTH_LONG).show();
                }
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mScannerView.stopCamera();
                scanning_content_frame.removeView(mScannerView);
                mMainLayout.setVisibility(View.VISIBLE);
                mScanLayout.setVisibility(View.GONE);
             /*   if (appUser.serial_arr.size() > 0) {
                    String listString="";
                for (String s : appUser.serial_arr) {
                    listString += s + ",";
                }
                mSr_no.setText(listString);*/
           // }
        }
        });


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
                    Dialog dialogbal = new Dialog(PurchaseAddItemActivity.this);
                    dialogbal.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialogbal.setContentView(R.layout.dialog_serail);
                    dialogbal.setCancelable(true);
                    LinearLayout serialLayout = (LinearLayout) dialogbal.findViewById(R.id.main_layout);
                    LinearLayout submit = (LinearLayout) dialogbal.findViewById(R.id.submit);
                    LinearLayout serial_layout = (LinearLayout) dialogbal.findViewById(R.id.serial_layout);
                    int width=getWidth();
                    int height=getHeight();
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
                    lp.setMargins(20,10,20,0);
                    EditText[] pairs = new EditText[Integer.parseInt(serial)];
                    for (int l = 0; l < Integer.parseInt(serial); l++) {
                        pairs[l] = new EditText(getApplicationContext());
                        pairs[l].setPadding(20, 10, 10, 0);
                        pairs[l].setInputType(InputType.TYPE_CLASS_NUMBER);
                        pairs[l].setWidth(width);
                        pairs[l].setHeight(height);
                        pairs[l].setBackgroundResource(R.drawable.grey_stroke_rect);
                        pairs[l].setTextSize(18);
                        if(appUser.serial_arr.size()>0) {
                            if(appUser.serial_arr.size()>l) {
                                pairs[l].setText(appUser.serial_arr.get(l));
                            }
                            else{
                                pairs[l].setText("");
                            }
                        }
                        pairs[l].setHint("Enter Serial Number"+" "+(l+1));
                        pairs[l].setHintTextColor(Color.GRAY);
                        pairs[l].setTextColor(Color.BLACK);
                        pairs[l].setLayoutParams(lp);
                        pairs[l].setId(l);
                        //pairs[l].setText((l + 1) + ": something");
                        serialLayout.addView(pairs[l]);

                      /*  final int finalL = l;
                        pairs[l].addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                                if(appUser.serial_arr.contains(pairs[finalL].getText().toString())){
                                    appUser.serial_arr.set(finalL,"");
                                }
                                else{

                                    appUser.serial_arr.add(pairs[finalL].getText().toString());
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                }


                            }
                        });*/
                    }

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appUser.serial_arr.clear();
                          //  appUser.purchase_item_serail_arr.clear();
                            LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                            for(int i=0;i<Integer.parseInt(serial);i++) {

                                    if (appUser.serial_arr.contains(pairs[i].getText().toString())) {
                                        pairs[i].setText("");
                                        appUser.serial_arr.add(i, "");
                                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                        Toast.makeText(PurchaseAddItemActivity.this, pairs[i].getText().toString() + "already added", Toast.LENGTH_SHORT).show();
                                    } else {
                                        if(!pairs[i].getText().toString().equals("")) {
                                            appUser.serial_arr.add(i, pairs[i].getText().toString());
                                            //  appUser.purchase_item_serail_arr.add(i,appUser.serial_arr.get(i));
                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                        }
                                        else{
                                            appUser.serial_arr.add(i, "");
                                            //  appUser.purchase_item_serail_arr.add(i,appUser.serial_arr.get(i));
                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                        }
                                    }

                            }
                            appUser.purchase_item_serail_arr.clear();
                            LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                            for(int i=0;i<appUser.serial_arr.size();i++){
                                if(!appUser.serial_arr.get(i).equals("")){
                                    appUser.purchase_item_serail_arr.add(appUser.serial_arr.get(i));
                                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);

                                }
                            }
                            appUser.serial_arr.clear();
                            LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                            for(int i=0;i<appUser.purchase_item_serail_arr.size();i++){
                                appUser.serial_arr.add(appUser.purchase_item_serail_arr.get(i));
                                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                            }



                            String listString = "";

                            for (String s : appUser.purchase_item_serail_arr)
                            {
                                listString += s + ",";
                            }
                            mSr_no.setText(listString);
                            dialogbal.dismiss();
                        }
                    });
                    dialogbal.show();

                }
            }

        });


        if (!packaging_unit.equals("")) {
            mSpinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if (i == 2) {
                        purchase_unit=packaging_unit;
                        price_selected_unit = "packaging";
                        if (default_unit.equals("Pckg. Unit")) {
                            mRate.setText(String.valueOf(packaging_unit_purchase_price));
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
                        purchase_unit=main_unit;
                        price_selected_unit = "main";
                        if (default_unit.equals("Pckg. Unit")) {
                            Double main_unit_price = Double.parseDouble(packaging_unit_purchase_price) / Double.parseDouble(packaging_unit_con_factor);
                            mRate.setText(String.valueOf(main_unit_price));
                        } else {
                            if (purchase_price_applied_on.equals("Alternate Unit")) {
                                Double main_unit_price = Double.parseDouble(purchase_price_alternate) * Double.parseDouble(alternate_unit_con_factor);
                                mRate.setText(String.valueOf(main_unit_price));
                            } else {
                                mRate.setText(purchase_price_main);
                            }
                        }
                    } else if (i == 1) {
                        purchase_unit=alternate_unit;
                        price_selected_unit = "alternate";
                        if (default_unit.equals("Pckg. Unit")) {
                            Double main_unit_price = Double.parseDouble(packaging_unit_purchase_price) / Double.parseDouble(packaging_unit_con_factor);
                            Double alternate_unit_price = main_unit_price / Double.parseDouble(alternate_unit_con_factor);
                            mRate.setText(String.valueOf(alternate_unit_price));
                        } else {
                            if (purchase_price_applied_on.equals("Main Unit")) {
                                Double alternate_unit_price = Double.parseDouble(purchase_price_main) / Double.parseDouble(alternate_unit_con_factor);
                                mRate.setText(String.valueOf(alternate_unit_price));
                            } else {
                                mRate.setText(purchase_price_alternate);
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
                        purchase_unit=main_unit;
                        price_selected_unit = "main";
                        if (purchase_price_applied_on.equals("Alternate Unit")) {
                            Double main_unit_price = Double.parseDouble(purchase_price_alternate) * Double.parseDouble(alternate_unit_con_factor);
                            mRate.setText(String.valueOf(main_unit_price));
                        } else {
                           /* if (!purchase_price_main.equals("null")){*/
                                mRate.setText(purchase_price_main);
                         //   }
                          /*  else {
                                mRate.setText("");
                            }*/
                        }
                    } else if (i == 1) {
                        purchase_unit=alternate_unit;
                        price_selected_unit = "alternate";
                        if (purchase_price_applied_on.equals("Main Unit")) {
                         //   if (!purchase_price_main.equals("null")){
                                Double alternate_unit_price = Double.parseDouble(purchase_price_main) / Double.parseDouble(alternate_unit_con_factor);
                                mRate.setText(String.valueOf(alternate_unit_price));
                         //   }

                        } else {
                           // if (!purchase_price_main.equals("null")){
                                mRate.setText(purchase_price_alternate);
                           /* }else {
                                mRate.setText("");
                            }*/

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
                Timber.i("ARRAY"+appUser.purchase_item_serail_arr);
                mSubmit.startAnimation(blinkOnClick);
                if (mQuantity.getText().toString().equals("0")|mQuantity.getText().toString().equals("")){
                    Snackbar.make(coordinatorLayout,"enter minimum 1 quantity",Snackbar.LENGTH_LONG).show();
                    return;
                }
                mMap.put("id", id);
                mMap.put("item_name", mItemName.getText().toString());
                mMap.put("description", mDescription.getText().toString());
                mMap.put("quantity", mQuantity.getText().toString());
                mMap.put("unit", mSpinnerUnit.getSelectedItem().toString());
                mMap.put("sr_no", mSr_no.getText().toString());
                mMap.put("rate", mRate.getText().toString());
                mMap.put("discount", mDiscount.getText().toString());
                mMap.put("value", mValue.getText().toString());
                mMap.put("default_unit",default_unit);
                mMap.put("packaging_unit",packaging_unit);
                mMap.put("purchase_price_alternate",purchase_price_alternate);
                mMap.put("purchase_price_main",purchase_price_main);
                mMap.put("alternate_unit",alternate_unit);
                mMap.put("packaging_unit_sales_price",packaging_unit_purchase_price);
                mMap.put("main_unit",main_unit);
                mMap.put("batch_wise",batchwise);
                mMap.put("serial_wise",serailwise);
                mMap.put("purchase_unit",purchase_unit);
                String taxstring= Preferences.getInstance(getApplicationContext()).getPurchase_type_name();
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

                mMap.put("applied", purchase_price_applied_on);
                mMap.put("price_selected_unit", price_selected_unit);
                mMap.put("alternate_unit_con_factor", alternate_unit_con_factor);
                mMap.put("packaging_unit_con_factor", packaging_unit_con_factor);
                mMap.put("mrp", mrp);
                mMap.put("tax", tax);
                mMap.put("serial_number",appUser.purchase_item_serail_arr);
                mMap.put("unit_list",mUnitList);
                if(!frombillitemvoucherlist) {
                    appUser.mListMapForItemPurchase.add(mMap);
                    // appUser.mListMap = mListMap;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
                else{
                    appUser.mListMapForItemPurchase.remove(finalPos);
                    appUser.mListMapForItemPurchase.add(finalPos,mMap);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
                // mListMap.add(mMap);
                appUser.serial_arr.clear();
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                Intent in = new Intent(getApplicationContext(), CreatePurchaseActivity.class);
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
                    mSr_no.setText("");
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
                            if(((third*second)-first)>=0){
                                mTotal.setText("" + ((third*second)-first));
                            }
                            else{
                                mValue.setText("0.0");
                                Toast.makeText(getApplicationContext(),"Value can not be more than total price",Toast.LENGTH_LONG).show();
                            }

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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("PURCHASE ADD ITEM");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if ( ExpandableItemListActivity.comingFrom == 1) {
            startActivity(new Intent(this, ExpandableItemListActivity.class));
            finish();
        }
    }

    private int getWidth(){
        int density= getResources().getDisplayMetrics().densityDpi;
        int size =0;
        switch(density)
        {
            case DisplayMetrics.DENSITY_LOW:
                size = 500;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                size = 900;
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
        int height =150;
        switch(density)
        {
            case DisplayMetrics.DENSITY_LOW:
                height = 150;
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                height = 150;
                break;
            case DisplayMetrics.DENSITY_HIGH:
                height = 250;
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                height = 100;
                break;
            case DisplayMetrics.DENSITY_XXXHIGH:
                height = 150;
                break;

        }

        return height;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, ExpandableItemListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void handleResult(Result result) {
        if (!result.getContents().equals("")) {
            String listString="";
            int qty=Integer.parseInt( mQuantity.getText().toString());
            if (qty>appUser.serial_arr.size()) {
                // mScannerView.stopCamera();
                if (appUser.serial_arr.contains(result.getContents())) {
               /* appUser.serial_arr.add("");
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);*/
                    Toast.makeText(PurchaseAddItemActivity.this, result.getContents() + "already added", Toast.LENGTH_SHORT).show();
                } else {
                    appUser.serial_arr.add(result.getContents());
                    appUser.purchase_item_serail_arr.add(result.getContents());
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    for (String s : appUser.purchase_item_serail_arr) {
                        listString += s + ",";
                    }
                    mSr_no.setText(listString);
                    Toast.makeText(PurchaseAddItemActivity.this, result.getContents() + "added successfully", Toast.LENGTH_SHORT).show();
                }

                mScannerView.stopCamera();
                scanning_content_frame.removeView(mScannerView);
                mScannerView.setResultHandler(PurchaseAddItemActivity.this);
                scanning_content_frame.addView(mScannerView);
                mScannerView.startCamera();


            }
            else{
                Toast.makeText(PurchaseAddItemActivity.this,"Item quantity is less", Toast.LENGTH_SHORT).show();
                mScannerView.stopCamera();
                scanning_content_frame.removeView(mScannerView);
                mMainLayout.setVisibility(View.VISIBLE);
                mScanLayout.setVisibility(View.GONE);
            }
        }

    }
}
