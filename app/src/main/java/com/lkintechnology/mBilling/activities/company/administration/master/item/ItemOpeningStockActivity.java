package com.lkintechnology.mBilling.activities.company.administration.master.item;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemOpeningStockActivity extends AppCompatActivity {
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
    String batchwise,serailwise;
    String serial;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_opening_stock);
        ButterKnife.bind(this);
        initActionbar();
        appUser= LocalRepositories.getAppUser(this);
        String listString = "";

        mStockQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(count==0){
                    appUser.stock_serial_arr.clear();
                    LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                    mSr_no.setText("");
                }
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
        if(!Preferences.getInstance(getApplicationContext()).getitem_serial_number_wise_detail().equals("")) {
            serailwise = Preferences.getInstance(getApplicationContext()).getitem_serial_number_wise_detail();
        }
        if(!Preferences.getInstance(getApplicationContext()).getitem_batch_wise_detail().equals("")) {
            batchwise =Preferences.getInstance(getApplicationContext()).getitem_batch_wise_detail();
        }
        if(batchwise!=null||serailwise!=null) {
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
                            pairs[l].setInputType(InputType.TYPE_CLASS_NUMBER);
                            pairs[l].setWidth(width);
                            pairs[l].setHeight(height);
                            pairs[l].setBackgroundResource(R.drawable.grey_stroke_rect);
                            pairs[l].setTextSize(18);
                            if (appUser.stock_serial_arr.size() > 0) {
                                pairs[l].setText(appUser.stock_serial_arr.get(l));
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
                                Preferences.getInstance(getApplicationContext()).setSerial("");
                                appUser.stock_serial_arr.clear();
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                for (int i = 0; i < Integer.parseInt(serial); i++) {
                                    if (appUser.stock_serial_arr.contains(pairs[i].getText().toString())) {
                                        pairs[i].setText("");
                                        appUser.stock_serial_arr.add(i, "");
                                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    } else {
                                        appUser.stock_serial_arr.add(i, pairs[i].getText().toString());
                                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                    }
                                }
                                Preferences.getInstance(getApplicationContext()).setStockSerial("");
                                appUser.stock_item_serail_arr.clear();
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                for (int i = 0; i < appUser.stock_serial_arr.size(); i++) {
                                    if (!appUser.stock_serial_arr.get(i).equals("")) {
                                        appUser.stock_item_serail_arr.add(appUser.stock_serial_arr.get(i));
                                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);



                                    }
                                }


                                String listString = "";

                                for (String s : appUser.stock_item_serail_arr) {
                                    listString += s + ",";
                                }
                                Preferences.getInstance(getApplication()).setStockSerial(listString);
                                mSr_no.setText(listString);
                                dialogbal.dismiss();
                            }
                        });
                        dialogbal.show();

                    }
                }

            });
        }
        else{
            Toast.makeText(getApplicationContext(),"Select batchwise or serial",Toast.LENGTH_LONG).show();
        }
        /*Preferences.getInstance(getApplicationContext()).getItem_stock_quantity();
         mStockPrice.setText(Preferences.getInstance(getApplicationContext()).getItem_stock_amount());
          mStockValue.setText(Preferences.getInstance(getApplicationContext()).getItem_stock_value());
         */

        if (! Preferences.getInstance(getApplicationContext()).getItem_stock_quantity().equals("")) {
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
                Preferences.getInstance(getApplicationContext()).setItem_stock_quantity(mStockQuantity.getText().toString());
                Preferences.getInstance(getApplicationContext()).setItem_stock_amount(mStockPrice.getText().toString());
                if (!mStockPrice.getText().toString().isEmpty() && !mStockQuantity.getText().toString().isEmpty()) {
                Preferences.getInstance(getApplicationContext()).setItem_stock_value(String.valueOf(Double.valueOf(mStockQuantity.getText().toString()) * Double.valueOf(mStockPrice.getText().toString())));
                }
                else{
                    Preferences.getInstance(getApplicationContext()).setItem_stock_value("");
                }

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
        actionbarTitle.setText("OPENING STOCK");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
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
}