package com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.FirstPageActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.TransactionDashboardActivity;
import com.lkintechnology.mBilling.activities.company.pos.PosItemAddActivity;
import com.lkintechnology.mBilling.activities.company.pos.PosSettingActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase.CreatePurchaseActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase.PurchaseAddItemActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase_return.CreatePurchaseReturnActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase_return.PurchaseReturnAddItemActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale.CreateSaleActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale.SaleVoucherAddItemActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale_return.CreateSaleReturnActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale_return.SaleReturnAddItemActivity;
import com.lkintechnology.mBilling.activities.company.transaction.stocktransfer.CreateStockTransferActivity;
import com.lkintechnology.mBilling.activities.company.transaction.stocktransfer.StockTransferAddItemActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.MasterDashboardActivity;
import com.lkintechnology.mBilling.adapters.ItemExpandableListAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.item.DeleteItemResponse;
import com.lkintechnology.mBilling.networks.api_response.item.GetItemResponse;
import com.lkintechnology.mBilling.networks.api_response.voucherseries.VoucherSeriesResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventDeleteItem;
import com.lkintechnology.mBilling.utils.EventEditItem;
import com.lkintechnology.mBilling.utils.EventSaleAddItem;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ExpandableItemListActivity extends AppCompatActivity {

    public static Boolean isDirectForItem = true;
    public static Integer comingFrom = 0;
    public static Integer checkForStartActivityResult = 0;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    public static ExpandableListView expListView;
    @Bind(R.id.floating_button)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    @Bind(R.id.top_layout)
    RelativeLayout mOverlayLayout;
    public static TextView mTotal;
    @Bind(R.id.error_layout)
    LinearLayout error_layout;
    @Bind(R.id.pos_setting_layout)
    LinearLayout pos_setting_layout;
    @Bind(R.id.pos_setting)
    TextView pos_setting;
    @Bind(R.id.submit)
    TextView mSubmit;
    @Bind(R.id.submit_layout)
    LinearLayout submit_layout;
    public static ItemExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<Integer, List<String>> listDataChildId;
    HashMap<Integer, List<String>> listDataChildDesc;
    HashMap<Integer, List<String>> listDataChildUnit;
    HashMap<Integer, List<String>> listDataChildAlternateUnit;
    HashMap<Integer, List<String>> listDataChildSalePriceMain;
    HashMap<Integer, List<String>> listDataChildSalePriceAlternate;
    HashMap<Integer, List<Boolean>> listDataChildSerialWise;
    HashMap<Integer, List<Boolean>> listDataChildBatchWise;
    HashMap<Integer, List<String>> listDataChildApplied;
    HashMap<Integer, List<String>> listDataChildAlternateConFactor;
    HashMap<Integer, List<String>> listDataChildDefaultUnit;
    HashMap<Integer, List<String>> listDataChildPackagingConfactor;
    HashMap<Integer, List<String>> listDataChildPackagingSalesPrice;
    HashMap<Integer, List<String>> listDataChildPackagingUnit;
    HashMap<Integer, List<String>> listDataChildMrp;
    HashMap<Integer, List<String>> listDataTax;
    HashMap<Integer, List<String>> listDataBarcode;
    public Map<String, String> mSaleVoucherItem;
    HashMap<Integer, List<String>> listDataChildPurchasePriceMain;
    HashMap<Integer, List<String>> listDataChildPurchasePriceAlternate;
    HashMap<Integer, List<String>> listDataChildPackagingPurchasePrice;
    public Map<String, String> mPurchaseVoucherItem;
    public static HashMap<Integer, String[]> mChildCheckStates;
    public Map<String, String> mPurchaseReturnItem;
    public Map<String, String> mSaleReturnItem;
    public static List<Map> mListMapForItemSale;
    // Boolean fromsalelist;


    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar snackbar;
    List<String> name;
    List<String> id;
    List<String> description;
    List<String> unit;
    List<String> alternateUnit;
    List<String> salesPriceMain;
    List<String> salesPriceAlternate;
    List<Boolean> serailWise;
    List<Boolean> batchWise;
    List<String> applied;
    List<String> alternate_con_factor;
    List<String> default_unit;
    List<String> packaging_con_factor;
    List<String> packaging_sales_price;
    List<String> packaging_unit;
    List<String> mrp;
    List<String> tax;
    List<String> purchasePriceMain;
    List<String> purchasePriceAlternate;
    List<String> packaging_purchase_price;
    List<String> barcode;
    List<String> nameList;
    List<String> idList;
    private ArrayAdapter<String> adapter;
    private SimpleDateFormat dateFormatter;
    public static Boolean boolForAdapterSet = false;
    ArrayList<String> mUnitList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_item_list);
        ButterKnife.bind(this);
     /*   if (isFirstTime()) {
            mOverlayLayout.setVisibility(View.INVISIBLE);
        }*/
        initActionbar();
        mTotal = (TextView) findViewById(R.id.total);
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        appUser = LocalRepositories.getAppUser(this);
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        long date = System.currentTimeMillis();
        String dateString = dateFormatter.format(date);
        appUser.stock_in_hand_date = dateString;
//        fromsalelist = getIntent().getExtras().getBoolean("fromsalelist");

      /*  mVchNumber.setText(Preferences.getInstance(getApplicationContext()).getVoucher_number());
        if (Preferences.getInstance(getApplicationContext()).getAuto_increment() != null) {
            if (Preferences.getInstance(getApplicationContext()).getAuto_increment().equals("true")) {
                mVchNumber.setEnabled(false);
            } else {
                mVchNumber.setEnabled(true);
            }
        }*/

        if (ExpandableItemListActivity.comingFrom == 6) {
            floatingActionButton.setVisibility(View.GONE);
            pos_setting_layout.setVisibility(View.VISIBLE);
            autoCompleteTextView.setVisibility(View.GONE);
            submit_layout.setVisibility(View.VISIBLE);
            submit_layout.bringToFront();
        } else {
            floatingActionButton.setVisibility(View.VISIBLE);
            pos_setting_layout.setVisibility(View.GONE);
            autoCompleteTextView.setVisibility(View.VISIBLE);
            submit_layout.setVisibility(View.GONE);
            floatingActionButton.bringToFront();
        }
        appUser.item_name = "";
        appUser.item_code = "";
        appUser.item_hsn_number = "";
        appUser.item_group_name = "";
        appUser.item_unit_name = "";
        appUser.item_tax_category_name = "";
        appUser.item_print_name = "";
        appUser.edit_item_id = "";
        appUser.stock_item_serail_arr.clear();
        appUser.stock_serial_arr.clear();
        Preferences.getInstance(getApplicationContext()).setStockSerial("");
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);

        pos_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PosSettingActivity.class);
                startActivity(intent);
            }
        });

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(ExpandableItemListActivity.this, ExpandableItemListActivity.mTotal.getText().toString(), Toast.LENGTH_SHORT).show();
                if (!Preferences.getInstance(getApplicationContext()).getVoucherSeries().equals("")) {
                    if (!Preferences.getInstance(getApplicationContext()).getPos_date().equals("")) {
                        if (!Preferences.getInstance(getApplicationContext()).getVoucher_number().equals("")) {
                            if (!Preferences.getInstance(getApplicationContext()).getPos_sale_type().equals("")) {
                                if (!Preferences.getInstance(getApplicationContext()).getPos_store().equals("")) {
                                    if (!Preferences.getInstance(getApplicationContext()).getPos_party_name().equals("")) {
                                        if (ItemExpandableListAdapter.mMapPosItem.size() > 0) {
                                            // Preferences.getInstance(getApplicationContext()).setVoucher_number(mVchNumber.getText().toString());
                                            appUser.billsundrytotal.clear();
                                            mListMapForItemSale.clear();
                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                            Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                                                    R.anim.blink_on_click);
                                            v.startAnimation(animFadeIn);
                                            Map mMap;
                                            Double subtotal = 0.0;
                                            ArrayList<String> sale_item_serial_arr = new ArrayList<>();
                                            sale_item_serial_arr.clear();
                                            for (int i = 0; i < listDataHeader.size(); i++) {
                                                Double total = 0.0;
                                                for (int j = 0; j < listDataChild.get(listDataHeader.get(i)).size(); j++) {
                                                    String pos = i + "," + j;
                                                    //String id = pos.getPosition();
                                                    String key = "";
                                                    if ((ItemExpandableListAdapter.mMapPosItem.get(pos) != null)) {
                                                        key = pos;
                                                    }

                                                    if (key.equals(pos)) {
                                                        mMap = new HashMap();
                                                        String[] arr = pos.split(",");
                                                        String groupid = arr[0];
                                                        String childid = arr[1];
                                                        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));

                                                        //Intent intent = new Intent(getApplicationContext(), SaleVoucherAddItemActivity.class);
                                                        String itemId = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
                                                        String arr1[] = itemName.split(",");
                                                        itemName = arr1[0];
                                                        String tax = listDataTax.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        Double sales_price_main = Double.valueOf(listDataChildSalePriceMain.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid)));
                                                        String quantity = ItemExpandableListAdapter.mMapPosItem.get(pos).toString();
                                                        total = sales_price_main * Double.valueOf(quantity);

                                                        if (Preferences.getInstance(getApplicationContext()).getPos_sale_type().contains("GST-ItemWise") && tax.contains("GST ")) {
                                                            Double item_tax = (Double.valueOf(total) * taxSplit(tax)) / 100;
                                                            Double taxInclude = Double.valueOf(total) + item_tax;
                                                            total = taxInclude;
                                                        }
                                                        Double multiRate = 0.0;
                                                        if (Preferences.getInstance(getApplicationContext()).getPos_sale_type().contains("GST-MultiRate") && tax.contains("GST ")) {
                                                            multiRate = Double.valueOf(taxSplit(tax));
                                                        }
                                                        String descr;
                                                        String alternate_unit;
                                                        String sales_price_alternate;
                                                        Boolean batch, serial;
                                                        descr = listDataChildDesc.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        sales_price_alternate = listDataChildSalePriceAlternate.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        alternate_unit = listDataChildAlternateUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        batch = listDataChildBatchWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        serial = listDataChildSerialWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        String main_unit = listDataChildUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        String applied = listDataChildApplied.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        String alternate_unit_con_factor = listDataChildAlternateConFactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        String default_unit = "";
                                                        try {
                                                            default_unit = listDataChildDefaultUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        } catch (Exception e) {
                                                            default_unit = "";
                                                        }
                                                        String packaging_unit_con_factor = listDataChildPackagingConfactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        String packaging_unit_sales_price = listDataChildPackagingSalesPrice.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        String packaging_unit = listDataChildPackagingUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        String mrp = listDataChildMrp.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        String barcode = listDataBarcode.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                                                        mUnitList = new ArrayList<>();
                                                        mUnitList.add("Main Unit : " + main_unit);
                                                        mUnitList.add("Alternate Unit :" + alternate_unit);
                                                        subtotal = subtotal + total;
                                                        if (!quantity.equals("0")) {
                                                            mMap.put("id", "");
                                                            mMap.put("item_id", itemId);
                                                            mMap.put("item_name", itemName);
                                                            mMap.put("total", String.valueOf(total));
                                                            mMap.put("quantity", quantity);
                                                            mMap.put("sales_price_main", sales_price_main);
                                                            mMap.put("tax", tax);
                                                            mMap.put(itemId, multiRate);
                                                            mMap.put("description", descr);
                                                            mMap.put("main_unit", main_unit);
                                                            mMap.put("alternate_unit", alternate_unit);
                                                            mMap.put("serial_wise", String.valueOf(serial));
                                                            mMap.put("batch_wise", String.valueOf(batch));
                                                            mMap.put("applied", applied);
                                                            mMap.put("alternate_unit_con_factor", alternate_unit_con_factor);
                                                            mMap.put("sales_price_alternate", sales_price_alternate);
                                                            mMap.put("default_unit", default_unit);
                                                            mMap.put("packaging_unit_con_factor", packaging_unit_con_factor);
                                                            mMap.put("packaging_unit_sales_price", packaging_unit_sales_price);
                                                            mMap.put("packaging_unit", packaging_unit);
                                                            mMap.put("mrp", mrp);
                                                            mMap.put("sr_no", "");
                                                            mMap.put("serial_number", sale_item_serial_arr);

                                                            mMap.put("rate", appUser.sale_item_serial_arr);
                                                            mMap.put("discount", "0");
                                                            mMap.put("value", "0.0");
                                                            mMap.put("sale_unit", main_unit);
                                                            mMap.put("unit", "Main Unit");
                                                            mMap.put("rate", sales_price_main);
                                                            mMap.put("unit_list", mUnitList);


                                                            // mMap.put("unit", mSpinnerUnit.getSelectedItem().toString());
                                                            // mMap.put("id", itemid);

                                                            mListMapForItemSale.add(mMap);
                                                            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                                                        }
                                                    }
                                                }
                                            }
                                            Preferences.getInstance(getApplicationContext()).setParty_name("");
                                            Preferences.getInstance(getApplicationContext()).setParty_id("");
                                            if (mListMapForItemSale.size()>0){
                                                Intent intent = new Intent(getApplicationContext(), PosItemAddActivity.class);
                                                intent.putExtra("subtotal", subtotal);
                                                boolForAdapterSet = true;
                                                startActivity(intent);
                                            }else {
                                                Toast.makeText(ExpandableItemListActivity.this, "Please add item!!!", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(ExpandableItemListActivity.this, "Please add item!!!", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(ExpandableItemListActivity.this, "Please select party name in setting menu!!!", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(ExpandableItemListActivity.this, "Please select store in setting menu!!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ExpandableItemListActivity.this, "Please select sale type in setting menu!!!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ExpandableItemListActivity.this, "Please select voucher number in setting menu!!!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ExpandableItemListActivity.this, "Please select date in setting menu!!!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ExpandableItemListActivity.this, "Please select voucher series in setting menu!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        //SharedPreferences preferences1=getP
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
            mOverlayLayout.setVisibility(View.VISIBLE);
            mOverlayLayout.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mOverlayLayout.setVisibility(View.INVISIBLE);
                    return false;
                }

            });


        }
        return ranBefore;
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
        actionbarTitle.setText("ITEM LIST");
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSaleVoucherItem = new HashMap<>();
        mPurchaseVoucherItem = new HashMap<>();
        mPurchaseReturnItem = new HashMap<>();
        mSaleReturnItem = new HashMap<>();
        EventBus.getDefault().register(this);
        appUser.item_unit_name = "";
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
        Boolean isConnected = ConnectivityReceiver.isConnected();
       /* if(isDirectForItem==true){*/
        if (!FirstPageActivity.posSetting) {
            mListMapForItemSale = new ArrayList();
            FirstPageActivity.posNotifyAdapter = false;
            if (isConnected) {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                ItemExpandableListAdapter.mMapPosItem.clear();
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ITEM);
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
        }
       /* if (FirstPageActivity.posNotifyAdapter){
            System.out.println(mListMapForItemSale);
            expListView.setAdapter(listAdapter);
            listAdapter.notifyDataSetChanged();
            for (int i = 0; i < listAdapter.getGroupCount(); i++) {
                expListView.expandGroup(i);
            }
        }*/
 /*       }else{
            if (isConnected) {
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Info...");
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(true);
                mProgressDialog.show();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ITEM_MATERRIAL_CENTRE);
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
        }*/
    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void add(View v) {

        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_on_click);
        v.startAnimation(animFadeIn);
        Intent intent = new Intent(getApplicationContext(), CreateNewItemActivity.class);
        intent.putExtra("fromitemlist", false);
        startActivity(intent);
    }


    @Subscribe
    public void getItem(GetItemResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            ExpandableItemListActivity.mTotal.setText("Total : 0.0");
            nameList = new ArrayList();
            idList = new ArrayList();
            nameList = new ArrayList();

            listDataHeader = new ArrayList<>();
            listDataChildDesc = new HashMap<Integer, List<String>>();
            listDataChildUnit = new HashMap<Integer, List<String>>();
            listDataChildAlternateUnit = new HashMap<Integer, List<String>>();
            listDataChildSalePriceMain = new HashMap<Integer, List<String>>();
            listDataChildPurchasePriceMain = new HashMap<Integer, List<String>>();
            listDataBarcode = new HashMap<Integer, List<String>>();

            listDataChildSalePriceAlternate = new HashMap<Integer, List<String>>();
            listDataChildPurchasePriceAlternate = new HashMap<Integer, List<String>>();

            listDataChildSerialWise = new HashMap<Integer, List<Boolean>>();
            listDataChildBatchWise = new HashMap<Integer, List<Boolean>>();
            listDataChildApplied = new HashMap<Integer, List<String>>();
            listDataChildAlternateConFactor = new HashMap<Integer, List<String>>();
            listDataChildDefaultUnit = new HashMap<Integer, List<String>>();
            listDataChildPackagingUnit = new HashMap<Integer, List<String>>();
            listDataChildDefaultUnit = new HashMap<Integer, List<String>>();
            listDataChildPackagingSalesPrice = new HashMap<Integer, List<String>>();
            listDataChildPackagingPurchasePrice = new HashMap<Integer, List<String>>();
            listDataChildPackagingConfactor = new HashMap<Integer, List<String>>();
            listDataChild = new HashMap<String, List<String>>();
            listDataChildId = new HashMap<Integer, List<String>>();
            listDataChildMrp = new HashMap<Integer, List<String>>();
            listDataTax = new HashMap<Integer, List<String>>();
            if (response.getOrdered_items().size() == 0) {
                expListView.setVisibility(View.GONE);
                error_layout.setVisibility(View.VISIBLE);
            } else {
                expListView.setVisibility(View.VISIBLE);
                error_layout.setVisibility(View.GONE);
            }
            for (int i = 0; i < response.getOrdered_items().size(); i++) {
                listDataHeader.add(response.getOrdered_items().get(i).getGroup_name());
                name = new ArrayList<>();
                description = new ArrayList<>();
                unit = new ArrayList<>();
                salesPriceMain = new ArrayList<>();
                purchasePriceMain = new ArrayList<>();
                salesPriceAlternate = new ArrayList<>();
                purchasePriceAlternate = new ArrayList<>();
                alternateUnit = new ArrayList<>();
                serailWise = new ArrayList<>();
                applied = new ArrayList<>();
                batchWise = new ArrayList<>();
                alternate_con_factor = new ArrayList<>();
                default_unit = new ArrayList<>();
                packaging_con_factor = new ArrayList<>();
                packaging_sales_price = new ArrayList<>();
                packaging_purchase_price = new ArrayList<>();
                packaging_unit = new ArrayList<>();
                mrp = new ArrayList<>();
                tax = new ArrayList<>();
                barcode = new ArrayList<>();
                id = new ArrayList<>();
                default_unit.clear();
                for (int j = 0; j < response.getOrdered_items().get(i).getData().size(); j++) {
                    name.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getName()
                            + "," + String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getTotal_stock_quantity()
                            + "," + response.getOrdered_items().get(i).getData().get(j).getAttributes().getSales_price_main()
                            + "," + response.getOrdered_items().get(i).getData().get(j).getId()));

                    nameList.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getName());
                    idList.add(String.valueOf(i) + "," + String.valueOf(j));

                    if (response.getOrdered_items().get(i).getData().get(j).getAttributes().getItem_description() != null) {
                        description.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getItem_description());
                    } else {
                        description.add("");
                    }
                    if (response.getOrdered_items().get(i).getData().get(j).getAttributes().getSales_price_applied_on() != null) {
                        applied.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getSales_price_applied_on());
                    } else {
                        applied.add("");
                    }
                    if (String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getSales_price_main()) != null) {
                        salesPriceMain.add(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getSales_price_main()));
                    } else {
                        salesPriceMain.add("");
                    }
                    if (String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getPurchase_price_main()) != null) {
                        purchasePriceMain.add(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getPurchase_price_main()));
                    } else {
                        purchasePriceMain.add("");
                    }
                    if (String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getSales_price_alternate()) != null) {
                        salesPriceAlternate.add(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getSales_price_alternate()));
                    } else {
                        salesPriceAlternate.add("");
                    }
                    if (String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getPurchase_price_alternate()) != null) {
                        purchasePriceAlternate.add(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getPurchase_price_alternate()));
                    } else {
                        purchasePriceAlternate.add("");
                    }
                    if (String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getConversion_factor()) != null) {
                        alternate_con_factor.add(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getConversion_factor()));
                    } else {
                        alternate_con_factor.add("");
                    }
                    if (String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getMrp()) != null) {
                        mrp.add(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getMrp()));
                    } else {
                        mrp.add("");
                    }
                    StringBuilder sb = new StringBuilder();
                    if (response.getOrdered_items().get(i).getData().get(j).getAttributes().getBarcode() != null) {
                        for (String str : response.getOrdered_items().get(i).getData().get(j).getAttributes().getBarcode()) {
                            sb.append(str).append(","); //separating contents using semi colon
                        }
                        String strfromArrayList = sb.toString();

                      /*  for(int k=0;k<response.getOrdered_items().get(i).getData().get(j).getAttributes().getBarcode().size();k++){
                            String barcode= StringUtils.collectionToDelimitedString(",",response.getOrdered_items().get(i).getData().get(j).getAttributes().getBarcode().get(k).toString());

                        }*/
                        barcode.add(strfromArrayList);

                        Timber.i("MYBARCODE" + barcode);
                    } else {
                        barcode.add("");

                    }
                    unit.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getItem_unit());

                    if (response.getOrdered_items().get(i).getData().get(j).getAttributes().getAlternate_unit() != null) {
                        alternateUnit.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getAlternate_unit());
                    } else {
                        alternateUnit.add("");
                    }
                    if (ExpandableItemListActivity.comingFrom == 0) {
                        if (response.getOrdered_items().get(i).getData().get(j).getAttributes().getDefault_unit_for_sales() != null) {
                            default_unit.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getDefault_unit_for_sales());
                        } else {
                            default_unit.add("");
                        }
                    } else if (ExpandableItemListActivity.comingFrom == 1) {
                        if (response.getOrdered_items().get(i).getData().get(j).getAttributes().getDefault_unit_for_purchase() != null) {
                            default_unit.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getDefault_unit_for_purchase());
                        } else {
                            default_unit.add("");
                        }
                    } else if (ExpandableItemListActivity.comingFrom == 2) {
                        if (response.getOrdered_items().get(i).getData().get(j).getAttributes().getDefault_unit_for_sales() != null) {
                            default_unit.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getDefault_unit_for_sales());
                        } else {
                            default_unit.add("");
                        }
                    } else if (ExpandableItemListActivity.comingFrom == 3) {
                        if (response.getOrdered_items().get(i).getData().get(j).getAttributes().getDefault_unit_for_purchase() != null) {
                            default_unit.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getDefault_unit_for_purchase());
                        } else {
                            default_unit.add("");
                        }
                    } else if (ExpandableItemListActivity.comingFrom == 4) {
                        if (response.getOrdered_items().get(i).getData().get(j).getAttributes().getDefault_unit_for_purchase() != null) {
                            default_unit.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getDefault_unit_for_purchase());
                        } else {
                            default_unit.add("");
                        }
                    }

                    if (response.getOrdered_items().get(i).getData().get(j).getAttributes().getItem_package_unit() != null) {
                        packaging_unit.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getItem_package_unit());
                    } else {
                        packaging_unit.add("");
                    }
                    if (String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getConversion_factor_package()) != null) {
                        packaging_con_factor.add(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getConversion_factor_package()));
                    } else {
                        packaging_con_factor.add("");
                    }
                    if (String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getSale_price()) != null) {
                        packaging_sales_price.add(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getSale_price()));
                    } else {
                        packaging_sales_price.add("");
                    }
                    if (String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getPurchase_price()) != null) {
                        packaging_purchase_price.add(String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getPurchase_price()));
                    } else {
                        packaging_purchase_price.add("");
                    }
                    if (String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().isBatch_wise_detail()) != null) {
                        batchWise.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().isBatch_wise_detail());
                    } else {
                        batchWise.add(false);
                    }
                    if (response.getOrdered_items().get(i).getData().get(j).getAttributes().getTax_category() != null) {
                        tax.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getTax_category());
                    } else {
                        tax.add("");
                    }
                    if (String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().isSerial_number_wise_detail()) != null) {
                        serailWise.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().isSerial_number_wise_detail());
                    } else {
                        serailWise.add(false);
                    }
                    id.add(response.getOrdered_items().get(i).getData().get(j).getId());
                }
                listDataChild.put(listDataHeader.get(i), name);
                listDataChildId.put(i, id);
                listDataChildDesc.put(i, description);
                listDataChildUnit.put(i, unit);
                listDataChildAlternateUnit.put(i, alternateUnit);
                listDataChildSalePriceMain.put(i, salesPriceMain);
                listDataChildPurchasePriceMain.put(i, purchasePriceMain);

                listDataChildSalePriceAlternate.put(i, salesPriceAlternate);
                listDataChildPurchasePriceAlternate.put(i, purchasePriceAlternate);

                listDataChildSerialWise.put(i, serailWise);
                listDataChildBatchWise.put(i, batchWise);
                listDataChildApplied.put(i, applied);
                listDataChildAlternateConFactor.put(i, alternate_con_factor);
                listDataChildDefaultUnit.put(i, default_unit);
                listDataChildPackagingConfactor.put(i, packaging_con_factor);
                listDataChildPackagingSalesPrice.put(i, packaging_sales_price);
                listDataChildPackagingPurchasePrice.put(i, packaging_purchase_price);

                listDataChildPackagingUnit.put(i, packaging_unit);
                listDataChildMrp.put(i, mrp);
                listDataTax.put(i, tax);
                listDataBarcode.put(i, barcode);
            }
            listAdapter = new ItemExpandableListAdapter(this, listDataHeader, listDataChild, listDataChildSalePriceMain, ExpandableItemListActivity.comingFrom);

            // setting list adapter
            expListView.setAdapter(listAdapter);
            if (ExpandableItemListActivity.comingFrom == 6) {
                for (int i = 0; i < listAdapter.getGroupCount(); i++) {
                    expListView.expandGroup(i);
                }
                ApiCallsService.action(getApplicationContext(), Cv.ACTION_VOUCHER_SERIES);
            }

            autoCompleteTextView();

        } else {
            //   startActivity(new Intent(getApplicationContext(), MasterDashboardActivity.class));
            // Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this, response.getMessage());
        }


    }


    @Subscribe
    public void delete_item(EventDeleteItem pos) {
        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        appUser.delete_item_id = arrid;
        LocalRepositories.saveAppUser(this, appUser);
        new AlertDialog.Builder(ExpandableItemListActivity.this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(ExpandableItemListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_ITEM);
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

                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();

    }

    @Subscribe
    public void deleteitemresponse(DeleteItemResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ITEM);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this, response.getMessage());
        }
    }

    @Subscribe
    public void editgroup(EventEditItem pos) {
        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        appUser.edit_item_id = arrid;
        LocalRepositories.saveAppUser(this, appUser);
        Intent intent = new Intent(getApplicationContext(), CreateNewItemActivity.class);
        intent.putExtra("fromitemlist", true);
        startActivity(intent);
    }

    @Subscribe
    public void ClickEventAddSaleVoucher(EventSaleAddItem pos) {


        autoCompleteTextView();

        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        appUser.childId = arrid;
        LocalRepositories.saveAppUser(this, appUser);

        if (!isDirectForItem) {

            if (ExpandableItemListActivity.comingFrom == 0) {
                Intent intent = new Intent(getApplicationContext(), SaleVoucherAddItemActivity.class);
                String itemid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
                String arr1[] = itemName.split(",");
                itemName = arr1[0];
                String descr;
                String alternate_unit;
                String sales_price_main;
                String sales_price_alternate;
                Boolean batch, serial;
                descr = listDataChildDesc.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                sales_price_main = listDataChildSalePriceMain.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                sales_price_alternate = listDataChildSalePriceAlternate.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                alternate_unit = listDataChildAlternateUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                batch = listDataChildBatchWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                serial = listDataChildSerialWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String main_unit = listDataChildUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String applied = listDataChildApplied.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String alternate_unit_con_factor = listDataChildAlternateConFactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String default_unit = listDataChildDefaultUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit_con_factor = listDataChildPackagingConfactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit_sales_price = listDataChildPackagingSalesPrice.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit = listDataChildPackagingUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String mrp = listDataChildMrp.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String tax = listDataTax.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String barcode = listDataBarcode.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                intent.putExtra("fromitemlist", true);
                intent.putExtra("fromSaleVoucherItemList", true);
                mSaleVoucherItem.put("name", itemName);
                mSaleVoucherItem.put("desc", descr);
                mSaleVoucherItem.put("main_unit", main_unit);
                mSaleVoucherItem.put("alternate_unit", alternate_unit);
                mSaleVoucherItem.put("serial_wise", String.valueOf(serial));
                mSaleVoucherItem.put("batch_wise", String.valueOf(batch));
                mSaleVoucherItem.put("sales_price_main", sales_price_main);
                mSaleVoucherItem.put("applied", applied);
                mSaleVoucherItem.put("alternate_unit_con_factor", alternate_unit_con_factor);
                mSaleVoucherItem.put("default_unit", default_unit);
                mSaleVoucherItem.put("packaging_unit_con_factor", packaging_unit_con_factor);
                mSaleVoucherItem.put("packaging_unit_sales_price", packaging_unit_sales_price);
                mSaleVoucherItem.put("packaging_unit", packaging_unit);
                mSaleVoucherItem.put("mrp", mrp);
                mSaleVoucherItem.put("tax", tax);
                appUser.mMapSaleVoucherItem = mSaleVoucherItem;
                LocalRepositories.saveAppUser(this, appUser);
                intent.putExtra("item_id", itemid);
                intent.putExtra("name", itemName);
                intent.putExtra("desc", descr);
                intent.putExtra("main_unit", main_unit);
                intent.putExtra("alternate_unit", alternate_unit);
                intent.putExtra("serial_wise", serial);
                intent.putExtra("batch_wise", batch);
                intent.putExtra("sales_price_main", sales_price_main);
                intent.putExtra("sales_price_alternate", sales_price_alternate);
                intent.putExtra("applied", applied);
                intent.putExtra("alternate_unit_con_factor", alternate_unit_con_factor);
                intent.putExtra("default_unit", default_unit);
                intent.putExtra("packaging_unit_con_factor", packaging_unit_con_factor);
                intent.putExtra("packaging_unit_sales_price", packaging_unit_sales_price);
                intent.putExtra("packaging_unit", packaging_unit);
                intent.putExtra("mrp", mrp);
                intent.putExtra("tax", tax);
                intent.putExtra("barcode", barcode);
                intent.putExtra("frombillitemvoucherlist", false);
              /*  if(fromsalelist){
                    intent.putExtra("fromsalelist", true);
                }
                else{
                    intent.putExtra("fromsalelist", false);
                }*/
                startActivity(intent);
                finish();
            } else if (ExpandableItemListActivity.comingFrom == 1) {
                Intent intent = new Intent(getApplicationContext(), PurchaseAddItemActivity.class);
                String itemid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
                String arr1[] = itemName.split(",");
                itemName = arr1[0];
                String descr;
                String alternate_unit;
                String purchase_price_main;
                String purchase_price_alternate;
                Boolean batch, serial;
                descr = listDataChildDesc.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                purchase_price_main = listDataChildPurchasePriceMain.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                purchase_price_alternate = listDataChildPurchasePriceAlternate.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                alternate_unit = listDataChildAlternateUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                batch = listDataChildBatchWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                serial = listDataChildSerialWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String main_unit = listDataChildUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String applied = listDataChildApplied.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String alternate_unit_con_factor = listDataChildAlternateConFactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String default_unit = listDataChildDefaultUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit_con_factor = listDataChildPackagingConfactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit_purchase_price = listDataChildPackagingPurchasePrice.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit = listDataChildPackagingUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String mrp = listDataChildMrp.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String tax = listDataTax.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                intent.putExtra("fromitemlist", true);
                intent.putExtra("fromPurchaseVoucherItemList", true);
                mPurchaseVoucherItem.put("name", itemName);
                mPurchaseVoucherItem.put("desc", descr);
                mPurchaseVoucherItem.put("main_unit", main_unit);
                mPurchaseVoucherItem.put("alternate_unit", alternate_unit);
                mPurchaseVoucherItem.put("serial_wise", String.valueOf(serial));
                mPurchaseVoucherItem.put("batch_wise", String.valueOf(batch));
                mPurchaseVoucherItem.put("purchase_price_main", purchase_price_main);
                mPurchaseVoucherItem.put("applied", applied);
                mPurchaseVoucherItem.put("alternate_unit_con_factor", alternate_unit_con_factor);
                mPurchaseVoucherItem.put("default_unit", default_unit);
                mPurchaseVoucherItem.put("packaging_unit_con_factor", packaging_unit_con_factor);
                mPurchaseVoucherItem.put("packaging_unit_purchase_price", packaging_unit_purchase_price);
                mPurchaseVoucherItem.put("packaging_unit", packaging_unit);
                mPurchaseVoucherItem.put("mrp", mrp);
                appUser.mMapPurchaseVoucherItem = mPurchaseVoucherItem;
                LocalRepositories.saveAppUser(this, appUser);
                intent.putExtra("item_id", itemid);
                intent.putExtra("name", itemName);
                intent.putExtra("desc", descr);
                intent.putExtra("main_unit", main_unit);
                intent.putExtra("alternate_unit", alternate_unit);
                intent.putExtra("serial_wise", serial);
                intent.putExtra("batch_wise", batch);
                intent.putExtra("purchase_price_main", purchase_price_main);
                intent.putExtra("purchase_price_alternate", purchase_price_alternate);
                intent.putExtra("applied", applied);
                intent.putExtra("alternate_unit_con_factor", alternate_unit_con_factor);
                intent.putExtra("default_unit", default_unit);
                intent.putExtra("packaging_unit_con_factor", packaging_unit_con_factor);
                intent.putExtra("packaging_unit_purchase_price", packaging_unit_purchase_price);
                intent.putExtra("packaging_unit", packaging_unit);
                intent.putExtra("mrp", mrp);
                intent.putExtra("tax", tax);
                startActivity(intent);
                finish();
            } else if (ExpandableItemListActivity.comingFrom == 2) {
                //Toast.makeText(ExpandableItemListActivity.this, "2", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SaleReturnAddItemActivity.class);
                String itemid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
                String arr1[] = itemName.split(",");
                itemName = arr1[0];
                String descr;
                String alternate_unit;
                String sales_price_main;
                String sales_price_alternate;
                Boolean batch, serial;
                descr = listDataChildDesc.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                sales_price_main = listDataChildSalePriceMain.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                sales_price_alternate = listDataChildSalePriceAlternate.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                alternate_unit = listDataChildAlternateUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                batch = listDataChildBatchWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                serial = listDataChildSerialWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String main_unit = listDataChildUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String applied = listDataChildApplied.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String alternate_unit_con_factor = listDataChildAlternateConFactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String default_unit = listDataChildDefaultUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit_con_factor = listDataChildPackagingConfactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit_sales_price = listDataChildPackagingSalesPrice.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit = listDataChildPackagingUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String mrp = listDataChildMrp.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String tax = listDataTax.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                intent.putExtra("fromitemlist", true);
                intent.putExtra("fromSaleVoucherItemList", true);


                mSaleReturnItem.put("name", itemName);
                mSaleReturnItem.put("desc", descr);
                mSaleReturnItem.put("main_unit", main_unit);
                mSaleReturnItem.put("alternate_unit", alternate_unit);
                mSaleReturnItem.put("serial_wise", String.valueOf(serial));
                mSaleReturnItem.put("batch_wise", String.valueOf(batch));
                mSaleReturnItem.put("sales_price_main", sales_price_main);
                mSaleReturnItem.put("applied", applied);
                mSaleReturnItem.put("alternate_unit_con_factor", alternate_unit_con_factor);
                mSaleReturnItem.put("default_unit", default_unit);
                mSaleReturnItem.put("packaging_unit_con_factor", packaging_unit_con_factor);
                mSaleReturnItem.put("packaging_unit_sales_price", packaging_unit_sales_price);
                mSaleReturnItem.put("packaging_unit", packaging_unit);
                mSaleReturnItem.put("mrp", mrp);
                mSaleReturnItem.put("tax", tax);
                appUser.mMapSaleReturnItem = mSaleReturnItem;
                LocalRepositories.saveAppUser(this, appUser);
                intent.putExtra("item_id", itemid);
                intent.putExtra("name", itemName);
                intent.putExtra("desc", descr);
                intent.putExtra("main_unit", main_unit);
                intent.putExtra("alternate_unit", alternate_unit);
                intent.putExtra("serial_wise", serial);
                intent.putExtra("batch_wise", batch);
                intent.putExtra("sales_price_main", sales_price_main);
                intent.putExtra("sales_price_alternate", sales_price_alternate);
                intent.putExtra("applied", applied);
                intent.putExtra("alternate_unit_con_factor", alternate_unit_con_factor);
                intent.putExtra("default_unit", default_unit);
                intent.putExtra("packaging_unit_con_factor", packaging_unit_con_factor);
                intent.putExtra("packaging_unit_sales_price", packaging_unit_sales_price);
                intent.putExtra("packaging_unit", packaging_unit);
                intent.putExtra("mrp", mrp);
                intent.putExtra("tax", tax);

                intent.putExtra("frombillitemvoucherlist", false);
                startActivity(intent);
                finish();
            } else if (ExpandableItemListActivity.comingFrom == 3) {

                Intent intent = new Intent(getApplicationContext(), PurchaseReturnAddItemActivity.class);
                String itemid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
                String arr1[] = itemName.split(",");
                itemName = arr1[0];
                String descr;
                String alternate_unit;
                String purchase_price_main;
                String purchase_price_alternate;
                Boolean batch, serial;
                descr = listDataChildDesc.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                purchase_price_main = listDataChildPurchasePriceMain.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                purchase_price_alternate = listDataChildPurchasePriceAlternate.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                alternate_unit = listDataChildAlternateUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                batch = listDataChildBatchWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                serial = listDataChildSerialWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String main_unit = listDataChildUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String applied = listDataChildApplied.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String alternate_unit_con_factor = listDataChildAlternateConFactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String default_unit = listDataChildDefaultUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit_con_factor = listDataChildPackagingConfactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit_purchase_price = listDataChildPackagingPurchasePrice.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit = listDataChildPackagingUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String mrp = listDataChildMrp.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String tax = listDataTax.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String barcode = listDataBarcode.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                intent.putExtra("fromitemlist", true);
                intent.putExtra("fromPurchaseReturnItemList", true);
                intent.putExtra("id", childid);
                intent.putExtra("fromitemlist", true);
                intent.putExtra("fromPurchaseReturnItemList", true);

                mPurchaseReturnItem.put("name", itemName);
                mPurchaseReturnItem.put("desc", descr);
                mPurchaseReturnItem.put("main_unit", main_unit);
                mPurchaseReturnItem.put("alternate_unit", alternate_unit);
                mPurchaseReturnItem.put("serial_wise", String.valueOf(serial));
                mPurchaseReturnItem.put("batch_wise", String.valueOf(batch));
                mPurchaseReturnItem.put("purchase_price_main", purchase_price_main);
                mPurchaseReturnItem.put("applied", applied);
                mPurchaseReturnItem.put("alternate_unit_con_factor", alternate_unit_con_factor);
                mPurchaseReturnItem.put("default_unit", default_unit);
                mPurchaseReturnItem.put("packaging_unit_con_factor", packaging_unit_con_factor);
                mPurchaseReturnItem.put("packaging_unit_purchase_price", packaging_unit_purchase_price);
                mPurchaseReturnItem.put("packaging_unit", packaging_unit);
                mPurchaseReturnItem.put("mrp", mrp);
                appUser.mMapPurchaseReturnItem = mPurchaseReturnItem;
                LocalRepositories.saveAppUser(this, appUser);

                intent.putExtra("item_id", itemid);
                intent.putExtra("name", itemName);
                intent.putExtra("desc", descr);
                intent.putExtra("main_unit", main_unit);
                intent.putExtra("alternate_unit", alternate_unit);
                intent.putExtra("serial_wise", serial);
                intent.putExtra("batch_wise", batch);
                intent.putExtra("purchase_price_main", purchase_price_main);
                intent.putExtra("purchase_price_alternate", purchase_price_alternate);
                intent.putExtra("applied", applied);
                intent.putExtra("alternate_unit_con_factor", alternate_unit_con_factor);
                intent.putExtra("default_unit", default_unit);
                intent.putExtra("packaging_unit_con_factor", packaging_unit_con_factor);
                intent.putExtra("packaging_unit_purchase_price", packaging_unit_purchase_price);
                intent.putExtra("packaging_unit", packaging_unit);
                intent.putExtra("mrp", mrp);
                intent.putExtra("tax", tax);
                intent.putExtra("barcode", barcode);
                Timber.i("sssss " + barcode);
                startActivity(intent);
                finish();


            } else if (ExpandableItemListActivity.comingFrom == 4) {
                Intent intent = new Intent(getApplicationContext(), StockTransferAddItemActivity.class);
                String itemid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
                String arr1[] = itemName.split(",");
                itemName = arr1[0];
                String descr;
                String alternate_unit;
                String purchase_price_main;
                String purchase_price_alternate;
                Boolean batch, serial;
                descr = listDataChildDesc.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                purchase_price_main = listDataChildPurchasePriceMain.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                purchase_price_alternate = listDataChildPurchasePriceAlternate.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                alternate_unit = listDataChildAlternateUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                batch = listDataChildBatchWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                serial = listDataChildSerialWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String main_unit = listDataChildUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String applied = listDataChildApplied.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String alternate_unit_con_factor = listDataChildAlternateConFactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String default_unit = listDataChildDefaultUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit_con_factor = listDataChildPackagingConfactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit_purchase_price = listDataChildPackagingPurchasePrice.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String packaging_unit = listDataChildPackagingUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String mrp = listDataChildMrp.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String tax = listDataTax.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                intent.putExtra("fromitemlist", true);
                intent.putExtra("fromPurchaseVoucherItemList", true);
                mPurchaseVoucherItem.put("name", itemName);
                mPurchaseVoucherItem.put("desc", descr);
                mPurchaseVoucherItem.put("main_unit", main_unit);
                mPurchaseVoucherItem.put("alternate_unit", alternate_unit);
                mPurchaseVoucherItem.put("serial_wise", String.valueOf(serial));
                mPurchaseVoucherItem.put("batch_wise", String.valueOf(batch));
                mPurchaseVoucherItem.put("purchase_price_main", purchase_price_main);
                mPurchaseVoucherItem.put("applied", applied);
                mPurchaseVoucherItem.put("alternate_unit_con_factor", alternate_unit_con_factor);
                mPurchaseVoucherItem.put("default_unit", default_unit);
                mPurchaseVoucherItem.put("packaging_unit_con_factor", packaging_unit_con_factor);
                mPurchaseVoucherItem.put("packaging_unit_purchase_price", packaging_unit_purchase_price);
                mPurchaseVoucherItem.put("packaging_unit", packaging_unit);
                mPurchaseVoucherItem.put("mrp", mrp);
                appUser.mMapPurchaseVoucherItem = mPurchaseVoucherItem;
                LocalRepositories.saveAppUser(this, appUser);
                intent.putExtra("item_id", itemid);
                intent.putExtra("name", itemName);
                intent.putExtra("desc", descr);
                intent.putExtra("main_unit", main_unit);
                intent.putExtra("alternate_unit", alternate_unit);
                intent.putExtra("serial_wise", serial);
                intent.putExtra("batch_wise", batch);
                intent.putExtra("purchase_price_main", purchase_price_main);
                intent.putExtra("purchase_price_alternate", purchase_price_alternate);
                intent.putExtra("applied", applied);
                intent.putExtra("alternate_unit_con_factor", alternate_unit_con_factor);
                intent.putExtra("default_unit", default_unit);
                intent.putExtra("packaging_unit_con_factor", packaging_unit_con_factor);
                intent.putExtra("packaging_unit_purchase_price", packaging_unit_purchase_price);
                intent.putExtra("packaging_unit", packaging_unit);
                intent.putExtra("mrp", mrp);
                intent.putExtra("tax", tax);
                startActivity(intent);
                finish();
            } else if (ExpandableItemListActivity.comingFrom == 5) {
                Intent intent = new Intent();
                String itemid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
                String arr1[] = itemName.split(",");
                String item = arr1[0];
                intent.putExtra("item_id", itemid);
                intent.putExtra("name", itemName);
                intent.putExtra("from_stock_in_hand", false);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!ExpandableItemListActivity.isDirectForItem) {
            if (ExpandableItemListActivity.comingFrom == 0) {
                Intent intent = new Intent(this, CreateSaleActivity.class);
               /* if(fromsalelist){
                    intent.putExtra("fromsalelist", true);
                }
                else{
                    intent.putExtra("fromsalelist", false);
                }*/
                if (CreateSaleActivity.fromsalelist) {
                    CreateSaleActivity.isForEdit = true;
                } else {
                    CreateSaleActivity.isForEdit = false;
                }

                intent.putExtra("is", true);
                startActivity(intent);
                finish();
            } else if (ExpandableItemListActivity.comingFrom == 1) {

                if (CreatePurchaseActivity.fromsalelist) {
                    CreatePurchaseActivity.isForEdit = true;
                } else {
                    CreatePurchaseActivity.isForEdit = false;
                }
                Intent intent = new Intent(this, CreatePurchaseActivity.class);
                intent.putExtra("is", true);
                startActivity(intent);
                finish();
            } else if (ExpandableItemListActivity.comingFrom == 2) {
                if (CreateSaleReturnActivity.fromsalelist) {
                    CreateSaleReturnActivity.isForEdit = true;
                } else {
                    CreateSaleReturnActivity.isForEdit = false;
                }
                Intent intent = new Intent(this, CreateSaleReturnActivity.class);
                intent.putExtra("is", true);
                startActivity(intent);
                finish();
            } else if (ExpandableItemListActivity.comingFrom == 3) {
                if (CreatePurchaseReturnActivity.fromsalelist) {
                    CreatePurchaseReturnActivity.isForEdit = true;
                } else {
                    CreatePurchaseReturnActivity.isForEdit = false;
                }

                Intent intent = new Intent(this, CreatePurchaseReturnActivity.class);
                intent.putExtra("is", true);
                startActivity(intent);
                finish();
            } else if (ExpandableItemListActivity.comingFrom == 4) {
                Intent intent = new Intent(this, CreateStockTransferActivity.class);
                intent.putExtra("is", true);
                startActivity(intent);
                finish();
            } else if (ExpandableItemListActivity.comingFrom == 5) {
                finish();
            } else if (ExpandableItemListActivity.comingFrom == 6) {
                Intent intent = new Intent(this, FirstPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(this, MasterDashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        } else {
            finish();
        }


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!ExpandableItemListActivity.isDirectForItem) {
                    if (ExpandableItemListActivity.comingFrom == 0) {

                        Intent intent = new Intent(this, CreateSaleActivity.class);
                        intent.putExtra("is", true);
                       /* if(fromsalelist){
                            intent.putExtra("fromsalelist", true);
                        }
                        else{
                            intent.putExtra("fromsalelist", false);
                        }*/
                        startActivity(intent);
                        finish();
                    } else if (ExpandableItemListActivity.comingFrom == 1) {
                        Intent intent = new Intent(this, CreatePurchaseActivity.class);
                        intent.putExtra("is", true);
                        startActivity(intent);
                        finish();
                    } else if (ExpandableItemListActivity.comingFrom == 2) {
                        Intent intent = new Intent(this, CreateSaleReturnActivity.class);
                        intent.putExtra("is", true);
                        startActivity(intent);
                        finish();
                    } else if (ExpandableItemListActivity.comingFrom == 3) {
                        Intent intent = new Intent(this, CreatePurchaseReturnActivity.class);
                        intent.putExtra("is", true);
                        startActivity(intent);
                        finish();
                    } else if (ExpandableItemListActivity.comingFrom == 4) {
                        Intent intent = new Intent(this, CreateStockTransferActivity.class);
                        intent.putExtra("is", true);
                        startActivity(intent);
                        finish();
                    } else if (ExpandableItemListActivity.comingFrom == 5) {
                        finish();
                    } else if (ExpandableItemListActivity.comingFrom == 6) {
                        Intent intent = new Intent(this, FirstPageActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(this, MasterDashboardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                } else {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void autoCompleteTextView() {
        autoCompleteTextView.setThreshold(1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameList);
        autoCompleteTextView.setAdapter(adapter);
        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                autoCompleteTextView.setText("");
                String id = idList.get(getPositionOfItem(adapter.getItem(i)));
                Timber.i("IDD----" + id);
                String[] arr = id.split(",");
                String groupid = arr[0];
                String childid = arr[1];
                String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
               /* appUser.childId = arrid;
                LocalRepositories.saveAppUser(this, appUser);
*/
                if (!isDirectForItem) {

                    if (ExpandableItemListActivity.comingFrom == 0) {
                        Intent intent = new Intent(getApplicationContext(), SaleVoucherAddItemActivity.class);
                        String itemid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
                        String arr1[] = itemName.split(",");
                        itemName = arr1[0];
                        String descr;
                        String alternate_unit;
                        String sales_price_main;
                        String sales_price_alternate;
                        Boolean batch, serial;
                        descr = listDataChildDesc.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        sales_price_main = listDataChildSalePriceMain.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        sales_price_alternate = listDataChildSalePriceAlternate.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        alternate_unit = listDataChildAlternateUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        batch = listDataChildBatchWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        serial = listDataChildSerialWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String main_unit = listDataChildUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String applied = listDataChildApplied.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String alternate_unit_con_factor = listDataChildAlternateConFactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String default_unit = listDataChildDefaultUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String packaging_unit_con_factor = listDataChildPackagingConfactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String packaging_unit_sales_price = listDataChildPackagingSalesPrice.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String packaging_unit = listDataChildPackagingUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String mrp = listDataChildMrp.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String tax = listDataTax.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String barcode = listDataBarcode.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        intent.putExtra("fromitemlist", true);
                        intent.putExtra("fromSaleVoucherItemList", true);
                        mSaleVoucherItem.put("name", itemName);
                        mSaleVoucherItem.put("desc", descr);
                        mSaleVoucherItem.put("main_unit", main_unit);
                        mSaleVoucherItem.put("alternate_unit", alternate_unit);
                        mSaleVoucherItem.put("serial_wise", String.valueOf(serial));
                        mSaleVoucherItem.put("batch_wise", String.valueOf(batch));
                        mSaleVoucherItem.put("sales_price_main", sales_price_main);
                        mSaleVoucherItem.put("applied", applied);
                        mSaleVoucherItem.put("alternate_unit_con_factor", alternate_unit_con_factor);
                        mSaleVoucherItem.put("default_unit", default_unit);
                        mSaleVoucherItem.put("packaging_unit_con_factor", packaging_unit_con_factor);
                        mSaleVoucherItem.put("packaging_unit_sales_price", packaging_unit_sales_price);
                        mSaleVoucherItem.put("packaging_unit", packaging_unit);
                        mSaleVoucherItem.put("mrp", mrp);
                        mSaleVoucherItem.put("tax", tax);
                        appUser.mMapSaleVoucherItem = mSaleVoucherItem;
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        intent.putExtra("item_id", itemid);
                        intent.putExtra("name", itemName);
                        intent.putExtra("desc", descr);
                        intent.putExtra("main_unit", main_unit);
                        intent.putExtra("alternate_unit", alternate_unit);
                        intent.putExtra("serial_wise", serial);
                        intent.putExtra("batch_wise", batch);
                        intent.putExtra("sales_price_main", sales_price_main);
                        intent.putExtra("sales_price_alternate", sales_price_alternate);
                        intent.putExtra("applied", applied);
                        intent.putExtra("alternate_unit_con_factor", alternate_unit_con_factor);
                        intent.putExtra("default_unit", default_unit);
                        intent.putExtra("packaging_unit_con_factor", packaging_unit_con_factor);
                        intent.putExtra("packaging_unit_sales_price", packaging_unit_sales_price);
                        intent.putExtra("packaging_unit", packaging_unit);
                        intent.putExtra("mrp", mrp);
                        intent.putExtra("tax", tax);
                        intent.putExtra("barcode", barcode);
                        intent.putExtra("frombillitemvoucherlist", false);
                     /*   if(fromsalelist){
                            intent.putExtra("fromsalelist", true);
                        }
                        else{
                            intent.putExtra("fromsalelist", false);
                        }*/
                        startActivity(intent);
                        finish();
                    } else if (ExpandableItemListActivity.comingFrom == 1) {
                        Intent intent = new Intent(getApplicationContext(), PurchaseAddItemActivity.class);
                        String itemid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
                        String arr1[] = itemName.split(",");
                        itemName = arr1[0];
                        String descr;
                        String alternate_unit;
                        String purchase_price_main;
                        String purchase_price_alternate;
                        Boolean batch, serial;
                        descr = listDataChildDesc.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        purchase_price_main = listDataChildPurchasePriceMain.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        purchase_price_alternate = listDataChildPurchasePriceAlternate.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        alternate_unit = listDataChildAlternateUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        batch = listDataChildBatchWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        serial = listDataChildSerialWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String main_unit = listDataChildUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String applied = listDataChildApplied.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String alternate_unit_con_factor = listDataChildAlternateConFactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String default_unit = listDataChildDefaultUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String packaging_unit_con_factor = listDataChildPackagingConfactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String packaging_unit_purchase_price = listDataChildPackagingPurchasePrice.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String packaging_unit = listDataChildPackagingUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String mrp = listDataChildMrp.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String tax = listDataTax.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        intent.putExtra("fromitemlist", true);
                        intent.putExtra("fromPurchaseVoucherItemList", true);
                        mPurchaseVoucherItem.put("name", itemName);
                        mPurchaseVoucherItem.put("desc", descr);
                        mPurchaseVoucherItem.put("main_unit", main_unit);
                        mPurchaseVoucherItem.put("alternate_unit", alternate_unit);
                        mPurchaseVoucherItem.put("serial_wise", String.valueOf(serial));
                        mPurchaseVoucherItem.put("batch_wise", String.valueOf(batch));
                        mPurchaseVoucherItem.put("purchase_price_main", purchase_price_main);
                        mPurchaseVoucherItem.put("applied", applied);
                        mPurchaseVoucherItem.put("alternate_unit_con_factor", alternate_unit_con_factor);
                        mPurchaseVoucherItem.put("default_unit", default_unit);
                        mPurchaseVoucherItem.put("packaging_unit_con_factor", packaging_unit_con_factor);
                        mPurchaseVoucherItem.put("packaging_unit_purchase_price", packaging_unit_purchase_price);
                        mPurchaseVoucherItem.put("packaging_unit", packaging_unit);
                        mPurchaseVoucherItem.put("mrp", mrp);
                        appUser.mMapPurchaseVoucherItem = mPurchaseVoucherItem;
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        intent.putExtra("item_id", itemid);
                        intent.putExtra("name", itemName);
                        intent.putExtra("desc", descr);
                        intent.putExtra("main_unit", main_unit);
                        intent.putExtra("alternate_unit", alternate_unit);
                        intent.putExtra("serial_wise", serial);
                        intent.putExtra("batch_wise", batch);
                        intent.putExtra("purchase_price_main", purchase_price_main);
                        intent.putExtra("purchase_price_alternate", purchase_price_alternate);
                        intent.putExtra("applied", applied);
                        intent.putExtra("alternate_unit_con_factor", alternate_unit_con_factor);
                        intent.putExtra("default_unit", default_unit);
                        intent.putExtra("packaging_unit_con_factor", packaging_unit_con_factor);
                        intent.putExtra("packaging_unit_purchase_price", packaging_unit_purchase_price);
                        intent.putExtra("packaging_unit", packaging_unit);
                        intent.putExtra("mrp", mrp);
                        intent.putExtra("tax", tax);
                        startActivity(intent);
                        finish();
                    } else if (ExpandableItemListActivity.comingFrom == 2) {
                        //Toast.makeText(ExpandableItemListActivity.this, "2", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), SaleReturnAddItemActivity.class);
                        String itemid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
                        String arr1[] = itemName.split(",");
                        itemName = arr1[0];
                        String descr;
                        String alternate_unit;
                        String sales_price_main;
                        String sales_price_alternate;
                        Boolean batch, serial;
                        descr = listDataChildDesc.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        sales_price_main = listDataChildSalePriceMain.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        sales_price_alternate = listDataChildSalePriceAlternate.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        alternate_unit = listDataChildAlternateUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        batch = listDataChildBatchWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        serial = listDataChildSerialWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String main_unit = listDataChildUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String applied = listDataChildApplied.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String alternate_unit_con_factor = listDataChildAlternateConFactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String default_unit = listDataChildDefaultUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String packaging_unit_con_factor = listDataChildPackagingConfactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String packaging_unit_sales_price = listDataChildPackagingSalesPrice.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String packaging_unit = listDataChildPackagingUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String mrp = listDataChildMrp.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String tax = listDataTax.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String barcode = listDataBarcode.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        intent.putExtra("fromitemlist", true);
                        intent.putExtra("fromSaleVoucherItemList", true);
                        mSaleReturnItem.put("name", itemName);
                        mSaleReturnItem.put("desc", descr);
                        mSaleReturnItem.put("main_unit", main_unit);
                        mSaleReturnItem.put("alternate_unit", alternate_unit);
                        mSaleReturnItem.put("serial_wise", String.valueOf(serial));
                        mSaleReturnItem.put("batch_wise", String.valueOf(batch));
                        mSaleReturnItem.put("sales_price_main", sales_price_main);
                        mSaleReturnItem.put("applied", applied);
                        mSaleReturnItem.put("alternate_unit_con_factor", alternate_unit_con_factor);
                        mSaleReturnItem.put("default_unit", default_unit);
                        mSaleReturnItem.put("packaging_unit_con_factor", packaging_unit_con_factor);
                        mSaleReturnItem.put("packaging_unit_sales_price", packaging_unit_sales_price);
                        mSaleReturnItem.put("packaging_unit", packaging_unit);
                        mSaleReturnItem.put("mrp", mrp);
                        mSaleReturnItem.put("tax", tax);
                        appUser.mMapSaleReturnItem = mSaleReturnItem;
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        intent.putExtra("item_id", itemid);
                        intent.putExtra("name", itemName);
                        intent.putExtra("desc", descr);
                        intent.putExtra("main_unit", main_unit);
                        intent.putExtra("alternate_unit", alternate_unit);
                        intent.putExtra("serial_wise", serial);
                        intent.putExtra("batch_wise", batch);
                        intent.putExtra("sales_price_main", sales_price_main);
                        intent.putExtra("sales_price_alternate", sales_price_alternate);
                        intent.putExtra("applied", applied);
                        intent.putExtra("alternate_unit_con_factor", alternate_unit_con_factor);
                        intent.putExtra("default_unit", default_unit);
                        intent.putExtra("packaging_unit_con_factor", packaging_unit_con_factor);
                        intent.putExtra("packaging_unit_sales_price", packaging_unit_sales_price);
                        intent.putExtra("packaging_unit", packaging_unit);
                        intent.putExtra("mrp", mrp);
                        intent.putExtra("tax", tax);
                        intent.putExtra("barcode", barcode);
                        intent.putExtra("frombillitemvoucherlist", false);
                        startActivity(intent);
                        finish();
                    } else if (ExpandableItemListActivity.comingFrom == 3) {

                        Intent intent = new Intent(getApplicationContext(), PurchaseReturnAddItemActivity.class);
                        String itemid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
                        String arr1[] = itemName.split(",");
                        itemName = arr1[0];
                        String descr;
                        String alternate_unit;
                        String purchase_price_main;
                        String purchase_price_alternate;
                        Boolean batch, serial;
                        descr = listDataChildDesc.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        purchase_price_main = listDataChildPurchasePriceMain.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        purchase_price_alternate = listDataChildPurchasePriceAlternate.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        alternate_unit = listDataChildAlternateUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        batch = listDataChildBatchWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        serial = listDataChildSerialWise.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String main_unit = listDataChildUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String applied = listDataChildApplied.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String alternate_unit_con_factor = listDataChildAlternateConFactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String default_unit = listDataChildDefaultUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String packaging_unit_con_factor = listDataChildPackagingConfactor.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String packaging_unit_purchase_price = listDataChildPackagingPurchasePrice.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String packaging_unit = listDataChildPackagingUnit.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String mrp = listDataChildMrp.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String tax = listDataTax.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        intent.putExtra("fromitemlist", true);
                        intent.putExtra("fromPurchaseReturnItemList", true);
                        intent.putExtra("id", childid);
                        intent.putExtra("fromitemlist", true);
                        intent.putExtra("fromPurchaseReturnItemList", true);

                        mPurchaseReturnItem.put("name", itemName);
                        mPurchaseReturnItem.put("desc", descr);
                        mPurchaseReturnItem.put("main_unit", main_unit);
                        mPurchaseReturnItem.put("alternate_unit", alternate_unit);
                        mPurchaseReturnItem.put("serial_wise", String.valueOf(serial));
                        mPurchaseReturnItem.put("batch_wise", String.valueOf(batch));
                        mPurchaseReturnItem.put("purchase_price_main", purchase_price_main);
                        mPurchaseReturnItem.put("applied", applied);
                        mPurchaseReturnItem.put("alternate_unit_con_factor", alternate_unit_con_factor);
                        mPurchaseReturnItem.put("default_unit", default_unit);
                        mPurchaseReturnItem.put("packaging_unit_con_factor", packaging_unit_con_factor);
                        mPurchaseReturnItem.put("packaging_unit_purchase_price", packaging_unit_purchase_price);
                        mPurchaseReturnItem.put("packaging_unit", packaging_unit);
                        mPurchaseReturnItem.put("mrp", mrp);
                        appUser.mMapPurchaseReturnItem = mPurchaseReturnItem;
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        intent.putExtra("item_id", itemid);
                        intent.putExtra("name", itemName);
                        intent.putExtra("desc", descr);
                        intent.putExtra("main_unit", main_unit);
                        intent.putExtra("alternate_unit", alternate_unit);
                        intent.putExtra("serial_wise", serial);
                        intent.putExtra("batch_wise", batch);
                        intent.putExtra("purchase_price_main", purchase_price_main);
                        intent.putExtra("purchase_price_alternate", purchase_price_alternate);
                        intent.putExtra("applied", applied);
                        intent.putExtra("alternate_unit_con_factor", alternate_unit_con_factor);
                        intent.putExtra("default_unit", default_unit);
                        intent.putExtra("packaging_unit_con_factor", packaging_unit_con_factor);
                        intent.putExtra("packaging_unit_purchase_price", packaging_unit_purchase_price);
                        intent.putExtra("packaging_unit", packaging_unit);
                        intent.putExtra("mrp", mrp);
                        intent.putExtra("tax", tax);
                        startActivity(intent);
                        finish();


                    } else if (ExpandableItemListActivity.comingFrom == 5) {
                        Intent intent = new Intent();
                        String itemid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
                        String arr1[] = itemName.split(",");
                        String item = arr1[0];
                        intent.putExtra("item_id", itemid);
                        intent.putExtra("name", itemName);
                        intent.putExtra("from_stock_in_hand", false);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                }
                  /*  Intent returnIntent = new Intent();
                    returnIntent.putExtra("name", adapter.getItem(i));
                    String id = idList.get(getPositionOfItem(adapter.getItem(i)));
                    returnIntent.putExtra("id", id);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();*/
            }
        });
        /*if (isDirectForItem){
            autoCompleteTextView.setVisibility(View.GONE);
        }*/
    }

    private int getPositionOfItem(String category) {
        for (int i = 0; i < this.nameList.size(); i++) {
            if (this.nameList.get(i) == category)
                return i;
        }

        return -1;
    }

    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();
    }

    public int taxSplit(String tax) {
        int a = 0;
        if (tax.contains("GST ")) {
            String[] arr = tax.split(" ");
            String[] arr1 = arr[1].split("%");
            a = Integer.parseInt(arr1[0]);
        }
        return a;
    }

    @Subscribe
    public void getVoucherNumber(VoucherSeriesResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            for (int i = 0; i < response.getVoucher_series().getData().size(); i++) {
                if (response.getVoucher_series().getData().get(i).getAttributes().isDefaults()) {
                    Preferences.getInstance(getApplicationContext()).setVoucherSeries(response.getVoucher_series().getData().get(i).getAttributes().getName());
                    Preferences.getInstance(getApplicationContext()).setVoucher_number(response.getVoucher_series().getData().get(i).getAttributes().getVoucher_number());
                }
            }
        } else {
            Helpers.dialogMessage(ExpandableItemListActivity.this, response.getMessage());
        }
    }
}


