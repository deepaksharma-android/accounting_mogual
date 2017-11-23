package com.berylsystems.buzz.activities.company.administration.master.billsundry;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.account.ExpandableAccountListActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountingInSaleActivity  extends AppCompatActivity {
    @Bind(R.id.spinner_affect_accounting)
    Spinner mSpinnerAffectAccounting;
    @Bind(R.id.spinner_adjust_in_sale_amount)
    Spinner mSpinnerAdjustInSaleAmount;
    @Bind(R.id.spinner_specify_in)
    Spinner mSpinnerSpecifyIn;
    @Bind(R.id.specify_in_layout)
    LinearLayout mSpecifyInLayout;
    @Bind(R.id.account_name)
    TextView mAccountHeadToPost;
    @Bind(R.id.head_to_post_layout)
    LinearLayout mHeadToPostLayout;
    @Bind(R.id.spinner_adjust_party_amount)
    Spinner mSpinnerAdjustPartyAmount;
    @Bind(R.id.spinner_party_specify_in)
    Spinner mSpinnerPartySpecifyIn;
    @Bind(R.id.spinner_post_over)
    Spinner mPostOverSpinner;
    @Bind(R.id.specify_in_party_amount_layout)
    LinearLayout mPartySpecifyInLayout;
    @Bind(R.id.party_account_name)
    TextView mPartyHeadToPost;
    @Bind(R.id.party_head_to_post_layout)
    LinearLayout mPartyHeadToPostLayout;
    @Bind(R.id.layout_affect_accounting)
    LinearLayout mAffectAccountingLayout;
    @Bind(R.id.layout_head_to_post)
    LinearLayout mHeadToPostMainLayout;
    @Bind(R.id.layout_party_head_to_post)
    LinearLayout mPartyHeadToPostMainLayout;
    @Bind(R.id.layout_post_over)
    LinearLayout mPostOverLayout;
    @Bind(R.id.submit)
    LinearLayout mSubmitButton;
    AppUser appUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounting_in_sale);
        ButterKnife.bind(this);
        initActionbar();
        appUser= LocalRepositories.getAppUser(this);
        mHeadToPostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), ExpandableAccountListActivity.class), 1);
            }
        });
        mPartyHeadToPostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), ExpandableAccountListActivity.class), 2);
            }
        });
        mSpinnerAffectAccounting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    mAffectAccountingLayout.setVisibility(View.GONE);
                }
                else{
                    mAffectAccountingLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerAdjustInSaleAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    mSpecifyInLayout.setVisibility(View.VISIBLE);

                }
                else{
                    mSpecifyInLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerSpecifyIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    mHeadToPostMainLayout.setVisibility(View.GONE);
                }
                else{
                    mHeadToPostMainLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

      mSpinnerAdjustPartyAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              if(i==1){
                  mPartySpecifyInLayout.setVisibility(View.VISIBLE);

              }
              else{
                  mPartySpecifyInLayout.setVisibility(View.GONE);
              }
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });
        mSpinnerPartySpecifyIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==1){
                    mPartyHeadToPostMainLayout.setVisibility(View.GONE);
                }
                else{
                    mPartyHeadToPostMainLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (appUser.sale_affect_accounting.equals("No")) {
            mSpinnerAffectAccounting.setSelection(1);
            mAffectAccountingLayout.setVisibility(View.GONE);
        }
        if (appUser.sale_affect_sale_amount.equals("No")) {
            mSpinnerAdjustInSaleAmount.setSelection(1);
            mSpecifyInLayout.setVisibility(View.VISIBLE);
        }
        if (appUser.sale_affect_sale_amount_specify_in.equals("Specify Acc. in Voucher")) {
            mSpinnerSpecifyIn.setSelection(1);
            mHeadToPostLayout.setVisibility(View.GONE);
        }
        if (!appUser.sale_account_head_to_post_sale_amount.equals("")) {
            mHeadToPostMainLayout.setVisibility(View.VISIBLE);
            mAccountHeadToPost.setText(appUser.sale_account_head_to_post_sale_amount);
        }
        if (appUser.sale_adjust_in_party_amount.equals("No")) {
            mSpinnerAdjustPartyAmount.setSelection(1);
            mPartySpecifyInLayout.setVisibility(View.VISIBLE);
        }
        if (appUser.sale_party_amount_specify_in.equals("Specify Acc. in Voucher")) {
            mSpinnerPartySpecifyIn.setSelection(1);
            mPartyHeadToPostMainLayout.setVisibility(View.GONE);
        }

        if (!appUser.sale_account_head_to_post_party_amount.equals("")) {
            mPartyHeadToPost.setText(appUser.sale_account_head_to_post_party_amount);
        }
        if (appUser.sale_post_over_above.equals("No")) {
            mPostOverSpinner.setSelection(1);
        }


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.sale_affect_accounting = mSpinnerAffectAccounting.getSelectedItem().toString();
                appUser.sale_affect_sale_amount = mSpinnerAdjustInSaleAmount.getSelectedItem().toString();
                appUser.sale_affect_sale_amount_specify_in = mSpinnerSpecifyIn.getSelectedItem().toString();
                appUser.sale_account_head_to_post_sale_amount = mAccountHeadToPost.getText().toString();
                appUser.sale_adjust_in_party_amount = mSpinnerAdjustPartyAmount.getSelectedItem().toString();
                appUser.sale_party_amount_specify_in = mSpinnerPartySpecifyIn.getSelectedItem().toString();
                appUser.sale_account_head_to_post_party_amount = mPartyHeadToPost.getText().toString();
                appUser.sale_post_over_above = mPostOverSpinner.getSelectedItem().toString();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
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
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#009DE0")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("ACCOUNTING IN SALE");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                String arr[]=result.split(",");
                String headtopost=arr[0];
                appUser.sale_account_head_to_post_sale_amount_id=id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mAccountHeadToPost.setText(headtopost);
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                String result = data.getStringExtra("name");
                String arr[]=result.split(",");
                String partyheadtopost=arr[0];
                String id = data.getStringExtra("id");
                appUser.sale_account_head_to_post_party_amount_id=id;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                mPartyHeadToPost.setText(partyheadtopost);
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

}