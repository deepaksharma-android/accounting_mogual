package com.lkintechnology.mBilling.activities.company.transaction.debitnotewoitem;

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
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.transaction.debitnotewoitem.AddDebitNoteItemActivity;
import com.lkintechnology.mBilling.adapters.DebitNoteItemDetailAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

public class DebitNoteItemDetailActivity extends AppCompatActivity implements View.OnClickListener {
  private LinearLayout llSelectItemList;
  private ListView itemList;
   private String amount,spGoodsKey;
    AppUser appUser;
    private DebitNoteItemDetailAdapter debitNoteItemDetailAdapter;
    private LinearLayout ll_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debit_note_item_detail);
        appUser= LocalRepositories.getAppUser(this);
        initView();
        initActionBarSet();
        initialPageSetup();
        llSelectItemList.setOnClickListener(this);
        ll_submit.setOnClickListener(this);
        amount=getIntent().getStringExtra("amount");
        spGoodsKey=getIntent().getStringExtra("fromsp2");
    }

    private void initialPageSetup() {
        debitNoteItemDetailAdapter = new DebitNoteItemDetailAdapter(this, appUser.mListMapForItemDebitNote);
        itemList.setAdapter(debitNoteItemDetailAdapter);
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
        actionbarTitle.setText("Add Debit Note Item");
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_select_item:
                Intent intent=new Intent(this,AddDebitNoteItemActivity.class);
                intent.putExtra("amount",amount);
                intent.putExtra("spKey",spGoodsKey);
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_submit:
                finish();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
               if (requestCode==1){
                   if (resultCode==RESULT_OK){
                       debitNoteItemDetailAdapter.notifyDataSetChanged();
                   }
               }

    }
}