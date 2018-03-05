package com.lkintechnology.mBilling.activities.company.transaction.receiptvoucher;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateReceiptItemActivity extends AppCompatActivity {
    @Bind(R.id.reference_number)
    EditText mReferenceNumber;
    @Bind(R.id.transaction_amount)
    TextView mTransactionAmount;
    @Bind(R.id.tax_rate)
    EditText mTaxRate;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    Map mMap;
    AppUser appUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_receipt_item);
        ButterKnife.bind(this);
        initActionbar();
        appUser=LocalRepositories.getAppUser(this);
        mTransactionAmount.setText(getIntent().getExtras().getString("amount"));
        mMap = new HashMap<>();
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String total=String.format("%.2f",((((Double.parseDouble(mTransactionAmount.getText().toString()))*(Double.parseDouble(mTaxRate.getText().toString())))/100)+(Double.parseDouble(mTransactionAmount.getText().toString()))));
                mMap.put("ref_num", mReferenceNumber.getText().toString());
                mMap.put("amount", mTransactionAmount.getText().toString());
                mMap.put("taxrate", mTaxRate.getText().toString());
                mMap.put("total",total);
                appUser.mListMapForItemReceipt.add(mMap);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent in = new Intent(getApplicationContext(), AddReceiptItemActivity.class);
                in.putExtra("amount",mTransactionAmount.getText().toString());
                startActivity(in);
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
        actionbarTitle.setText("CREATE RECEIPT ITEMS");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
}