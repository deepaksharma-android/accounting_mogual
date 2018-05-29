package com.lkintechnology.mBilling.activities.company.transaction.sale;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_response.PaymentSettleModel;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PaymentSettlementActivity extends AppCompatActivity {

    @Bind(R.id.select_account_layout1)
    LinearLayout select_account_layout1;
    @Bind(R.id.select_account_layout2)
    LinearLayout select_account_layout2;
    @Bind(R.id.select_account_layout3)
    LinearLayout select_account_layout3;
    @Bind(R.id.select_account_layout4)
    LinearLayout select_account_layout4;
    @Bind(R.id.select_account_layout5)
    LinearLayout select_account_layout5;

    @Bind(R.id.select_account1)
    TextView select_account1;
    @Bind(R.id.select_account2)
    TextView select_account2;
    @Bind(R.id.select_account3)
    TextView select_account3;
    @Bind(R.id.select_account4)
    TextView select_account4;
    @Bind(R.id.select_account5)
    TextView select_account5;

    @Bind(R.id.amount1)
    EditText amount1;
    @Bind(R.id.amount2)
    EditText amount2;
    @Bind(R.id.amount3)
    EditText amount3;
    @Bind(R.id.amount4)
    EditText amount4;
    @Bind(R.id.amount5)
    EditText amount5;

    @Bind(R.id.submit)
    LinearLayout mSubmit;
    AppUser appUser;
    public static String voucher_type = "";
    public Boolean fromedit;
    public Boolean finish1 = true, finish2 = true, finish3 = true, finish4 = true, finish5 = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_settlement);
        ButterKnife.bind(this);
        appUser = LocalRepositories.getAppUser(this);
        initActionbar();
        fromedit = getIntent().getExtras().getBoolean("fromedit");

        if (fromedit) {
            if (voucher_type.equals("sale")) {

            } else if (voucher_type.equals("purchase")) {

            } else if (voucher_type.equals("sale_return")) {

            } else if (voucher_type.equals("purchase_return")) {

            }
        } else {
            Map map;
            for (int i = 0; i < appUser.paymentSettlementList.size(); i++) {
                if (i == 0) {
                    map = new HashMap();
                    map = appUser.paymentSettlementList.get(i);
                    select_account1.setText("" + map.get("Payment_account_name"));
                    appUser.payment_settlement_id_1 = "" + map.get("Payment_account_id");
                    amount1.setText("" + map.get("Amount"));

                } else if (i == 1) {
                    map = new HashMap();
                    map = appUser.paymentSettlementList.get(i);
                    select_account2.setText("" + map.get("Payment_account_name"));
                    appUser.payment_settlement_id_2 = "" + map.get("Payment_account_id");
                    amount2.setText("" + map.get("Amount"));
                } else if (i == 2) {
                    map = new HashMap();
                    map = appUser.paymentSettlementList.get(i);
                    select_account3.setText("" + map.get("Payment_account_name"));
                    appUser.payment_settlement_id_3 = "" + map.get("Payment_account_id");
                    amount3.setText("" + map.get("Amount"));
                } else if (i == 3) {
                    map = new HashMap();
                    map = appUser.paymentSettlementList.get(i);
                    select_account4.setText("" + map.get("Payment_account_name"));
                    appUser.payment_settlement_id_4 = "" + map.get("Payment_account_id");
                    amount4.setText("" + map.get("Amount"));
                } else if (i == 4) {
                    map = new HashMap();
                    map = appUser.paymentSettlementList.get(i);
                    select_account5.setText("" + map.get("Payment_account_name"));
                    appUser.payment_settlement_id_5 = "" + map.get("Payment_account_id");
                    amount5.setText("" + map.get("Amount"));
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

        select_account_layout3.setOnClickListener(new View.OnClickListener() {
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
                startActivityForResult(intent, 3);
            }
        });

        select_account_layout4.setOnClickListener(new View.OnClickListener() {
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
                startActivityForResult(intent, 4);
            }
        });

        select_account_layout5.setOnClickListener(new View.OnClickListener() {
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
                startActivityForResult(intent, 5);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser.paymentSettlementHashMap.clear();
                appUser.paymentSettlementList.clear();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                PaymentSettleModel paymentSettleModel = new PaymentSettleModel();
                Map map;
                if (!select_account1.getText().toString().equals("")) {
                    if (!amount1.getText().toString().equals("")) {
                        Double amount = Double.parseDouble(amount1.getText().toString());
                        if (amount != 0) {
                            finish1 = true;
                            map = new HashMap();
                            map.put("Payment_account_name", select_account1.getText().toString());
                            map.put("Payment_account_id", appUser.payment_settlement_id_1);
                            map.put("Amount", amount1.getText().toString());
                            appUser.paymentSettlementList.add(map);
                            //paymentSettleModel.setPayment_mode(appUser.paymentSettlementList);
                        }
                    } else {
                        finish1 = false;
                        //Helpers.dialogMessage(context,"Please select account 1");
                        Toast.makeText(getApplicationContext(), "Please select amount 1", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!amount1.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please select account 1", Toast.LENGTH_SHORT).show();
                        finish1 = false;
                    } else {
                        finish1 = true;
                    }
                }
                if (!select_account2.getText().toString().equals("")) {
                    if (!amount2.getText().toString().equals("")) {
                        Double amount = Double.parseDouble(amount2.getText().toString());
                        if (amount != 0) {
                            finish2 = true;
                            map = new HashMap();
                            map.put("Payment_account_name", select_account2.getText().toString());
                            map.put("Payment_account_id", appUser.payment_settlement_id_2);
                            map.put("Amount", amount2.getText().toString());
                            appUser.paymentSettlementList.add(map);
                            //paymentSettleModel.setPayment_mode(appUser.paymentSettlementList);
                        }
                    } else {
                        finish2 = false;
                        //Helpers.dialogMessage(context,"Please select account 1");
                        Toast.makeText(getApplicationContext(), "Please select amount 2", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!amount2.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please select account 2", Toast.LENGTH_SHORT).show();
                        finish2 = false;
                    } else {
                        finish2 = true;
                    }
                }
                if (!select_account3.getText().toString().equals("")) {
                    if (!amount3.getText().toString().equals("")) {
                        Double amount = Double.parseDouble(amount3.getText().toString());
                        if (amount != 0) {
                            finish3 = true;
                            map = new HashMap();
                            map.put("Payment_account_name", select_account3.getText().toString());
                            map.put("Payment_account_id", appUser.payment_settlement_id_3);
                            map.put("Amount", amount3.getText().toString());
                            appUser.paymentSettlementList.add(map);
                            //paymentSettleModel.setPayment_mode(appUser.paymentSettlementList);
                        }
                    } else {
                        finish3 = false;
                        //Helpers.dialogMessage(context,"Please select account 1");
                        Toast.makeText(getApplicationContext(), "Please select amount 3", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!amount3.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please select account 3", Toast.LENGTH_SHORT).show();
                        finish3 = false;
                    } else {
                        finish3 = true;
                    }
                }
                if (!select_account4.getText().toString().equals("")) {
                    if (!amount4.getText().toString().equals("")) {
                        Double amount = Double.parseDouble(amount4.getText().toString());
                        if (amount != 0) {
                            finish4 = true;
                            map = new HashMap();
                            map.put("Payment_account_name", select_account4.getText().toString());
                            map.put("Payment_account_id", appUser.payment_settlement_id_4);
                            map.put("Amount", amount4.getText().toString());
                            appUser.paymentSettlementList.add(map);
                            //paymentSettleModel.setPayment_mode(appUser.paymentSettlementList);
                        }
                    } else {
                        finish4 = false;
                        //Helpers.dialogMessage(context,"Please select account 1");
                        Toast.makeText(getApplicationContext(), "Please select amount 4", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!amount4.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please select account 4", Toast.LENGTH_SHORT).show();
                        finish4 = false;
                    } else {
                        finish4 = true;
                    }
                }
                if (!select_account5.getText().toString().equals("")) {
                    if (!amount5.getText().toString().equals("")) {
                        Double amount = Double.parseDouble(amount5.getText().toString());
                        if (amount != 0) {
                            finish5 = true;
                            map = new HashMap();
                            map.put("Payment_account_name", select_account5.getText().toString());
                            map.put("Payment_account_id", appUser.payment_settlement_id_5);
                            map.put("Amount", amount5.getText().toString());
                            appUser.paymentSettlementList.add(map);
                            //paymentSettleModel.setPayment_mode(appUser.paymentSettlementList);
                        }
                    } else {
                        finish5 = false;
                        //Helpers.dialogMessage(context,"Please select account 1");
                        Toast.makeText(getApplicationContext(), "Please select amount 5", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (!amount5.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Please select account 5", Toast.LENGTH_SHORT).show();
                        finish5 = false;
                    } else {
                        finish5 = true;
                    }
                }
                paymentSettleModel.setPayment_mode(appUser.paymentSettlementList);
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);

                if (appUser.paymentSettlementList.size() > 0) {
                    paymentSettleModel.setType(voucher_type);
                    appUser.paymentSettlementHashMap.add(paymentSettleModel);
                    // appUser.paymentSettlementHashMap.put(map1, paymentSettleModel);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
                if (finish1 && finish2 && finish3 && finish4 && finish5) {
                    finish();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {

                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    select_account1.setText(ParameterConstant.name);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    appUser.payment_settlement_id_1 = ParameterConstant.id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                } else {
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    String[] strArr = result.split(",");
                    select_account1.setText(strArr[0]);
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
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    appUser.payment_settlement_id_2 = ParameterConstant.id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                } else {
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    String[] strArr = result.split(",");
                    select_account2.setText(strArr[0]);
                    appUser.payment_settlement_id_2 = id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    return;
                }
            }
        }

        if (requestCode == 3) {
            if (resultCode == Activity.RESULT_OK) {
                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    select_account3.setText(ParameterConstant.name);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    appUser.payment_settlement_id_3 = ParameterConstant.id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                } else {
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    String[] strArr = result.split(",");
                    select_account3.setText(strArr[0]);
                    appUser.payment_settlement_id_3 = id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    return;
                }
            }
        }

        if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    select_account4.setText(ParameterConstant.name);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    appUser.payment_settlement_id_4 = ParameterConstant.id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                } else {
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    String[] strArr = result.split(",");
                    select_account4.setText(strArr[0]);
                    appUser.payment_settlement_id_4 = id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    return;
                }
            }
        }

        if (requestCode == 5) {
            if (resultCode == Activity.RESULT_OK) {
                if (ParameterConstant.handleAutoCompleteTextView == 1) {
                    select_account5.setText(ParameterConstant.name);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    appUser.payment_settlement_id_5 = ParameterConstant.id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                } else {
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    String[] strArr = result.split(",");
                    select_account5.setText(strArr[0]);
                    appUser.payment_settlement_id_5 = id;
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    return;
                }
            }
        }
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
