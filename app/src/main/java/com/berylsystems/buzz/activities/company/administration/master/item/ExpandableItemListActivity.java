package com.berylsystems.buzz.activities.company.administration.master.item;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.app.BaseActivityCompany;
import com.berylsystems.buzz.activities.app.ConnectivityReceiver;
import com.berylsystems.buzz.activities.company.administration.master.account.ExpandableAccountListActivity;
import com.berylsystems.buzz.activities.company.transaction.purchase.CreatePurchaseActivity;
import com.berylsystems.buzz.activities.company.transaction.purchase.PurchaseAddItemActivity;
import com.berylsystems.buzz.activities.company.transaction.purchase_return.CreatePurchaseReturnActivity;
import com.berylsystems.buzz.activities.company.transaction.purchase_return.PurchaseReturnAddItemActivity;
import com.berylsystems.buzz.activities.company.transaction.sale.CreateSaleActivity;
import com.berylsystems.buzz.activities.company.transaction.sale.SaleVoucherAddItemActivity;
import com.berylsystems.buzz.activities.company.transaction.sale_return.CreateSaleReturnActivity;
import com.berylsystems.buzz.activities.company.transaction.sale_return.SaleReturnAddItemActivity;
import com.berylsystems.buzz.activities.dashboard.MasterDashboardActivity;
import com.berylsystems.buzz.adapters.ItemExpandableListAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.ApiCallsService;
import com.berylsystems.buzz.networks.api_response.item.DeleteItemResponse;
import com.berylsystems.buzz.networks.api_response.item.GetItemResponse;
import com.berylsystems.buzz.utils.Cv;
import com.berylsystems.buzz.utils.EventDeleteItem;
import com.berylsystems.buzz.utils.EventEditItem;
import com.berylsystems.buzz.utils.EventSaleAddItem;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ExpandableItemListActivity extends AppCompatActivity {

    public static Boolean isDirectForItem = true;
    public static Integer comingFrom = 0;

    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.lvExp)
    ExpandableListView expListView;
    @Bind(R.id.floating_button)
    FloatingActionButton floatingActionButton;
    @Bind(R.id.autoCompleteTextView)
    AutoCompleteTextView autoCompleteTextView;
    ItemExpandableListAdapter listAdapter;
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
    public Map<String, String> mSaleVoucherItem;
    HashMap<Integer, List<String>> listDataChildPurchasePriceMain;
    HashMap<Integer, List<String>> listDataChildPurchasePriceAlternate;
    HashMap<Integer, List<String>> listDataChildPackagingPurchasePrice;
    public Map<String, String> mPurchaseVoucherItem;

    public Map<String, String> mPurchaseReturnItem;
    public Map<String, String> mSaleReturnItem;


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

    List<String> nameList;
    List<String> idList;
    private ArrayAdapter<String> adapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_item_list);
        ButterKnife.bind(this);
        initActionbar();
        appUser = LocalRepositories.getAppUser(this);
        floatingActionButton.bringToFront();
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
        Boolean isConnected = ConnectivityReceiver.isConnected();
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

        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_on_click);
        v.startAnimation(animFadeIn);
        Intent intent = new Intent(getApplicationContext(), CreateNewItemActivity.class);
        intent.putExtra("fromlist", true);
        startActivity(intent);
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
                Snackbar.make(coordinatorLayout, "No Item Found!!", Snackbar.LENGTH_LONG).show();
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
                id = new ArrayList<>();
                default_unit.clear();
                for (int j = 0; j < response.getOrdered_items().get(i).getData().size(); j++) {
                    name.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getName());

                    nameList.add(response.getOrdered_items().get(i).getData().get(j).getAttributes().getName());
                    idList.add(String.valueOf(i)+","+String.valueOf(j));

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
            }
            listAdapter = new ItemExpandableListAdapter(this, listDataHeader, listDataChild);

            // setting list adapter
            expListView.setAdapter(listAdapter);

            expListView.setAdapter(listAdapter);
            for (int i = 0; i < listAdapter.getGroupCount(); i++) {
                expListView.expandGroup(i);
            }

            autoCompleteTextView();


        } else {
            //   startActivity(new Intent(getApplicationContext(), MasterDashboardActivity.class));
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
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
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
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
                intent.putExtra("id", itemid);
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
            } else if (ExpandableItemListActivity.comingFrom == 1) {
                Intent intent = new Intent(getApplicationContext(), PurchaseAddItemActivity.class);
                String itemid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
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
                intent.putExtra("id", itemid);
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
                intent.putExtra("id", itemid);
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
                LocalRepositories.saveAppUser(this, appUser);

                intent.putExtra("id", itemid);
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


            }
        }
    }

    @Override
    public void onBackPressed() {
        if (!ExpandableItemListActivity.isDirectForItem) {
            if (ExpandableItemListActivity.comingFrom == 0) {
                Intent intent = new Intent(this, CreateSaleActivity.class);
                intent.putExtra("is", true);
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
                String id = idList.get(getPositionOfItem(adapter.getItem(i)));
                Timber.i("IDD----"+id);
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
                        intent.putExtra("id", itemid);
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
                    } else if (ExpandableItemListActivity.comingFrom == 1) {
                        Intent intent = new Intent(getApplicationContext(), PurchaseAddItemActivity.class);
                        String itemid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
                        String itemName = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
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
                        intent.putExtra("id", itemid);
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
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        intent.putExtra("id", itemid);
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

                        intent.putExtra("id", itemid);
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
}


