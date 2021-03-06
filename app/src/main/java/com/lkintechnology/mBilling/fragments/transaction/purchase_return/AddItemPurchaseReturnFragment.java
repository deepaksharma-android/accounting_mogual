package com.lkintechnology.mBilling.fragments.transaction.purchase_return;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.FirstPageActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.billsundry.BillSundryListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase_return.PurchaseReturnAddBillActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase_return.PurchaseReturnAddItemActivity;
import com.lkintechnology.mBilling.adapters.AddBillsPurchaseReturnAdapter;
import com.lkintechnology.mBilling.adapters.AddItemsPurchaseReturnAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.ListHeight;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by BerylSystems on 11/22/2017.
 */

public class AddItemPurchaseReturnFragment extends Fragment {
    @Bind(R.id.add_item_button)
    LinearLayout add_item_button;
    @Bind(R.id.add_bill_button)
    LinearLayout add_bill_button;
    @Bind(R.id.listViewItems)
    ListView listViewItems;
    @Bind(R.id.listViewBills)
    ListView listViewBills;
    @Bind(R.id.total)
    TextView mTotal;
    AppUser appUser;
    RecyclerView.LayoutManager layoutManager;
    Animation blinkOnClick;
    public static AddItemPurchaseReturnFragment context;
    Boolean discount_bool=false;
    Boolean absolute_bool=false;
    Boolean cgst_sgst_bool=false;
    Double dicount_amount=0.0;
    Double absolute_amount=0.0;
    Double cgst_sgst_amount=0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_purchase_return, container, false);
        ButterKnife.bind(this, view);
        blinkOnClick = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_on_click);
        appUser = LocalRepositories.getAppUser(getActivity());
        context= AddItemPurchaseReturnFragment.this;
        appUser.billsundrytotalPurchase.clear();
        appUser.itemtotalPurchase.clear();
        LocalRepositories.saveAppUser(getActivity(),appUser);

        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Preferences.getInstance(getApplicationContext()).getSale_type_name().equals("")) {
                    add_item_button.startAnimation(blinkOnClick);
                    Intent intent = new Intent(getContext(), ExpandableItemListActivity.class);
                    ExpandableItemListActivity.comingFrom = 3;
                    FirstPageActivity.posSetting = false;
                    ExpandableItemListActivity.isDirectForItem = false;
                    startActivity(intent);
                    getActivity().finish();
                }
                else{
                    alertdialog();
                }
            }
        });
        add_bill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Preferences.getInstance(getApplicationContext()).getSale_type_name().equals("")) {
                    add_bill_button.startAnimation(blinkOnClick);
                    ExpandableItemListActivity.comingFrom = 3;
                    BillSundryListActivity.isDirectForBill = false;
                    startActivity(new Intent(getContext(), BillSundryListActivity.class));
                    getActivity().finish();
                }
                else{
                    alertdialog();
                }
            }
        });



        Timber.i("mListMapForItemPurchaseReturn" + appUser.mListMapForItemPurchaseReturn);
        Timber.i("mListMapForBillPurchaseReturn" + appUser.mListMapForBillPurchaseReturn);

      /*  listViewItems.setAdapter(new AddItemsPurchaseReturnAdapter(getContext(), appUser.mListMapForItemPurchaseReturn));
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);

        Timber.i("mListMapForBillPurchase" + appUser.mListMapForBillPurchaseReturn);
        listViewBills.setAdapter(new AddBillsPurchaseReturnAdapter(getContext(), appUser.mListMapForBillPurchaseReturn));
        ListHeight.setListViewHeightBasedOnChildren(listViewBills);
        ListHeight.setListViewHeightBasedOnChildren(listViewBills);
*/
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Removing...");

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), PurchaseReturnAddItemActivity.class);
                intent.putExtra("bool", true);
                intent.putExtra("frombillitemvoucherlist", true);
                intent.putExtra("pos", i);
                startActivity(intent);
                getActivity().finish();
            }
        });

        listViewBills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), PurchaseReturnAddBillActivity.class);
                intent.putExtra("frombillvoucherlist", true);
                intent.putExtra("pos", i);
                startActivity(intent);
                getActivity().finish();
            }
        });
        amountCalculation();
/*
        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PurchaseReturnAddItemActivity.class);
                intent.putExtra("bool", true);
                ExpandableItemListActivity.comingFrom = 3;
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });*/
       /* listViewBills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               *//* Intent intent = new Intent(getContext(), PurchaseReturnAddBillActivity.class);
                ExpandableItemListActivity.comingFrom = 3;
                intent.putExtra("bool", true);
                intent.putExtra("position", position);
                startActivity(intent);*//*
            }
        });*/

        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Are you sure to delete?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        AppUser appUser=LocalRepositories.getAppUser(getApplicationContext());
                        appUser.mListMapForItemPurchaseReturn.remove(position);
                        appUser.billsundrytotalPurchase.clear();
                        appUser.itemtotalPurchase.clear();
                        LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                        dialog.cancel();
                        amountCalculation();
                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
                return true;

            }
        });
        listViewBills.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Are you sure to delete?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        AppUser appUser=LocalRepositories.getAppUser(getApplicationContext());
                        appUser.mListMapForBillPurchaseReturn.remove(position);
                        appUser.itemtotalPurchase.clear();
                        appUser.billsundrytotalPurchase.clear();
                        LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                        dialog.cancel();
                        Timber.i("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"+appUser.mListMapForBillPurchaseReturn);
                        discount_bool=false;
                        dicount_amount=0.0;
                        absolute_bool=false;
                        cgst_sgst_bool=false;
                        absolute_amount=0.0;
                        amountCalculation();


                    }
                });
                alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alertDialog.show();
                return true;
            }
        });
        return view;
    }

    private void amountCalculation(){
        String taxstring = Preferences.getInstance(getApplicationContext()).getSale_type_name();
        double itemamount = 0.0;
        double billsundrymamount = 0.0;
        double billsundrymamounttotal = 0.0;
        double changeamount=0.0;

        appUser = LocalRepositories.getAppUser(getApplicationContext());

        if (appUser.mListMapForItemPurchaseReturn.size() > 0) {
            for (int i = 0; i < appUser.mListMapForItemPurchaseReturn.size(); i++) {

                Map map = appUser.mListMapForItemPurchaseReturn.get(i);
                String total = (String) map.get("total");

                if (total.equals("")) {
                    total = "0.0";
                } else {
                    appUser.itemtotalPurchase.add(total);
                    LocalRepositories.saveAppUser(getActivity(),appUser);
                    double tot = Double.parseDouble(total);
                    itemamount = itemamount + tot;
                    mTotal.setText(String.valueOf(itemamount));
                }
            }


        } else {
            itemamount = 0.0;
        }

        if (appUser.mListMapForBillPurchaseReturn.size() > 0) {
            LocalRepositories.saveAppUser(getActivity(),appUser);
            for (int i = 0; i < appUser.mListMapForBillPurchaseReturn.size(); i++) {
                billsundrymamount = 0.0;
                Map map = appUser.mListMapForBillPurchaseReturn.get(i);
                String billsundryname = (String) map.get("courier_charges");
                String amount = (String) map.get("amount");
                String type = (String) map.get("type");
                String other = (String) map.get("other");
                String fedas = (String) map.get("fed_as");
                String fed_as_percentage = (String) map.get("fed_as_percentage");
                if(fed_as_percentage!=null) {
                    if (fed_as_percentage.equals("valuechange")) {
                        changeamount = Double.parseDouble((String) map.get("changeamount"));
                    }
                }
                String percentage_value = (String) map.get("percentage_value");
                String number_of_bill = (String) map.get("number_of_bill");
                String consolidated = (String) map.get("consolidated");
                double amt = Double.parseDouble(amount);
                if (fedas.equals("Absolute Amount")) {
                    if (type.equals("Additive")) {
                        billsundrymamount = billsundrymamount + amt;
                    } else {
                        billsundrymamount = billsundrymamount - amt;
                    }
                    absolute_bool=true;
                    absolute_amount=amt;
                    mTotal.setText(String.valueOf(billsundrymamount+itemamount));
                } else if (fedas.equals("Per Main Qty.")) {
                    if (appUser.mListMapForItemPurchaseReturn.size() > 0) {
                        for (int j = 0; j < appUser.mListMapForItemPurchaseReturn.size(); j++) {
                            Map mapj = appUser.mListMapForItemPurchaseReturn.get(j);
                            double subtot = 0.0;
                            String price_selected_unit = (String) mapj.get("price_selected_unit");
                            if (price_selected_unit.equals("main")) {
                                String quantity = (String) mapj.get("quantity");
                                int quan = Integer.parseInt(quantity);
                                subtot = subtot + (amt * quan);
                                if (type.equals("Additive")) {
                                    billsundrymamount = billsundrymamount + subtot;
                                } else {
                                    billsundrymamount = billsundrymamount - subtot;
                                }

                            } else if (price_selected_unit.equals("alternate")) {
                                String alternate_unit_con_factor = (String) mapj.get("alternate_unit_con_factor");
                                if (alternate_unit_con_factor.equals("") || alternate_unit_con_factor.equals("0.0")) {
                                    alternate_unit_con_factor = "1.0";
                                }
                                double con_factor = Double.parseDouble(alternate_unit_con_factor);
                                String quantity = (String) mapj.get("quantity");
                                int quan = Integer.parseInt(quantity);
                                subtot = subtot + ((amt * quan) / con_factor);
                                if (type.equals("Additive")) {
                                    billsundrymamount = billsundrymamount + subtot;
                                } else {
                                    billsundrymamount = billsundrymamount - subtot;
                                }
                            } else {
                                String quantity = (String) mapj.get("quantity");
                                String packaging_unit_con_factor = (String) mapj.get("packaging_unit_con_factor");
                                if (packaging_unit_con_factor.equals("") || packaging_unit_con_factor.equals("0.0")) {
                                    packaging_unit_con_factor = "1.0";
                                }
                                double packaging_con = Double.parseDouble(packaging_unit_con_factor);
                                int quan = Integer.parseInt(quantity);
                                subtot = subtot + (amt * quan * packaging_con);
                                if (type.equals("Additive")) {
                                    billsundrymamount = billsundrymamount + subtot;
                                } else {
                                    billsundrymamount = billsundrymamount - subtot;
                                }
                            }
                        }

                    } else {
                        billsundrymamount = 0.0;
                        itemamount = 0.0;

                    }

                } else if (fedas.equals("Per Alt. Qty.")) {
                    if (appUser.mListMapForItemPurchaseReturn.size() > 0) {
                        for (int j = 0; j < appUser.mListMapForItemPurchaseReturn.size(); j++) {
                            Map mapj = appUser.mListMapForItemPurchaseReturn.get(j);
                            double subtot = 0.0;
                            String price_selected_unit = (String) mapj.get("price_selected_unit");
                            String alternate_unit_con_factor = (String) mapj.get("alternate_unit_con_factor");
                            if (alternate_unit_con_factor.equals("0.0") || alternate_unit_con_factor.equals("")) {
                                alternate_unit_con_factor = "1.0";
                            }
                            double con_factor = Double.parseDouble(alternate_unit_con_factor);
                            if (price_selected_unit.equals("main")) {
                                String quantity = (String) mapj.get("quantity");
                                int quan = Integer.parseInt(quantity);
                                subtot = subtot + (amt * quan * con_factor);
                                if (type.equals("Additive")) {
                                    billsundrymamount = billsundrymamount + subtot;
                                } else {
                                    billsundrymamount = billsundrymamount - subtot;
                                }

                            } else if (price_selected_unit.equals("alternate")) {
                                String quantity = (String) mapj.get("quantity");
                                int quan = Integer.parseInt(quantity);
                                subtot = subtot + (amt * quan);
                                if (type.equals("Additive")) {
                                    billsundrymamount = billsundrymamount + subtot;
                                } else {
                                    billsundrymamount = billsundrymamount - subtot;
                                }
                            } else {

                                String quantity = (String) mapj.get("quantity");
                                String packaging_unit_con_factor = (String) mapj.get("packaging_unit_con_factor");
                                if (packaging_unit_con_factor.equals("") || packaging_unit_con_factor.equals("0.0")) {
                                    packaging_unit_con_factor = "1.0";
                                }
                                double packaging_con = Double.parseDouble(packaging_unit_con_factor);
                                int quan = Integer.parseInt(quantity);
                                subtot = subtot + (amt * quan * packaging_con * con_factor);
                                if (type.equals("Additive")) {
                                    billsundrymamount = billsundrymamount + subtot;
                                } else {
                                    billsundrymamount = billsundrymamount - subtot;
                                }
                            }
                        }

                    } else {
                        billsundrymamount = 0.0;
                        itemamount = 0.0;

                    }
                } else if (fedas.equals("Per Packaging Qty.")) {
                    if (appUser.mListMapForItemPurchaseReturn.size() > 0) {
                        for (int j = 0; j < appUser.mListMapForItemPurchaseReturn.size(); j++) {
                            Map mapj = appUser.mListMapForItemPurchaseReturn.get(j);
                            double subtot = 0.0;
                            String price_selected_unit = (String) mapj.get("price_selected_unit");
                            String alternate_unit_con_factor = (String) mapj.get("alternate_unit_con_factor");
                            if (alternate_unit_con_factor.equals("") || alternate_unit_con_factor.equals("0.0")) {
                                alternate_unit_con_factor = "1.0";
                            }
                            double con_factor = Double.parseDouble(alternate_unit_con_factor);
                            String packagingunitconfactor = (String) mapj.get("packaging_unit_con_factor");
                            int pckconfac = Integer.parseInt(packagingunitconfactor);
                            if (pckconfac != 0) {
                                if (price_selected_unit.equals("main")) {
                                    String quantity = (String) mapj.get("quantity");
                                    String packaging_unit_con_factor = (String) mapj.get("packaging_unit_con_factor");
                                    if (packaging_unit_con_factor.equals("") || packaging_unit_con_factor.equals("0.0") || packaging_unit_con_factor.equals("0")) {
                                        packaging_unit_con_factor = "1.0";
                                    }
                                    double packaging_con = Double.parseDouble(packaging_unit_con_factor);
                                    int quan = Integer.parseInt(quantity);
                                    subtot = subtot + ((amt * quan) / packaging_con);
                                    if (type.equals("Additive")) {
                                        billsundrymamount = billsundrymamount + subtot;
                                    } else {
                                        billsundrymamount = billsundrymamount - subtot;
                                    }

                                } else if (price_selected_unit.equals("alternate")) {
                                    String quantity = (String) mapj.get("quantity");
                                    String packaging_unit_con_factor = (String) mapj.get("packaging_unit_con_factor");
                                    if (packaging_unit_con_factor.equals("") || packaging_unit_con_factor.equals("0.0") || packaging_unit_con_factor.equals("0")) {
                                        packaging_unit_con_factor = "1.0";
                                    }
                                    double packaging_con = Double.parseDouble(packaging_unit_con_factor);
                                    int quan = Integer.parseInt(quantity);
                                    subtot = subtot + ((amt * quan) / (packaging_con * con_factor));
                                    if (type.equals("Additive")) {
                                        billsundrymamount = billsundrymamount + subtot;
                                    } else {
                                        billsundrymamount = billsundrymamount - subtot;
                                    }
                                } else {
                                    String quantity = (String) mapj.get("quantity");
                                    int quan = Integer.parseInt(quantity);
                                    subtot = subtot + (amt * quan);
                                    if (type.equals("Additive")) {
                                        billsundrymamount = billsundrymamount + subtot;
                                    } else {
                                        billsundrymamount = billsundrymamount - subtot;
                                    }
                                }
                            } else {
                                // billsundrymamount=0.0;
                            }
                        }

                    } else {
                        billsundrymamount = 0.0;
                        itemamount = 0.0;

                    }
                } else if (fedas.equals("Percentage")) {
                    if (fed_as_percentage.equals("Nett Bill Amount")) {
                        if (appUser.mListMapForItemPurchaseReturn.size() > 0) {
                            discount_bool = true;
                            absolute_bool=false;
                            double subtot = 0.0;
                            for (int j = 0; j < appUser.mListMapForItemPurchaseReturn.size(); j++) {
                                Map mapj = appUser.mListMapForItemPurchaseReturn.get(j);
                                String total = (String) mapj.get("total");
                                double itemtot = Double.parseDouble(total);
                                subtot = subtot + itemtot;


                            }

                            double per_val = Double.parseDouble(percentage_value);
                            double percentagebillsundry = (billsundrymamounttotal + subtot) * (((per_val / 100) * amt) / 100);

                            if (type.equals("Additive")) {
                                billsundrymamount = billsundrymamount + percentagebillsundry;



                            } else {
                                billsundrymamount = billsundrymamount - percentagebillsundry;
                            }
                            dicount_amount=percentagebillsundry;
                            mTotal.setText(String.valueOf(billsundrymamount+itemamount));

                        }

                    } else if (fed_as_percentage.equals("Items Basic Amount")) {
                        if (appUser.mListMapForItemPurchaseReturn.size() > 0) {
                            double subtot = 0.0;
                            for (int j = 0; j < appUser.mListMapForItemPurchaseReturn.size(); j++) {
                                Map mapj = appUser.mListMapForItemPurchaseReturn.get(j);
                                String total = (String) mapj.get("total");
                                double itemtot = Double.parseDouble(total);
                                subtot = subtot + itemtot;


                            }

                            double per_val = Double.parseDouble(percentage_value);
                            double percentagebillsundry = (subtot) * (((per_val / 100) * amt) / 100);

                            if (type.equals("Additive")) {
                                billsundrymamount = billsundrymamount + percentagebillsundry;
                            } else {
                                billsundrymamount = billsundrymamount - percentagebillsundry;
                            }


                        }

                    } else if (fed_as_percentage.equals("Total MRP of Items")) {
                        if (appUser.mListMapForItemPurchaseReturn.size() > 0) {
                            double subtot = 0.0;
                            for (int j = 0; j < appUser.mListMapForItemPurchaseReturn.size(); j++) {
                                Map mapj = appUser.mListMapForItemPurchaseReturn.get(j);
                                String total = (String) mapj.get("mrp");
                                String quantity = (String) mapj.get("quantity");
                                int qty = Integer.parseInt(quantity);
                                double itemtot = Double.parseDouble(total);
                                subtot = subtot + (itemtot * qty);


                            }

                            double per_val = Double.parseDouble(percentage_value);
                            double percentagebillsundry = (subtot) * (((per_val / 100) * amt) / 100);

                            if (type.equals("Additive")) {
                                billsundrymamount = billsundrymamount + percentagebillsundry;
                            } else {
                                billsundrymamount = billsundrymamount - percentagebillsundry;
                            }


                        }

                    } else if (fed_as_percentage.equals("Taxable Amount")) {
                        if (appUser.mListMapForItemPurchaseReturn.size() > 0) {
                            double taxval = 0.0;
                            double subtot = 0.0;
                            for (int j = 0; j < appUser.mListMapForItemPurchaseReturn.size(); j++) {
                                Map mapj = appUser.mListMapForItemPurchaseReturn.get(j);
                                String itemtotalval = (String) mapj.get("total");
                                String itemtax = (String) mapj.get("tax");
                                String arr[] = itemtax.split(" ");
                                String taxpercentage = null;
                                if(!arr[0].equals("Exempt")){
                                    String itemtaxval = arr[1];
                                    String arrper[] = itemtaxval.split("%");
                                    taxpercentage = arrper[0];
                                }
                                else{
                                    taxpercentage="0";
                                }
                                double taxpercentagevalue = Double.parseDouble(taxpercentage);
                                double multi=0.0;
                                double itemprice = Double.parseDouble(itemtotalval);
                                String taxname="";
                                String taxvalue="";
                                if (taxstring.startsWith("I")) {
                                    String arrtaxstring[] = taxstring.split("-");
                                    taxname = arrtaxstring[0].trim();
                                    taxvalue = arrtaxstring[1].trim();
                                    if (taxvalue.equals("MultiRate")) {
                                        if (taxpercentage.equals(amount)) {
                                            multi = itemprice * (taxpercentagevalue / 100);
                                            subtot = subtot + multi;
                                        }
                                        else{
                                            if(billsundryname.equals("CGST")||billsundryname.equals("SGST")){
                                                multi = itemprice * (Double.parseDouble(amount) / 100);
                                                subtot = subtot + multi;
                                            }
                                        }

                                    } else if (taxvalue.equals("TaxIncl.")) {
                                        double per_val = Double.parseDouble(percentage_value);
                                        subtot = subtot + (itemprice / (100 + taxpercentagevalue)) * ((amt * per_val) / 100);

                                    }
                                }

                                if (billsundryname.equals("IGST")&&taxstring.startsWith("I")&&!taxvalue.equals("MultiRate")&&!taxvalue.equals("TaxIncl")) {
                                    if(taxvalue.equals("ItemWise")){

                                    }
                                    else {
                                        if (discount_bool) {
                                            if (!absolute_bool) {
                                                subtot = subtot + ((Double.parseDouble(mTotal.getText().toString())) + absolute_amount) * (amt / 100);
                                            } else {
                                                subtot = subtot + ((Double.parseDouble(mTotal.getText().toString()))-dicount_amount) * (amt / 100);
                                            }
                                            break;
                                        } else {
                                            if (absolute_bool) {
                                                subtot = subtot + ((Double.parseDouble(mTotal.getText().toString()))) * (amt / 100);
                                                break;
                                            } else {
                                                subtot = subtot + itemprice * (amt / 100);
                                            }
                                        }

                                    }


                                }

                                if (taxstring.startsWith("L")) {
                                    String arrtaxstring[] = taxstring.split("-");
                                    taxname = arrtaxstring[0].trim();
                                    taxvalue = arrtaxstring[1].trim();
                                    if (taxvalue.equals("MultiRate")) {
                                        Double amounts=Double.parseDouble(amount)*2;
                                        Double taxper=Double.parseDouble(taxpercentage);
                                        if (taxper.equals(amounts)) {
                                            multi = itemprice * (Double.parseDouble(amount) / 100);
                                            subtot = subtot + multi;
                                        }

                                    } else if (taxvalue.equals("TaxIncl.")) {
                                        double per_val = Double.parseDouble(percentage_value);
                                        subtot = subtot + (itemprice / (100 + taxpercentagevalue)) * ((amt * per_val) / 100);

                                    }
                                }

                                if ((billsundryname.equals("CGST")||billsundryname.equals("SGST"))&&taxstring.startsWith("L")&&!taxvalue.equals("MultiRate")&&!taxvalue.equals("TaxIncl")) {
                                    if (taxvalue.equals("ItemWise")) {

                                    } else {
                                        if(discount_bool) {
                                            if(!cgst_sgst_bool) {
                                                if(!absolute_bool) {
                                                    subtot = subtot + ((Double.parseDouble(mTotal.getText().toString())) + absolute_amount) * (amt / 100);
                                                }
                                                else{
                                                    subtot = subtot + ((Double.parseDouble(mTotal.getText().toString())) -dicount_amount) * (amt / 100);
                                                }
                                                cgst_sgst_bool=true;
                                                cgst_sgst_amount=subtot;
                                            }
                                            else{
                                                //subtot = subtot + ((Double.parseDouble(mTotal.getText().toString())) * (amt / 100))+dicount_amount;
                                                subtot=cgst_sgst_amount;
                                            }
                                            break;
                                        }

                                        else {
                                            if(absolute_bool){
                                                if(!cgst_sgst_bool) {
                                                    subtot = subtot + ((Double.parseDouble(mTotal.getText().toString()))) * (amt / 100);
                                                    cgst_sgst_bool=true;
                                                    cgst_sgst_amount=subtot;
                                                }
                                                else{
                                                    subtot=cgst_sgst_amount;
                                                }

                                                break;
                                            }
                                            else {
                                                subtot = subtot + itemprice * (amt / 100);
                                            }
                                        }
                                    }
                                }

                               /* if(!billsundryname.equals("CGST")||!billsundryname.equals("SGST")||!billsundryname.equals("IGST")){
                                    double per_val = Double.parseDouble(percentage_value);
                                    subtot = subtot+((itemprice) * (((per_val / 100) * amt) / 100));
                                }*/

                            }

                            if (type.equals("Additive")) {
                                billsundrymamount = billsundrymamount + (subtot);
                                mTotal.setText(String.valueOf(billsundrymamount+itemamount));
                            } else {
                                billsundrymamount = billsundrymamount - subtot;
                                mTotal.setText(String.valueOf(itemamount-billsundrymamount));
                            }
                        }
                    } else if (fed_as_percentage.equals("Previous Bill Sundry(s) Amount")) {
                        if (appUser.mListMapForItemPurchaseReturn.size() > 0) {
                            double subtot = 0.0;
                            int numberbill = Integer.parseInt(number_of_bill);
                            AppUser appUser = LocalRepositories.getAppUser(getActivity());
                            int size = appUser.billsundrytotalPurchase.size();
                            if (size >= numberbill) {
                                for (int j = size; j > size - numberbill; j--) {
                                    if (consolidated.equals("true")) {
                                        subtot = subtot + Double.parseDouble(appUser.billsundrytotalPurchase.get(j - 1));
                                    } else {
                                        subtot = subtot + Double.parseDouble(appUser.billsundrytotalPurchase.get(j - 1));
                                        break;
                                    }
                                }

                                double per_val = Double.parseDouble(percentage_value);
                                double percentagebillsundry = (subtot) * (((per_val / 100) * amt) / 100);

                                if (type.equals("Additive")) {
                                    billsundrymamount = billsundrymamount + percentagebillsundry;
                                } else {
                                    billsundrymamount = billsundrymamount - percentagebillsundry;
                                }


                            }
                        }

                    }
                    else if (fed_as_percentage.equals("Other Bill Sundry")) {
                        if (appUser.mListMapForBillPurchaseReturn.size() > 0) {
                            double subtot = 0.0;
                            AppUser appUser = LocalRepositories.getAppUser(getActivity());
                            for (int j = 0; j <appUser.mListMapForBillPurchaseReturn.size(); j++) {
                                Map mapj = appUser.mListMapForBillPurchaseReturn.get(j);
                                String itemname = (String) mapj.get("courier_charges");
                                if(other.equals(itemname)){
                                    subtot=subtot+Double.parseDouble(appUser.billsundrytotalPurchase.get(j));
                                }
                                else{
                                    subtot=subtot+0.0;
                                }

                            }

                            double per_val = Double.parseDouble(percentage_value);
                            double percentagebillsundry = (subtot) * (((per_val / 100) * amt) / 100);

                            if (type.equals("Additive")) {
                                billsundrymamount = billsundrymamount + percentagebillsundry;
                            } else {
                                billsundrymamount = billsundrymamount - percentagebillsundry;
                            }


                        }

                    }
                    else if(fed_as_percentage.equals("valuechange")){
                        if (appUser.mListMapForItemPurchaseReturn.size() > 0) {
                            discount_bool = true;
                            absolute_bool=false;
                            double subtot = 0.0;
                            for (int j = 0; j < appUser.mListMapForItemPurchaseReturn.size(); j++) {
                                Map mapj = appUser.mListMapForItemPurchaseReturn.get(j);
                                String total = (String) mapj.get("total");
                                double itemtot = Double.parseDouble(total);
                                subtot = subtot + itemtot;



                            }
                            if (type.equals("Additive")) {
                                billsundrymamount = billsundrymamount + changeamount;
                            } else {
                                billsundrymamount = billsundrymamount - changeamount;
                            }
                            mTotal.setText(String.valueOf(billsundrymamount+itemamount));
                           /* double per_val = Double.parseDouble(percentage_value);
                            double percentagebillsundry = (billsundrymamounttotal + subtot) * (((per_val / 100) * amt) / 100);

                            if (type.equals("Additive")) {
                                billsundrymamount = billsundrymamount + percentagebillsundry;
                            } else {
                                billsundrymamount = billsundrymamount - percentagebillsundry;
                            }*/


                        }
                    }


                }
                billsundrymamounttotal = billsundrymamounttotal + billsundrymamount;
                appUser.billsundrytotalPurchase.add(i, String.format("%.2f",billsundrymamount));
                LocalRepositories.saveAppUser(getActivity(), appUser);
            }


        } else {
            billsundrymamounttotal = 0.0;
        }

        listViewItems.setAdapter(new AddItemsPurchaseReturnAdapter(getContext(), appUser.mListMapForItemPurchaseReturn));
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);
        Timber.i("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB"+appUser.mListMapForBillPurchaseReturn);
        listViewBills.setAdapter(new AddBillsPurchaseReturnAdapter(getContext(), appUser.mListMapForBillPurchaseReturn,appUser.billsundrytotalPurchase));
        ListHeight.setListViewHeightBasedOnChildren(listViewBills);
        ListHeight.setListViewHeightBasedOnChildren(listViewBills);
        double totalbillsundryamount=0.0;
        double totalitemamount=0.0;
        for(int i=0;i<appUser.billsundrytotalPurchase.size();i++){
            String tot=appUser.billsundrytotalPurchase.get(i);
            double total=Double.parseDouble(tot);
            totalbillsundryamount=totalbillsundryamount+total;
        }
        for(int i=0;i<appUser.itemtotalPurchase.size();i++){
            String tot=appUser.itemtotalPurchase.get(i);
            double total=Double.parseDouble(tot);
            totalitemamount=totalitemamount+total;
        }
        mTotal.setText("Total Amount: " + String.format("%.2f",totalitemamount + totalbillsundryamount));
        appUser.items_amount=String.format("%.2f",totalitemamount);
        appUser.bill_sundries_amount=String.format("%.2f",totalbillsundryamount);
        appUser.totalamount=String.format("%.2f",totalitemamount + totalbillsundryamount);
        LocalRepositories.saveAppUser(getActivity(),appUser);
        // mTotal.setText("Total Amount: " + String.valueOf(itemamount + totalbillsundryamount));
    }

    public void alertdialog(){
        new AlertDialog.Builder(getContext())
                .setTitle("Purchase Return Voucher")
                .setMessage("Please add purchase return type in create voucher")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    return;

                })
                .show();
    }
}
