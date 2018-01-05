package com.berylsystems.buzz.activities.company.administration.master.item;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;
import com.berylsystems.buzz.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemOpeningStockActivity extends AppCompatActivity {
    @Bind(R.id.stock_quantity)
    EditText mStockQuantity;
    @Bind(R.id.stock_price)
    EditText mStockPrice;
    @Bind(R.id.stock_value)
    TextView mStockValue;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    AppUser appUser;
    Double stockquantity, stockprice;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_opening_stock);
        ButterKnife.bind(this);
        initActionbar();
        appUser= LocalRepositories.getAppUser(this);
        mStockQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
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
}