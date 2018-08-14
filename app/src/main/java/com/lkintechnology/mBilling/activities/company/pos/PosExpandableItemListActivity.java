package com.lkintechnology.mBilling.activities.company.pos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.CreateNewItemActivity;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.MasterDashboardActivity;
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
import com.lkintechnology.mBilling.adapters.ItemExpandableListAdapter;
import com.lkintechnology.mBilling.adapters.PosItemExpandableListAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.item.DeleteItemResponse;
import com.lkintechnology.mBilling.networks.api_response.item.GetItemResponse;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class PosExpandableItemListActivity extends AppCompatActivity {

    public static Boolean isDirectForItem = true;
    public static Integer comingFrom = 0;
    public static Integer checkForStartActivityResult = 0;
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.lvExp)
    ExpandableListView expListView;
    @Bind(R.id.floating_button)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    @Bind(R.id.top_layout)
    RelativeLayout mOverlayLayout;
    @Bind(R.id.error_layout)
    LinearLayout error_layout;
    PosItemExpandableListAdapter listAdapter;
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

    public Map<String, String> mPurchaseReturnItem;
    public Map<String, String> mSaleReturnItem;
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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pos_item_list);
        ButterKnife.bind(this);
     /*   if (isFirstTime()) {
            mOverlayLayout.setVisibility(View.INVISIBLE);
        }*/
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        dateFormatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        long date = System.currentTimeMillis();
        String dateString = dateFormatter.format(date);
        appUser.stock_in_hand_date = dateString;
//        fromsalelist = getIntent().getExtras().getBoolean("fromsalelist");

        floatingActionButton.bringToFront();
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
        if (isConnected) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
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
        appUser.mListMapForItemSale.clear();
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_on_click);
        v.startAnimation(animFadeIn);
        Map mMap;
        for (int i=0;i<listDataHeader.size();i++){
            Double total = 0.0;
            for (int j=0;j<listDataChild.get(listDataHeader.get(0)).size();j++){
                String pos = i + "," +j;
                //String id = pos.getPosition();
                String key="";
                if ((PosItemExpandableListAdapter.mMapPosItem.get(pos)!=null)){
                     key=pos;
                }

                if ( key.equals(pos)){
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
                    Double sales_price_main = Double.valueOf(listDataChildSalePriceMain.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid)));
                    String quantity = PosItemExpandableListAdapter.mMapPosItem.get(pos).toString();
                    total = sales_price_main * Double.valueOf(quantity);
                    mMap.put("item_id",itemId);
                    mMap.put("item_name",itemName);
                    mMap.put("total",total);
                    mMap.put("quantity",quantity);
                    appUser.mListMapForItemSale.add(mMap);
                    LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                }
            }
        }






        /*Intent intent = new Intent(getApplicationContext(), CreateNewItemActivity.class);
        intent.putExtra("fromitemlist", false);
        startActivity(intent);*/
        System.out.println("aaaaaaaaaaa "+PosItemExpandableListAdapter.mMapPosItem.toString());
        finish();
    }


    @Subscribe
    public void getItem(GetItemResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {

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
                Map mapForPos;
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
                mapForPos = new HashMap();
                for (int j = 0; j < response.getOrdered_items().get(i).getData().size(); j++) {
                    name.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getName()
                            + "," + String.valueOf(response.getOrdered_items().get(i).getData().get(j).getAttributes().getTotal_stock_quantity()));

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
                        salesPriceMain.add("0.0");
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
                    if (response.getOrdered_items().get(i).getData().get(j).getAttributes().getDefault_unit_for_sales() != null) {
                        default_unit.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getDefault_unit_for_sales());
                    } else {
                        default_unit.add("");
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
            listAdapter = new PosItemExpandableListAdapter(this, listDataHeader, listDataChild,listDataChildId);

            // setting list adapter
            expListView.setAdapter(listAdapter);

          /*  for (int i = 0; i < listAdapter.getGroupCount(); i++) {
                expListView.expandGroup(i);
            }*/

            //autoCompleteTextView();


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
        new AlertDialog.Builder(PosExpandableItemListActivity.this)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(PosExpandableItemListActivity.this);
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


        // autoCompleteTextView();

        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        appUser.childId = arrid;
        LocalRepositories.saveAppUser(this, appUser);

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
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
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
}


