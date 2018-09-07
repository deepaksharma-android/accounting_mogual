package com.lkintechnology.mBilling.activities.company.pos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.FirstPageActivity;
import com.lkintechnology.mBilling.activities.company.navigations.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.billsundry.BillSundryListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale.GetSaleVoucherListActivity;
import com.lkintechnology.mBilling.adapters.BillSundryListAdapter;
import com.lkintechnology.mBilling.adapters.ItemExpandableListAdapter;
import com.lkintechnology.mBilling.adapters.PosAddBillAdapter;
import com.lkintechnology.mBilling.adapters.PosAddItemsAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.bill_sundry.BillSundryData;
import com.lkintechnology.mBilling.networks.api_response.bill_sundry.GetBillSundryListResponse;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.CreateSaleVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.UpdateSaleVoucherResponse;
import com.lkintechnology.mBilling.networks.api_response.voucherseries.VoucherSeriesResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventForBillDelete;
import com.lkintechnology.mBilling.utils.EventForPos;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.ListHeight;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity.billSundryTotal;
import static com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity.mListMapForBillSale;

public class PosItemAddActivity extends AppCompatActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    RecyclerView.LayoutManager layoutManager;
    PosAddItemsAdapter mAdapter;
    public PosAddBillAdapter mBillAdapter;
    @Bind(R.id.listViewItems)
    RecyclerView mRecyclerView;
    @Bind(R.id.recycler_view_bill)
    RecyclerView mRecyclerViewBill;
    @Bind(R.id.party_name)
    TextView party_name;
    @Bind(R.id.submit_txt)
    TextView submit_txt;
    @Bind(R.id.add_bill_button)
    TextView add_bill_button;
    public static TextView mSubtotal;
    public static TextView grand_total;
    @Bind(R.id.change_layout)
    LinearLayout change_layout;
    @Bind(R.id.add_bill_layout)
    LinearLayout add_bill_layout;
    @Bind(R.id.view)
    View view;
    @Bind(R.id.submit)
    LinearLayout submit;
    Animation blinkOnClick;
    ProgressDialog mProgressDialog;
    Snackbar snackbar;
    public BillSundryData data = null;
    Boolean backPress = false;
    AppUser appUser;
    Double grandTotal = 0.0;
    Double gst_12 = 0.0, gst_18 = 0.0, gst_28 = 0.0, gst_5 = 0.0;
    String quantity = "";
    int size = 0;
    List<Map<String, String>> mapList;
    ArrayList<String> billList;

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
        if (FirstPageActivity.pos) {
            submit_txt.setText("Update");
        } else {
            submit_txt.setText("Submit");
        }
        mapList = new ArrayList<>();
        billList = new ArrayList<>();
        Intent intent = getIntent();
        Double subtotal = intent.getDoubleExtra("subtotal", 0.0);
        mSubtotal = (TextView) findViewById(R.id.subtotal);
        grand_total = (TextView) findViewById(R.id.grand_total);
        String taxString = Preferences.getInstance(getApplicationContext()).getPos_sale_type();
        if (!Preferences.getInstance(getApplicationContext()).getPos_party_name().equals("") && !Preferences.getInstance(getApplicationContext()).getPos_party_id().equals("")) {
            party_name.setText(Preferences.getInstance(getApplicationContext()).getPos_party_name()
                    + ", " + Preferences.getInstance(getApplicationContext()).getPos_mobile());
        } else {
            party_name.setText(Preferences.getInstance(getApplicationContext()).getPos_party_name()
                    + "," + Preferences.getInstance(getApplicationContext()).getMobile());
        }

        mSubtotal.setText("₹ " + String.format("%.2f", subtotal));
        grand_total.setText("₹ " + String.format("%.2f", subtotal));

        if (mListMapForBillSale.size() > 0) {
            mapList = new ArrayList<>();
            billList = new ArrayList<>();
            size = mListMapForBillSale.size();
            for (int i = 0; i < size; i++) {
                String courier_charges = mListMapForBillSale.get(i).get("courier_charges");
                if (!courier_charges.equals("IGST") && !courier_charges.equals("CGST") && !courier_charges.equals("SGST")) {
                    mapList.add(mListMapForBillSale.get(i));
                    billList.add(billSundryTotal.get(i));
                }
            }
            mListMapForBillSale.clear();
            billSundryTotal.clear();
           /* mListMapForBillSale = mapList;
            billSundryTotal = billList;*/
            System.out.println(mListMapForBillSale);
            System.out.println(mListMapForBillSale);
        }

        if (Preferences.getInstance(getApplicationContext()).getPos_sale_type().contains("GST-MultiRate")) {


            for (int i = 0; i < ExpandableItemListActivity.mListMapForItemSale.size(); i++) {
                Map map = ExpandableItemListActivity.mListMapForItemSale.get(i);
                String item_id = (String) map.get("item_id");
                String tax1 = (String) map.get("tax");
                quantity = (String) map.get("quantity");
                Double sales_price_main = (Double) map.get("sales_price_main");
                Double percentage = (Double) map.get(item_id);
                if (percentage != 0) {
                    if (percentage == 12) {
                        gst_12 = gst_12 + (Double.valueOf(quantity) * (sales_price_main * percentage) / 100);
                    } else if (percentage == 18) {
                        gst_18 = gst_18 + (Double.valueOf(quantity) * (sales_price_main * percentage) / 100);
                    } else if (percentage == 28) {
                        gst_28 = gst_28 + (Double.valueOf(quantity) * (sales_price_main * percentage) / 100);
                    } else if (percentage == 5) {
                        gst_5 = gst_5 + (Double.valueOf(quantity) * (sales_price_main * percentage) / 100);
                    }
                }
            }
            grandTotal = gst_12 + gst_18 + gst_28 + gst_5;
        }

        if ((taxString.startsWith("I") && taxString.endsWith("%")) || taxString.startsWith("L") && taxString.endsWith("%") || taxString.contains("GST-MultiRate")) {
            // add_bill_layout.setVisibility(View.GONE);
            Boolean isConnected = ConnectivityReceiver.isConnected();
            if (isConnected) {
                mProgressDialog = new ProgressDialog(PosItemAddActivity.this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_BILL_SUNDRY_LIST);
            } else {
                snackbar = Snackbar
                        .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Boolean isConnected = ConnectivityReceiver.isConnected();
                                if (isConnected) {
                                    snackbar.dismiss();
                                }
                            }
                        });
                snackbar.show();
            }
        } else {
            if (mapList.size() > 0) {
                mListMapForBillSale = mapList;
                billSundryTotal = billList;
                Double subtotal1 = txtSplit(mSubtotal.getText().toString());
                billCalculation(subtotal, true, true);
                setBillListDataAdapter();

               /* mListMapForBillSale = mapList;
                billSundryTotal = billList;
                for (int i = 0; i < mListMapForBillSale.size(); i++) {
                    Map map = mListMapForBillSale.get(i);
                    String type = (String) map.get("type");
                    if (type.equals("Additive")){
                        grandTotal = grandTotal + Double.valueOf(billSundryTotal.get(i));
                    }else {
                        grandTotal = grandTotal - Double.valueOf(billSundryTotal.get(i));
                    }
                }
                grand_total.setText("₹ " + (String.format("%.2f", txtSplit(grand_total.getText().toString()) + grandTotal)));
                setBillListDataAdapter();*/
            }
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
                if (ExpandableItemListActivity.mListMapForItemSale.size() > 0) {
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
            public void onClick(View v) {
                appUser = LocalRepositories.getAppUser(PosItemAddActivity.this);
                submit.startAnimation(blinkOnClick);
                if (ExpandableItemListActivity.mListMapForItemSale.size() > 0) {
                    if (!Preferences.getInstance(getApplicationContext()).getVoucher_number().equals("")) {
                        if (appUser.sale_partyEmail != null && !appUser.sale_partyEmail.equalsIgnoreCase("null") && !appUser.sale_partyEmail.equals("")) {
                            new AlertDialog.Builder(getApplicationContext())
                                    .setTitle("Email")
                                    .setMessage(R.string.btn_send_email)
                                    .setPositiveButton(R.string.btn_yes, (dialogInterface, i) -> {
                                        apiCall(true);
                                    })
                                    .setNegativeButton(R.string.btn_no, (dialogInterface, i) -> {
                                        apiCall(false);
                                    })
                                    .show();
                        } else {
                            apiCall(false);
                        }
                    } else {
                        Snackbar.make(coordinatorLayout, "Voucher number is blank", Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(coordinatorLayout, "Please add item", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        setDataOnItemAdapter();
    }

    void setDataOnItemAdapter() {
        mRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new PosAddItemsAdapter(this, ExpandableItemListActivity.mListMapForItemSale);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void add(View v) {
        Toast.makeText(this, "Yes", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        appUser = LocalRepositories.getAppUser(this);
        String taxString = Preferences.getInstance(getApplicationContext()).getPos_sale_type();
        EventBus.getDefault().register(this);
        if (mListMapForBillSale.size() > 0) {
            view.setVisibility(View.VISIBLE);
            if (ExpandableItemListActivity.boolForAdapterSet) {
                if (taxString.contains("GST-MultiRate")) {
                   /* if (mListMapForBillSale.size() == billSundryTotal.size()) {
                        Double subtotal = txtSplit(mSubtotal.getText().toString());
                        billCalculation(subtotal, true);
                       // billCalculationForMultiRate(subtotal, 0.0, 0.0, true, false);
                    } else {*/
                    billCalculationForMultiRate(0.0, 0.0, 0.0, false, false);
                    // if (ExpandableItemListActivity.boolForItemSubmit){
                    setBillListDataAdapter();
                    // }
                    // }
                } else {
                   /* if (mListMapForBillSale.size() == billSundryTotal.size()) {
                        Double subtotal = txtSplit(mSubtotal.getText().toString());
                        billCalculation(subtotal, true);
                    } else {*/
                    billCalculation(0.0, false, false);
                    //   }
                    setBillListDataAdapter();
                }
            }
        } else {
            view.setVisibility(View.GONE);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
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
                    Preferences.getInstance(getApplicationContext()).setPos_party_id(ParameterConstant.id);
                    Preferences.getInstance(getApplicationContext()).setPos_party_name(ParameterConstant.name);
                    Preferences.getInstance(getApplicationContext()).setPos_mobile(ParameterConstant.mobile);
                    appUser.sale_partyEmail = ParameterConstant.email;

                } else {
                    String result = data.getStringExtra("name");
                    String id = data.getStringExtra("id");
                    String mobile = data.getStringExtra("mobile");
                    String group = data.getStringExtra("group");
                    String[] strArr = result.split(",");
                    party_name.setText(strArr[0]);
                    // mMobileNumber.setText(mobile);
                    appUser.sale_partyEmail = strArr[3];
                    Preferences.getInstance(getApplicationContext()).setPos_party_id(id);
                    Preferences.getInstance(getApplicationContext()).setPos_party_name(strArr[0]);
                    Preferences.getInstance(getApplicationContext()).setPos_mobile(mobile);
                    return;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        FirstPageActivity.posSetting = true;
        FirstPageActivity.posNotifyAdapter = true;
        ExpandableItemListActivity.mChildCheckStates.clear();
        ExpandableItemListActivity.mMapPosItem.clear();
        if (backPress) {
            FirstPageActivity.posSetting = false;
            FirstPageActivity.posNotifyAdapter = false;
        }
        finish();
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                FirstPageActivity.posSetting = true;
                FirstPageActivity.posNotifyAdapter = true;
                ExpandableItemListActivity.mChildCheckStates.clear();
                if (backPress) {
                    FirstPageActivity.posSetting = false;
                    FirstPageActivity.posNotifyAdapter = false;
                }
                finish();
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void alertdialog() {
        new AlertDialog.Builder(getApplicationContext())
                .setTitle("Sale Voucher")
                .setMessage("Please add item!!!")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    return;

                })
                .show();
    }

    @Subscribe
    public void event_click_alert(EventForPos response) {
        String taxString = Preferences.getInstance(getApplicationContext()).getPos_sale_type();
        if (mListMapForBillSale.size() > 0) {
            if (taxString.contains("GST-MultiRate")) {
                String arr[] = response.getPosition().split(",");
                Double total = Double.valueOf(arr[0]);
                Double gst = Double.valueOf(arr[1]);
                Double taxValue = Double.valueOf(arr[2]);
                Boolean mBool = Boolean.valueOf(arr[3]);
                billCalculationForMultiRate(total, gst, taxValue, true, mBool);
                setBillListDataAdapter();
            } else {
                billCalculation(Double.valueOf(response.getPosition()), true, false);
                setBillListDataAdapter();
            }
        }
    }

    @Subscribe
    public void eventDeleteBill(EventForBillDelete response) {
        String[] data = response.getPosition().split(",");
        int position = Integer.parseInt(data[0]);
        String type = data[1];
        String grandTotal = "0.0";
        if (type.equals("Additive")) {
            grandTotal = String.format("%.2f", getTotal(grand_total.getText().toString()) - Double.valueOf(billSundryTotal.get(position)));
        } else {
            grandTotal = String.format("%.2f", getTotal(grand_total.getText().toString()) + Double.valueOf(billSundryTotal.get(position)));
        }
        grand_total.setText("₹ " + grandTotal);
        mListMapForBillSale.remove(position);
        billSundryTotal.remove(position);
        if (mListMapForBillSale.size() > 0) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        setBillListDataAdapter();
    }

    public void setBillListDataAdapter() {
        mRecyclerViewBill.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerViewBill.setLayoutManager(layoutManager);
        mBillAdapter = new PosAddBillAdapter(this, mListMapForBillSale, billSundryTotal);
        mRecyclerViewBill.setAdapter(mBillAdapter);
        //mBillAdapter.notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        // billCalculation();
        mRecyclerViewBill.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        //  mRecyclerViewBill.setLayoutManager(layoutManager);
        // mBillAdapter = new PosAddBillAdapter(this, mListMapForBillSale, false);
        mRecyclerViewBill.setAdapter(mBillAdapter);
        notifyDataSetChanged();
    }

    public void billCalculation(Double subtotal, Boolean aBoolean, Boolean backBool) {
        Double grandTotal = 0.0;
        if (aBoolean) {
            grandTotal = subtotal;
        } else {
            grandTotal = txtSplit(grand_total.getText().toString());
        }
        for (int i = 0; i < mListMapForBillSale.size(); i++) {
            Double total = 0.0;
            Map map = mListMapForBillSale.get(i);
            String itemName = (String) map.get("courier_charges");
            String amount = (String) map.get("amount");
            String fed_as_percentage = (String) map.get("fed_as_percentage");
            if (fed_as_percentage == null) {
                fed_as_percentage = "";
            }
            String fed_as = (String) map.get("fed_as");
            String type = (String) map.get("type");
            if (fed_as_percentage != null) {
                if (fed_as_percentage.equals("valuechange")) {
                    Double changeamount = Double.parseDouble((String) map.get("changeamount"));
                    total = changeamount;
                    if (!aBoolean) {
                        if (mListMapForBillSale.size() - 1 == i) {
                            if (type.equals("Additive")) {
                                grandTotal = grandTotal + total;
                            } else {
                                grandTotal = grandTotal - total;
                            }
                            billSundryTotal.add(i, String.format("%.2f", total));
                        }
                    } else {
                        if (type.equals("Additive")) {
                            grandTotal = grandTotal + total;
                        } else {
                            grandTotal = grandTotal - total;
                        }
                        billSundryTotal.set(i, String.format("%.2f", total));
                    }

                } else {
                    if (!aBoolean) {
                        if (mListMapForBillSale.size() - 1 == i) {
                            total = (grandTotal * Double.valueOf(amount)) / 100;
                            if (type.equals("Additive")) {
                                grandTotal = grandTotal + total;
                            } else {
                                grandTotal = grandTotal - total;
                            }
                            billSundryTotal.add(i, String.format("%.2f", total));
                        }
                    } else {
                        if (!backBool) {
                            total = (grandTotal * Double.valueOf(amount)) / 100;
                            if (type.equals("Additive")) {
                                grandTotal = grandTotal + total;
                            } else {
                                grandTotal = grandTotal - total;
                            }
                            billSundryTotal.set(i, String.format("%.2f", total));
                        } else {
                            if (itemName.equals("IGST") || itemName.equals("CGST") || itemName.equals("SGST")) {
                                if (type.equals("Additive")) {
                                    grandTotal = grandTotal + Double.valueOf(billSundryTotal.get(i));
                                } else {
                                    grandTotal = grandTotal - Double.valueOf(billSundryTotal.get(i));
                                }
                            } else {
                                total = (grandTotal * Double.valueOf(amount)) / 100;
                                if (type.equals("Additive")) {
                                    grandTotal = grandTotal + total;
                                } else {
                                    grandTotal = grandTotal - total;
                                }
                                billSundryTotal.set(i, String.format("%.2f", total));
                            }
                        }
                    }
                }
            }
        }
        // LocalRepositories.saveAppUser(getApplicationContext(), appUser);

        // PosItemAddActivity.mSubtotal.setText("₹ " + String.format("%.2f", subtotal));
        PosItemAddActivity.grand_total.setText("₹ " + String.format("%.2f", grandTotal));
        System.out.println(grandTotal);
    }

    public void billCalculationForMultiRate(Double subtotal, Double gst, double taxValue, Boolean aBoolean, Boolean mBool) {
        if (aBoolean) {
            grandTotal = subtotal;
        } else {
            grandTotal = txtSplit(grand_total.getText().toString());
        }
        for (int i = 0; i < mListMapForBillSale.size(); i++) {
            Double total = 0.0;
            Map map = mListMapForBillSale.get(i);
            String itemName = (String) map.get("courier_charges");
            double amount = Double.valueOf((String) map.get("amount"));
            String fed_as_percentage = (String) map.get("fed_as_percentage");
            String fed_as = (String) map.get("fed_as");
            String type = (String) map.get("type");
            if (fed_as_percentage != null) {
                if (fed_as_percentage.equals("valuechange")) {
                    Double changeamount = Double.parseDouble((String) map.get("changeamount"));
                    total = changeamount;
                    if (!aBoolean) {
                        if (mListMapForBillSale.size() - 1 == i) {
                            if (type.equals("Additive")) {
                                grandTotal = grandTotal + total;
                            } else {
                                grandTotal = grandTotal - total;
                            }
                            billSundryTotal.add(i, String.format("%.2f", total));
                        }
                    } else {
                        if (type.equals("Additive")) {
                            grandTotal = grandTotal + total;
                        } else {
                            grandTotal = grandTotal - total;
                        }
                        billSundryTotal.set(i, String.format("%.2f", total));
                    }
                } else {
                    if (!aBoolean) {
                        if (!itemName.equals("IGST") && !itemName.equals("SGST") && !itemName.equals("CGST")) {
                            total = (grandTotal * Double.valueOf(amount)) / 100;
                            if (mListMapForBillSale.size() - 1 == i) {
                                if (type.equals("Additive")) {
                                    grandTotal = grandTotal + total;
                                } else {
                                    grandTotal = grandTotal - total;
                                }
                                billSundryTotal.add(i, String.format("%.2f", total));
                            } /*else {
                                if (type.equals("Additive")) {
                                    grandTotal = grandTotal + total;
                                } else {
                                    grandTotal = grandTotal - total;
                                }
                                billSundryTotal.set(i, String.format("%.2f", total));
                            }*/
                        }
                    } else {
                        if (Preferences.getInstance(getApplicationContext()).getPos_sale_type().equals("I/GST-MultiRate")) {
                            if (itemName.equals("IGST")) {
                                total = Double.valueOf(billSundryTotal.get(i));
                                if (amount == taxValue) {
                                    if (mBool) {
                                        total = total + gst;
                                        grandTotal = (grandTotal + total)/* + gst*/;
                                    } else {
                                        total = total - gst;
                                        grandTotal = (grandTotal + total)/* - gst*/;
                                    }
                                    billSundryTotal.set(i, String.format("%.2f", total));
                                } else {
                                    grandTotal = grandTotal + total;
                                    billSundryTotal.set(i, String.format("%.2f", total));
                                }
                            } else {
                                total = (grandTotal * Double.valueOf(amount)) / 100;
                                if (type.equals("Additive")) {
                                    grandTotal = grandTotal + total;
                                } else {
                                    grandTotal = grandTotal - total;
                                }
                                billSundryTotal.set(i, String.format("%.2f", total));
                            }
                        } else {
                            if (itemName.equals("SGST") || itemName.equals("CGST")) {
                                total = Double.valueOf(billSundryTotal.get(i));
                                if (amount == (taxValue / 2)) {
                                    if (mBool) {
                                        total = total + (gst / 2);
                                        grandTotal = (grandTotal + total) /*+ (gst / 2)*/;
                                    } else {
                                        total = total - (gst / 2);
                                        grandTotal = (grandTotal + total) /*- (gst / 2)*/;
                                    }
                                    billSundryTotal.set(i, String.format("%.2f", total));
                                } else {
                                    grandTotal = grandTotal + total;
                                    billSundryTotal.set(i, String.format("%.2f", total));
                                }
                            } else {
                                total = (grandTotal * Double.valueOf(amount)) / 100;
                                if (type.equals("Additive")) {
                                    grandTotal = grandTotal + total;
                                } else {
                                    grandTotal = grandTotal - total;
                                }
                                billSundryTotal.set(i, String.format("%.2f", total));
                            }
                        }
                    }
                }
            }
        }
        // LocalRepositories.saveAppUser(getApplicationContext(), appUser);
        PosItemAddActivity.grand_total.setText("₹ " + String.format("%.2f", grandTotal));
        System.out.println(grandTotal);
    }

    public Double txtSplit(String total) {
        Double a = 0.0;
        String[] arr = total.split("₹ ");
        a = Double.valueOf(arr[1].trim());
        return a;
    }

    @Subscribe
    public void getBillSundryList(GetBillSundryListResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            appUser.arr_billSundryId.clear();
            appUser.arr_billSundryName.clear();
            appUser.billSundryName.clear();
            appUser.billSundryId.clear();
            LocalRepositories.saveAppUser(this, appUser);
            String taxString = Preferences.getInstance(getApplicationContext()).getPos_sale_type();
          /*  Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {*/
            for (int i = 0; i < response.getBill_sundries().getData().size(); i++) {
                appUser.billSundryName.add(response.getBill_sundries().getData().get(i).getAttributes().getName());
                appUser.billSundryId.add(response.getBill_sundries().getData().get(i).getId());
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                if (response.getBill_sundries().getData().get(i).getAttributes().getName().equals("IGST")
                        || response.getBill_sundries().getData().get(i).getAttributes().getName().equals("CGST")
                        || response.getBill_sundries().getData().get(i).getAttributes().getName().equals("SGST")) {
                    if ((taxString.startsWith("I") && taxString.endsWith("%"))
                            || (taxString.startsWith("L") && taxString.endsWith("%"))) {
                        data = response.getBill_sundries().getData().get(i);
                        gstBillSundryCalculation(data);
                    }
                }
                if (taxString.contains("GST-MultiRate")) {
                    if (taxString.contains("I/GST-MultiRate")) {
                        if (response.getBill_sundries().getData().get(i).getAttributes().getName().equals("IGST")) {
                            data = response.getBill_sundries().getData().get(i);
                            gstBillSundryCalculation(data);
                        }
                    } else if (taxString.contains("L/GST-MultiRate")) {
                        if (response.getBill_sundries().getData().get(i).getAttributes().getName().equals("CGST")
                                || response.getBill_sundries().getData().get(i).getAttributes().getName().equals("SGST")) {
                            data = response.getBill_sundries().getData().get(i);
                            gstBillSundryCalculation(data);
                        }
                    }
                }
            }
           /*     }
            }, 1);*/

            if (billList.size() > 0) {
                int size = billSundryTotal.size() + billList.size();
                int billSize = billSundryTotal.size();
                for (int i = 0; i < billList.size(); i++) {
                    mListMapForBillSale.add(mapList.get(i));
                    billSundryTotal.add(billList.get(i));
                }
                Double subtotal = txtSplit(mSubtotal.getText().toString());
                billCalculation(subtotal, true, true);
                setBillListDataAdapter();
            } else {
                grand_total.setText("₹ " + (String.format("%.2f", txtSplit(grand_total.getText().toString()) + grandTotal)));
                setBillListDataAdapter();
            }

        } else {
            Helpers.dialogMessage(this, response.getMessage());
        }
    }

    void gstBillSundryCalculation(BillSundryData data) {
        Boolean boolForMap = false;
        String billSundaryPercentage;
        String billSundryAmount = "0";
        String billSundryCharges;
        String billsundryothername = "";
        String billSundryId;
        String billSundryFedAs;
        String billSundryFedAsPercentage;
        String billSundryFedAsPercentagePrevious;
        String billSundryType;
        String changeAmount = "0.0";
        Double billSundryDefaultValue = 0.0;
        int billSundryNumber;
        Boolean billSundryConsolidated;
        double taxval = 0.0;
        String id = "";
        Map<String, String> mMap;

        String taxString = Preferences.getInstance(getApplicationContext()).getPos_sale_type();
        billSundryCharges = data.getAttributes().getName();
        billSundryFedAsPercentagePrevious = data.getAttributes().getBill_sundry_of_percentage();
        billSundaryPercentage = data.getAttributes().getBill_sundry_percentage_value();
        billSundryFedAs = data.getAttributes().getAmount_of_bill_sundry_fed_as();
        billSundryDefaultValue = data.getAttributes().getDefault_value();
        billSundryFedAsPercentage = "";
        /*if (data.getAttributes().getBill_sundry_of_percentage() != null) {
            billSundryFedAsPercentage = data.getAttributes().getBill_sundry_of_percentage();
        } else {
            billSundryFedAsPercentage = "";
        }*/
        billSundryType = data.getAttributes().getBill_sundry_type();
        billSundryNumber = data.getAttributes().getNumber_of_bill_sundry();
        billSundryConsolidated = data.getAttributes().isConsolidate_bill_sundry();
        billSundryId = data.getId();
        /*if (billSundryFedAsPercentage.equals("valuechange")) {
            billAmount = "0.0";
        } else {
            billAmount = billSundryAmount;
        }*/


        if ((taxString.startsWith("I") && taxString.endsWith("%")) || (taxString.startsWith("L") && taxString.endsWith("%"))) {
            String arrtaxstring[] = taxString.split("-");
            String taxname = arrtaxstring[0].trim();
            String taxvalue = arrtaxstring[1].trim();
            String taxvalpercent[] = taxvalue.split("%");
            String taxvalpercentval = taxvalpercent[0];
            taxval = Double.parseDouble(taxvalpercentval);
        }


        mMap = new HashMap<>();
        if (billSundryCharges.equals("IGST")) {
            if (taxString.startsWith("I") && taxString.endsWith("%")) {
                boolForMap = true;
                Double subtotal = txtSplit(PosItemAddActivity.mSubtotal.getText().toString());
                changeAmount = String.valueOf((subtotal * taxval) / 100);
                billSundryAmount = String.valueOf(taxval);
                billSundryTotal.add(changeAmount);
                grandTotal = grandTotal + Double.valueOf(changeAmount);
                // LocalRepositories.saveAppUser(PosItemAddActivity.this,appUser);
            }
        } else {
            if (taxString.startsWith("L") && taxString.endsWith("%")) {
                boolForMap = true;
                Double subtotal = txtSplit(PosItemAddActivity.mSubtotal.getText().toString());
                changeAmount = String.valueOf((subtotal * taxval / 2) / 100);
                billSundryAmount = String.valueOf(taxval / 2);
                billSundryTotal.add("" + Double.valueOf(changeAmount));
                // billSundryTotal.add("" + Double.valueOf(changeAmount));
                grandTotal = grandTotal + Double.valueOf(changeAmount);
                // LocalRepositories.saveAppUser(PosItemAddActivity.this,appUser);
                taxval = taxval / 2;
            }
        }

        if (taxString.contains("I/GST-MultiRate")) {
            if (gst_5 != 0) {
                mMap = new HashMap<>();
                changeAmount = String.format("%.2f", gst_5);
                billSundryAmount = "5";
                billSundryTotal.add(changeAmount);
                mMap.put("id", id);
                mMap.put("courier_charges", billSundryCharges);
                mMap.put("bill_sundry_id", billSundryId);
                mMap.put("percentage", billSundryAmount);
                mMap.put("percentage_value", billSundaryPercentage);
                mMap.put("default_unit", String.valueOf(billSundryDefaultValue));
                mMap.put("fed_as", billSundryFedAs);
                mMap.put("fed_as_percentage", billSundryFedAsPercentage);
                mMap.put("type", billSundryType);
                mMap.put("amount", billSundryAmount);
                mMap.put("previous", billSundryFedAsPercentagePrevious);
                mMap.put("gst_5", "5");
                if (String.valueOf(billSundryNumber) != null) {
                    mMap.put("number_of_bill", String.valueOf(billSundryNumber));
                }
                if (String.valueOf(billSundryConsolidated) != null) {
                    mMap.put("consolidated", String.valueOf(billSundryConsolidated));
                }
                mMap.put("changeamount", changeAmount);
               /* if (billSundryFedAsPercentage != null) {
                    if (billSundryFedAsPercentage.equals("valuechange")) {
                        mMap.put("changeamount", changeAmount);
                    }
                }*/
                if (String.valueOf(billSundryId) != null) {
                    int size = appUser.arr_billSundryId.size();
                    for (int j = 0; j < size; j++) {
                        String id1 = appUser.arr_billSundryId.get(j);
                        if (id1.equals(String.valueOf(billSundryId))) {
                            billsundryothername = appUser.arr_billSundryName.get(j);
                            break;
                        }
                    }
                    mMap.put("other", billsundryothername);
                }
                mListMapForBillSale.add(mMap);
                //LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            }
            if (gst_12 != 0) {
                mMap = new HashMap<>();
                changeAmount = String.format("%.2f", gst_12);
                billSundryAmount = "12";
                billSundryTotal.add(changeAmount);
                mMap.put("id", id);
                mMap.put("courier_charges", billSundryCharges);
                mMap.put("bill_sundry_id", billSundryId);
                mMap.put("percentage", billSundryAmount);
                mMap.put("percentage_value", billSundaryPercentage);
                mMap.put("default_unit", String.valueOf(billSundryDefaultValue));
                mMap.put("fed_as", billSundryFedAs);
                mMap.put("fed_as_percentage", billSundryFedAsPercentage);
                mMap.put("type", billSundryType);
                mMap.put("amount", billSundryAmount);
                mMap.put("previous", billSundryFedAsPercentagePrevious);
                mMap.put("gst_12", "12");
                if (String.valueOf(billSundryNumber) != null) {
                    mMap.put("number_of_bill", String.valueOf(billSundryNumber));
                }
                if (String.valueOf(billSundryConsolidated) != null) {
                    mMap.put("consolidated", String.valueOf(billSundryConsolidated));
                }
                mMap.put("changeamount", changeAmount);
               /* if (billSundryFedAsPercentage != null) {
                    if (billSundryFedAsPercentage.equals("valuechange")) {
                        mMap.put("changeamount", changeAmount);
                    }
                }*/
                if (String.valueOf(billSundryId) != null) {
                    int size = appUser.arr_billSundryId.size();
                    for (int j = 0; j < size; j++) {
                        String id1 = appUser.arr_billSundryId.get(j);
                        if (id1.equals(String.valueOf(billSundryId))) {
                            billsundryothername = appUser.arr_billSundryName.get(j);
                            break;
                        }
                    }
                    mMap.put("other", billsundryothername);
                }
                mListMapForBillSale.add(mMap);
                // LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            }
            if (gst_18 != 0) {
                mMap = new HashMap<>();
                changeAmount = String.format("%.2f", gst_18);
                billSundryAmount = "18";
                billSundryTotal.add(changeAmount);
                mMap.put("id", id);
                mMap.put("courier_charges", billSundryCharges);
                mMap.put("bill_sundry_id", billSundryId);
                mMap.put("percentage", billSundryAmount);
                mMap.put("percentage_value", billSundaryPercentage);
                mMap.put("default_unit", String.valueOf(billSundryDefaultValue));
                mMap.put("fed_as", billSundryFedAs);
                mMap.put("fed_as_percentage", billSundryFedAsPercentage);
                mMap.put("type", billSundryType);
                mMap.put("amount", billSundryAmount);
                mMap.put("previous", billSundryFedAsPercentagePrevious);
                mMap.put("gst_18", "18");
                if (String.valueOf(billSundryNumber) != null) {
                    mMap.put("number_of_bill", String.valueOf(billSundryNumber));
                }
                if (String.valueOf(billSundryConsolidated) != null) {
                    mMap.put("consolidated", String.valueOf(billSundryConsolidated));
                }
                mMap.put("changeamount", changeAmount);
               /* if (billSundryFedAsPercentage != null) {
                    if (billSundryFedAsPercentage.equals("valuechange")) {
                        mMap.put("changeamount", changeAmount);
                    }
                }*/
                if (String.valueOf(billSundryId) != null) {
                    int size = appUser.arr_billSundryId.size();
                    for (int j = 0; j < size; j++) {
                        String id1 = appUser.arr_billSundryId.get(j);
                        if (id1.equals(String.valueOf(billSundryId))) {
                            billsundryothername = appUser.arr_billSundryName.get(j);
                            break;
                        }
                    }
                    mMap.put("other", billsundryothername);
                }
                mListMapForBillSale.add(mMap);
                //  LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            }
            if (gst_28 != 0) {
                mMap = new HashMap<>();
                changeAmount = String.format("%.2f", gst_28);
                billSundryAmount = "28";
                billSundryTotal.add(changeAmount);
                mMap.put("id", id);
                mMap.put("courier_charges", billSundryCharges);
                mMap.put("bill_sundry_id", billSundryId);
                mMap.put("percentage", billSundryAmount);
                mMap.put("percentage_value", billSundaryPercentage);
                mMap.put("default_unit", String.valueOf(billSundryDefaultValue));
                mMap.put("fed_as", billSundryFedAs);
                mMap.put("fed_as_percentage", billSundryFedAsPercentage);
                mMap.put("type", billSundryType);
                mMap.put("amount", billSundryAmount);
                mMap.put("previous", billSundryFedAsPercentagePrevious);
                mMap.put("gst_28", "28");
                if (String.valueOf(billSundryNumber) != null) {
                    mMap.put("number_of_bill", String.valueOf(billSundryNumber));
                }
                if (String.valueOf(billSundryConsolidated) != null) {
                    mMap.put("consolidated", String.valueOf(billSundryConsolidated));
                }
                mMap.put("changeamount", changeAmount);
              /*  if (billSundryFedAsPercentage != null) {
                    if (billSundryFedAsPercentage.equals("valuechange")) {
                        mMap.put("changeamount", changeAmount);
                    }
                }*/
                if (String.valueOf(billSundryId) != null) {
                    int size = appUser.arr_billSundryId.size();
                    for (int j = 0; j < size; j++) {
                        String id1 = appUser.arr_billSundryId.get(j);
                        if (id1.equals(String.valueOf(billSundryId))) {
                            billsundryothername = appUser.arr_billSundryName.get(j);
                            break;
                        }
                    }
                    mMap.put("other", billsundryothername);
                }
                mListMapForBillSale.add(mMap);
                //  LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            }
        }
        if (taxString.contains("L/GST-MultiRate")) {
            if (gst_5 != 0) {
                if (billSundryCharges.equals("CGST")){
                    mMap = new HashMap<>();
                    changeAmount = String.format("%.2f", gst_5 / 2);
                    billSundryAmount = "2.5";
                    billSundryTotal.add(changeAmount);
                    mMap.put("id", id);
                    mMap.put("courier_charges", "CGST");
                    mMap.put("bill_sundry_id", billSundryId);
                    mMap.put("percentage", billSundryAmount);
                    mMap.put("percentage_value", billSundaryPercentage);
                    mMap.put("default_unit", String.valueOf(billSundryDefaultValue));
                    mMap.put("fed_as", billSundryFedAs);
                    mMap.put("fed_as_percentage", billSundryFedAsPercentage);
                    mMap.put("type", billSundryType);
                    mMap.put("amount", billSundryAmount);
                    mMap.put("previous", billSundryFedAsPercentagePrevious);
                    if (String.valueOf(billSundryNumber) != null) {
                        mMap.put("number_of_bill", String.valueOf(billSundryNumber));
                    }
                    if (String.valueOf(billSundryConsolidated) != null) {
                        mMap.put("consolidated", String.valueOf(billSundryConsolidated));
                    }
                    mMap.put("changeamount", changeAmount);
             /*   if (billSundryFedAsPercentage != null) {
                    if (billSundryFedAsPercentage.equals("valuechange")) {
                        mMap.put("changeamount", changeAmount);
                    }
                }*/
                    if (String.valueOf(billSundryId) != null) {
                        int size = appUser.arr_billSundryId.size();
                        for (int j = 0; j < size; j++) {
                            String id1 = appUser.arr_billSundryId.get(j);
                            if (id1.equals(String.valueOf(billSundryId))) {
                                billsundryothername = appUser.arr_billSundryName.get(j);
                                break;
                            }
                        }
                        mMap.put("other", billsundryothername);
                    }
                    mListMapForBillSale.add(mMap);
                    //   LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }

                if (billSundryCharges.equals("SGST")){
                    mMap = new HashMap<>();
                    changeAmount = String.format("%.2f", gst_5 / 2);
                    billSundryAmount = "2.5";
                    billSundryTotal.add(changeAmount);
                    mMap.put("id", id);
                    mMap.put("courier_charges", "SGST");
                    mMap.put("bill_sundry_id", billSundryId);
                    mMap.put("percentage", billSundryAmount);
                    mMap.put("percentage_value", billSundaryPercentage);
                    mMap.put("default_unit", String.valueOf(billSundryDefaultValue));
                    mMap.put("fed_as", billSundryFedAs);
                    mMap.put("fed_as_percentage", billSundryFedAsPercentage);
                    mMap.put("type", billSundryType);
                    mMap.put("amount", billSundryAmount);
                    mMap.put("previous", billSundryFedAsPercentagePrevious);
                    if (String.valueOf(billSundryNumber) != null) {
                        mMap.put("number_of_bill", String.valueOf(billSundryNumber));
                    }
                    if (String.valueOf(billSundryConsolidated) != null) {
                        mMap.put("consolidated", String.valueOf(billSundryConsolidated));
                    }
                    mMap.put("changeamount", changeAmount);
               /* if (billSundryFedAsPercentage != null) {
                    if (billSundryFedAsPercentage.equals("valuechange")) {
                        mMap.put("changeamount", changeAmount);
                    }
                }*/
                    if (String.valueOf(billSundryId) != null) {
                        int size = appUser.arr_billSundryId.size();
                        for (int j = 0; j < size; j++) {
                            String id1 = appUser.arr_billSundryId.get(j);
                            if (id1.equals(String.valueOf(billSundryId))) {
                                billsundryothername = appUser.arr_billSundryName.get(j);
                                break;
                            }
                        }
                        mMap.put("other", billsundryothername);
                    }
                    mListMapForBillSale.add(mMap);
                    //  LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                  //  gst_5 = 0.0;
                }
            }
            if (gst_12 != 0) {
                if (billSundryCharges.equals("CGST")){
                    mMap = new HashMap<>();
                    changeAmount = String.format("%.2f", gst_12 / 2);
                    billSundryAmount = "6";
                    billSundryTotal.add(changeAmount);
                    mMap.put("id", id);
                    mMap.put("courier_charges", "CGST");
                    mMap.put("bill_sundry_id", billSundryId);
                    mMap.put("percentage", billSundryAmount);
                    mMap.put("percentage_value", billSundaryPercentage);
                    mMap.put("default_unit", String.valueOf(billSundryDefaultValue));
                    mMap.put("fed_as", billSundryFedAs);
                    mMap.put("fed_as_percentage", billSundryFedAsPercentage);
                    mMap.put("type", billSundryType);
                    mMap.put("amount", billSundryAmount);
                    mMap.put("previous", billSundryFedAsPercentagePrevious);
                    if (String.valueOf(billSundryNumber) != null) {
                        mMap.put("number_of_bill", String.valueOf(billSundryNumber));
                    }
                    if (String.valueOf(billSundryConsolidated) != null) {
                        mMap.put("consolidated", String.valueOf(billSundryConsolidated));
                    }
                    mMap.put("changeamount", changeAmount);
               /* if (billSundryFedAsPercentage != null) {
                    if (billSundryFedAsPercentage.equals("valuechange")) {
                        mMap.put("changeamount", changeAmount);
                    }
                }*/
                    if (String.valueOf(billSundryId) != null) {
                        int size = appUser.arr_billSundryId.size();
                        for (int j = 0; j < size; j++) {
                            String id1 = appUser.arr_billSundryId.get(j);
                            if (id1.equals(String.valueOf(billSundryId))) {
                                billsundryothername = appUser.arr_billSundryName.get(j);
                                break;
                            }
                        }
                        mMap.put("other", billsundryothername);
                    }
                    mListMapForBillSale.add(mMap);
                    // LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
                if (billSundryCharges.equals("SGST")){
                    mMap = new HashMap<>();
                    changeAmount = String.format("%.2f", gst_12 / 2);
                    billSundryAmount = "6";
                    billSundryTotal.add(changeAmount);
                    mMap.put("id", id);
                    mMap.put("courier_charges", "SGST");
                    mMap.put("bill_sundry_id", billSundryId);
                    mMap.put("percentage", billSundryAmount);
                    mMap.put("percentage_value", billSundaryPercentage);
                    mMap.put("default_unit", String.valueOf(billSundryDefaultValue));
                    mMap.put("fed_as", billSundryFedAs);
                    mMap.put("fed_as_percentage", billSundryFedAsPercentage);
                    mMap.put("type", billSundryType);
                    mMap.put("amount", billSundryAmount);
                    mMap.put("previous", billSundryFedAsPercentagePrevious);
                    if (String.valueOf(billSundryNumber) != null) {
                        mMap.put("number_of_bill", String.valueOf(billSundryNumber));
                    }
                    if (String.valueOf(billSundryConsolidated) != null) {
                        mMap.put("consolidated", String.valueOf(billSundryConsolidated));
                    }
                    mMap.put("changeamount", changeAmount);
                /*if (billSundryFedAsPercentage != null) {
                    if (billSundryFedAsPercentage.equals("valuechange")) {
                        mMap.put("changeamount", changeAmount);
                    }
                }*/
                    if (String.valueOf(billSundryId) != null) {
                        int size = appUser.arr_billSundryId.size();
                        for (int j = 0; j < size; j++) {
                            String id1 = appUser.arr_billSundryId.get(j);
                            if (id1.equals(String.valueOf(billSundryId))) {
                                billsundryothername = appUser.arr_billSundryName.get(j);
                                break;
                            }
                        }
                        mMap.put("other", billsundryothername);
                    }
                    mListMapForBillSale.add(mMap);
                    //  LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                  //  gst_12 = 0.0;
                }
            }
            if (gst_18 != 0) {
                if (billSundryCharges.equals("CGST")){
                    mMap = new HashMap<>();
                    changeAmount = String.format("%.2f", gst_18 / 2);
                    billSundryAmount = "9";
                    billSundryTotal.add(changeAmount);
                    mMap.put("id", id);
                    mMap.put("courier_charges", "CGST");
                    mMap.put("bill_sundry_id", billSundryId);
                    mMap.put("percentage", billSundryAmount);
                    mMap.put("percentage_value", billSundaryPercentage);
                    mMap.put("default_unit", String.valueOf(billSundryDefaultValue));
                    mMap.put("fed_as", billSundryFedAs);
                    mMap.put("fed_as_percentage", billSundryFedAsPercentage);
                    mMap.put("type", billSundryType);
                    mMap.put("amount", billSundryAmount);
                    mMap.put("previous", billSundryFedAsPercentagePrevious);
                    if (String.valueOf(billSundryNumber) != null) {
                        mMap.put("number_of_bill", String.valueOf(billSundryNumber));
                    }
                    if (String.valueOf(billSundryConsolidated) != null) {
                        mMap.put("consolidated", String.valueOf(billSundryConsolidated));
                    }
                    mMap.put("changeamount", changeAmount);
              /*  if (billSundryFedAsPercentage != null) {
                    if (billSundryFedAsPercentage.equals("valuechange")) {
                        mMap.put("changeamount", changeAmount);
                    }
                }*/
                    if (String.valueOf(billSundryId) != null) {
                        int size = appUser.arr_billSundryId.size();
                        for (int j = 0; j < size; j++) {
                            String id1 = appUser.arr_billSundryId.get(j);
                            if (id1.equals(String.valueOf(billSundryId))) {
                                billsundryothername = appUser.arr_billSundryName.get(j);
                                break;
                            }
                        }
                        mMap.put("other", billsundryothername);
                    }
                    mListMapForBillSale.add(mMap);
                    //    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }

                if (billSundryCharges.equals("SGST")){
                    mMap = new HashMap<>();
                    changeAmount = String.format("%.2f", gst_18 / 2);
                    billSundryAmount = "9";
                    billSundryTotal.add(changeAmount);
                    mMap.put("id", id);
                    mMap.put("courier_charges", "SGST");
                    mMap.put("bill_sundry_id", billSundryId);
                    mMap.put("percentage", billSundryAmount);
                    mMap.put("percentage_value", billSundaryPercentage);
                    mMap.put("default_unit", String.valueOf(billSundryDefaultValue));
                    mMap.put("fed_as", billSundryFedAs);
                    mMap.put("fed_as_percentage", billSundryFedAsPercentage);
                    mMap.put("type", billSundryType);
                    mMap.put("amount", billSundryAmount);
                    mMap.put("previous", billSundryFedAsPercentagePrevious);
                    if (String.valueOf(billSundryNumber) != null) {
                        mMap.put("number_of_bill", String.valueOf(billSundryNumber));
                    }
                    if (String.valueOf(billSundryConsolidated) != null) {
                        mMap.put("consolidated", String.valueOf(billSundryConsolidated));
                    }
                    mMap.put("changeamount", changeAmount);
               /* if (billSundryFedAsPercentage != null) {
                    if (billSundryFedAsPercentage.equals("valuechange")) {
                        mMap.put("changeamount", changeAmount);
                    }
                }*/
                    if (String.valueOf(billSundryId) != null) {
                        int size = appUser.arr_billSundryId.size();
                        for (int j = 0; j < size; j++) {
                            String id1 = appUser.arr_billSundryId.get(j);
                            if (id1.equals(String.valueOf(billSundryId))) {
                                billsundryothername = appUser.arr_billSundryName.get(j);
                                break;
                            }
                        }
                        mMap.put("other", billsundryothername);
                    }
                    mListMapForBillSale.add(mMap);
                    //   LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                   // gst_18 = 0.0;
                }
            }
            if (gst_28 != 0) {
                if (billSundryCharges.equals("CGST")){
                    mMap = new HashMap<>();
                    changeAmount = String.format("%.2f", gst_28 / 2);
                    billSundryAmount = "14";
                    billSundryTotal.add(changeAmount);
                    mMap.put("id", id);
                    mMap.put("courier_charges", "CGST");
                    mMap.put("bill_sundry_id", billSundryId);
                    mMap.put("percentage", billSundryAmount);
                    mMap.put("percentage_value", billSundaryPercentage);
                    mMap.put("default_unit", String.valueOf(billSundryDefaultValue));
                    mMap.put("fed_as", billSundryFedAs);
                    mMap.put("fed_as_percentage", billSundryFedAsPercentage);
                    mMap.put("type", billSundryType);
                    mMap.put("amount", billSundryAmount);
                    mMap.put("previous", billSundryFedAsPercentagePrevious);
                    if (String.valueOf(billSundryNumber) != null) {
                        mMap.put("number_of_bill", String.valueOf(billSundryNumber));
                    }
                    if (String.valueOf(billSundryConsolidated) != null) {
                        mMap.put("consolidated", String.valueOf(billSundryConsolidated));
                    }
                    mMap.put("changeamount", changeAmount);
               /* if (billSundryFedAsPercentage != null) {
                    if (billSundryFedAsPercentage.equals("valuechange")) {
                        mMap.put("changeamount", changeAmount);
                    }
                }*/
                    if (String.valueOf(billSundryId) != null) {
                        int size = appUser.arr_billSundryId.size();
                        for (int j = 0; j < size; j++) {
                            String id1 = appUser.arr_billSundryId.get(j);
                            if (id1.equals(String.valueOf(billSundryId))) {
                                billsundryothername = appUser.arr_billSundryName.get(j);
                                break;
                            }
                        }
                        mMap.put("other", billsundryothername);
                    }
                    mListMapForBillSale.add(mMap);
                    //   LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }

                if (billSundryCharges.equals("SGST")){
                    mMap = new HashMap<>();
                    changeAmount = String.format("%.2f", gst_28 / 2);
                    billSundryAmount = "14";
                    billSundryTotal.add(changeAmount);
                    mMap.put("id", id);
                    mMap.put("courier_charges", "SGST");
                    mMap.put("bill_sundry_id", billSundryId);
                    mMap.put("percentage", billSundryAmount);
                    mMap.put("percentage_value", billSundaryPercentage);
                    mMap.put("default_unit", String.valueOf(billSundryDefaultValue));
                    mMap.put("fed_as", billSundryFedAs);
                    mMap.put("fed_as_percentage", billSundryFedAsPercentage);
                    mMap.put("type", billSundryType);
                    mMap.put("amount", billSundryAmount);
                    mMap.put("previous", billSundryFedAsPercentagePrevious);
                    if (String.valueOf(billSundryNumber) != null) {
                        mMap.put("number_of_bill", String.valueOf(billSundryNumber));
                    }
                    if (String.valueOf(billSundryConsolidated) != null) {
                        mMap.put("consolidated", String.valueOf(billSundryConsolidated));
                    }
                    mMap.put("changeamount", changeAmount);
              /*  if (billSundryFedAsPercentage != null) {
                    if (billSundryFedAsPercentage.equals("valuechange")) {
                        mMap.put("changeamount", changeAmount);
                    }
                }*/
                    if (String.valueOf(billSundryId) != null) {
                        int size = appUser.arr_billSundryId.size();
                        for (int j = 0; j < size; j++) {
                            String id1 = appUser.arr_billSundryId.get(j);
                            if (id1.equals(String.valueOf(billSundryId))) {
                                billsundryothername = appUser.arr_billSundryName.get(j);
                                break;
                            }
                        }
                        mMap.put("other", billsundryothername);
                    }
                    mListMapForBillSale.add(mMap);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                    gst_28 = 0.0;
                }
            }
        }

        if (boolForMap) {
            mMap.put("id", id);
            mMap.put("courier_charges", billSundryCharges);
            mMap.put("bill_sundry_id", billSundryId);
            mMap.put("percentage", "" + taxval);
            mMap.put("percentage_value", billSundaryPercentage);
            mMap.put("default_unit", String.valueOf(billSundryDefaultValue));
            mMap.put("fed_as", billSundryFedAs);
            mMap.put("fed_as_percentage", billSundryFedAsPercentage);
            mMap.put("type", billSundryType);
            mMap.put("amount", billSundryAmount);
            mMap.put("previous", billSundryFedAsPercentagePrevious);
            if (String.valueOf(billSundryNumber) != null) {
                mMap.put("number_of_bill", String.valueOf(billSundryNumber));
            }
            if (String.valueOf(billSundryConsolidated) != null) {
                mMap.put("consolidated", String.valueOf(billSundryConsolidated));
            }
            if (billSundryFedAsPercentage != null) {
                if (billSundryFedAsPercentage.equals("valuechange")) {
                    mMap.put("changeamount", changeAmount);
                }
            }
            if (String.valueOf(billSundryId) != null) {
                int size = appUser.arr_billSundryId.size();
                for (int j = 0; j < size; j++) {
                    String id1 = appUser.arr_billSundryId.get(j);
                    if (id1.equals(String.valueOf(billSundryId))) {
                        billsundryothername = appUser.arr_billSundryName.get(j);
                        break;
                    }
                }
                mMap.put("other", billsundryothername);
            }
            mListMapForBillSale.add(mMap);
            // LocalRepositories.saveAppUser(getApplicationContext(), appUser);
        }

    }

    void apiCall(Boolean aBoolean) {
        appUser.totalamount = String.valueOf(txtSplit(PosItemAddActivity.grand_total.getText().toString()));
        appUser.items_amount = String.valueOf(txtSplit(PosItemAddActivity.mSubtotal.getText().toString()));
        Double bill_sundries_amount = 0.0;
        appUser.billsundrytotal.clear();
        appUser.mListMapForBillSale.clear();
        appUser.mListMapForItemSale.clear();
        if (mListMapForBillSale.size() > 0) {
            for (int i = 0; i < mListMapForBillSale.size(); i++) {
                bill_sundries_amount = bill_sundries_amount + Double.valueOf(billSundryTotal.get(i));
                if (!billSundryTotal.get(i).equals("0.00")) {
                    appUser.billsundrytotal.add(billSundryTotal.get(i));
                    appUser.mListMapForBillSale.add(mListMapForBillSale.get(i));
                }
            }
        }
        appUser.mListMapForItemSale = ExpandableItemListActivity.mListMapForItemSale;
        appUser.bill_sundries_amount = String.valueOf(bill_sundries_amount);
        // voucher.put("bill_sundries_amount", appUser.bill_sundries_amount);
        if (aBoolean) {
            appUser.email_yes_no = "true";
        } else {
            appUser.email_yes_no = "false";
        }
        appUser.pos_identifier = true;
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);

        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(PosItemAddActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            if (FirstPageActivity.pos) {
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_UPDATE_POS_VOUCHER_DETAILS);
            } else {
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_CREATE_POS_VOUCHER);
            }
        } else {
            snackbar = Snackbar.make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG).setAction("RETRY", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        snackbar.dismiss();
                    }
                }
            });
            snackbar.show();
        }
    }

    @Subscribe
    public void createPosVoucher(CreateSaleVoucherResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            backPress = true;
            appUser.pos_identifier = false;
            Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            appUser.mListMapForItemSale.clear();
            appUser.mListMapForBillSale.clear();
            ExpandableItemListActivity.mListMapForItemSale.clear();
            mListMapForBillSale.clear();
            appUser.arr_series.clear();
            appUser.series_details.clear();
            billSundryTotal.clear();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            Preferences.getInstance(getApplicationContext()).setPos_date("");
            Preferences.getInstance(getApplicationContext()).setVoucher_number("");
            setBillListDataAdapter();
            setDataOnItemAdapter();
            ExpandableItemListActivity.mMapPosItem.clear();
            mSubtotal.setText("₹ 0.00");
            grand_total.setText("₹ 0.00");
            new AlertDialog.Builder(PosItemAddActivity.this)
                    .setTitle("Print/Preview").setMessage("")
                    .setMessage(R.string.print_preview_mesage)
                    .setPositiveButton(R.string.btn_print_preview, (dialogInterface, i) -> {
                        appUser.edit_sale_voucher_id = String.valueOf(response.getId());
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        Intent intent = new Intent(getApplicationContext(), TransactionPdfActivity.class);
                        intent.putExtra("company_report", response.getHtml());
                        intent.putExtra("type", "sale_voucher");
                        intent.putExtra("backPress", true);
                        startActivity(intent);
                        finish();
                    })
                    .setNegativeButton(R.string.btn_cancel, (dialogInterface, i) -> {
                        FirstPageActivity.posSetting = false;
                        FirstPageActivity.posNotifyAdapter = false;
                        ExpandableItemListActivity.isDirectForItem = false;
                        ExpandableItemListActivity.comingFrom = 6;
                        finish();
                    })
                    .show();

        } else {
            Helpers.dialogMessage(PosItemAddActivity.this, response.getMessage());
        }
    }

    private void hideKeyPad() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    public Double getTotal(String total) {
        String[] arr = total.split("₹ ");
        Double a = Double.valueOf(arr[1].trim());
        return a;
    }

    @Subscribe
    public void updatePosVoucher(UpdateSaleVoucherResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            snackbar = Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG);
            snackbar.show();
            Preferences.getInstance(getApplicationContext()).setPos_date("");
            Preferences.getInstance(getApplicationContext()).setVoucher_number("");
            Preferences.getInstance(getApplicationContext()).setAuto_increment(null);
            appUser.pos_identifier = false;
            appUser.mListMapForItemSale.clear();
            appUser.mListMapForBillSale.clear();
            appUser.billsundrytotal.clear();
            ExpandableItemListActivity.mListMapForItemSale.clear();
            ExpandableItemListActivity.mListMapForBillSale.clear();
            appUser.arr_series.clear();
            appUser.series_details.clear();
            billSundryTotal.clear();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            setBillListDataAdapter();
            setDataOnItemAdapter();
            ExpandableItemListActivity.mMapPosItem.clear();
            mSubtotal.setText("₹ 0.00");
            grand_total.setText("₹ 0.00");
            Intent intent = new Intent(getApplicationContext(), GetSaleVoucherListActivity.class);
            intent.putExtra("forDate", true);
            startActivity(intent);

        } else {
            Helpers.dialogMessage(PosItemAddActivity.this, response.getMessage());
        }
    }


}
