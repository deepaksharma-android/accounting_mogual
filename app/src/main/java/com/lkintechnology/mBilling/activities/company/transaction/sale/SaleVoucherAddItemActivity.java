package com.lkintechnology.mBilling.activities.company.transaction.sale;

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
import com.lkintechnology.mBilling.activities.company.administration.master.item.ExpandableItemListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

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

public class SaleVoucherAddItemActivity extends AppCompatActivity implements ZBarScannerView.ResultHandler {

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
    String barcode;
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
    ArrayList arr_barcode;
    ArrayList arr_new_barcode;
    private ZBarScannerView mScannerView;
    @Bind(R.id.mainLayout)
    LinearLayout mMainLayout;
    @Bind(R.id.add_item)
    LinearLayout mAddItem;
    @Bind(R.id.scan_item)
    LinearLayout mScanItem;
    @Bind(R.id.scanLayout)
    RelativeLayout mScanLayout;
    @Bind(R.id.scanning_content_frame)
    FrameLayout scanning_content_frame;
    @Bind(R.id.cancel)
    ImageView mCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_voucher_add_item);
        appUser = LocalRepositories.getAppUser(this);
        frombillitemvoucherlist = getIntent().getExtras().getBoolean("frombillitemvoucherlist");
        ButterKnife.bind(this);
        initActionbar();
        mListMap = new ArrayList<>();
        mScannerView = new ZBarScannerView(this);
        mMap = new HashMap<>();
        mUnitList = new ArrayList<>();
        int pos = -1;
        appUser.sale_item_serial_arr.clear();
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
        blinkOnClick = AnimationUtils.loadAnimation(this, R.anim.blink_on_click);

        if (frombillitemvoucherlist) {
            pos = getIntent().getExtras().getInt("pos");
            Map map = new HashMap<>();
            map = appUser.mListMapForItemSale.get(pos);
            String iid = (String) map.get("id");
            String itemName = (String) map.get("item_name");
            String description = (String) map.get("description");
            String quantity = (String) map.get("quantity");
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
            List<String> myList = new ArrayList<String>(Arrays.asList(unit_list.split(",")));
            for (int i = 0; i < myList.size(); i++) {
                mUnitList.add(myList.get(i));
            }
            Timber.i("UNIT_LIST" + myList);
            barcode = (String) map.get("barcode");
            arr_barcode = new ArrayList();
            arr_new_barcode = new ArrayList<String>(Arrays.asList(barcode.split(";")));
            arr_barcode.add(0, "None");
            for (int i = 0; i < arr_new_barcode.size(); i++) {
                arr_barcode.add(i + 1, arr_new_barcode.get(i));
                LocalRepositories.saveAppUser(this, appUser);
            }

       /*     mUnitAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, mUnitList);
            mUnitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerUnit.setAdapter(mUnitAdapter);*/
            id = iid;
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
            arr_barcode = new ArrayList();
            arr_new_barcode = new ArrayList<String>(Arrays.asList(barcode.split(";")));
            arr_barcode.add(0, "None");
            for (int i = 0; i < arr_new_barcode.size(); i++) {
                arr_barcode.add(i + 1, arr_new_barcode.get(i));
                LocalRepositories.saveAppUser(this, appUser);
            }
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
                                String listString = "";
                                int qty = Integer.parseInt(mQuantity.getText().toString());
                                if (qty > appUser.sale_item_serial_arr.size()) {
                                    // mScannerView.stopCamera();
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
                                        mSerialNumber.setText("");
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), " Quantity is less.", Toast.LENGTH_LONG).show();
                                    dialogbal.dismiss();
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Enter Serial number", Toast.LENGTH_LONG).show();
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
                    LinearLayout serial_layout = (LinearLayout) dialogbal.findViewById(R.id.serial_layout);
                    //  FrameLayout scanning_content_frame = (FrameLayout) dialogbal.findViewById(R.id.scanning_content_frame);
                    int width = getWidth();
                    int height = getHeight();
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
                    lp.setMargins(20, 10, 20, 0);
                    Spinner[] pairs = new Spinner[Integer.parseInt(serial)];

                    for (int l = 0; l < Integer.parseInt(serial); l++) {
                        pairs[l] = new Spinner(getApplicationContext(), Spinner.MODE_DROPDOWN/*,null,android.R.style.Widget_Spinner,Spinner.MODE_DROPDOWN*/);
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

           /*         scan.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            serial_layout.setVisibility(View.GONE);
                            mMainLayout.setVisibility(View.GONE);
                            scanning_content_frame.setVisibility(View.VISIBLE);
                            mScannerView.setResultHandler(SaleVoucherAddItemActivity.this);
                            scanning_content_frame.addView(mScannerView);
                            mScannerView.startCamera();
                        }
                    });*/

                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            appUser.sale_item_serial_arr.clear();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                               /* appUser.sale_item_serial_arr.add("5");
                                LocalRepositories.saveAppUser(getApplicationContext(),appUser);*/
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

                       /*     if (appUser.sale_item_serial_arr.contains("")) {
                                Toast.makeText(getApplicationContext(), "SAME VALUE", Toast.LENGTH_LONG).show();

                            } else {
                                String listString = "";

                                for (String s : appUser.sale_item_serial_arr) {
                                    listString += s + ",";
                                }

                                mSr_no.setText(listString);
                                dialogbal.dismiss();
                            }*/
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
                        sale_unit = main_unit;
                        price_selected_unit = "main";
                        if (sales_price_applied_on.equals("Alternate Unit")) {
                            Double main_unit_price = Double.parseDouble(sales_price_alternate) * Double.parseDouble(alternate_unit_con_factor);
                            mRate.setText(String.valueOf(main_unit_price));
                        } else {
                            mRate.setText(sales_price_main);
                        }
                    } else if (i == 1) {
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
                if (mQuantity.getText().toString().equals("0") | mQuantity.getText().toString().equals("")) {
                    Snackbar.make(coordinatorLayout, "enter minimum 1 quantity", Snackbar.LENGTH_LONG).show();
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
                mMap.put("sale_unit", sale_unit);

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
                mMap.put("serial_number", appUser.sale_item_serial_arr);
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
                if (count == 0) {
                    mSr_no.setText("");
                    appUser.sale_item_serial_arr.clear();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
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
                /*    if(count==0){
                        mValue.setText("0.0");
                    }*/
                if (!mValue.getText().toString().isEmpty()) {
                    if (!mQuantity.getText().toString().isEmpty()) {
                        second = Double.valueOf(mQuantity.getText().toString());
                        if (!mValue.getText().toString().isEmpty()) {
                            first = Double.valueOf(mValue.getText().toString());
                            third = Double.valueOf(mRate.getText().toString());
                            if (((third * second) - first) >= 0) {
                                mTotal.setText("" + ((third * second) - first));
                            } else {
                                mValue.setText("0.0");
                                Toast.makeText(getApplicationContext(), "Value can not be more than total price", Toast.LENGTH_LONG).show();
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
}
