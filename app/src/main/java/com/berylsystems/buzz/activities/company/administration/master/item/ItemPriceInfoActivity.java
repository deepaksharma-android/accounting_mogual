package com.berylsystems.buzz.activities.company.administration.master.item;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;
import com.berylsystems.buzz.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemPriceInfoActivity extends AppCompatActivity {
    @Bind(R.id.sales_price_edittext)
    EditText sales_price_edittext;
    @Bind(R.id.sale_price_alt_unit_edittext)
    EditText sale_price_alt_unit_edittext;
    @Bind(R.id.min_sale_price_main_edittext)
    EditText min_sale_price_main_edittext;
    @Bind(R.id.min_sale_price_alt_edittext)
    EditText min_sale_price_alt_edittext;
    @Bind(R.id.purchase_price_main_edittext)
    EditText purchase_price_min_edittext;
    @Bind(R.id.purchase_price_alt_edittext)
    EditText purchase_price_alt_edittext;
    @Bind(R.id.mrp)
    EditText mrp;
    @Bind(R.id.self_val_price)
    EditText self_val_price;
    @Bind(R.id.sales_price_main_unit_text)
    TextView sales_price_main_unit_text;
    @Bind(R.id.sale_price_alt_unit_text)
    TextView sale_price_alt_unit_text;
    @Bind(R.id.min_sale_price_main_text)
    TextView min_sale_price_main_text;
    @Bind(R.id.min_sale_price_alt_text)
    TextView min_sale_price_alt_text;
    @Bind(R.id.purchase_price_main_text)
    TextView purchase_price_min_text;
    @Bind(R.id.purchase_price_alt_text)
    TextView purchase_price_alt_text;
    @Bind(R.id.sale_price_main_layout)
    LinearLayout sale_price_main_layout;
    @Bind(R.id.sale_price_alt_layout)
    LinearLayout sale_price_alt_layout;
    @Bind(R.id.min_sale_price_main_layout)
    LinearLayout min_sale_price_main_layout;
    @Bind(R.id.min_sale_price_alt_layout)
    LinearLayout min_sale_price_alt_layout;
    @Bind(R.id.purchase_price_main_layout)
    LinearLayout purchase_price_main_layout;
    @Bind(R.id.purchase_price_alt_layout)
    LinearLayout purchase_price_alt_layout;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    @Bind(R.id.spinner_sale_price_applied)
    Spinner mSpinnerSalePriceApplied;
    @Bind(R.id.spinner_purchase_price_applied_on)
    Spinner mSpinnerPurchasePriceApplied;
    AppUser appUser;
    String item_unit;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_price_info);
        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        item_unit = getIntent().getStringExtra("unit");
        sales_price_main_unit_text.setText("Sales Price Main Unit (" + item_unit + ")");
        sale_price_alt_unit_text.setText("Sales Price Alt. Unit (" + Preferences.getInstance(getApplicationContext()).getitem_alternate_unit_name() + ")");
        min_sale_price_main_text.setText("Min Sales Price Main Unit (" + item_unit + ")");
        min_sale_price_alt_text.setText("Min Sales Price Alt. Unit (" + Preferences.getInstance(getApplicationContext()).getitem_alternate_unit_name() + ")");
        purchase_price_min_text.setText("Purchase Price Main Unit (" + item_unit + ")");
        purchase_price_alt_text.setText("Purchase Price Alt. Unit (" + Preferences.getInstance(getApplicationContext()).getitem_alternate_unit_name() + ")");
        if (!Preferences.getInstance(getApplicationContext()).getitem_price_mrp().equals("")){
            mrp.setText(Preferences.getInstance(getApplicationContext()).getitem_price_mrp());
        }

        if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_self_val_price().equals("")){
            self_val_price.setText(Preferences.getInstance(getApplicationContext()).getitem_price_info_self_val_price());
        }

        if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_sale_price_applied_on().equals("")) {
            if (Preferences.getInstance(getApplicationContext()).getitem_price_info_sale_price_applied_on().equals("Main Unit")) {
                mSpinnerSalePriceApplied.setSelection(0);
                if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_sales_price_edittext().equals("")) {
                    sales_price_edittext.setText(Preferences.getInstance(getApplicationContext()).getitem_price_info_sales_price_edittext());
                }
                if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_min_sale_price_main_edittext().equals("")) {
                    min_sale_price_main_edittext.setText(Preferences.getInstance(getApplicationContext()).getitem_price_info_min_sale_price_main_edittext());
                }
            } else if (Preferences.getInstance(getApplicationContext()).getitem_price_info_sale_price_applied_on().equals("Alternate Unit")) {
                mSpinnerSalePriceApplied.setSelection(1);
                if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_sale_price_alt_unit_edittext().equals("")) {
                    sale_price_alt_unit_edittext.setText(Preferences.getInstance(getApplicationContext()).getitem_price_info_sale_price_alt_unit_edittext());
                }
                if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_min_sale_price_alt_edittext().equals("")) {
                    min_sale_price_alt_edittext.setText(Preferences.getInstance(getApplicationContext()).getitem_price_info_min_sale_price_alt_edittext());
                }
            } else {
                mSpinnerSalePriceApplied.setSelection(2);
                if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_sales_price_edittext().equals("")) {
                    sales_price_edittext.setText(Preferences.getInstance(getApplicationContext()).getitem_price_info_sales_price_edittext());
                }
                if (Preferences.getInstance(getApplicationContext()).getitem_price_info_min_sale_price_main_edittext().equals("")) {
                    min_sale_price_main_edittext.setText(Preferences.getInstance(getApplicationContext()).getitem_price_info_min_sale_price_main_edittext());
                }
                if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_sale_price_alt_unit_edittext().equals("")) {
                    sale_price_alt_unit_edittext.setText(Preferences.getInstance(getApplicationContext()).getitem_price_info_sale_price_alt_unit_edittext());
                }
                if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_min_sale_price_alt_edittext().equals("")) {
                    min_sale_price_alt_edittext.setText(Preferences.getInstance(getApplicationContext()).getitem_price_info_min_sale_price_alt_edittext());
                }
            }
        }

        if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_purchase_price_applied_on().equals("")) {

            if (Preferences.getInstance(getApplicationContext()).getitem_price_info_purchase_price_applied_on().equals("Main Unit")) {
                mSpinnerPurchasePriceApplied.setSelection(0);
                if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_purchase_price_min_edittext().equals("")) {
                    purchase_price_min_edittext.setText(Preferences.getInstance(getApplicationContext()).getitem_price_info_purchase_price_min_edittext());
                }

            } else if (Preferences.getInstance(getApplicationContext()).getitem_price_info_purchase_price_applied_on().equals("Alternate Unit")) {
                mSpinnerPurchasePriceApplied.setSelection(1);
                if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_purchase_price_alt_edittext().equals("")) {
                    purchase_price_alt_edittext.setText(Preferences.getInstance(getApplicationContext()).getitem_price_info_purchase_price_alt_edittext());
                }
            } else {
                mSpinnerPurchasePriceApplied.setSelection(2);
                if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_purchase_price_min_edittext().equals("")) {
                    purchase_price_min_edittext.setText(Preferences.getInstance(getApplicationContext()).getitem_price_info_purchase_price_min_edittext());
                }
                if (!Preferences.getInstance(getApplicationContext()).getitem_price_info_purchase_price_alt_edittext().equals("")) {
                    purchase_price_alt_edittext.setText(Preferences.getInstance(getApplicationContext()).getitem_price_info_purchase_price_alt_edittext());
                }
            }
        }


        mSpinnerSalePriceApplied.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    min_sale_price_alt_layout.setVisibility(View.GONE);
                    sale_price_alt_layout.setVisibility(View.GONE);
                    sale_price_main_layout.setVisibility(View.VISIBLE);
                    min_sale_price_main_layout.setVisibility(View.VISIBLE);
                } else if (i == 1) {
                    sale_price_main_layout.setVisibility(View.GONE);
                    min_sale_price_main_layout.setVisibility(View.GONE);
                    min_sale_price_alt_layout.setVisibility(View.VISIBLE);
                    sale_price_alt_layout.setVisibility(View.VISIBLE);
                } else {
                    sale_price_main_layout.setVisibility(View.VISIBLE);
                    min_sale_price_main_layout.setVisibility(View.VISIBLE);
                    min_sale_price_alt_layout.setVisibility(View.VISIBLE);
                    sale_price_alt_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerPurchasePriceApplied.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    purchase_price_alt_layout.setVisibility(View.GONE);
                    purchase_price_main_layout.setVisibility(View.VISIBLE);
                } else if (i == 1) {
                    purchase_price_main_layout.setVisibility(View.GONE);
                    purchase_price_alt_layout.setVisibility(View.VISIBLE);
                } else {
                    purchase_price_main_layout.setVisibility(View.VISIBLE);
                    purchase_price_alt_layout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.getInstance(getApplicationContext()).setitem_price_info_sale_price_applied_on( mSpinnerSalePriceApplied.getSelectedItem().toString());
                Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_applied_on( mSpinnerPurchasePriceApplied.getSelectedItem().toString());
                Preferences.getInstance(getApplicationContext()).setitem_price_mrp(mrp.getText().toString());
                Preferences.getInstance(getApplicationContext()).setitem_price_info_self_val_price(self_val_price.getText().toString());

                if (mSpinnerSalePriceApplied.getSelectedItem().toString().equals("Main Unit")) {
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_sales_price_edittext(sales_price_edittext.getText().toString());
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_sale_price_alt_unit_edittext("");
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_min_sale_price_main_edittext(min_sale_price_main_edittext.getText().toString());
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_min_sale_price_alt_edittext("");

                } else if (mSpinnerSalePriceApplied.getSelectedItem().toString().equals("Alternate Unit")) {
                    appUser.item_price_info_sales_price_edittext = "";
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_sales_price_edittext("");
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_sale_price_alt_unit_edittext(sale_price_alt_unit_edittext.getText().toString());
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_min_sale_price_main_edittext("");
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_min_sale_price_alt_edittext(min_sale_price_alt_edittext.getText().toString());

                } else {
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_sales_price_edittext(sales_price_edittext.getText().toString());
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_min_sale_price_main_edittext(min_sale_price_main_edittext.getText().toString());
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_sale_price_alt_unit_edittext(sale_price_alt_unit_edittext.getText().toString());
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_min_sale_price_alt_edittext(min_sale_price_alt_edittext.getText().toString());

                }

                if (mSpinnerPurchasePriceApplied.getSelectedItem().toString().equals("Main Unit")) {
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_min_edittext(purchase_price_min_edittext.getText().toString());
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_alt_edittext("");

                } else if (mSpinnerPurchasePriceApplied.getSelectedItem().toString().equals("Alternate Unit")) {
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_min_edittext("");
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_alt_edittext(purchase_price_alt_edittext.getText().toString());

                } else {
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_min_edittext("");
                    Preferences.getInstance(getApplicationContext()).setitem_price_info_purchase_price_alt_edittext(purchase_price_alt_edittext.getText().toString());
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009DE0")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("PRICE INFO");
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
}