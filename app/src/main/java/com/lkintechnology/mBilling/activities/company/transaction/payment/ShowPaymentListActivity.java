package com.lkintechnology.mBilling.activities.company.transaction.payment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.transaction.debitnotewoitem.CreateDebitNoteItemActivity;
import com.lkintechnology.mBilling.adapters.DebitNoteItemDetailAdapter;
import com.lkintechnology.mBilling.adapters.PaymentShowListAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

public class ShowPaymentListActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llSelectItemList;
    private ListView itemList;
    private String amount,spGoodsKey;

    AppUser appUser;
    private PaymentShowListAdapter paymentShowListAdapter;
    private LinearLayout ll_submit;
    String state;
    private Spinner reason;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_payment_list);
        appUser= LocalRepositories.getAppUser(this);
        initView();
        initActionBarSet();
        initialPageSetup();
        llSelectItemList.setOnClickListener(this);
        ll_submit.setOnClickListener(this);

        // get position and amount from debit note
        amount=getIntent().getStringExtra("amount");
        spGoodsKey=getIntent().getStringExtra("sp_position");
        state=getIntent().getStringExtra("state");

    }

    private void initialPageSetup() {
        paymentShowListAdapter = new PaymentShowListAdapter(this, appUser.mListMapForItemPaymentList);
        itemList.setAdapter(paymentShowListAdapter);
        // notifyAll();
    }

    private void initActionBarSet() {
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
        actionbarTitle.setText("Add Payment List Item");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
    // define id here
    private void initView() {
        llSelectItemList= (LinearLayout) findViewById(R.id.ll_select_item);
        itemList= (ListView) findViewById(R.id.listViewItems);
        ll_submit= (LinearLayout) findViewById(R.id.tv_submit);
        reason= (Spinner) findViewById(R.id.reason);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_select_item:
                /*if (spGoodsKey!=null){
                    if (spGoodsKey.equals("1")){
                        Intent intent=new Intent(this,CreatePaymentListActivity.class);
                        intent.putExtra("Diff_amount",amount);
                        intent.putExtra("gst_nature_position",spGoodsKey);
                        startActivity(intent);
                        finish();
                    }
                }else {
                    Intent intent = new Intent(this, CreatePaymentListActivity.class);
                    intent.putExtra("amount", amount);
                    intent.putExtra("sp_position", spGoodsKey);
                    intent.putExtra("state", state);
                    startActivity(intent);
                    finish();
                }*/
                Intent intent = new Intent(this, CreatePaymentListActivity.class);
                intent.putExtra("amount", amount);
                intent.putExtra("sp_position", spGoodsKey);
                intent.putExtra("state", state);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_submit:
                appUser.debitreason=reason.getSelectedItem().toString();
                LocalRepositories.saveAppUser(this,appUser);
                finish();
                break;
        }
    }


   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            if (resultCode==RESULT_OK){
                paymentShowListAdapter.notifyDataSetChanged();
            }
        }

    }*/
}
