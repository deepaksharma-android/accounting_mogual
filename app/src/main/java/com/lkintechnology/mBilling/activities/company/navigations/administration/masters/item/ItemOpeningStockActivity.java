package com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.app.RegisterAbstractActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_request.RequestCheckBarcode;
import com.lkintechnology.mBilling.networks.api_response.checkbarcode.CheckBarcodeResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.Subscribe;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_item_opening_stock);
        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        String listString = "";
        for (String s : appUser.stock_serial_arr) {
            listString += s + ",";
        }
        mSr_no.setText(listString);
       /* if(mSr_no.getText().toString().equals("")){
            mSr_no.setText(".");
        }
        if(mStockQuantity.equals("0")){
            mStockQuantity.setText("");
            mStockPrice.setText("");
            mStockValue.setText("");
            mSr_no.setText(".");
        }*/
        mScannerView = new ZBarScannerView(this);
        mStockQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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

                      /*  if(count==0){
                            mSr_no.setText("");
                        }*/

                //    Preferences.getInstance(getApplicationContext()).setItem_stock_quantity(mStockQuantity.getText().toString());
//                    appUser.stock_serial_arr.clear();
                 /*   appUser.stock_item_serail_arr.clear();
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                    mSr_no.setText("");*/

                if (!mStockQuantity.getText().toString().isEmpty()) {
                    stockquantity = Double.valueOf(mStockQuantity.getText().toString());
                    if (!mStockPrice.getText().toString().isEmpty()) {
                        stockprice = Double.valueOf(mStockPrice.getText().toString());
                        mStockValue.setText("" + (stockquantity * stockprice));
                    }
                } else {
                    mStockValue.setText("");
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
                            mStockValue.setText("" + (stockquantity * stockprice));
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
                                    if (mSerialNumber.getText().toString().length() >= 15 && mSerialNumber.getText().toString().length() <= 20) {
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
                        Dialog dialogbal = new Dialog(ItemOpeningStockActivity.this);
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
                            pairs[l].setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });
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
                                for (int i = 0; i < Integer.parseInt(serial); i++) {
                                    if (pairs[i].getText().toString().length()>=15 && pairs[i].getText().toString().length()<=20){
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
                                        if(pairs[i].getText().toString().equals("")) {
                                            isbool = true;
                                        }else {
                                            isbool = false;
                                            Toast.makeText(ItemOpeningStockActivity.this, pairs[i].getText().toString() + " is not a IMEI number", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                }
                                if (isbool){
                                    dialogbal.dismiss();
                                }
                            }
                        });
                        dialogbal.show();

                    }
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
      /*          if (stock == false && appUser.stock_serial_arr.size() > 0) {
                    Toast.makeText(getApplicationContext(), "Please select serial number", Toast.LENGTH_LONG).show();
                } else {
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
                    finish();
                }*/
                appUser.barcode_voucher_type = "item";
                appUser.item_id = appUser.edit_item_id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Boolean isConnected = ConnectivityReceiver.isConnected();
                if (stock == false && appUser.stock_serial_arr.size() > 0) {
                    Toast.makeText(getApplicationContext(), "Please select serial number", Toast.LENGTH_LONG).show();
                } else {
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
                        finish();
                    }
                }
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

    private int getWidth() {
        int density = getResources().getDisplayMetrics().densityDpi;
        int size = 0;
        switch (density) {
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

    private int getHeight() {
        int density = getResources().getDisplayMetrics().densityDpi;
        int height = 150;
        switch (density) {
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
    public void handleResult(Result result) {
        if (!result.getContents().equals("")) {
            String listString = "";
            int qty = Integer.parseInt(mStockQuantity.getText().toString());
            if (qty > appUser.stock_serial_arr.size()) {
                if (result.getContents().length() >= 15 && result.getContents().length() <= 20)  {
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
            finish();
        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
}