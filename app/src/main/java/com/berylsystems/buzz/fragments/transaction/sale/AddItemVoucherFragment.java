package com.berylsystems.buzz.fragments.transaction.sale;

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

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.billsundry.BillSundryListActivity;
import com.berylsystems.buzz.activities.company.administration.master.item.ExpandableItemListActivity;
import com.berylsystems.buzz.activities.company.transaction.sale.SaleVoucherAddBillActivity;
import com.berylsystems.buzz.activities.company.transaction.sale.SaleVoucherAddItemActivity;
import com.berylsystems.buzz.adapters.AddBillsVoucherAdapter;
import com.berylsystems.buzz.adapters.AddItemsVoucherAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.ListHeight;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by BerylSystems on 11/22/2017.
 */

public class AddItemVoucherFragment extends Fragment {
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
    List<Map<String, String>> mListMap;
    RecyclerView.LayoutManager layoutManager;
    Animation blinkOnClick;
    ArrayList<String> billsuncal;
    public static AddItemVoucherFragment context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item_voucher, container, false);
        ButterKnife.bind(this, view);
        context=AddItemVoucherFragment.this;
        blinkOnClick = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_on_click);
        appUser = LocalRepositories.getAppUser(getActivity());
        appUser.billsundrytotal.clear();
        appUser.itemtotal.clear();
        LocalRepositories.saveAppUser(getActivity(), appUser);
        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Preferences.getInstance(getContext()).getSale_type_name().equals("")) {
                    add_item_button.startAnimation(blinkOnClick);
                    ExpandableItemListActivity.comingFrom = 0;
                    ExpandableItemListActivity.isDirectForItem = false;
                    Intent intent = new Intent(getContext(), ExpandableItemListActivity.class);
                    intent.putExtra("bool", true);
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
                if(!Preferences.getInstance(getContext()).getSale_type_name().equals("")) {
                    add_bill_button.startAnimation(blinkOnClick);
                    ExpandableItemListActivity.comingFrom = 0;
                    BillSundryListActivity.isDirectForBill = false;
                    startActivity(new Intent(getContext(), BillSundryListActivity.class));
                    getActivity().finish();
                }
                else{
                    alertdialog();
                }
            }
        });
        amountCalculation();

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), SaleVoucherAddItemActivity.class);
                intent.putExtra("frombillitemvoucherlist", true);
                intent.putExtra("pos", i);
                startActivity(intent);
                getActivity().finish();
            }
        });

        listViewBills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), SaleVoucherAddBillActivity.class);
                intent.putExtra("frombillvoucherlist", true);
                intent.putExtra("pos", i);
                startActivity(intent);
                getActivity().finish();
            }
        });

        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Are you sure to delete?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        AppUser appUser = LocalRepositories.getAppUser(getApplicationContext());
                        appUser.mListMapForItemSale.remove(position);
                        appUser.billsundrytotal.clear();
                        appUser.itemtotal.clear();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
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
                        AppUser appUser = LocalRepositories.getAppUser(getApplicationContext());
                        appUser.mListMapForBillSale.remove(position);
                        appUser.itemtotal.clear();
                        appUser.billsundrytotal.clear();
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
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

    public void amountCalculation() {
        String taxstring = Preferences.getInstance(getApplicationContext()).getSale_type_name();
        double itemamount = 0.0;
        double billsundrymamount = 0.0;
        double billsundrymamounttotal = 0.0;

        appUser = LocalRepositories.getAppUser(getApplicationContext());

        if (appUser.mListMapForItemSale.size() > 0) {
            for (int i = 0; i < appUser.mListMapForItemSale.size(); i++) {

                Map map = appUser.mListMapForItemSale.get(i);
                String total = (String) map.get("total");

                if (total.equals("")) {
                    total = "0.0";
                } else {
                    appUser.itemtotal.add(total);
                    LocalRepositories.saveAppUser(getActivity(), appUser);
                    double tot = Double.parseDouble(total);
                    itemamount = itemamount + tot;
                }
            }


        } else {
            itemamount = 0.0;
        }

        if (appUser.mListMapForBillSale.size() > 0) {
            LocalRepositories.saveAppUser(getActivity(), appUser);
            for (int i = 0; i < appUser.mListMapForBillSale.size(); i++) {
                billsundrymamount = 0.0;
                Map map = appUser.mListMapForBillSale.get(i);
                String billsundryname = (String) map.get("courier_charges");
                String amount = (String) map.get("amount");
                String type = (String) map.get("type");
                String other = (String) map.get("other");
                String fedas = (String) map.get("fed_as");
                String fed_as_percentage = (String) map.get("fed_as_percentage");
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
                } else if (fedas.equals("Per Main Qty.")) {
                    if (appUser.mListMapForItemSale.size() > 0) {
                        for (int j = 0; j < appUser.mListMapForItemSale.size(); j++) {
                            Map mapj = appUser.mListMapForItemSale.get(j);
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
                    if (appUser.mListMapForItemSale.size() > 0) {
                        for (int j = 0; j < appUser.mListMapForItemSale.size(); j++) {
                            Map mapj = appUser.mListMapForItemSale.get(j);
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
                    if (appUser.mListMapForItemSale.size() > 0) {
                        for (int j = 0; j < appUser.mListMapForItemSale.size(); j++) {
                            Map mapj = appUser.mListMapForItemSale.get(j);
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
                        if (appUser.mListMapForItemSale.size() > 0) {
                            double subtot = 0.0;
                            for (int j = 0; j < appUser.mListMapForItemSale.size(); j++) {
                                Map mapj = appUser.mListMapForItemSale.get(j);
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


                        }

                    } else if (fed_as_percentage.equals("Items Basic Amount")) {
                        if (appUser.mListMapForItemSale.size() > 0) {
                            double subtot = 0.0;
                            for (int j = 0; j < appUser.mListMapForItemSale.size(); j++) {
                                Map mapj = appUser.mListMapForItemSale.get(j);
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
                        if (appUser.mListMapForItemSale.size() > 0) {
                            double subtot = 0.0;
                            for (int j = 0; j < appUser.mListMapForItemSale.size(); j++) {
                                Map mapj = appUser.mListMapForItemSale.get(j);
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
                        if (appUser.mListMapForItemSale.size() > 0) {
                            double taxval = 0.0;
                            double subtot = 0.0;
                            for (int j = 0; j < appUser.mListMapForItemSale.size(); j++) {
                                Map mapj = appUser.mListMapForItemSale.get(j);
                                String itemtotalval = (String) mapj.get("total");
                                String itemtax = (String) mapj.get("tax");
                                String arr[] = itemtax.split(" ");
                                String itemtaxval = arr[1];
                                String arrper[] = itemtaxval.split("%");
                                String taxpercentage = arrper[0];
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

                                    } else if (taxvalue.equals("TaxIncl.")) {
                                        double per_val = Double.parseDouble(percentage_value);
                                        subtot = subtot + (itemprice / (100 + taxpercentagevalue)) * ((amt * per_val) / 100);

                                    }


                                }

                                if (billsundryname.equals("IGST")&&taxstring.startsWith("I")&&!taxvalue.equals("MultiRate")&&!taxvalue.equals("TaxIncl")) {
                                    if(taxvalue.equals("ItemWise")){

                                    }
                                    else{
                                        subtot=subtot+itemprice*(amt/100);
                                    }


                                }


                                if (taxstring.startsWith("L")) {
                                    String arrtaxstring[] = taxstring.split("-");
                                    taxname = arrtaxstring[0].trim();
                                    taxvalue = arrtaxstring[1].trim();
                                    if (taxvalue.equals("MultiRate")) {
                                        if (taxpercentage.equals(amount)) {
                                            multi = itemprice * (taxpercentagevalue / 100);
                                            subtot = subtot + multi;
                                        }

                                    } else if (taxvalue.equals("TaxIncl.")) {
                                        double per_val = Double.parseDouble(percentage_value);
                                        subtot = subtot + (itemprice / (100 + taxpercentagevalue)) * ((amt * per_val) / 100);

                                    }
                                }

                                if ((billsundryname.equals("CGST")||billsundryname.equals("SGST"))&&taxstring.startsWith("L")&&!taxvalue.equals("MultiRate")&&!taxvalue.equals("TaxIncl")) {
                                    if(taxvalue.equals("ItemWise")){

                                    }
                                    else{
                                        subtot=subtot+itemprice*(amt/100);
                                    }


                                }
                                if(!billsundryname.equals("CGST")||!billsundryname.equals("SGST")||!billsundryname.equals("IGST")){
                                    double per_val = Double.parseDouble(percentage_value);
                                    subtot = subtot+((itemprice) * (((per_val / 100) * amt) / 100));
                                }

                            }

                            if (type.equals("Additive")) {
                                billsundrymamount = billsundrymamount + (subtot);
                            } else {
                                billsundrymamount = billsundrymamount - subtot;
                            }
                        }

                    } else if (fed_as_percentage.equals("Previous Bill Sundry(s) Amount")) {
                        if (appUser.mListMapForItemSale.size() > 0) {
                            double subtot = 0.0;
                            int numberbill = Integer.parseInt(number_of_bill);
                            AppUser appUser = LocalRepositories.getAppUser(getActivity());
                            int size = appUser.billsundrytotal.size();
                            if (size >= numberbill) {
                                for (int j = size; j > size - numberbill; j--) {
                                    if (consolidated.equals("true")) {
                                        subtot = subtot + Double.parseDouble(appUser.billsundrytotal.get(j - 1));
                                    } else {
                                        subtot = subtot + Double.parseDouble(appUser.billsundrytotal.get(j - 1));
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

                    } else if (fed_as_percentage.equals("Other Bill Sundry")) {
                        if (appUser.mListMapForBillSale.size() > 0) {
                            double subtot = 0.0;
                            AppUser appUser = LocalRepositories.getAppUser(getActivity());
                            for (int j = 0; j < appUser.mListMapForBillSale.size(); j++) {
                                Map mapj = appUser.mListMapForBillSale.get(j);
                                String itemname = (String) mapj.get("courier_charges");
                                if (other.equals(itemname)) {
                                    subtot = subtot + Double.parseDouble(appUser.billsundrytotal.get(j));
                                } else {
                                    subtot = subtot + 0.0;
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
                billsundrymamounttotal = billsundrymamounttotal + billsundrymamount;
                appUser.billsundrytotal.add(i, String.valueOf(billsundrymamount));
                LocalRepositories.saveAppUser(getActivity(), appUser);
            }


        } else {
            billsundrymamounttotal = 0.0;
        }

        listViewItems.setAdapter(new AddItemsVoucherAdapter(getContext(), appUser.mListMapForItemSale));
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);

        listViewBills.setAdapter(new AddBillsVoucherAdapter(getContext(), appUser.mListMapForBillSale, /*appUser.mListMapForItemSale*/appUser.billsundrytotal));
        ListHeight.setListViewHeightBasedOnChildren(listViewBills);
        ListHeight.setListViewHeightBasedOnChildren(listViewBills);
        double totalbillsundryamount = 0.0;
        double totalitemamount = 0.0;
        for (int i = 0; i < appUser.billsundrytotal.size(); i++) {
            String tot = appUser.billsundrytotal.get(i);
            double total = Double.parseDouble(tot);
            totalbillsundryamount = totalbillsundryamount + total;
        }
        for (int i = 0; i < appUser.itemtotal.size(); i++) {
            String tot = appUser.itemtotal.get(i);
            double total = Double.parseDouble(tot);
            totalitemamount = totalitemamount + total;
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
                .setTitle("Sale Voucher")
                .setMessage("Please add sale type in create voucher")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    return;

                })
                .show();
    }
    

}
