package com.berylsystems.buzz.activities.company.administration.master.item;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

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
    Spinner mPurchasePriceApplied;
    AppUser appUser;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_price_info);
        ButterKnife.bind(this);
        appUser= LocalRepositories.getAppUser(this);



    }
}