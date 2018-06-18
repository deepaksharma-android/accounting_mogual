package com.lkintechnology.mBilling.activities.company.transaction.barcode;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.adapters.SerialNoListAdapter;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class CheckBoxVoucherBarcodeActivity extends AppCompatActivity {

    /* @Bind(R.id.serialNo)
     RecyclerView serialNo;*/
    /*@Bind(R.id.submit)
    LinearLayout mSubmit;*/
    public static LinearLayout mSubmit;
    private ArrayList arrayList;
    private ArrayList serialNoList, serialNoPurchaseReturn;
    public static TextView barcodeMessage;
    public static RecyclerView serialNo;
    private SerialNoListAdapter serialNoListAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private String barcode, quantity, tempSaleNo;
    private boolean fromPurchaseReturn, fromSale, frombillitemvoucherlist;
    public static int locQuantity, flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serial_no_list);
        ButterKnife.bind(this);
        barcodeMessage = (TextView) findViewById(R.id.barcode_message);
        mSubmit = (LinearLayout) findViewById(R.id.submit);
        serialNo = (RecyclerView) findViewById(R.id.serialNo);
        arrayList = new ArrayList();
        initActionbar();
        arrayList = getIntent().getStringArrayListExtra("array_bar_code");
        serialNoList = getIntent().getStringArrayListExtra("serial_number");
        quantity = getIntent().getStringExtra("quantity");
        fromPurchaseReturn = getIntent().getBooleanExtra("fromPurchaseReturn", false);
        frombillitemvoucherlist = getIntent().getBooleanExtra("frombillitemvoucherlist", false);
        fromSale = getIntent().getBooleanExtra("fromSale", false);
        if (fromSale == true) {
            tempSaleNo = getIntent().getStringExtra("tempSaleNo");
        }

        if (fromPurchaseReturn == true) {
            serialNoPurchaseReturn = getIntent().getStringArrayListExtra("serial_number_purchase_return");
        }
        initView();


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromPurchaseReturn == true) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("serial_number_purchase_return", serialNoPurchaseReturn);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                } else {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("serial_number", serialNoList);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });
    }

    private void initView() {
        if (fromPurchaseReturn == true) {
            layoutManager = new LinearLayoutManager(getApplicationContext());
            serialNo.setLayoutManager(layoutManager);
            serialNoListAdapter = new SerialNoListAdapter(this, arrayList, serialNoPurchaseReturn, quantity, fromPurchaseReturn);
            serialNo.setAdapter(serialNoListAdapter);
        } else {
            layoutManager = new LinearLayoutManager(getApplicationContext());
            serialNo.setLayoutManager(layoutManager);
            serialNoListAdapter = new SerialNoListAdapter(this, arrayList, serialNoList, quantity, frombillitemvoucherlist, tempSaleNo);
            serialNo.setAdapter(serialNoListAdapter);
        }
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
        if (fromSale == true) {
            actionbarTitle.setText("Sale Voucher");
        } else if (fromPurchaseReturn == true) {
            actionbarTitle.setText("Purchase Return");
        }
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
               /* Intent intent = new Intent(this, SaleVoucherAddItemActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);*/
                finish();
                // return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
