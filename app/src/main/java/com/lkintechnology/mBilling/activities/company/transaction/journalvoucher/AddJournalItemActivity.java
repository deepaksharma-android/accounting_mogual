package com.lkintechnology.mBilling.activities.company.transaction.journalvoucher;

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
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.adapters.AddJournalVoucherItemAdapter;
import com.lkintechnology.mBilling.adapters.DebitNoteItemDetailAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddJournalItemActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.ll_select_item)
     LinearLayout createJournalList;
    @Bind(R.id.tv_submit)
     LinearLayout llSubmit;
    @Bind(R.id.listViewjournal_Items)
     ListView journalItemList;
    private String pos1,pos2;
    private AddJournalVoucherItemAdapter addJournalItemAdapter;
    AppUser appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_journal_item);
        ButterKnife.bind(this);
        actionBarSetup();
        initialpageSetup();
        pos1=getIntent().getStringExtra("gst_pos1");
        pos2=getIntent().getStringExtra("gst_pos2");
        createJournalList.setOnClickListener(this);
        llSubmit.setOnClickListener(this);
    }

    private void initialpageSetup() {
        appUser= LocalRepositories.getAppUser(this) ;
        addJournalItemAdapter = new AddJournalVoucherItemAdapter(this, appUser.mListMapForItemJournalVoucherNote);
        journalItemList.setAdapter(addJournalItemAdapter);
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
        actionbarTitle.setText("Add Journal Item");
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
                Intent intent=new Intent(AddJournalItemActivity.this,CreateJournalItemActivity.class);
                intent.putExtra("gst_pos1",pos1);
                intent.putExtra("gst_pos2",pos2);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_submit:
                if (appUser.mListMapForItemJournalVoucherNote.size()>0) {
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
                addJournalItemAdapter.notifyDataSetChanged();
            }
        }

    }
}
