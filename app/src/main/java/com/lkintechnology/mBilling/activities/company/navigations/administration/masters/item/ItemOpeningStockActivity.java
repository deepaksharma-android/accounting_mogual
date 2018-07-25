package com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.checkbarcode.CheckBarcodeResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

import java.text.Format;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zbar.Result;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class ItemOpeningStockActivity extends RegisterAbstractActivity implements ZBarScannerView.ResultHandler {

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.stock_quantity)
    EditText mStockQuantity;
    @Bind(R.id.stock_price)
    EditText mStockPrice;
    @Bind(R.id.stock_value)
    TextView mStockValue;
    @Bind(R.id.serail_number_layout)
    LinearLayout mSerialNumberLayout;
    @Bind(R.id.sr_no)
    TextView mSr_no;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    @Bind(R.id.business_type)
    Spinner mBusinessType;
    AppUser appUser;
    Double stockquantity, stockprice;
    String batchwise, serailwise;
    String serial;
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
    Boolean stock = true;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    int i = 0;
    public static int flag = 0,flag1 = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_item_opening_stock);
        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        String listString = "";

       /* if(mSr_no.getText().toString().equals("")){
            mSr_no.setText(".");
        }
        if(mStockQuantity.equals("0")){
            mStockQuantity.setText("");
            mStockPrice.setText("");
            mStockValue.setText("");
            mSr_no.setText(".");
        }*/
        if (Preferences.getInstance(getApplicationContext()).getBusiness_type() != null) {
            if (Preferences.getInstance(getApplicationContext()).getBusiness_type().equals("Mobile Dealer") ||
                    Preferences.getInstance(getApplicationContext()).getBusiness_type().equals("")) {
                mBusinessType.setSelection(0);
            } else {
                mBusinessType.setSelection(1);
            }
        } else {
            mBusinessType.setSelection(0);
        }

        for (String s : appUser.stock_serial_arr) {
            listString += s + ",";
        }
        mSr_no.setText(listString);

        mScannerView = new ZBarScannerView(this);
        mStockQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (CreateNewItemActivity.boolForFlag){
                    flag = 1;
                }else {
                    flag = 0;
                }
                CreateNewItemActivity.boolForFlag =true;
                if (CreateNewItemActivity.isForEdit) {
                    if (i == 0) {
                        appUser.quantity = Integer.valueOf(mStockQuantity.getText().toString());
                        LocalRepositories.saveAppUser(getApplication(), appUser);
                        i++;
                    }
                    if (!mStockQuantity.getText().toString().equals("") && !mStockQuantity.getText().toString().isEmpty()) {
                        if (appUser.quantity != Integer.valueOf(mStockQuantity.getText().toString())) {
                            stock = false;
                        }
                    }
                } else {
                    if (!mStockQuantity.getText().toString().equals("") && !mStockQuantity.getText().toString().isEmpty()) {
                        if (appUser.quantity != Integer.valueOf(mStockQuantity.getText().toString())) {
                            stock = false;
                        }
                    }
                }

                if (!mStockQuantity.getText().toString().isEmpty()) {
                    stockquantity = Double.valueOf(mStockQuantity.getText().toString());
                    if (!mStockPrice.getText().toString().isEmpty()) {
                        stockprice = Double.valueOf(mStockPrice.getText().toString());
                        mStockValue.setText("" + (stockquantity * stockprice));
                    }
                } else {
                    mStockValue.setText("");
                    mSr_no.setText("");
                    mStockPrice.setText("");
                    mBusinessType.setSelection(0);
                    Preferences.getInstance(getApplicationContext()).setBusiness_type(mBusinessType.getSelectedItem().toString());
                    Preferences.getInstance(getApplicationContext()).setItem_stock_quantity("0");
                    Preferences.getInstance(getApplicationContext()).setItem_stock_amount("0");
                    Preferences.getInstance(getApplicationContext()).setStockSerial("");
                    Preferences.getInstance(getApplicationContext()).setItem_stock_value("0");
                    appUser.stock_serial_arr.clear();
                    appUser.stock_item_serail_arr.clear();
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                // Preferences.getInstance(getApplicationContext()).setItem_stock_quantity(mStockQuantity.getText().toString());
            }
        });

        mStockPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mStockQuantity.getText().toString().isEmpty()) {
                    if (!mStockPrice.getText().toString().isEmpty()) {
                        stockprice = Double.valueOf(mStockPrice.getText().toString());
                        if (!mStockQuantity.getText().toString().isEmpty()) {
                            stockquantity = Double.valueOf(mStockQuantity.getText().toString());
                            Double price = stockprice*stockquantity;
                            mStockValue.setText(String.format("%.2f",price,10));
                        }
                    } else {
                        mStockValue.setText("");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mStockQuantity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!mStockQuantity.getText().toString().equals("")) {
                        Double aDouble = Double.valueOf(mStockQuantity.getText().toString());
                        if (aDouble == 0) {
                            mStockQuantity.setText("");
                        }
                    }
                }
            }
        });

        mStockPrice.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (!mStockPrice.getText().toString().equals("")) {
                        Double aDouble = Double.valueOf(mStockPrice.getText().toString());
                        if (aDouble == 0) {
                            mStockPrice.setText("");
                        }
                    }
                }
            }
        });

        if (!Preferences.getInstance(getApplicationContext()).getitem_serial_number_wise_detail().equals("")) {
            serailwise = Preferences.getInstance(getApplicationContext()).getitem_serial_number_wise_detail();
        }
        if (!Preferences.getInstance(getApplicationContext()).getitem_batch_wise_detail().equals("")) {
            batchwise = Preferences.getInstance(getApplicationContext()).getitem_batch_wise_detail();
        }
        mAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mStockQuantity.getText().toString().equals("0")) {
                    Dialog dialogbal = new Dialog(ItemOpeningStockActivity.this);
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
                                int qty = Integer.parseInt(mStockQuantity.getText().toString());
                                if (qty > appUser.stock_serial_arr.size()) {
                                    // mScannerView.stopCamera();
                                    if (mBusinessType.getSelectedItem().toString().equals("Mobile Dealer")) {
                                        if (mSerialNumber.getText().toString().length() == 15) {
                                            if (appUser.stock_serial_arr.contains(mSerialNumber.getText().toString())) {
               /* appUser.serial_arr.add("");
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);*/
//                                        Toast.makeText(ItemOpeningStockActivity.this, mSerialNumber.getText().toString() + "already added", Toast.LENGTH_SHORT).show();
                                            } else {
                                                appUser.stock_serial_arr.add(mSerialNumber.getText().toString());
                                                appUser.stock_item_serail_arr.add(mSerialNumber.getText().toString());
                                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                                for (String s : appUser.stock_item_serail_arr) {
                                                    listString += s + ",";
                                                }
                                                Preferences.getInstance(getApplication()).setStockSerial(listString);
                                                mSr_no.setText(listString);
                                                mSerialNumber.setText("");
                                            }
                                        } else {
                                            Toast.makeText(getApplicationContext(), mSerialNumber.getText().toString() + " is not a IMEI number", Toast.LENGTH_LONG).show();
                                        }
                                    } else {
                                        if (appUser.stock_serial_arr.contains(mSerialNumber.getText().toString())) {
               /* appUser.serial_arr.add("");
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);*/
//                                        Toast.makeText(ItemOpeningStockActivity.this, mSerialNumber.getText().toString() + "already added", Toast.LENGTH_SHORT).show();
                                        } else {
                                            appUser.stock_serial_arr.add(mSerialNumber.getText().toString());
                                            appUser.stock_item_serail_arr.add(mSerialNumber.getText().toString());
                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                            for (String s : appUser.stock_item_serail_arr) {
                                                listString += s + ",";
                                            }
                                            Preferences.getInstance(getApplication()).setStockSerial(listString);
                                            mSr_no.setText(listString);
                                            mSerialNumber.setText("");
                                        }
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), " Quantity is less.", Toast.LENGTH_LONG).show();
                                    dialogbal.dismiss();
                                }

                            } else {
                                dialogbal.dismiss();
                                // Toast.makeText(getApplicationContext(), "Enter Serial number", Toast.LENGTH_LONG).show();
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
                if (!mStockQuantity.getText().toString().equals("0")) {
                    mMainLayout.setVisibility(View.GONE);
                    mScanLayout.setVisibility(View.VISIBLE);
                    mScannerView.setResultHandler(ItemOpeningStockActivity.this);
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
        if (batchwise != null || serailwise != null) {
            mSerialNumberLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Preferences.getInstance(getApplicationContext()).setStockSerial("");
                    flag=0;
                    if (batchwise.equals("Yes") && serailwise.equals("No")) {
                        serial = "1";
                    } else if (batchwise.equals("No") && serailwise.equals("Yes")) {
                        if (mStockQuantity.getText().toString().equals("")) {
                            serial = "0";
                        } else {
                            serial = mStockQuantity.getText().toString();
                        }

                    } else {
                        serial = "0";
                    }
                    if (!serial.equals("0")) {
                        String serialnumber = mSr_no.getText().toString();
                        String[] arr = serialnumber.split(",");
                        int qt = Integer.valueOf(mStockQuantity.getText().toString());
                        appUser.stock_serial_arr.clear();
                        appUser.stock_item_serail_arr.clear();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        String listString = "";
                        if (qt < arr.length ) {
                            for (int i = 0; i < qt; i++) {
                                listString += arr[i] + ",";
                                appUser.stock_serial_arr.add(arr[i]);
                                appUser.stock_item_serail_arr.add(arr[i]);
                            }
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            mSr_no.setText(listString);
                            Preferences.getInstance(getApplicationContext()).setStockSerial(listString);
                        }else {
                            for (int i = 0; i < arr.length; i++) {
                                listString += arr[i] + ",";
                                appUser.stock_serial_arr.add(arr[i]);
                                appUser.stock_item_serail_arr.add(arr[i]);
                            }
                            Preferences.getInstance(getApplicationContext()).setStockSerial(listString);
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        }
                        int pos;
                        if (mBusinessType.getSelectedItem().toString().equals("Mobile Dealer")) {
                            pos = 0;
                        } else {
                            pos = 1;
                        }
                        appUser.quantity = Integer.valueOf(mStockQuantity.getText().toString());
                        Preferences.getInstance(getApplicationContext()).setItem_stock_quantity(mStockQuantity.getText().toString());
                        Intent intent = new Intent(getApplicationContext(), ItemBarcodeActivity.class);
                        stock = true;
                        intent.putExtra("serial", serial);
                        intent.putExtra("businessType", pos);
                        startActivityForResult(intent, 1);

                    }

                    mBusinessType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            appUser.stock_serial_arr.clear();
                            appUser.stock_item_serail_arr.clear();
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            Preferences.getInstance(getApplicationContext()).setStockSerial("");
                            mSr_no.setText("");

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                    //PPPPPPCCCCCCCCCCCCCCCCCCCCCCCCCC
                      /*  Dialog dialogbal = new Dialog(ItemOpeningStockActivity.this);
                        dialogbal.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                        dialogbal.setContentView(R.layout.dialog_serail);
                        dialogbal.setCancelable(true);
                        LinearLayout serialLayout = (LinearLayout) dialogbal.findViewById(R.id.main_layout);
                        LinearLayout submit = (LinearLayout) dialogbal.findViewById(R.id.submit);
                        int width = getWidth();
                        int height = getHeight();
                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
                        lp.setMargins(20, 10, 20, 0);
                        EditText[] pairs = new EditText[Integer.parseInt(serial)];
                        for (int l = 0; l < Integer.parseInt(serial); l++) {
                            pairs[l] = new EditText(getApplicationContext());
                            pairs[l].setPadding(20, 10, 10, 0);
                            pairs[l].setWidth(width);
                            pairs[l].setHeight(height);
                            pairs[l].setBackgroundResource(R.drawable.grey_stroke_rect);
                            pairs[l].setTextSize(18);
                            pairs[l].setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                            if (appUser.stock_serial_arr.size() > 0) {
                                if (appUser.stock_serial_arr.size() > l) {
                                    pairs[l].setText(appUser.stock_serial_arr.get(l));
                                } else {
                                    pairs[l].setText("");
                                }
                            }
                            pairs[l].setHint("Enter Serial Number" + " " + (l + 1));
                            pairs[l].setHintTextColor(Color.GRAY);
                            pairs[l].setTextColor(Color.BLACK);
                            pairs[l].setLayoutParams(lp);
                            pairs[l].setId(l);
                            //pairs[l].setText((l + 1) + ": something");
                            serialLayout.addView(pairs[l]);*/

                    //PPPPPPCCCCCCCCCCCCCCCCCCCCCCCCCC

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
                                }*/

                    //PPPPCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC

                        /*submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //  appUser.stock_serial_arr.add("3");
                                stock = true;
                                appUser.quantity = Integer.valueOf(mStockQuantity.getText().toString());
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                Preferences.getInstance(getApplicationContext()).setSerial("");
                                appUser.stock_serial_arr.clear();
                                Preferences.getInstance(getApplicationContext()).setItem_stock_quantity(mStockQuantity.getText().toString());
                                // appUser.stock_item_serail_arr.clear();
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                boolean isbool = false;
                                int count = 0;
                                for (int i = 0; i < Integer.parseInt(serial); i++) {
                                    if (mBusinessType.getSelectedItem().toString().equals("Mobile Dealer")) {
                                        if (pairs[i].getText().toString().length() == 15) {
                                            if (appUser.stock_serial_arr.contains(pairs[i].getText().toString())) {
                                                pairs[i].setText("");
                                                appUser.stock_serial_arr.add(i, "");
                                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                                // Toast.makeText(ItemOpeningStockActivity.this, pairs[i].getText().toString() + "already added", Toast.LENGTH_SHORT).show();
                                            } else {

                                                if (!pairs[i].getText().toString().equals("")) {
                                                    if ((appUser.stock_serial_arr.size() - 1) == i) {
                                                        appUser.stock_serial_arr.set(i, pairs[i].getText().toString());
                                                    } else {
                                                        appUser.stock_serial_arr.add(pairs[i].getText().toString());
                                                    }

                                                    //  appUser.purchase_item_serail_arr.add(i,appUser.serial_arr.get(i));
                                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                                } else {
                                                    appUser.stock_serial_arr.add(i, "");
                                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                                    //  appUser.purchase_item_serail_arr.add(i,appUser.serial_arr.get(i));

                                                }
                                            }
                                            Preferences.getInstance(getApplicationContext()).setStockSerial("");
                                            appUser.stock_item_serail_arr.clear();
                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                            for (int j = 0; j < appUser.stock_serial_arr.size(); j++) {
                                                if (!appUser.stock_serial_arr.get(j).equals("")) {
                                                    appUser.stock_item_serail_arr.add(appUser.stock_serial_arr.get(j));
                                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                                }
                                            }

                                            appUser.stock_serial_arr.clear();
                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                            for (int k = 0; k < appUser.stock_item_serail_arr.size(); k++) {
                                                appUser.stock_serial_arr.add(appUser.stock_item_serail_arr.get(k));
                                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                            }


                                            String listString = "";

                                            for (String s : appUser.stock_item_serail_arr) {
                                                listString += s + ",";
                                            }
                                            Preferences.getInstance(getApplication()).setStockSerial(listString);
                                            mSr_no.setText(listString);
                                            isbool = true;
                                        } else {
                                            if (pairs[i].getText().toString().equals("")) {
                                                isbool = true;
                                            } else {
                                                isbool = false;
                                                Toast.makeText(ItemOpeningStockActivity.this, pairs[i].getText().toString() + " is not a IMEI number", Toast.LENGTH_SHORT).show();
                                                return;
                                            }
                                        }
                                    } else {
                                        if (pairs[i].getText().toString().length() > 0) {
                                            if (appUser.stock_serial_arr.contains(pairs[i].getText().toString())) {
                                                pairs[i].setText("");
                                                appUser.stock_serial_arr.add(i, "");
                                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                                // Toast.makeText(ItemOpeningStockActivity.this, pairs[i].getText().toString() + "already added", Toast.LENGTH_SHORT).show();
                                            } else {

                                                if (!pairs[i].getText().toString().equals("")) {
                                                    if ((appUser.stock_serial_arr.size() - 1) == i) {
                                                        appUser.stock_serial_arr.set(i, pairs[i].getText().toString());
                                                    } else {
                                                        appUser.stock_serial_arr.add(pairs[i].getText().toString());
                                                    }

                                                    //  appUser.purchase_item_serail_arr.add(i,appUser.serial_arr.get(i));
                                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                                } else {
                                                    appUser.stock_serial_arr.add(i, "");
                                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                                    //  appUser.purchase_item_serail_arr.add(i,appUser.serial_arr.get(i));

                                                }
                                            }
                                            Preferences.getInstance(getApplicationContext()).setStockSerial("");
                                            appUser.stock_item_serail_arr.clear();
                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                            for (int j = 0; j < appUser.stock_serial_arr.size(); j++) {
                                                if (!appUser.stock_serial_arr.get(j).equals("")) {
                                                    appUser.stock_item_serail_arr.add(appUser.stock_serial_arr.get(j));
                                                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                                }
                                            }

                                            appUser.stock_serial_arr.clear();
                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                            for (int k = 0; k < appUser.stock_item_serail_arr.size(); k++) {
                                                appUser.stock_serial_arr.add(appUser.stock_item_serail_arr.get(k));
                                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                            }


                                            String listString = "";

                                            for (String s : appUser.stock_item_serail_arr) {
                                                listString += s + ",";
                                            }
                                            Preferences.getInstance(getApplication()).setStockSerial(listString);
                                            mSr_no.setText(listString);
                                            isbool = true;
                                        }else {
                                            count++;
                                        }
                                    }
                                }
                                if (Integer.parseInt(serial)==count){
                                    isbool = true;
                                    mSr_no.setText("");
                                    appUser.stock_item_serail_arr.clear();
                                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                                }
                                if (isbool) {
                                    dialogbal.dismiss();
                                }
                            }
                        });
                        dialogbal.show();

                    }*/
                }

            });
        } else {
            Toast.makeText(getApplicationContext(), "Select batchwise or serial", Toast.LENGTH_LONG).show();
        }
        /*Preferences.getInstance(getApplicationContext()).getItem_stock_quantity();
         mStockPrice.setText(Preferences.getInstance(getApplicationContext()).getItem_stock_amount());
          mStockValue.setText(Preferences.getInstance(getApplicationContext()).getItem_stock_value());
         */

        if (!Preferences.getInstance(getApplicationContext()).getItem_stock_quantity().equals("")) {
            stock = true;
            mStockQuantity.setText(Preferences.getInstance(getApplicationContext()).getItem_stock_quantity());
        }
        if (!Preferences.getInstance(getApplicationContext()).getItem_stock_amount().equals("")) {
            mStockPrice.setText(Preferences.getInstance(getApplicationContext()).getItem_stock_amount());
        }
        if (!Preferences.getInstance(getApplicationContext()).getItem_stock_value().equals("")) {
            mStockValue.setText(Preferences.getInstance(getApplicationContext()).getItem_stock_value());
        }


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double aDouble=0.0;
                if (!mStockQuantity.getText().toString().equals("")){
                    aDouble = Double.valueOf(mStockQuantity.getText().toString());
                }
               /* if (aDouble==0 | mStockQuantity.getText().toString().equals("")) {
                    Snackbar.make(coordinatorLayout, "enter minimum 1 quantity", Snackbar.LENGTH_LONG).show();
                    return;
                }*/
                if (aDouble != 0 && !mStockQuantity.getText().toString().equals("")){
                    if (!mSr_no.getText().toString().equals("")){
                        if (flag==1){
                            Snackbar.make(coordinatorLayout, "Please select serial number!", Snackbar.LENGTH_LONG).show();
                            return;
                        }
                    }
                }
                appUser.barcode_voucher_type = "item";
                appUser.item_id = appUser.edit_item_id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Boolean isConnected = ConnectivityReceiver.isConnected();
                Preferences.getInstance(getApplicationContext()).setBusiness_type("");
               /* if (stock == false && appUser.stock_serial_arr.size() > 0) {
                    Toast.makeText(getApplicationContext(), "Please select serial number", Toast.LENGTH_LONG).show();
                } else {*/
                if (CreateNewItemActivity.isForEdit) {
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(ItemOpeningStockActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_CHECK_BARCODE);
                    } else {
                        snackbar = Snackbar
                                .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Boolean isConnected = ConnectivityReceiver.isConnected();
                                        if (isConnected) {
                                            snackbar.dismiss();
                                        }
                                    }
                                });
                        snackbar.show();

                    }
                } else {
                    if (!mStockQuantity.getText().toString().equals("")) {
                        if (Integer.parseInt(mStockQuantity.getText().toString()) > 0) {
                            if (!mStockPrice.getText().toString().equals("")) {
                                if (Double.parseDouble(mStockPrice.getText().toString()) > 0.0) {
                                    Preferences.getInstance(getApplicationContext()).setBusiness_type(mBusinessType.getSelectedItem().toString());
                                    Preferences.getInstance(getApplicationContext()).setItem_stock_amount(mStockPrice.getText().toString());
                                    Preferences.getInstance(getApplicationContext()).setItem_stock_quantity(mStockQuantity.getText().toString());
                                    Preferences.getInstance(getApplicationContext()).setItem_stock_value(String.valueOf(Double.valueOf(mStockQuantity.getText().toString()) * Double.valueOf(mStockPrice.getText().toString())));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Stock price must be greater than 0", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Please enter stock price", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (Integer.parseInt(mStockQuantity.getText().toString()) < 0) {
                                Toast.makeText(getApplicationContext(), "Quantity must be positive ", Toast.LENGTH_LONG).show();
                            } else {
                                Preferences.getInstance(getApplicationContext()).setBusiness_type(mBusinessType.getSelectedItem().toString());
                                Preferences.getInstance(getApplicationContext()).setItem_stock_quantity("0");
                                Preferences.getInstance(getApplicationContext()).setItem_stock_amount("0");
                                Preferences.getInstance(getApplicationContext()).setItem_stock_value("0");
                                finish();
                            }
                        }
                    } else {
                        Preferences.getInstance(getApplicationContext()).setBusiness_type(mBusinessType.getSelectedItem().toString());
                        Preferences.getInstance(getApplicationContext()).setItem_stock_quantity("0");
                        Preferences.getInstance(getApplicationContext()).setItem_stock_amount("0");
                        Preferences.getInstance(getApplicationContext()).setItem_stock_value("0");
                        finish();
                    }
                }
                // }
            }
        });


    }

    @Override
    protected int layoutId() {
        return R.layout.activity_item_opening_stock;
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
        actionbarTitle.setText("OPENING STOCK");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public void handleResult(Result result) {
        if (!result.getContents().equals("")) {
            String listString = "";
            int qty = Integer.parseInt(mStockQuantity.getText().toString());
            if (qty > appUser.stock_serial_arr.size()) {
                if (result.getContents().length() == 15) {
                    if (mBusinessType.getSelectedItem().toString().equals("Mobile Dealer")) {
                        // mScannerView.stopCamera();
                        if (appUser.stock_serial_arr.contains(result.getContents())) {
               /* appUser.serial_arr.add("");
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);*/
                            Toast.makeText(ItemOpeningStockActivity.this, result.getContents() + "already added", Toast.LENGTH_SHORT).show();
                        } else {
                            appUser.stock_serial_arr.add(result.getContents());
                            appUser.stock_item_serail_arr.add(result.getContents());
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            for (String s : appUser.stock_item_serail_arr) {
                                listString += s + ",";
                            }
                            Preferences.getInstance(getApplication()).setStockSerial(listString);
                            mSr_no.setText(listString);
                        }
                    } else {
                        if (appUser.stock_serial_arr.contains(result.getContents())) {
               /* appUser.serial_arr.add("");
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);*/
                            Toast.makeText(ItemOpeningStockActivity.this, result.getContents() + "already added", Toast.LENGTH_SHORT).show();
                        } else {
                            appUser.stock_serial_arr.add(result.getContents());
                            appUser.stock_item_serail_arr.add(result.getContents());
                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            for (String s : appUser.stock_item_serail_arr) {
                                listString += s + ",";
                            }
                            Preferences.getInstance(getApplication()).setStockSerial(listString);
                            mSr_no.setText(listString);
                        }
                    }
                }
                mScannerView.stopCamera();
                scanning_content_frame.removeView(mScannerView);
                mScannerView.setResultHandler(ItemOpeningStockActivity.this);
                scanning_content_frame.addView(mScannerView);
                mScannerView.startCamera();


            } else {
                Toast.makeText(ItemOpeningStockActivity.this, "Item quantity is less", Toast.LENGTH_SHORT).show();
                mScannerView.stopCamera();
                scanning_content_frame.removeView(mScannerView);
                mMainLayout.setVisibility(View.VISIBLE);
                mScanLayout.setVisibility(View.GONE);
            }
        }

    }

    @Subscribe
    public void checkbarcode(CheckBarcodeResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            if (!mStockQuantity.getText().toString().equals("")) {
                Preferences.getInstance(getApplicationContext()).setItem_stock_quantity(mStockQuantity.getText().toString());
            } else {
                Preferences.getInstance(getApplicationContext()).setItem_stock_quantity("0");
            }
            if (!mStockPrice.getText().toString().equals("")) {
                Preferences.getInstance(getApplicationContext()).setItem_stock_amount(mStockPrice.getText().toString());
            } else {
                Preferences.getInstance(getApplicationContext()).setItem_stock_amount("0");
            }
            if (!mStockPrice.getText().toString().isEmpty() && !mStockQuantity.getText().toString().isEmpty()) {
                Preferences.getInstance(getApplicationContext()).setItem_stock_value(String.valueOf(Double.valueOf(mStockQuantity.getText().toString()) * Double.valueOf(mStockPrice.getText().toString())));
            } else {
                Preferences.getInstance(getApplicationContext()).setItem_stock_value("0");
            }
            Preferences.getInstance(getApplicationContext()).setBusiness_type(mBusinessType.getSelectedItem().toString());
            finish();
        } else {
            // snackbar = Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            //snackbar.show();
            Helpers.dialogMessage(this, response.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                mSr_no.setText("");
                appUser = LocalRepositories.getAppUser(this);
                //appUser.stock_item_serail_arr.clear();
                String listString = data.getStringExtra("serial");
                mSr_no.setText(listString);
            }
        }
    }
}