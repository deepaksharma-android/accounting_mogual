package com.berylsystems.buzz.fragments.company.sale_return;

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

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.billsundry.BillSundryListActivity;
import com.berylsystems.buzz.activities.company.administration.master.item.ExpandableItemListActivity;
import com.berylsystems.buzz.activities.company.sale.SaleVoucherAddItemActivity;
import com.berylsystems.buzz.adapters.AddBillsVoucherAdapter;
import com.berylsystems.buzz.adapters.AddItemsVoucherAdapter;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.ListHeight;
import com.berylsystems.buzz.utils.LocalRepositories;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by BerylSystems on 11/22/2017.
 */

public class AddItemSaleReturnFragment extends Fragment {
    @Bind(R.id.add_item_button)
    LinearLayout add_item_button;
    @Bind(R.id.add_bill_button)
    LinearLayout add_bill_button;
    @Bind(R.id.listViewItems)
    ListView listViewItems;
    @Bind(R.id.listViewBills)
    ListView listViewBills;
    AppUser appUser;
    RecyclerView.LayoutManager layoutManager;
    Animation blinkOnClick;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_item_sale_return, container, false);
        ButterKnife.bind(this, view);
        blinkOnClick = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.blink_on_click);
        appUser = LocalRepositories.getAppUser(getActivity());
        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add_item_button.startAnimation(blinkOnClick);
                Intent intent=new Intent(getContext(), ExpandableItemListActivity.class);
                intent.putExtra("bool",true);
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
        Timber.i("mlistmap" + appUser.mListMapForItemSale);
        Timber.i("mlistmapforbill" + appUser.mListMapForBillSale);

        listViewItems.setAdapter(new AddItemsVoucherAdapter(getContext(), appUser.mListMapForBillSale));
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);
        ListHeight.setListViewHeightBasedOnChildren(listViewItems);

        listViewBills.setAdapter(new AddBillsVoucherAdapter(getContext(), appUser.mListMapForBillSale));
        ListHeight.setListViewHeightBasedOnChildren(listViewBills);
        ListHeight.setListViewHeightBasedOnChildren(listViewBills);
        ProgressDialog progressDialog=new ProgressDialog(getActivity());
        progressDialog.setMessage("Removing...");

        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getContext(), SaleVoucherAddItemActivity.class);
                intent.putExtra("bool",true);
                startActivity(intent);
                getActivity().finish();
            }
        });
        listViewItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                AlertDialog.Builder alertDialog = new  AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Are you sure to delete?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        AppUser appUser=LocalRepositories.getAppUser(getApplicationContext());
                        appUser.mListMapForItemSale.remove(position);
                        LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                        dialog.cancel();
                        listViewItems.setAdapter(new AddItemsVoucherAdapter(getContext(), appUser.mListMapForItemSale));
                        ListHeight.setListViewHeightBasedOnChildren(listViewItems);
                        ListHeight.setListViewHeightBasedOnChildren(listViewItems);
                        progressDialog.dismiss();
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

                AlertDialog.Builder alertDialog = new  AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Are you sure to delete?");
                alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        progressDialog.show();
                        AppUser appUser=LocalRepositories.getAppUser(getApplicationContext());
                        appUser.mListMapForBillSale.remove(position);
                        LocalRepositories.saveAppUser(getApplicationContext(),appUser);
                        dialog.cancel();
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

}
