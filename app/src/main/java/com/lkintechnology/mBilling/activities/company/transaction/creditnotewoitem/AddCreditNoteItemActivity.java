package com.lkintechnology.mBilling.activities.company.transaction.creditnotewoitem;

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
import com.lkintechnology.mBilling.adapters.CreditNoteItemDetailAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import timber.log.Timber;

public class AddCreditNoteItemActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llSelectItem;
    private ListView listItem;
    AppUser appUser;
   private String amount,position,state,positionJournalVoucher;
    private CreditNoteItemDetailAdapter creditNoteItemDetailAdapter;
    private LinearLayout ll_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_note_item_detail);
        appUser = LocalRepositories.getAppUser(this);
        initView();
        initActionBarSetup();
        initialpageSetup();
        llSelectItem.setOnClickListener(this);
        ll_submit.setOnClickListener(this);

        amount=getIntent().getStringExtra("amount");
        position=getIntent().getStringExtra("sp_position");
        positionJournalVoucher=getIntent().getStringExtra("gst_pos6");
        state=getIntent().getStringExtra("state");
        Timber.i("mystate"+state);

        Timber.i("state"+appUser.company_state);

    }
  //  to setup action bar layout
    private void initActionBarSetup() {
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
        actionbarTitle.setText("Add Credit Note Item");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private void initialpageSetup() {
        creditNoteItemDetailAdapter= new CreditNoteItemDetailAdapter(this, appUser.mListMapForItemCreditNote);
        listItem.setAdapter(creditNoteItemDetailAdapter);
      //  notifyAll();

    }
  //  to defined id
    private void initView() {
        llSelectItem= (LinearLayout) findViewById(R.id.ll_select_item);
        listItem= (ListView) findViewById(R.id.listViewItems);
        ll_submit= (LinearLayout) findViewById(R.id.tv_submit);
    }

        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_select_item:
                if (positionJournalVoucher.equals("6")){
                    Intent intent=new Intent(this,CreateCreditNoteItemActivity.class);
                    intent.putExtra("gst_position6",positionJournalVoucher);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(this,CreateCreditNoteItemActivity.class);
                    intent.putExtra("amount",amount);
                    intent.putExtra("sp_position",position);
                    intent.putExtra("state",state);
                    startActivity(intent);
                    finish();
                }

                break;
            case R.id.tv_submit:
                finish();
                break;
        }

        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2){
            if (resultCode==RESULT_OK){
                creditNoteItemDetailAdapter.notifyDataSetChanged();
            }
        }
    }
}

