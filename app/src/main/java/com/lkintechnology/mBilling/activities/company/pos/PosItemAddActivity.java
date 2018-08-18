package com.lkintechnology.mBilling.activities.company.pos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.FirstPageActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.adapters.PosAddItemsAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.ListHeight;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PosItemAddActivity extends AppCompatActivity {
    @Bind(R.id.listViewItems)
    ListView listViewItems;
    @Bind(R.id.party_name)
    TextView party_name;
    public static TextView mSubtotal;
    @Bind(R.id.change_layout)
    LinearLayout change_layout;
    public static LinearLayout igst_layout;
    public static LinearLayout sgst_cgst_layout;
    public static TextView igst;
    public static TextView sgst;
    public static TextView cgst;
    public static TextView grand_total;


    RecyclerView.LayoutManager layoutManager;
    AppUser appUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_item_add);
        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        Intent intent = getIntent();
        Double subtotal = intent.getDoubleExtra("subtotal",0.0);
        mSubtotal = (TextView) findViewById(R.id.subtotal);
        igst_layout = (LinearLayout) findViewById(R.id.igst_layout);
        sgst_cgst_layout = (LinearLayout) findViewById(R.id.sgst_cgst_layout);
        igst = (TextView) findViewById(R.id.igst);
        sgst = (TextView) findViewById(R.id.sgst);
        cgst = (TextView) findViewById(R.id.cgst);
        grand_total = (TextView) findViewById(R.id.grand_total);


        if(!Preferences.getInstance(getApplicationContext()).getPos_mobile().equals("")){
            party_name.setText(Preferences.getInstance(getApplicationContext()).getPos_party_name()
                    +", "+Preferences.getInstance(getApplicationContext()).getPos_mobile());
        }else {
            party_name.setText(Preferences.getInstance(getApplicationContext()).getPos_party_name());
        }
        mSubtotal.setText("₹ "+subtotal);
        PosAddItemsAdapter.setTaxChange(getApplicationContext(),subtotal);

        change_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser = LocalRepositories.getAppUser(getApplicationContext());
                ParameterConstant.forAccountIntentBool = false;
                appUser.account_master_group = "Sundry Debtors,Sundry Creditors,Cash-in-hand";
                ExpandableAccountListActivity.isDirectForAccount = false;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ParameterConstant.handleAutoCompleteTextView = 0;
                Intent intent = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                //intent.putExtra("bool",true);
                startActivityForResult(intent, 1);
            }
        });

        listViewItems.setAdapter(new PosAddItemsAdapter(getApplicationContext(), appUser.mListMapForItemSale));
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);
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
        actionbarTitle.setText("SELECTED ITEM LIST");
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        appUser = LocalRepositories.getAppUser(getApplicationContext());

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    party_name.setText(ParameterConstant.name);
                   // mMobileNumber.setText(ParameterConstant.mobile);
                    Preferences.getInstance(getApplicationContext()).setParty_id(ParameterConstant.id);
                    Preferences.getInstance(getApplicationContext()).setParty_name(ParameterConstant.name);
                    Preferences.getInstance(getApplicationContext()).setMobile(ParameterConstant.mobile);
                } else {
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    String[] strArr = result.split(",");
                    party_name.setText(strArr[0]);
                   // mMobileNumber.setText(mobile);
                    Preferences.getInstance(getApplicationContext()).setParty_id(id);
                    Preferences.getInstance(getApplicationContext()).setParty_name(strArr[0]);
                    Preferences.getInstance(getApplicationContext()).setMobile(mobile);
                    return;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        FirstPageActivity.posSetting = true;
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FirstPageActivity.posSetting = true;
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
