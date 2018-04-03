package com.lkintechnology.mBilling.activities.company.transaction.creditnotewoitem;

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
import com.lkintechnology.mBilling.activities.company.transaction.sale.SaleVoucherAddItemActivity;
import com.lkintechnology.mBilling.adapters.CreditNoteItemDetailAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import timber.log.Timber;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AddCreditNoteItemActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout llSelectItem;
    private ListView listItem;
    AppUser appUser;
   private String amount,position,state,positionJournalVoucher,journalDiffAmount;
    private CreditNoteItemDetailAdapter creditNoteItemDetailAdapter;
    private LinearLayout ll_submit;
    private Spinner reason;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credit_note_item_detail);
        appUser = LocalRepositories.getAppUser(this);
        initView();
        initActionBarSetup();

        llSelectItem.setOnClickListener(this);
        ll_submit.setOnClickListener(this);

        amount=getIntent().getStringExtra("amount");
        position=getIntent().getStringExtra("sp_position");
        positionJournalVoucher=getIntent().getStringExtra("gst_pos6");
        journalDiffAmount=getIntent().getStringExtra("diff_amount");
        state=getIntent().getStringExtra("state");
        Timber.i("mystate"+state);
        initialpageSetup();
        Timber.i("state"+appUser.company_state);
        String group_type = Preferences.getInstance(getApplicationContext()).getReason();
        int groupindex = -1;
        for (int i = 0; i < getResources().getStringArray(R.array.reasons).length; i++) {
            if (getResources().getStringArray(R.array.reasons)[i].equals(group_type)) {
                groupindex = i;
                break;
            }
        }
        reason.setSelection(groupindex);
        listItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), CreateCreditNoteItemActivity.class);
                if(positionJournalVoucher!=null){
                    if(positionJournalVoucher.equals("6")){
                        intent.putExtra("fromcredit", true);
                        intent.putExtra("pos", String.valueOf(i));
                        intent.putExtra("journal",true);
                    }
                }
                else{
                    intent.putExtra("fromcredit",true);
                    intent.putExtra("journal", false);
                    intent.putExtra("pos", String.valueOf(i));
                }
                Preferences.getInstance(getApplicationContext()).setReason(reason.getSelectedItem().toString());
                startActivity(intent);
                finish();
            }

        });
        listItem.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddCreditNoteItemActivity.this);
                alertDialog.setMessage("Are you sure to delete?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AppUser appUser = LocalRepositories.getAppUser(getApplicationContext());
                        if (positionJournalVoucher != null) {
                            if (positionJournalVoucher.equals("6")) {
                                appUser.mListMapForItemJournalVoucherNote.remove(position);
                            }
                        }
                        else {
                            appUser.mListMapForItemCreditNote.remove(position);
                        }
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
        appUser=LocalRepositories.getAppUser(this);
        if(positionJournalVoucher!=null) {
            if (positionJournalVoucher.equals("6")) {
                creditNoteItemDetailAdapter = new CreditNoteItemDetailAdapter(this, appUser.mListMapForItemJournalVoucherNote);
            }
        }
        else{
            creditNoteItemDetailAdapter= new CreditNoteItemDetailAdapter(this, appUser.mListMapForItemCreditNote);
        }

        listItem.setAdapter(creditNoteItemDetailAdapter);
      //  notifyAll();

    }
  //  to defined id
    private void initView() {
        llSelectItem= (LinearLayout) findViewById(R.id.ll_select_item);
        listItem= (ListView) findViewById(R.id.listViewItems);
        ll_submit= (LinearLayout) findViewById(R.id.tv_submit);
        reason= (Spinner) findViewById(R.id.reason);
    }

        @Override
        public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_select_item:
                if (positionJournalVoucher!=null){
                if (positionJournalVoucher.equals("6")) {
                    Intent intent = new Intent(this, CreateCreditNoteItemActivity.class);
                    intent.putExtra("gst_pos6", positionJournalVoucher);
                    intent.putExtra("diff_amount", journalDiffAmount);
                    intent.putExtra("state", state);
                    Preferences.getInstance(getApplicationContext()).setReason(reason.getSelectedItem().toString());
                    startActivity(intent);
                    finish();
                }
                }else {
                    Intent intent=new Intent(this,CreateCreditNoteItemActivity.class);
                    intent.putExtra("amount",amount);
                    intent.putExtra("sp_position",position);
                    intent.putExtra("state", state);
                    Preferences.getInstance(getApplicationContext()).setReason(reason.getSelectedItem().toString());
                    startActivity(intent);
                    finish();
                }

                break;
            case R.id.tv_submit:
                if (positionJournalVoucher!=null) {
                    if (positionJournalVoucher.equals("6")) {
                        if (appUser.mListMapForItemJournalVoucherNote.size() > 0) {
                            appUser.journalreason = reason.getSelectedItem().toString();
                            Preferences.getInstance(getApplicationContext()).setReason(reason.getSelectedItem().toString());
                            LocalRepositories.saveAppUser(this, appUser);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Please select atleast one item", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    if (appUser.mListMapForItemCreditNote.size() > 0) {
                        appUser.creditreason = reason.getSelectedItem().toString();
                        Preferences.getInstance(getApplicationContext()).setReason(reason.getSelectedItem().toString());
                        LocalRepositories.saveAppUser(this, appUser);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please select atleast one item", Toast.LENGTH_LONG).show();
                    }
                }
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

