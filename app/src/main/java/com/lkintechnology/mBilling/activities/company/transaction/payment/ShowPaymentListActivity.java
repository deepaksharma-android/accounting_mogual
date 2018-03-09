package com.lkintechnology.mBilling.activities.company.transaction.payment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.transaction.creditnotewoitem.CreateCreditNoteItemActivity;
import com.lkintechnology.mBilling.activities.company.transaction.debitnotewoitem.CreateDebitNoteItemActivity;
import com.lkintechnology.mBilling.activities.company.transaction.journalvoucher.CreateJournalItemActivity;
import com.lkintechnology.mBilling.adapters.AddJournalVoucherItemAdapter;
import com.lkintechnology.mBilling.adapters.DebitNoteItemDetailAdapter;
import com.lkintechnology.mBilling.adapters.PaymentShowListAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ShowPaymentListActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.ll_select_item)
    LinearLayout createJournalList;
    @Bind(R.id.tv_submit)
    LinearLayout llSubmit;
    @Bind(R.id.listViewjournal_Items)
    ListView paymentItemList;
    private String pos1,pos2,amount;
    private PaymentShowListAdapter paymentShowListAdapter;
    AppUser appUser;
    String state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_payment_list);
        ButterKnife.bind(this);
        actionBarSetup();
        initialpageSetup();
        pos1=getIntent().getStringExtra("sp_position1");
        amount=getIntent().getStringExtra("amount");
        pos2=getIntent().getStringExtra("sp_position2");
        state=getIntent().getStringExtra("state");

        createJournalList.setOnClickListener(this);
        llSubmit.setOnClickListener(this);

        paymentItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CreatePaymentListActivity.class);
                intent.putExtra("frompayment", true);
                intent.putExtra("pos",String.valueOf(i));
                startActivity(intent);
                finish();
            }
        });

        paymentItemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ShowPaymentListActivity.this);
                alertDialog.setMessage("Are you sure to delete?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AppUser appUser = LocalRepositories.getAppUser(getApplicationContext());
                        appUser.mListMapForItemPaymentList.remove(position);
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        dialog.cancel();
                        initialpageSetup();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
                return true;

            }
        });
    }

    private void initialpageSetup() {
        appUser= LocalRepositories.getAppUser(this) ;
        paymentShowListAdapter = new PaymentShowListAdapter(this, appUser.mListMapForItemPaymentList);
        paymentItemList.setAdapter(paymentShowListAdapter);
    }

    private void actionBarSetup() {
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
        actionbarTitle.setText("Add Payment Item");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(),3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_select_item:
                Intent intent=new Intent(ShowPaymentListActivity.this,CreatePaymentListActivity.class);
                intent.putExtra("gst_pos1",pos1);
                intent.putExtra("gst_pos2",pos2);
                intent.putExtra("amount",amount);
                intent.putExtra("state",state);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_submit:
                if (appUser.mListMapForItemPaymentList.size()>0) {
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Please select atleast one item", Toast.LENGTH_LONG).show();
                }
                break;
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==3){
            if (resultCode==RESULT_OK){
                paymentShowListAdapter.notifyDataSetChanged();
            }
        }

    }
}
