package com.lkintechnology.mBilling.activities.company.transaction.sale;

import android.app.Activity;
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
import com.lkintechnology.mBilling.activities.company.FirstPageActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.barcode.CheckBoxVoucherBarcodeActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase.CreatePurchaseActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.fragments.transaction.sale.AddItemVoucherFragment;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;
import timber.log.Timber;

public class SaleVoucherAddItemActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.item_name)
    TextView mItemName;
    @Bind(R.id.item_layout)
    LinearLayout mItemLayout;
    @Bind(R.id.description)
    EditText mDescription;
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
    Double first = 0.0, second = 0.0, third = 0.0;
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
    String barcode, voucher_barcode;
    Boolean frombillitemvoucherlist;
    String name;
    String desc;
    String main_unit;
    String alternate_unit;
    String sales_price_main;
    String sales_price_alternate;
    Boolean serailwise;
    Boolean batchwise;
    String packaging_unit;
    String default_unit;
    String packaging_unit_sales_price;
    String sale_type;
    String totalitemprice;
    String id;
    String sale_unit;
    ArrayAdapter<String> spinnerAdapter;
    ArrayList<String> arr_barcode;
    ArrayList<String> arr_new_barcode;
    private ZBarScannerView mScannerView;
    @Bind(R.id.mainLayout)
    LinearLayout mMainLayout;
    @Bind(R.id.add_item)
    LinearLayout mAddItem;
    @Bind(R.id.business_type)
    Spinner mBusinessType;
    @Bind(R.id.scan_item)
    LinearLayout mScanItem;
    @Bind(R.id.scanLayout)
    RelativeLayout mScanLayout;
    @Bind(R.id.scanning_content_frame)
    FrameLayout scanning_content_frame;
    @Bind(R.id.cancel)
    ImageView mCancel;
    Boolean fromsalelist;
    String itemid = "";
    String[] barcodeArray;
    String quantity;
    List<String> myListForSerialNo;
    Boolean boolForBarcode;
    private ArrayList<String> serialNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_voucher_add_item);
        appUser = LocalRepositories.getAppUser(this);
        fromsalelist = getIntent().getExtras().getBoolean("fromsalelist");
        frombillitemvoucherlist = getIntent().getExtras().getBoolean("frombillitemvoucherlist");
        ButterKnife.bind(this);
        serialNo = new ArrayList<>();
        initActionbar();
        mListMap = new ArrayList<>();
        mScannerView = new ZBarScannerView(this);
        mMap = new HashMap<>();
        mSpinnerUnit.setEnabled(false);
        mUnitList = new ArrayList<>();
        mUnitList = new ArrayList<>();
        int pos = -1;
        myListForSerialNo = new ArrayList<>();
        myListForSerialNo.clear();
        boolForBarcode = true;
        appUser.sale_item_serial_arr.clear();
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
        blinkOnClick = AnimationUtils.loadAnimation(this, R.anim.blink_on_click);
        if (frombillitemvoucherlist) {
            Timber.i("frombillitemvoucherlist true");
            pos = getIntent().getExtras().getInt("pos");
            Map map = new HashMap<>();
            map = appUser.mListMapForItemSale.get(pos);
            String item_id = (String) map.get("id");
            String iid = (String) map.get("item_id");
            String itemName = (String) map.get("item_name");
            String description = (String) map.get("description");
            quantity = (String) map.get("quantity");
            String unit = (String) map.get("unit");
            String srNo = (String) map.get("sr_no");
            String rate = (String) map.get("rate");
            String discount = (String) map.get("discount");
            String value = (String) map.get("value");
            String total = (String) map.get("total");
            String mrpitem = (String) map.get("mrp");
            String saleunit = (String) map.get("sale_unit");
            String applieditem = (String) map.get("applied");
            String priceselectedunititem = (String) map.get("price_selected_unit");
            String alternateunitconfactoritem = (String) map.get("alternate_unit_con_factor");
            String packagingunitconfactoritem = (String) map.get("packaging_unit_con_factor");
            String taxitem = (String) map.get("tax");
            String batch_wise = (String) map.get("batch_wise").toString();
            String serial_wise = (String) map.get("serial_wise").toString();
            String packagingunit = (String) map.get("packaging_unit");
            String mainunit = (String) map.get("main_unit");
            String alternateunit = (String) map.get("alternate_unit");
            String defaultunit = (String) map.get("default_unit");
            String salepricemain = (String) map.get("sales_price_main");
            String salepricealternate = (String) map.get("sales_price_alternate");
            String unit_list = (String) map.get("unit_list").toString().replace("[", "").replace("]", "");
            String businessType = (String) map.get("business_type");
            List<String> myList = new ArrayList<String>(Arrays.asList(unit_list.split(",")));
            for (int i = 0; i < myList.size(); i++) {
                mUnitList.add(myList.get(i).trim());
            }
            Timber.i("UNIT_LIST" + myList);
            barcode = (String) map.get("barcode").toString().replace("[", "").replace("]", "");
            arr_barcode = new ArrayList();
            arr_new_barcode = new ArrayList<String>(Arrays.asList(barcode.split(",")));

           /* arr_barcode.add(0, "None");
            for (int i = 0; i < arr_new_barcode.size(); i++) {
                arr_barcode.add(i + 1, arr_new_barcode.get(i));
                LocalRepositories.saveAppUser(this, appUser);
            }*/
            for (int i = 0; i < arr_new_barcode.size(); i++) {
                arr_barcode.add(i, arr_new_barcode.get(i));
                LocalRepositories.saveAppUser(this, appUser);
            }
            itemid = item_id;
            id = iid;

            voucher_barcode = (String) map.get("voucher_barcode");
            barcodeArray = voucher_barcode.split(",");
            CheckBoxVoucherBarcodeActivity.locQuantity = barcodeArray.length;
            mSr_no.setText(voucher_barcode);
            boolForBarcode = true;
            myListForSerialNo = new ArrayList<String>(Arrays.asList(voucher_barcode.split(",")));
            if (businessType != null) {
                if (businessType.equals("Mobile Dealer")) {
                    mBusinessType.setSelection(0);
                } else {
                    mBusinessType.setSelection(1);
                }
            } else {
                mBusinessType.setSelection(0);
            }

            mItemName.setText(itemName);
            mQuantity.setText(quantity);
            mRate.setText(rate);
            mValue.setText(value);
            mDiscount.setText(discount);
            mTotal.setText(total);
            mDescription.setText(description);
            default_unit = defaultunit;
            mrp = mrpitem;
            sale_unit = saleunit;
            sales_price_applied_on = applieditem;
            sales_price_main = salepricemain;
            alternate_unit_con_factor = alternateunitconfactoritem;
            packaging_unit_con_factor = packagingunitconfactoritem;
            sales_price_alternate = salepricealternate;
            main_unit = mainunit;
            alternate_unit = alternateunit;
            price_selected_unit = priceselectedunititem;
            serailwise = Boolean.valueOf(serial_wise);
            batchwise = Boolean.valueOf(batch_wise);
            Timber.i("PRICESELECTED" + price_selected_unit);
         /*   if(packagingunit==null){
                packaging_unit="";
            }
            else{*/
            packaging_unit = packagingunit;
            // }
            packaging_unit_con_factor = packagingunitconfactoritem;
            tax = taxitem;
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
                    sale_unit = main_unit;
                    mSpinnerUnit.setSelection(0);
                } else if (price_selected_unit.equals("alternate")) {
                    mSpinnerUnit.setSelection(1);
                    sale_unit = alternate_unit;
                } else if (price_selected_unit.equals("packaging")) {
                    mSpinnerUnit.setSelection(2);
                    sale_unit = packaging_unit;
                } else {
                    mSpinnerUnit.setSelection(0);
                    sale_unit = main_unit;
                }
            } else {
                if (price_selected_unit.equals("main")) {
                    mSpinnerUnit.setSelection(0);
                    sale_unit = main_unit;
                } else if (price_selected_unit.equals("alternate")) {
                    mSpinnerUnit.setSelection(1);
                    sale_unit = alternate_unit;
                } else {
                    mSpinnerUnit.setSelection(0);
                    sale_unit = main_unit;
                }


            }
        } else {
            CheckBoxVoucherBarcodeActivity.locQuantity = 0;
            Timber.i("frombillitemvoucherlist false");
            CreateSaleActivity.hideKeyPad(this);
            Intent intent = getIntent();
            id = intent.getStringExtra("item_id");
            name = intent.getStringExtra("name");
            desc = intent.getStringExtra("desc");
            main_unit = intent.getStringExtra("main_unit");
            alternate_unit = intent.getStringExtra("alternate_unit");
            sales_price_main = intent.getStringExtra("sales_price_main");
            sales_price_alternate = intent.getStringExtra("sales_price_alternate");
            serailwise = intent.getExtras().getBoolean("serial_wise");
            batchwise = intent.getExtras().getBoolean("batch_wise");
            totalitemprice = intent.getStringExtra("total");
            packaging_unit = intent.getStringExtra("packaging_unit");
            default_unit = intent.getStringExtra("default_unit");
            packaging_unit_sales_price = intent.getStringExtra("packaging_unit_sales_price");
            mrp = intent.getStringExtra("mrp");
            packaging_unit_con_factor = intent.getStringExtra("packaging_unit_con_factor");
            sales_price_applied_on = intent.getStringExtra("applied");
            alternate_unit_con_factor = intent.getStringExtra("alternate_unit_con_factor");
            tax = intent.getStringExtra("tax");
            barcode = intent.getStringExtra("barcode");
            arr_new_barcode = new ArrayList<String>(Arrays.asList(barcode.split(",")));
            arr_barcode = new ArrayList();
           /* arr_barcode.add(0, "None");
            for (int i = 0; i < arr_new_barcode.size(); i++) {
                arr_barcode.add(i + 1, arr_new_barcode.get(i));
                LocalRepositories.saveAppUser(this, appUser);
            }*/

            for (int i = 0; i < arr_new_barcode.size(); i++) {
                arr_barcode.add(i, arr_new_barcode.get(i));
                LocalRepositories.saveAppUser(this, appUser);
            }

            Timber.i("ssss " + barcode);


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
                    Dialog dialogbal = new Dialog(SaleVoucherAddItemActivity.this);
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
                                boolForBarcode = false;
                                String listString = "";
                                int qty = Integer.parseInt(mQuantity.getText().toString());
                                if (qty > appUser.sale_item_serial_arr.size()) {
                                    // mScannerView.stopCamera();
                                    if (mBusinessType.getSelectedItem().toString().equals("Mobile Dealer")) {
                                        if (mSerialNumber.getText().toString().length() == 15) {
                                            if (appUser.sale_item_serial_arr.contains(mSerialNumber.getText().toString())) {
               /* appUser.serial_arr.add("");
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);*/
                                                Toast.makeText(SaleVoucherAddItemActivity.this, mSerialNumber.getText().toString() + "already added", Toast.LENGTH_SHORT).show();
                                            } else {
                                                appUser.sale_item_serial_arr.add(mSerialNumber.getText().toString());
                                                // appUser.purchase_item_serail_arr.add(mSerialNumber.getText().toString());
                                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                                for (String s : appUser.sale_item_serial_arr) {
                                                    listString += s + ",";
                                                }
                                                mSr_no.setText(listString);
                                                Toast.makeText(SaleVoucherAddItemActivity.this, mSerialNumber.getText().toString() + "added successfully", Toast.LENGTH_SHORT).show();
                                                mSerialNumber.setText("");
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), mSerialNumber.getText().toString() + " is not a IMEI number", Toast.LENGTH_LONG).show();
                                        }

                                    } else {
                                        if (appUser.sale_item_serial_arr.contains(mSerialNumber.getText().toString())) {
               /* appUser.serial_arr.add("");
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);*/
                                            Toast.makeText(SaleVoucherAddItemActivity.this, mSerialNumber.getText().toString() + "already added", Toast.LENGTH_SHORT).show();
                                        } else {
                                            appUser.sale_item_serial_arr.add(mSerialNumber.getText().toString());
                                            // appUser.purchase_item_serail_arr.add(mSerialNumber.getText().toString());
                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                            for (String s : appUser.sale_item_serial_arr) {
                                                listString += s + ",";
                                            }
                                            mSr_no.setText(listString);
                                            Toast.makeText(SaleVoucherAddItemActivity.this, mSerialNumber.getText().toString() + "added successfully", Toast.LENGTH_SHORT).show();
                                            mSerialNumber.setText("");
                                        }

                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), " Quantity is less.", Toast.LENGTH_LONG).show();
                                    dialogbal.dismiss();
                                }

                            } else {
                                dialogbal.dismiss();
                                //Toast.makeText(getApplicationContext(), "Enter Serial number", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    dialogbal.show();
                } else {
                    Toast.makeText(getApplicationContext(), "Add quantity", Toast.LENGTH_LONG).show();
                }
            }

        });

        mScanItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mQuantity.getText().toString().equals("")) {
                    boolForBarcode = false;
                    mMainLayout.setVisibility(View.GONE);
                    mScanLayout.setVisibility(View.VISIBLE);
                    mScannerView.setResultHandler(SaleVoucherAddItemActivity.this);
                    scanning_content_frame.addView(mScannerView);
                    mScannerView.startCamera();
                } else {
                    Toast.makeText(getApplicationContext(), "Add quantity", Toast.LENGTH_LONG).show();
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

        //--------- manjoor ------
        if (frombillitemvoucherlist) {
            int i = 0;
            for (i = 0; i < arr_barcode.size(); i++) {
                serialNo.add("false");
            }
            int j = 0;
            for (j = 0; j < barcodeArray.length; j++) {
                for (int k = 0; k < arr_barcode.size(); k++) {
                    if (barcodeArray[j].equals(arr_barcode.get(k).toString())) {
                        serialNo.remove(k);
                        serialNo.add(k, "true");
                    }
                }
            }

        } else {
            for (int i = 0; i < arr_barcode.size(); i++) {
                serialNo.add("false");

            }
        }
        //---------------------------

        mSerialNumberLayout.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                appUser = LocalRepositories.getAppUser(getApplicationContext());
                CheckBoxVoucherBarcodeActivity.flag = 0;
                if (batchwise && !serailwise) {
                    serial = "1";
                } else if (!batchwise && serailwise) {
                    if (mQuantity.getText().toString().equals("")) {
                        serial = "0";
                    } else {
                       String  voucher_barcode = mSr_no.getText().toString();
                        String[] arr = voucher_barcode.split(",");
                        int qt = Integer.valueOf(mQuantity.getText().toString());
                        List<String> list;

                        if (qt > 0) {
                            int count = 0;
                            if (arr.length>1){
                                list = new ArrayList<>();
                                list = Helpers.mergeTwoArray(arr,arr_barcode);
                                arr_barcode.addAll(list);
                            }
                            serialNo.clear();
                            for (int i = 0; i < arr_barcode.size(); i++) {
                                serialNo.add("false");
                            }
                            if (qt < arr.length) {
                                String listString = "";
                                appUser.sale_item_serial_arr.clear();
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                for (int j = 0; j < qt; j++) {
                                    for (int k = 0; k < arr_barcode.size(); k++) {
                                        if (arr[j].equals(arr_barcode.get(k).toString())) {
                                            serialNo.remove(k);
                                            serialNo.add(k, "true");
                                            count++;
                                        }
                                    }
                                    listString += arr[j] + ",";
                                    appUser.sale_item_serial_arr.add(arr[j]);
                                }
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                mSr_no.setText(listString);
                            }else {
                                if (!mSr_no.getText().toString().equals("")) {
                                    String listString = "";
                                    appUser.sale_item_serial_arr.clear();
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    for (int j = 0; j < arr.length; j++) {
                                        for (int k = 0; k < arr_barcode.size(); k++) {
                                            if (arr[j].equals(arr_barcode.get(k).toString())) {
                                                serialNo.remove(k);
                                                serialNo.add(k, "true");
                                                count++;
                                            }
                                        }
                                        listString += arr[j] + ",";
                                        appUser.sale_item_serial_arr.add(arr[j]);
                                    }
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    mSr_no.setText(listString);
                                }
                            }

                            CheckBoxVoucherBarcodeActivity.locQuantity = count;
                            serial = mQuantity.getText().toString();
                            System.out.println(CheckBoxVoucherBarcodeActivity.locQuantity);
                            Intent intent = new Intent(getApplicationContext(), CheckBoxVoucherBarcodeActivity.class);
                            intent.putExtra("array_bar_code", arr_barcode);
                            intent.putExtra("serial_number", serialNo);
                            intent.putExtra("quantity", serial);
                            intent.putExtra("fromSale", true);
                            intent.putExtra("tempSaleNo", "1");
                            intent.putExtra("frombillitemvoucherlist", frombillitemvoucherlist);
                            startActivityForResult(intent, 11);
                        }
                    }

                } else {
                    serial = "0";
                }

                mBusinessType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        CheckBoxVoucherBarcodeActivity.locQuantity = 0;
                        appUser.sale_item_serial_arr.clear();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        mSr_no.setText("");
                        serialNo.clear();
                        for (int i = 0; i < arr_barcode.size(); i++) {
                            serialNo.add("false");

                        }

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                  /*  Dialog dialogbal = new Dialog(SaleVoucherAddItemActivity.this);
                    dialogbal.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialogbal.setContentView(R.layout.dialog_serail);
                    dialogbal.setCancelable(true);
                    LinearLayout serialLayout = (LinearLayout) dialogbal.findViewById(R.id.main_layout);
                    LinearLayout submit = (LinearLayout) dialogbal.findViewById(R.id.submit);
                    LinearLayout serial_layout = (LinearLayout) dialogbal.findViewById(R.id.serial_layout);
                    //  FrameLayout scanning_content_frame = (FrameLayout) dialogbal.findViewById(R.id.scanning_content_frame);
                    int width = getWidth();
                    int height = getHeight();
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
                    lp.setMargins(20, 10, 20, 0);
                    Spinner[] pairs = new Spinner[Integer.parseInt(serial)];

                    for (int l = 0; l < Integer.parseInt(serial); l++) {
                        pairs[l] = new Spinner(getApplicationContext(), Spinner.MODE_DROPDOWN*//*,null,android.R.style.Widget_Spinner,Spinner.MODE_DROPDOWN*//*);
                        pairs[l].setLayoutParams(new LinearLayout.LayoutParams(500, 100));
                        spinnerAdapter = new ArrayAdapter<String>(getApplicationContext(),
                                R.layout.layout_trademark_type_spinner_dropdown_item, arr_barcode);
                        spinnerAdapter.setDropDownViewResource(R.layout.layout_trademark_type_spinner_dropdown_item);
                        pairs[l].setAdapter(spinnerAdapter);
                        pairs[l].setLayoutParams(lp);
                        pairs[l].setId(l);
                        serialLayout.addView(pairs[l]);
                    }
                    if (appUser.sale_item_serial_arr.size() > 0) {
                        for (int i = 0; i < Integer.parseInt(serial); i++) {
                            if (appUser.sale_item_serial_arr.size() > i) {
                                String group_type = appUser.sale_item_serial_arr.get(i);
                                int groupindex = -1;
                                for (int j = 0; j < arr_barcode.size(); j++) {
                                    if (arr_barcode.get(j).equals(group_type)) {
                                        groupindex = j;
                                        break;
                                    }
                                }
                                pairs[i].setSelection(groupindex);
                            } else {
                                pairs[i].setSelection(0);
                            }
                        }

                    }

                    // set value in spinner for edit
                    if (frombillitemvoucherlist) {
                        for (int i = 0; i < Integer.parseInt(serial); i++) {
                            for (int j = i; j < arr_barcode.size(); j++) {
                                if (i < barcodeArray.length) {
                                    if ((barcodeArray[i]).equals((arr_barcode.get(j)).toString()) || (barcodeArray[i].trim()).equals((arr_barcode.get(j)).toString().trim())) {
                                        pairs[i].setSelection(j);
                                    }
                                }
                            }
                        }
                    }

           *//*         scan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            serial_layout.setVisibility(View.GONE);
                            mMainLayout.setVisibility(View.GONE);
                            scanning_content_frame.setVisibility(View.VISIBLE);
                            mScannerView.setResultHandler(SaleVoucherAddItemActivity.this);
                            scanning_content_frame.addView(mScannerView);
                            mScannerView.startCamera();
                        }
                    });*//*

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolForBarcode = false;
                            quantity = mQuantity.getText().toString();
                            appUser.sale_item_serial_arr.clear();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                               *//* appUser.sale_item_serial_arr.add("5");
                                LocalRepositories.saveAppUser(getApplicationContext(),appUser);*//*
                            for (int l = 0; l < Integer.parseInt(serial); l++) {
                                if (appUser.sale_item_serial_arr.contains(pairs[l].getSelectedItem().toString())) {
                                    pairs[l].setSelection(0);
                                    appUser.sale_item_serial_arr.add(l, "");
                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);

                                } else {
                                    if (!pairs[l].getSelectedItem().toString().equals("None")) {
                                        appUser.sale_item_serial_arr.add(l, pairs[l].getSelectedItem().toString());
                                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    } else {
                                        appUser.sale_item_serial_arr.add("");
                                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    }
                                }
                            }
                            List<String> arr = new ArrayList<String>();
                            for (int i = 0; i < appUser.sale_item_serial_arr.size(); i++) {
                                if (!appUser.sale_item_serial_arr.get(i).equals("")) {
                                    arr.add(appUser.sale_item_serial_arr.get(i));
                                }
                            }
                            appUser.sale_item_serial_arr.clear();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);

                            for (int i = 0; i < arr.size(); i++) {
                                appUser.sale_item_serial_arr.add(arr.get(i));
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            }
                            String listString = "";
                            for (String s : appUser.sale_item_serial_arr) {
                                listString += s + ",";
                            }

                            mSr_no.setText(listString);
                            dialogbal.dismiss();

                       *//*     if (appUser.sale_item_serial_arr.contains("")) {
                                Toast.makeText(getApplicationContext(), "SAME VALUE", Toast.LENGTH_LONG).show();

                            } else {
                                String listString = "";

                                for (String s : appUser.sale_item_serial_arr) {
                                    listString += s + ",";
                                }

                                mSr_no.setText(listString);
                                dialogbal.dismiss();
                            }*//*
                        }
                    });

                    dialogbal.show();
                    */
            }

        });

        if (!packaging_unit.equals("")) {
            mSpinnerUnit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    if (i == 2) {
                        Timber.i("pcccc 2");
                        sale_unit = packaging_unit;
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
                        Timber.i("pcccc 0");
                        sale_unit = main_unit;
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
                        Timber.i("pcccc 1");
                        sale_unit = alternate_unit;
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
                        Timber.i("pcccc else 0");
                        sale_unit = main_unit;
                        price_selected_unit = "main";
                        if (sales_price_applied_on.equals("Alternate Unit")) {
                            Double main_unit_price = Double.parseDouble(sales_price_alternate) * Double.parseDouble(alternate_unit_con_factor);
                            mRate.setText(String.valueOf(main_unit_price));
                        } else {
                            if (frombillitemvoucherlist) {
                                Timber.i("frombillitemvoucherlist true");
                                int pos = getIntent().getExtras().getInt("pos");
                                Map map = new HashMap<>();
                                map = appUser.mListMapForItemSale.get(pos);
                                String rate = (String) map.get("rate");
                                String discount = (String) map.get("discount");
                                String value = (String) map.get("value");
                                mRate.setText(rate);
                                mDiscount.setText(discount);
                                mValue.setText(value);
                            } else {
                                mRate.setText(sales_price_main);
                            }
                        }
                    } else if (i == 1) {
                        Timber.i("pcccc else 1");
                        sale_unit = alternate_unit;
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
                Double aDouble = 0.0,rate=0.0;
                if (!mQuantity.getText().toString().equals("")) {
                    aDouble = Double.valueOf(mQuantity.getText().toString());
                }
                if (aDouble == 0 | mQuantity.getText().toString().equals("")) {
                    Snackbar.make(coordinatorLayout, "enter minimum 1 quantity!", Snackbar.LENGTH_LONG).show();
                    return;
                }
                if (aDouble != 0 && !mQuantity.getText().toString().equals("")){
                    if (!mSr_no.getText().toString().equals("")){
                        if (CheckBoxVoucherBarcodeActivity.flag==1){
                            Snackbar.make(coordinatorLayout, "Please select serial number!", Snackbar.LENGTH_LONG).show();
                            return;
                        }
                    }
                }
                if (!mRate.getText().toString().equals("")) {
                    rate = Double.valueOf(mRate.getText().toString());
                }
                if (rate == 0 | mRate.getText().toString().equals("")) {
                    Snackbar.make(coordinatorLayout, "enter rate", Snackbar.LENGTH_LONG).show();
                    return;
                }
                // For Barcode
                /*int qt = Integer.valueOf(mQuantity.getText().toString());
                    if (qt < appUser.sale_item_serial_arr.size()) {
                        String voucher_barcode = mSr_no.getText().toString();
                        String[] arr1 = voucher_barcode.split(",");
                        appUser.sale_item_serial_arr.clear();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        for (int j = 0; j < qt; j++) {
                            appUser.sale_item_serial_arr.add(arr1[j]);
                        }
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    }*/
                // End for barcode

                if (CreateSaleActivity.fromsalelist) {
                    CreateSaleActivity.isForEdit = true;
                }

                if (!frombillitemvoucherlist) {
                    quantity = mQuantity.getText().toString();
                }
                mMap.put("id", itemid);
                mMap.put("item_id", id);
                mMap.put("item_name", mItemName.getText().toString());
                mMap.put("description", mDescription.getText().toString());
                mMap.put("quantity", mQuantity.getText().toString());
                mMap.put("unit", mSpinnerUnit.getSelectedItem().toString());
                mMap.put("sr_no", mSr_no.getText().toString());
                mMap.put("rate", mRate.getText().toString());
                mMap.put("discount", mDiscount.getText().toString());
                mMap.put("value", mValue.getText().toString());
                mMap.put("default_unit", default_unit);
                mMap.put("packaging_unit", packaging_unit);
                mMap.put("sales_price_alternate", sales_price_alternate);
                mMap.put("sales_price_main", sales_price_main);
                mMap.put("alternate_unit", alternate_unit);
                mMap.put("packaging_unit_sales_price", packaging_unit_sales_price);
                mMap.put("main_unit", main_unit);
                mMap.put("batch_wise", batchwise);
                mMap.put("serial_wise", serailwise);
                mMap.put("barcode", barcode);
                mMap.put("voucher_barcode", mSr_no.getText().toString());
                mMap.put("sale_unit", sale_unit);
                mMap.put("business_type",mBusinessType.getSelectedItem());
                String taxstring = Preferences.getInstance(getApplicationContext()).getSale_type_name();
                if (taxstring.startsWith("I") || taxstring.startsWith("L")) {
                    String arrtaxstring[] = taxstring.split("-");
                    String taxname = arrtaxstring[0].trim();
                    String taxvalue = arrtaxstring[1].trim();
                    if (taxvalue.equals("ItemWise")) {
                        String total = mTotal.getText().toString();
                        String arr[] = tax.split(" ");
                        String itemtax = arr[1];
                        String taxval[] = itemtax.split("%");
                        String taxpercent = taxval[0];
                        double totalamt = Double.parseDouble(total) * (Double.parseDouble(taxpercent) / 100);
                        totalamt = Double.parseDouble(total) + totalamt;
                        mMap.put("total", String.valueOf(totalamt));
                        mMap.put("itemwiseprice", totalitemprice);

                    } else {
                        mMap.put("total", mTotal.getText().toString());
                    }
                } else {
                    mMap.put("total", mTotal.getText().toString());
                }

                mMap.put("applied", sales_price_applied_on);
                mMap.put("price_selected_unit", price_selected_unit);
                mMap.put("alternate_unit_con_factor", alternate_unit_con_factor);
                mMap.put("packaging_unit_con_factor", packaging_unit_con_factor);
                mMap.put("mrp", mrp);
                mMap.put("tax", tax);
                //mMap.put("serial_number", appUser.sale_item_serial_arr);
                if (boolForBarcode) {
                    mMap.put("serial_number", myListForSerialNo);
                } else {
                    mMap.put("serial_number", appUser.sale_item_serial_arr);
                }
                mMap.put("unit_list", mUnitList);
                // mListMap.add(mMap);

                if (!frombillitemvoucherlist) {
                    appUser.mListMapForItemSale.add(mMap);
                    // appUser.mListMap = mListMap;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                } else {
                    appUser.mListMapForItemSale.remove(finalPos);
                    appUser.mListMapForItemSale.add(finalPos, mMap);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }

                // if (mQuantity.getText().toString().equals(quantity)) {
                Intent in = new Intent(getApplicationContext(), CreateSaleActivity.class);

                in.putExtra("fromdashboard", false);

                in.putExtra("is", true);
                startActivity(in);
                finish();
               /* } else {
                    Toast.makeText(getApplicationContext(), "Please select serial number", Toast.LENGTH_LONG).show();
                }*/
            }
        });

        mRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mDiscount.getText().toString().equals("")) {
                    mDiscount.setText("0.0");
                }

                if (!mDiscount.getText().toString().isEmpty()) {
                    if (!mRate.getText().toString().isEmpty()) {
                        if (!mRate.getText().toString().equals("")) {
                            second = Double.valueOf(mRate.getText().toString());
                        }
                        if (!mDiscount.getText().toString().isEmpty()) {
                            if (!mDiscount.getText().toString().equals("")) {
                                first = Double.valueOf(mDiscount.getText().toString());
                            }
                            mValue.setText(String.format("%.2f", (first * second)));

                        }
                    } else {
                        mValue.setText("0.0");
                        mDiscount.setText("0.0");
                        mTotal.setText("0.0");
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
                    if (!mDiscount.getText().toString().equals("")) {
                        first = Double.valueOf(mDiscount.getText().toString());
                    }
                    if (!mRate.getText().toString().isEmpty()) {
                        if (!mRate.getText().toString().equals("")) {
                            second = Double.valueOf(mRate.getText().toString());
                        }
                        if (!mQuantity.getText().toString().equals("")) {
                            if (!mQuantity.getText().toString().equals("")) {
                                third = Double.valueOf(mQuantity.getText().toString());
                            }
                        } else {
                            third = 0.0;
                        }
                        mValue.setText(String.format("%.2f", ((first * second * third) / 100)));
                    }
                } else {
                    mValue.setText("0.0");
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
                CheckBoxVoucherBarcodeActivity.flag=1;
                if (count == 0) {
                    mTotal.setText(String.format("%.2f", 0.0));
                    mValue.setText("0.0");
                    mDiscount.setText("0.0");
                  /*  mSr_no.setText("");
                    appUser.sale_item_serial_arr.clear();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);*/
                }
                if (!mValue.getText().toString().isEmpty()) {
                    if (!mQuantity.getText().toString().isEmpty()) {
                        if (!mQuantity.getText().toString().equals("")) {
                            second = Double.valueOf(mQuantity.getText().toString());
                        } else {
                            second = 0.00;
                        }
                        if (!mValue.getText().toString().isEmpty()) {
                            first = Double.valueOf(mValue.getText().toString());
                            if (!mRate.getText().toString().equals("")) {
                                third = Double.valueOf(mRate.getText().toString());
                            } else {
                                third = 0.0;
                            }
                            mTotal.setText(String.format("%.2f", ((third - first) * second)));
                        }
                    } else {
                        mTotal.setText("0.0");
                    }
                }
              /*  if (start == 0) {
                    if (!mQuantity.getText().toString().isEmpty()) {
                        if (!mQuantity.getText().toString().equals("")) {
                           *//* voucher_barcode = mSr_no.getText().toString();
                            String[] arr = voucher_barcode.split(",");
                            int qt = Integer.valueOf(mQuantity.getText().toString());
                            if (qt < CheckBoxVoucherBarcodeActivity.locQuantity && qt > 0) {
                                // barcodeArray = new String[0];
                                String listString = "";
                                serialNo.clear();
                                for (int i = 0; i < arr_barcode.size(); i++) {
                                    serialNo.add("false");
                                }
                                appUser.sale_item_serial_arr.clear();
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                for (int j = 0; j < qt; j++) {
                                    for (int k = 0; k < arr_barcode.size(); k++) {
                                        if (arr[j].equals(arr_barcode.get(k).toString())) {
                                            serialNo.remove(k);
                                            serialNo.add(k, "true");
                                        }
                                    }
                                    listString += arr[j] + ",";
                                    appUser.sale_item_serial_arr.add(arr[j]);
                                }
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                mSr_no.setText(listString);
                            }*//*
                        }
                    }
                }*/
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
                        if (!mQuantity.getText().toString().equals("")) {
                            second = Double.valueOf(mQuantity.getText().toString());
                        }
                        if (!mValue.getText().toString().isEmpty()) {
                            if (!mValue.getText().toString().equals("")) {
                                first = Double.valueOf(mValue.getText().toString());
                            }
                            if (!mRate.getText().toString().equals("")) {
                                third = Double.valueOf(mRate.getText().toString());
                            } else {
                                third = 0.0;
                            }
                            if (((third * second) - first) >= 0) {
                                mTotal.setText(String.format("%.2f", ((third * second) - first)));
                            } else {
                                mValue.setText("0.0");
                                //Toast.makeText(getApplicationContext(), "Value can not be more than total price", Toast.LENGTH_LONG).show();
                            }
                        }
                    } else {
                        mTotal.setText("0.0");


                    }
                } else {
                    if (!mRate.getText().toString().equals("")) {
                        third = Double.valueOf(mRate.getText().toString());
                    }
                    if (!mQuantity.getText().toString().equals("")) {
                        second = Double.valueOf(mQuantity.getText().toString());
                    } else {
                        second = 0.0;
                    }
                    mTotal.setText(String.format("%.2f", ((third * second))));
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                //  mDiscount.setText("0.0");
            }
        });

        mRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!mRate.getText().toString().equals("")) {
                        Double aDouble = Double.valueOf(mRate.getText().toString());
                        if (aDouble == 0) {
                            mRate.setText("");
                        }
                    }
                }
            }
        });

        mDiscount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!mDiscount.getText().toString().equals("")) {
                        Double aDouble = Double.valueOf(mDiscount.getText().toString());
                        if (aDouble == 0) {
                            mDiscount.setText("");
                        }
                    }
                }
            }
        });

        mValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!mValue.getText().toString().equals("")) {
                        Double aDouble = Double.valueOf(mValue.getText().toString());
                        if (aDouble == 0) {
                            mValue.setText("");
                        }
                    }
                }
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
        if (CreateSaleActivity.fromsalelist) {
            CreateSaleActivity.isForEdit = true;
        }
        Intent intent = getIntent();
        boolean b = intent.getBooleanExtra("bool", false);
        if (b) {
            Intent intent1 = new Intent(this, CreateSaleActivity.class);
            intent1.putExtra("is", true);
            startActivity(intent1);
            finish();
        } else {
            Intent intent2 = new Intent(this, ExpandableItemListActivity.class);
            FirstPageActivity.posSetting = false;
            FirstPageActivity.posNotifyAdapter = false;
            startActivity(intent2);
            finish();
        }


    }

    private int getWidth() {
        int density = getResources().getDisplayMetrics().densityDpi;
        int size = 800;
        switch (density) {
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

    private int getHeight() {
        int density = getResources().getDisplayMetrics().densityDpi;
        int height = 100;
        switch (density) {
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, ExpandableItemListActivity.class);
                FirstPageActivity.posSetting = false;
                FirstPageActivity.posNotifyAdapter = false;
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
            String listString = "";
            int qty = Integer.parseInt(mQuantity.getText().toString());
            if (qty > appUser.sale_item_serial_arr.size()) {
                if (mBusinessType.getSelectedItem().toString().equals("Mobile Dealer")) {
                    if (result.getContents().length() == 15) {
                        // mScannerView.stopCamera();
                        if (appUser.sale_item_serial_arr.contains(result.getContents())) {
               /* appUser.serial_arr.add("");
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);*/
                            Toast.makeText(SaleVoucherAddItemActivity.this, result.getContents() + "already added", Toast.LENGTH_SHORT).show();
                        } else {
                            appUser.sale_item_serial_arr.add(result.getContents());
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            for (String s : appUser.sale_item_serial_arr) {
                                listString += s + ",";
                            }
                            mSr_no.setText(listString);
                            Toast.makeText(SaleVoucherAddItemActivity.this, result.getContents() + "added successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    if (appUser.sale_item_serial_arr.contains(result.getContents())) {
               /* appUser.serial_arr.add("");
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);*/
                        Toast.makeText(SaleVoucherAddItemActivity.this, result.getContents() + "already added", Toast.LENGTH_SHORT).show();
                    } else {
                        appUser.sale_item_serial_arr.add(result.getContents());
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        for (String s : appUser.sale_item_serial_arr) {
                            listString += s + ",";
                        }
                        mSr_no.setText(listString);
                        Toast.makeText(SaleVoucherAddItemActivity.this, result.getContents() + "added successfully", Toast.LENGTH_SHORT).show();
                    }
                }

                mScannerView.stopCamera();
                scanning_content_frame.removeView(mScannerView);
                mScannerView.setResultHandler(SaleVoucherAddItemActivity.this);
                scanning_content_frame.addView(mScannerView);
                mScannerView.startCamera();


            } else {
                Toast.makeText(SaleVoucherAddItemActivity.this, "Item quantity is less", Toast.LENGTH_SHORT).show();
                mScannerView.stopCamera();
                scanning_content_frame.removeView(mScannerView);
                mMainLayout.setVisibility(View.VISIBLE);
                mScanLayout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        appUser = LocalRepositories.getAppUser(this);
        boolForBarcode = false;
        if (requestCode == 11) {
            if (resultCode == Activity.RESULT_OK) {
                appUser.sale_item_serial_arr.clear();
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                serialNo.clear();
                serialNo.addAll(data.getStringArrayListExtra("serial_number"));
                String listString = "";

                for (int i = 0; i < serialNo.size(); i++) {
                    if (serialNo.get(i).equals("true")) {
                        listString += arr_barcode.get(i) + ",";
                        appUser.sale_item_serial_arr.add(arr_barcode.get(i).toString());
                    }
                }
                LocalRepositories.saveAppUser(this, appUser);
                mSr_no.setText(listString);
            }
        }
    }
}
