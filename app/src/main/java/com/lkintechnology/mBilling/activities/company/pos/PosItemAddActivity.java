package com.lkintechnology.mBilling.activities.company.pos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.FirstPageActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.billsundry.BillSundryListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity;
import com.lkintechnology.mBilling.adapters.PosAddBillAdapter;
import com.lkintechnology.mBilling.adapters.PosAddItemsAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.EventForPos;
import com.lkintechnology.mBilling.utils.ListHeight;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

public class PosItemAddActivity extends AppCompatActivity {
    RecyclerView.LayoutManager layoutManager;
    PosAddItemsAdapter mAdapter;
    public PosAddBillAdapter mBillAdapter;
    @Bind(R.id.listViewItems)
    RecyclerView mRecyclerView;
    @Bind(R.id.recycler_view_bill)
    RecyclerView mRecyclerViewBill;
    @Bind(R.id.party_name)
    TextView party_name;
    @Bind(R.id.add_bill_button)
    TextView add_bill_button;
    public static TextView mSubtotal;
    @Bind(R.id.change_layout)
    LinearLayout change_layout;
    @Bind(R.id.submit)
    LinearLayout submit;
    Animation blinkOnClick;
    public static LinearLayout igst_layout;
    public static LinearLayout sgst_cgst_layout;

    public LinearLayout igst_layout_12;
    public LinearLayout igst_layout_18;
    public LinearLayout igst_layout_28;
    public LinearLayout igst_layout_5;

    public LinearLayout sgst_cgst_layout_12;
    public LinearLayout sgst_cgst_layout_18;
    public LinearLayout sgst_cgst_layout_28;
    public LinearLayout sgst_cgst_layout_5;

    public static TextView igst;
    public static TextView sgst;
    public static TextView cgst;
    public static TextView grand_total;

    public LinearLayout sgst_cgst_multirate_layout;
    public LinearLayout igst_multirate_layout;

    public static TextView igst_12;
    public static TextView igst_18;
    public static TextView igst_28;
    public static TextView igst_5;

    public static TextView sgst_12;
    public static TextView cgst_12;
    public static TextView sgst_18;
    public static TextView cgst_18;
    public static TextView sgst_28;
    public static TextView cgst_28;
    public static TextView sgst_5;
    public static TextView cgst_5;


    AppUser appUser;
    Double grandTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_item_add);
        ButterKnife.bind(this);
        initActionbar();
        blinkOnClick = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_on_click);
        appUser = LocalRepositories.getAppUser(this);
        // floatingActionButton.bringToFront();
        appUser.billsundrytotal.clear();
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
        Intent intent = getIntent();
        Double subtotal = intent.getDoubleExtra("subtotal", 0.0);
        mSubtotal = (TextView) findViewById(R.id.subtotal);
        igst_layout = (LinearLayout) findViewById(R.id.igst_layout);
        sgst_cgst_layout = (LinearLayout) findViewById(R.id.sgst_cgst_layout);
        igst_multirate_layout = (LinearLayout) findViewById(R.id.igst_multirate_layout);
        sgst_cgst_multirate_layout = (LinearLayout) findViewById(R.id.sgst_cgst_multirate_layout);

        igst_layout_12 = (LinearLayout) findViewById(R.id.igst_layout_12);
        igst_layout_18 = (LinearLayout) findViewById(R.id.igst_layout_18);
        igst_layout_28 = (LinearLayout) findViewById(R.id.igst_layout_28);
        igst_layout_5 = (LinearLayout) findViewById(R.id.igst_layout_5);

        sgst_cgst_layout_12 = (LinearLayout) findViewById(R.id.sgst_cgst_layout_12);
        sgst_cgst_layout_18 = (LinearLayout) findViewById(R.id.sgst_cgst_layout_18);
        sgst_cgst_layout_28 = (LinearLayout) findViewById(R.id.sgst_cgst_layout_28);
        sgst_cgst_layout_5 = (LinearLayout) findViewById(R.id.sgst_cgst_layout_5);

        igst = (TextView) findViewById(R.id.igst);
        sgst = (TextView) findViewById(R.id.sgst);
        cgst = (TextView) findViewById(R.id.cgst);

        igst_12 = (TextView) findViewById(R.id.igst_12);
        igst_18 = (TextView) findViewById(R.id.igst_18);
        igst_28 = (TextView) findViewById(R.id.igst_28);
        igst_5 = (TextView) findViewById(R.id.igst_5);

        sgst_12 = (TextView) findViewById(R.id.sgst_12);
        cgst_12 = (TextView) findViewById(R.id.cgst_12);
        sgst_18 = (TextView) findViewById(R.id.sgst_18);
        cgst_18 = (TextView) findViewById(R.id.cgst_18);
        sgst_28 = (TextView) findViewById(R.id.sgst_28);
        cgst_28 = (TextView) findViewById(R.id.cgst_28);
        sgst_5 = (TextView) findViewById(R.id.sgst_5);
        cgst_5 = (TextView) findViewById(R.id.cgst_5);


        grand_total = (TextView) findViewById(R.id.grand_total);


        if (!Preferences.getInstance(getApplicationContext()).getPos_mobile().equals("")) {
            party_name.setText(Preferences.getInstance(getApplicationContext()).getPos_party_name()
                    + ", " + Preferences.getInstance(getApplicationContext()).getPos_mobile());
        } else {
            party_name.setText(Preferences.getInstance(getApplicationContext()).getPos_party_name());
        }
        mSubtotal.setText("₹ " + subtotal);
        appUser.subTotal = String.valueOf((subtotal));
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
        if (Preferences.getInstance(getApplicationContext()).getPos_sale_type().contains("GST-MultiRate")) {
            Double gst_12 = 0.0, gst_18 = 0.0, gst_28 = 0.0, gst_5 = 0.0;
            String quantity = "";
            for (int i = 0; i < appUser.mListMapForItemSale.size(); i++) {
                Map map = appUser.mListMapForItemSale.get(i);
                String item_id = (String) map.get("item_id");
                String tax1 = (String) map.get("tax");
                quantity = (String) map.get("quantity");
                Double sales_price_main = (Double) map.get("sales_price_main");
                Double percentage = (Double) map.get(item_id);
                if (percentage != 0) {
                    if (percentage == 12) {
                        gst_12 = Double.valueOf(quantity) * (gst_12 + (sales_price_main * percentage) / 100);
                    } else if (percentage == 18) {
                        gst_18 = Double.valueOf(quantity) * (gst_18 + (sales_price_main * percentage) / 100);
                    } else if (percentage == 28) {
                        gst_28 = Double.valueOf(quantity) * (gst_28 + (sales_price_main * percentage) / 100);
                    } else if (percentage == 5) {
                        gst_5 = Double.valueOf(quantity) * (gst_5 + (sales_price_main * percentage) / 100);
                    }
                }
            }
            if (Preferences.getInstance(getApplicationContext()).getPos_sale_type().contains("I/GST-MultiRate")) {
                igst_layout.setVisibility(View.GONE);
                sgst_cgst_layout.setVisibility(View.GONE);
                sgst_cgst_multirate_layout.setVisibility(View.GONE);
                igst_multirate_layout.setVisibility(View.VISIBLE);
                igst_layout_12.setVisibility(View.GONE);
                igst_layout_18.setVisibility(View.GONE);
                igst_layout_28.setVisibility(View.GONE);
                igst_layout_5.setVisibility(View.GONE);
                if (gst_12 != 0) {
                    igst_layout_12.setVisibility(View.VISIBLE);
                    igst_12.setText("₹ " + gst_12);
                    grandTotal = grandTotal + gst_12;
                }
                if (gst_18 != 0) {
                    igst_layout_18.setVisibility(View.VISIBLE);
                    igst_18.setText("₹ " + gst_18);
                    grandTotal = grandTotal + gst_18;
                }
                if (gst_28 != 0) {
                    igst_layout_28.setVisibility(View.VISIBLE);
                    igst_28.setText("₹ " + gst_28);
                    grandTotal = grandTotal + gst_28;
                }
                if (gst_5 != 0) {
                    igst_layout_5.setVisibility(View.VISIBLE);
                    igst_5.setText("₹ " + gst_5);
                    grandTotal = grandTotal + gst_5;
                }
            } else {
                igst_layout.setVisibility(View.GONE);
                sgst_cgst_layout.setVisibility(View.GONE);
                sgst_cgst_multirate_layout.setVisibility(View.VISIBLE);
                igst_multirate_layout.setVisibility(View.GONE);
                sgst_cgst_layout_12.setVisibility(View.GONE);
                sgst_cgst_layout_18.setVisibility(View.GONE);
                sgst_cgst_layout_28.setVisibility(View.GONE);
                sgst_cgst_layout_5.setVisibility(View.GONE);
                if (gst_12 != 0) {
                    sgst_cgst_layout_12.setVisibility(View.VISIBLE);
                    sgst_12.setText("₹ " + gst_12 / 2);
                    cgst_12.setText("₹ " + gst_12 / 2);
                    grandTotal = grandTotal + gst_12;
                }
                if (gst_18 != 0) {
                    sgst_cgst_layout_18.setVisibility(View.VISIBLE);
                    sgst_18.setText("₹ " + gst_18 / 2);
                    cgst_18.setText("₹ " + gst_18 / 2);
                    grandTotal = grandTotal + gst_18;
                }
                if (gst_28 != 0) {
                    sgst_cgst_layout_28.setVisibility(View.VISIBLE);
                    sgst_28.setText("₹ " + gst_28 / 2);
                    cgst_28.setText("₹ " + gst_28 / 2);
                    grandTotal = grandTotal + gst_28;
                }
                if (gst_5 != 0) {
                    sgst_cgst_layout_5.setVisibility(View.VISIBLE);
                    sgst_5.setText("₹ " + gst_5 / 2);
                    cgst_5.setText("₹ " + gst_5 / 2);
                    grandTotal = grandTotal + gst_5;
                }
            }
            grand_total.setText("₹ " + (subtotal + grandTotal));
            appUser.grandTotal = String.valueOf(subtotal + grandTotal);
            appUser.subTotal = String.valueOf((subtotal));
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
        } else {
            PosAddItemsAdapter.setTaxChange(getApplicationContext(), subtotal, 0.0, 0.0, "", false);
        }

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

        add_bill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Preferences.getInstance(getApplicationContext()).getPos_sale_type().equals("")) {
                    add_bill_button.startAnimation(blinkOnClick);
                    ExpandableItemListActivity.comingFrom = 5;
                    BillSundryListActivity.isDirectForBill = false;
                    startActivity(new Intent(getApplicationContext(), BillSundryListActivity.class));
                    // finish();
                } else {
                    alertdialog();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

     /*   listViewItems.setAdapter(new PosAddItemsAdapter(getApplicationContext(), appUser.mListMapForItemSale));
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);*/

        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new PosAddItemsAdapter(this, appUser.mListMapForItemSale);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void add(View v) {
        Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        EventBus.getDefault().register(this);
        appUser = LocalRepositories.getAppUser(getApplicationContext());
        System.out.println(appUser.mListMapForBillSale.toString());
        if (appUser.mListMapForBillSale.size() > 0) {
            if (ExpandableItemListActivity.boolForAdapterSet) {
              billCalculation( 0.0, false);
              setBillListDataAdapter();
            }
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        System.out.println(appUser.mListMapForBillSale.toString());
        ExpandableItemListActivity.boolForAdapterSet = false;
        super.onPause();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
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

    public void alertdialog() {
        new AlertDialog.Builder(getApplicationContext())
                .setTitle("Sale Voucher")
                .setMessage("Please add sale type in create voucher")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    return;

                })
                .show();
    }

    @Subscribe
    public void event_click_alert(EventForPos response) {
            appUser = LocalRepositories.getAppUser(this);
            if (appUser.mListMapForBillSale.size() > 0) {
                appUser.billsundrytotal.clear();
                LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                billCalculation(Double.valueOf(response.getPosition()),true);
                setBillListDataAdapter();
            }
    }

    public void setBillListDataAdapter() {
        mRecyclerViewBill.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewBill.setLayoutManager(layoutManager);
        mBillAdapter = new PosAddBillAdapter(this, appUser.mListMapForBillSale, appUser.billsundrytotal);
        mRecyclerViewBill.setAdapter(mBillAdapter);
        //mBillAdapter.notifyDataSetChanged();

    }

    public void notifyDataSetChanged() {
        // billCalculation();
        mRecyclerViewBill.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        //  mRecyclerViewBill.setLayoutManager(layoutManager);
        // mBillAdapter = new PosAddBillAdapter(this, appUser.mListMapForBillSale, false);
        mRecyclerViewBill.setAdapter(mBillAdapter);
        mBillAdapter.notifyDataSetChanged();
    }

    public void billCalculation(Double subtotal, Boolean aBoolean) {
        Double grandTotal = 0.0;
        if (aBoolean) {
            grandTotal = subtotal;
        } else {
            grandTotal = txtSplit(grand_total.getText().toString());
        }
        for (int i = 0; i <appUser.mListMapForBillSale.size(); i++) {
            Double total = 0.0;
            Map map = appUser.mListMapForBillSale.get(i);
            String itemName = (String) map.get("courier_charges");
            String amount = (String) map.get("amount");
            String fed_as_percentage = (String) map.get("fed_as_percentage");
            String fed_as = (String) map.get("fed_as");
            String type = (String) map.get("type");
            if (fed_as_percentage != null) {
                if (fed_as_percentage.equals("valuechange")) {
                    Double changeamount = Double.parseDouble((String) map.get("changeamount"));
                    total = changeamount;
                    if (!aBoolean) {
                        if (appUser.mListMapForBillSale.size() - 1 == i) {
                            if (type.equals("Additive")) {
                                grandTotal = grandTotal + total;
                            } else {
                                grandTotal = grandTotal - total;
                            }
                            appUser.billsundrytotal.add(i, String.format("%.2f", total));
                        }
                    } else {
                        if (type.equals("Additive")) {
                            grandTotal = grandTotal + total;
                        } else {
                            grandTotal = grandTotal - total;
                        }
                        appUser.billsundrytotal.add(i, String.format("%.2f", total));
                    }

                } else {
                    if (!aBoolean) {
                        if (appUser.mListMapForBillSale.size() - 1 == i) {
                            total = (grandTotal * Double.valueOf(amount)) / 100;
                            if (type.equals("Additive")) {
                                grandTotal = grandTotal + total;
                            } else {
                                grandTotal = grandTotal - total;
                            }

                            appUser.billsundrytotal.add(i, String.format("%.2f", total));
                        }
                    } else {
                        total = (grandTotal * Double.valueOf(amount)) / 100;
                        if (type.equals("Additive")) {
                            grandTotal = grandTotal + total;
                        } else {
                            grandTotal = grandTotal - total;
                        }
                        appUser.billsundrytotal.add(i, String.format("%.2f", total));
                    }
                }
            }
        }
        LocalRepositories.saveAppUser(getApplicationContext(),appUser);

        // PosItemAddActivity.mSubtotal.setText("₹ " + String.format("%.2f", subtotal));
        PosItemAddActivity.grand_total.setText("₹ " + String.format("%.2f", grandTotal));
        System.out.println(grandTotal);

    }


    public Double txtSplit(String total) {
        Double a = 0.0;
        String[] arr = total.split("₹ ");
        a = Double.valueOf(arr[1].trim());
        return a;
    }

}
