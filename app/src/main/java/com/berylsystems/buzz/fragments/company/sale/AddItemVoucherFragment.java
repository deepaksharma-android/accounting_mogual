package com.berylsystems.buzz.fragments.company.sale;

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

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.billsundry.BillSundryListActivity;
import com.berylsystems.buzz.activities.company.administration.master.billsundry.CreateBillSundryActivity;
import com.berylsystems.buzz.activities.company.administration.master.item.ExpandableItemListActivity;
import com.berylsystems.buzz.activities.company.sale.CreateSaleActivity;
import com.berylsystems.buzz.activities.company.sale.SaleVoucherAddBillActivity;
import com.berylsystems.buzz.activities.company.sale.SaleVoucherAddItemActivity;
import com.berylsystems.buzz.adapters.AddBillsVoucherAdapter;
import com.berylsystems.buzz.adapters.AddItemsVoucherAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.api_response.bill_sundry.BillSundryData;
import com.berylsystems.buzz.utils.ListHeight;
import com.berylsystems.buzz.utils.LocalRepositories;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item_voucher, container, false);
        ButterKnife.bind(this, view);

        blinkOnClick = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_on_click);
        appUser = LocalRepositories.getAppUser(getActivity());
        Timber.i("FEDAS" + appUser.bill_sundry_fed_as);
        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_item_button.startAnimation(blinkOnClick);
                ExpandableItemListActivity.comingFrom = 0;
                Intent intent = new Intent(getContext(), ExpandableItemListActivity.class);
                intent.putExtra("bool", true);
                startActivity(intent);
                getActivity().finish();
            }
        });
        add_bill_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_bill_button.startAnimation(blinkOnClick);
                startActivity(new Intent(getContext(), BillSundryListActivity.class));
                getActivity().finish();
            }
        });
        amountCalculation();


        listViewItems.setAdapter(new AddItemsVoucherAdapter(getContext(), appUser.mListMapForItemSale));
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);


        listViewBills.setAdapter(new AddBillsVoucherAdapter(getContext(), appUser.mListMapForBillSale));
        ListHeight.setListViewHeightBasedOnChildren(listViewBills);
        ListHeight.setListViewHeightBasedOnChildren(listViewBills);
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Removing...");

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), SaleVoucherAddItemActivity.class);
                intent.putExtra("bool", true);
                ExpandableItemListActivity.comingFrom = 0;
                intent.putExtra("position", position);
                startActivity(intent);
                getActivity().finish();
            }
        });
        listViewBills.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), SaleVoucherAddBillActivity.class);
                intent.putExtra("bool", true);
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
                        progressDialog.show();
                        AppUser appUser = LocalRepositories.getAppUser(getApplicationContext());
                        appUser.mListMapForItemSale.remove(position);
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        dialog.cancel();

                        listViewItems.setAdapter(new AddItemsVoucherAdapter(getContext(), appUser.mListMapForItemSale));
                        ListHeight.setListViewHeightBasedOnChildren(listViewItems);
                        ListHeight.setListViewHeightBasedOnChildren(listViewItems);
                        progressDialog.dismiss();
                        amountCalculation();
                        Timber.i("SIZEE" + appUser.mListMapForItemSale.size());
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
                        progressDialog.show();
                        AppUser appUser = LocalRepositories.getAppUser(getApplicationContext());
                        appUser.mListMapForBillSale.remove(position);
                        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                        dialog.cancel();
                        amountCalculation();

                        listViewBills.setAdapter(new AddBillsVoucherAdapter(getContext(), appUser.mListMapForBillSale));
                        ListHeight.setListViewHeightBasedOnChildren(listViewBills);
                        ListHeight.setListViewHeightBasedOnChildren(listViewBills);
                        progressDialog.dismiss();
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
        double itemamount = 0.0;
        double billsundrymamount = 0.0;
        appUser = LocalRepositories.getAppUser(getApplicationContext());

        if (appUser.mListMapForItemSale.size() > 0) {
            for (int i = 0; i < appUser.mListMapForItemSale.size(); i++) {
                Map map = appUser.mListMapForItemSale.get(i);
                String total = (String) map.get("total");
                if (total.equals("")) {
                    total = "0.0";
                } else {
                    double tot = Double.parseDouble(total);
                    itemamount = itemamount + tot;
                }
            }


        } else {
            itemamount = 0.0;
        }

        if (appUser.mListMapForBillSale.size() > 0) {
            for (int i = 0; i < appUser.mListMapForBillSale.size(); i++) {
                Map map = appUser.mListMapForBillSale.get(i);
                String amount = (String) map.get("amount");
                String type = (String) map.get("type");
                String fedas = (String) map.get("fed_as");
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
                                if (alternate_unit_con_factor.equals("")||alternate_unit_con_factor.equals("0.0")) {
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
                                if (packaging_unit_con_factor.equals("")||packaging_unit_con_factor.equals("0.0")) {
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
                            if (alternate_unit_con_factor.equals("0.0")||alternate_unit_con_factor.equals("")) {
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
                                if (packaging_unit_con_factor.equals("")||packaging_unit_con_factor.equals("0.0")) {
                                    packaging_unit_con_factor = "1.0";
                                }
                                double packaging_con = Double.parseDouble(packaging_unit_con_factor);
                                int quan = Integer.parseInt(quantity);
                                subtot = subtot + (amt * quan * packaging_con*con_factor);
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
                            if (alternate_unit_con_factor.equals("")||alternate_unit_con_factor.equals("0.0")) {
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
                                if (packaging_unit_con_factor.equals("")||packaging_unit_con_factor.equals("0.0")) {
                                    packaging_unit_con_factor = "1.0";
                                }
                                double packaging_con = Double.parseDouble(packaging_unit_con_factor);
                                int quan = Integer.parseInt(quantity);
                                subtot = subtot + ((amt * quan) / (packaging_con*con_factor));
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
                }


            }
        } else {
            billsundrymamount = 0.0;
        }

        mTotal.setText("Total Amount: " + String.valueOf(itemamount + billsundrymamount));
    }

}
