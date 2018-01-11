package com.lkintechnology.mBilling.activities.company.administration.master.billsundry;

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
import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.administration.master.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountingInPurchaseActivity extends AppCompatActivity {
    @Bind(R.id.spinner_affect_accounting)
    Spinner mSpinnerAffectAccounting;
    @Bind(R.id.spinner_adjust_in_purchase_amount)
    Spinner mSpinnerAdjustInPurchaseAmount;
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

    public Boolean boolForReceivedFrom = false;
    public Boolean boolForReceivedBy = false;
    public static int intStartActivityForResult=0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounting_purchase);
        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        mHeadToPostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group="";
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                intStartActivityForResult=1;
                ParameterConstant.checkStartActivityResultForAccount =14;
                ExpandableAccountListActivity.isDirectForAccount=false;
                startActivityForResult(new Intent(getApplicationContext(), ExpandableAccountListActivity.class), 1);
            }
        });
        mPartyHeadToPostLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.account_master_group="";
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                intStartActivityForResult=2;
                ParameterConstant.checkStartActivityResultForAccount =14;
                ExpandableAccountListActivity.isDirectForAccount=false;
                startActivityForResult(new Intent(getApplicationContext(), ExpandableAccountListActivity.class), 2);
            }
        });
        mSpinnerAffectAccounting.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    mAffectAccountingLayout.setVisibility(View.GONE);
                } else {
                    mAffectAccountingLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerAdjustInPurchaseAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    mSpecifyInLayout.setVisibility(View.VISIBLE);
                    mPostOverLayout.setVisibility(View.VISIBLE);

                } else {
                    mSpecifyInLayout.setVisibility(View.GONE);
                    mPostOverLayout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mSpinnerSpecifyIn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 1) {
                    mHeadToPostMainLayout.setVisibility(View.GONE);
                } else {
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
                if (i == 1) {
                    mPartySpecifyInLayout.setVisibility(View.VISIBLE);

                } else {
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
                if (i == 1) {
                    mPartyHeadToPostMainLayout.setVisibility(View.GONE);
                } else {
                    mPartyHeadToPostMainLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        if (Preferences.getInstance(getApplicationContext()).getpurchase_affect_accounting().equals("No")) {
            mSpinnerAffectAccounting.setSelection(1);
            mAffectAccountingLayout.setVisibility(View.GONE);
        }
        if (Preferences.getInstance(getApplicationContext()).getpurchase_affect_purchase_amount().equals("No")) {
            mSpinnerAdjustInPurchaseAmount.setSelection(1);
            mSpecifyInLayout.setVisibility(View.VISIBLE);
        }
        if ( Preferences.getInstance(getApplicationContext()).getpurchase_affect_purchase_amount_specify_in().equals("Specify Acc. in Voucher")) {
            mSpinnerSpecifyIn.setSelection(1);
            mHeadToPostLayout.setVisibility(View.GONE);
        }
        if (!Preferences.getInstance(getApplicationContext()).getpurchase_account_head_to_post_purchase_amount().equals("")) {
            mHeadToPostMainLayout.setVisibility(View.VISIBLE);
            mAccountHeadToPost.setText(Preferences.getInstance(getApplicationContext()).getpurchase_account_head_to_post_purchase_amount());
        }
        if ( Preferences.getInstance(getApplicationContext()).getpurchase_adjust_in_party_amount().equals("No")) {
            mSpinnerAdjustPartyAmount.setSelection(1);
            mPartySpecifyInLayout.setVisibility(View.VISIBLE);
        }
        if (Preferences.getInstance(getApplicationContext()).getpurchase_party_amount_specify_in().equals("Specify Acc. in Voucher")) {
            mSpinnerPartySpecifyIn.setSelection(1);
            mPartyHeadToPostMainLayout.setVisibility(View.GONE);
        }

        if (! Preferences.getInstance(getApplicationContext()).getpurchase_account_head_to_post_party_amount().equals("")) {
            mPartyHeadToPost.setText(Preferences.getInstance(getApplicationContext()).getpurchase_account_head_to_post_party_amount());
        }
        if (Preferences.getInstance(getApplicationContext()).getpurchase_post_over_above().equals("No")) {
            mPostOverSpinner.setSelection(1);
        }




        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSpinnerAffectAccounting.getSelectedItem().toString().equals("Yes")) {
                    Preferences.getInstance(getApplicationContext()).setpurchase_affect_accounting(mSpinnerAffectAccounting.getSelectedItem().toString());
                    Preferences.getInstance(getApplicationContext()).setpurchase_affect_purchase_amount(mSpinnerAdjustInPurchaseAmount.getSelectedItem().toString());
                    if (mSpinnerAdjustInPurchaseAmount.getSelectedItem().toString().equals("No")) {
                        Preferences.getInstance(getApplicationContext()).setpurchase_post_over_above(mPostOverSpinner.getSelectedItem().toString());
                        Preferences.getInstance(getApplicationContext()).setpurchase_affect_purchase_amount_specify_in(mSpinnerSpecifyIn.getSelectedItem().toString());
                        if (mSpinnerSpecifyIn.getSelectedItem().toString().equals("Specify Acc. Here")) {
                            Preferences.getInstance(getApplicationContext()).setpurchase_account_head_to_post_purchase_amount(mAccountHeadToPost.getText().toString());
                        }
                    }
                    Preferences.getInstance(getApplicationContext()).setpurchase_adjust_in_party_amount(mSpinnerAdjustPartyAmount.getSelectedItem().toString());
                    if (mSpinnerAdjustPartyAmount.getSelectedItem().toString().equals("No")) {
                        Preferences.getInstance(getApplicationContext()).setpurchase_party_amount_specify_in(mSpinnerPartySpecifyIn.getSelectedItem().toString());
                        if (mSpinnerPartySpecifyIn.getSelectedItem().toString().equals("Specify Acc. Here")) {
                            Preferences.getInstance(getApplicationContext()).setpurchase_account_head_to_post_party_amount(mPartyHeadToPost.getText().toString());
                        }
                    }
                }
                else{
                    Preferences.getInstance(getApplicationContext()).setpurchase_affect_accounting(mSpinnerAffectAccounting.getSelectedItem().toString());
                }

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
        actionbarTitle.setText("ACCOUNTING IN PURCHASE");
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
                finish();
                break;
        }
        return true;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                boolForReceivedFrom = true;
                String result = data.getStringExtra("name");
                String id = data.getStringExtra("id");
                String arr[]=result.split(",");
                String headtopost=arr[0];
                Preferences.getInstance(getApplicationContext()).setpurchase_account_head_to_post_purchase_amount_id(id);
                mAccountHeadToPost.setText(headtopost);
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                boolForReceivedBy = true;
                String result = data.getStringExtra("name");
                String arr[]=result.split(",");
                String partyheadtopost=arr[0];
                String id = data.getStringExtra("id");
                Preferences.getInstance(getApplicationContext()).setpurchase_account_head_to_post_party_amount_id(id);
                mPartyHeadToPost.setText(partyheadtopost);
            }
            if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
                //mItemGroup.setText("");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);
        if (bool) {

            if (intStartActivityForResult==1){
                boolForReceivedBy=true;

            }else if (intStartActivityForResult==2){
                boolForReceivedFrom=true;
            }
            if (!boolForReceivedFrom) {

                String result = intent.getStringExtra("name");
                String id = intent.getStringExtra("id");
                String arr[]=result.split(",");
                String headtopost=arr[0];
                Preferences.getInstance(getApplicationContext()).setpurchase_account_head_to_post_purchase_amount_id(id);
                mAccountHeadToPost.setText(headtopost);

            }
            if (!boolForReceivedBy) {

                String result = intent.getStringExtra("name");
                String arr[]=result.split(",");
                String partyheadtopost=arr[0];
                String id = intent.getStringExtra("id");
                Preferences.getInstance(getApplicationContext()).setpurchase_account_head_to_post_party_amount_id(id);
                mPartyHeadToPost.setText(partyheadtopost);
            }
        }

    }
}