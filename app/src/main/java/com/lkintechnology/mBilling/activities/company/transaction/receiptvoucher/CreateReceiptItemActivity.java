package com.lkintechnology.mBilling.activities.company.transaction.receiptvoucher;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    @Bind(R.id.total_amount)
    TextView mTotalAmount;
    @Bind(R.id.transaction_amount)
    TextView mTransactionAmount;
    @Bind(R.id.tax_rate)
    EditText mTaxRate;
    @Bind(R.id.igst_layout)
    LinearLayout mIgstLayout;
    @Bind(R.id.sgst_layout)
    LinearLayout mSgstLayout;
    @Bind(R.id.cgst_layout)
    LinearLayout mCgstLayout;
    @Bind(R.id.tv_igst)
    EditText tvIGST;
    @Bind(R.id.tv_cgst)
    EditText tvCGST;
    @Bind(R.id.tv_sgst)
    EditText tvSgst;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    Map mMap;
    AppUser appUser;
    public Boolean fromreceipt;
    public String itempos;
    public String voucher_type="";
    public String voucher_id="";
    public String state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_receipt_item);
        ButterKnife.bind(this);
        initActionbar();
        appUser=LocalRepositories.getAppUser(this);
        fromreceipt=getIntent().getExtras().getBoolean("fromreceipt");
        state=getIntent().getStringExtra("state");
        mTransactionAmount.setText(getIntent().getExtras().getString("amount"));
        mMap = new HashMap<>();
        mTaxRate.setText("0.0");
        tvIGST.setText("0.0");
        tvCGST.setText("0.0");
        tvSgst.setText("0.0");
        mTotalAmount.setText("0.0");
        if(fromreceipt){
            itempos=getIntent().getExtras().getString("pos");
            Map map=appUser.mListMapForItemReceipt.get(Integer.parseInt(itempos));
            mReferenceNumber.setText((String)map.get("ref_num"));
            voucher_id=(String)map.get("voucher_id");
            voucher_type=(String)map.get("voucher_type");
            mTransactionAmount.setText((String)map.get("amount"));
            mTaxRate.setText((String)map.get("taxrate"));
            mTotalAmount.setText((String)map.get("total"));
            double percentage = ((Double.parseDouble(mTransactionAmount.getText().toString()) * Double.parseDouble(mTaxRate.getText().toString())) / 100);
            double halfPer = percentage / 2.0;
            tvSgst.setText(String.valueOf(halfPer));
            tvCGST.setText(String.valueOf(halfPer));
            tvIGST.setText(String.valueOf(percentage));
        }

        if (state!=null && appUser.company_state!=null){
            if(state.equals(appUser.company_state)){
                mIgstLayout.setVisibility(View.GONE);
                mCgstLayout.setVisibility(View.VISIBLE);
                mSgstLayout.setVisibility(View.VISIBLE);
            }
            else{
                mIgstLayout.setVisibility(View.VISIBLE);
                mCgstLayout.setVisibility(View.GONE);
                mSgstLayout.setVisibility(View.GONE);
            }
        }else {
            mIgstLayout.setVisibility(View.VISIBLE);
            mCgstLayout.setVisibility(View.GONE);
            mSgstLayout.setVisibility(View.GONE);
        }


        mTaxRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int count) {
                if(count==0){
                    tvIGST.setText("0.0");
                    tvCGST.setText("0.0");
                    tvSgst.setText("0.0");
                    mTotalAmount.setText("0.0");
                }
                if (s.length() > 0) {
                    Double amount = 0.0,rate=0.0;
                    if (!mTransactionAmount.getText().toString().equals("")){
                        amount = Double.parseDouble(mTransactionAmount.getText().toString());
                    }
                    if (!mTaxRate.getText().toString().equals("")){
                        rate = Double.parseDouble(mTaxRate.getText().toString());
                    }
                    double percentage = ((amount * rate) / 100);
                    double halfPer = percentage / 2.0;
                    tvSgst.setText(String.valueOf(halfPer));
                    tvCGST.setText(String.valueOf(halfPer));
                    tvIGST.setText(String.valueOf(percentage));
                    mTotalAmount.setText(String.format("%.2f",(amount-percentage)));
                }else if (s.length()<=0){
                    tvSgst.setText("");
                    tvCGST.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mTransactionAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2==0){
                    //etDiffAmount.setText("0.0");
                    mTaxRate.setText("0.0");
                    tvIGST.setText("0.0");
                    tvCGST.setText("0.0");
                    tvSgst.setText("0.0");
                    mTotalAmount.setText("0.0");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !mReferenceNumber.getText().toString().equals("")){
                        if(!mTaxRate.getText().toString().equals("")){
                            mMap.put("voucher_id", voucher_id);
                            mMap.put("voucher_type", voucher_type);
                            mMap.put("amount", mTransactionAmount.getText().toString());
                            mMap.put("taxrate", mTaxRate.getText().toString());
                            mMap.put("total",mTotalAmount.getText().toString());
                            mMap.put("ref_num",mReferenceNumber.getText().toString());
                            if (state!=null && appUser.company_state!=null) {
                                if (state.equals(appUser.company_state)) {
                                    mMap.put("cgst", tvCGST.getText().toString());
                                    mMap.put("sgst", tvSgst.getText().toString());
                                    mMap.put("igst", "");
                                } else {
                                    mMap.put("igst", tvIGST.getText().toString());
                                }
                            }else {
                                mMap.put("igst", tvIGST.getText().toString());
                            }
                            if (!fromreceipt) {
                                appUser.mListMapForItemReceipt.add(mMap);
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            } else {
                                appUser.mListMapForItemReceipt.remove(Integer.parseInt(itempos));
                                appUser.mListMapForItemReceipt.add(Integer.parseInt(itempos), mMap);
                                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                            }
                            Intent in = new Intent(getApplicationContext(), AddReceiptItemActivity.class);
                            in.putExtra("amount",mTransactionAmount.getText().toString());
                            in.putExtra("state",state);
                            startActivity(in);
                            finish();
                        }
                    else{
                        Toast.makeText(getApplicationContext(),"Please enter the tax rate",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please enter the reference number",Toast.LENGTH_LONG).show();
                }

            }
        });

        mTransactionAmount.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (!mTransactionAmount.getText().toString().equals("")){
                        Double aDouble = Double.valueOf(mTransactionAmount.getText().toString());
                        if (aDouble==0){
                            mTransactionAmount.setText("");
                        }
                    }
                }
            }
        });

        mTaxRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (!mTaxRate.getText().toString().equals("")){
                        Double aDouble = Double.valueOf(mTaxRate.getText().toString());
                        if (aDouble==0){
                            mTaxRate.setText("");
                        }
                    }
                }
            }
        });

        tvIGST.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (!tvIGST.getText().toString().equals("")){
                        Double aDouble = Double.valueOf(tvIGST.getText().toString());
                        if (aDouble==0){
                            tvIGST.setText("");
                        }
                    }
                }
            }
        });

        tvCGST.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (!tvCGST.getText().toString().equals("")){
                        Double aDouble = Double.valueOf(tvCGST.getText().toString());
                        if (aDouble==0){
                            tvCGST.setText("");
                        }
                    }
                }
            }
        });

        tvSgst.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (!tvSgst.getText().toString().equals("")){
                        Double aDouble = Double.valueOf(tvSgst.getText().toString());
                        if (aDouble==0){
                            tvSgst.setText("");
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
        actionbarTitle.setText("CREATE RECEIPT ITEMS");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
}