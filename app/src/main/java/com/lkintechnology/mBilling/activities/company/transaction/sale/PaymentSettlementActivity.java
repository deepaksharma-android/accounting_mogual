package com.lkintechnology.mBilling.activities.company.transaction.sale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PaymentSettlementActivity extends AppCompatActivity {

    @Bind(R.id.select_account_layout1)
    LinearLayout select_account_layout1;
    @Bind(R.id.select_account_layout2)
    LinearLayout select_account_layout2;

    @Bind(R.id.select_account1)
    TextView select_account1;
    @Bind(R.id.select_account2)
    TextView select_account2;

    @Bind(R.id.amount1)
    EditText amount1;
    @Bind(R.id.amount2)
    EditText amount2;

    @Bind(R.id.submit)
    LinearLayout mSubmit;
    AppUser appUser;
    public static String voucher_type="";
    public Boolean fromedit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_settlement);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
        fromedit=getIntent().getExtras().getBoolean("fromedit");

        if (fromedit){

        }else {
            Map map;
            for (int i=0;i<appUser.paymentSettlementList.size();i++){
                if (i==0){
                    map = new HashMap();
                    map = appUser.paymentSettlementList.get(i);
                    select_account1.setText(""+map.get("Payment_account_name"));
                    appUser.payment_settlement_id_1 = ""+map.get("Payment_account_id");
                    amount1.setText(""+map.get("Amount"));

                }else if (i==1){
                    map = new HashMap();
                    map = appUser.paymentSettlementList.get(i);
                    select_account2.setText(""+map.get("Payment_account_name"));
                    appUser.payment_settlement_id_2 = ""+map.get("Payment_account_id");
                    amount2.setText(""+map.get("Amount"));
                }
            }
        }

        select_account_layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParameterConstant.forAccountIntentBool = false;
                ParameterConstant.forAccountIntentName = "";
                ParameterConstant.forAccountIntentId = "";
                //intStartActivityForResult = 2;
                //ParameterConstant.checkStartActivityResultForAccount = 0;
                appUser.account_master_group = "";
                ExpandableAccountListActivity.isDirectForAccount = false;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ParameterConstant.handleAutoCompleteTextView = 0;
                Intent intent = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                //intent.putExtra("bool",true);
                startActivityForResult(intent, 1);
            }
        });

        select_account_layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParameterConstant.forAccountIntentBool = false;
                ParameterConstant.forAccountIntentName = "";
                ParameterConstant.forAccountIntentId = "";
                //intStartActivityForResult = 2;
                //ParameterConstant.checkStartActivityResultForAccount = 0;
                appUser.account_master_group = "";
                ExpandableAccountListActivity.isDirectForAccount = false;
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ParameterConstant.handleAutoCompleteTextView = 0;
                Intent intent = new Intent(getApplicationContext(), ExpandableAccountListActivity.class);
                //intent.putExtra("bool",true);
                startActivityForResult(intent, 2);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               appUser.paymentSettlementList.clear();
               LocalRepositories.saveAppUser(getApplicationContext(),appUser);
               Map map;
               if (!amount1.getText().toString().equals("")){
                   map = new HashMap();
                   map.put("Payment_account_name",select_account1.getText().toString());
                   map.put("Payment_account_id",appUser.payment_settlement_id_1);
                   map.put("Amount",amount1.getText().toString());
                   appUser.paymentSettlementList.add(map);
               }
                if (!amount2.getText().toString().equals("")){
                    map = new HashMap();
                    map.put("Payment_account_name",select_account2.getText().toString());
                    map.put("Payment_account_id",appUser.payment_settlement_id_2);
                    map.put("Amount",amount2.getText().toString());
                    appUser.paymentSettlementList.add(map);
                }
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                   // boolForPartyName = true;
                    select_account1.setText(ParameterConstant.name);
                    //mMobileNumber.setText(ParameterConstant.mobile);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    //appUser.payment_settlement_name_1 = ParameterConstant.name;
                    appUser.payment_settlement_id_1 = ParameterConstant.id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    //Preferences.getInstance(getApplicationContext()).setParty_id(ParameterConstant.id);
                    //Preferences.getInstance(getApplicationContext()).setParty_name(ParameterConstant.name);


                } else {
                    //boolForPartyName = true;
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    //appUser.sale_party_group = group;
                    String[] strArr = result.split(",");
                    select_account1.setText(strArr[0]);
                    //appUser.payment_settlement_name_1 = strArr[0];
                    appUser.payment_settlement_id_1 = id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    return;
                }
            }
        }

        if (requestCode == 2) {
            if (resultCode == Activity.RESULT_OK) {

                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    // boolForPartyName = true;
                    select_account2.setText(ParameterConstant.name);
                    //mMobileNumber.setText(ParameterConstant.mobile);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                   // appUser.payment_settlement_name_2 = ParameterConstant.name;
                    appUser.payment_settlement_id_2 = ParameterConstant.id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    //Preferences.getInstance(getApplicationContext()).setParty_id(ParameterConstant.id);
                    //Preferences.getInstance(getApplicationContext()).setParty_name(ParameterConstant.name);


                } else {
                    //boolForPartyName = true;
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    //appUser.sale_party_group = group;
                    String[] strArr = result.split(",");
                    select_account2.setText(strArr[0]);
                    //appUser.payment_settlement_name_2 = strArr[0];
                    appUser.payment_settlement_id_2 = id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    return;
                }
            }
        }

        //super.onActivityResult(requestCode, resultCode, data);
    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        //actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("Payment Settlement");
        actionbarTitle.setTextSize(16);
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
}
