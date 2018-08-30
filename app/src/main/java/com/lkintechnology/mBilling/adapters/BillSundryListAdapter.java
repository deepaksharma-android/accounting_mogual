package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.billsundry.CreateBillSundryActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity;
import com.lkintechnology.mBilling.activities.company.pos.PosAddBillActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase.PurchaseAddBillActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase_return.PurchaseReturnAddBillActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale.SaleVoucherAddBillActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale_return.SaleReturnAddBillActivity;
import com.lkintechnology.mBilling.activities.company.transaction.stocktransfer.StockTransferAddBillActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_response.bill_sundry.BillSundryData;
import com.lkintechnology.mBilling.utils.EventDeleteBillSundry;
import com.lkintechnology.mBilling.utils.EventSaleAddBill;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BillSundryListAdapter extends RecyclerView.Adapter<BillSundryListAdapter.ViewHolder> {
    private ArrayList<BillSundryData> data;
    private Context context;
    private int count = -1;


    public BillSundryListAdapter(Context context, ArrayList<BillSundryData> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public BillSundryListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_bill_sundry_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BillSundryListAdapter.ViewHolder viewHolder, int i) {
       /* count++;
        if (ExpandableItemListActivity.comingFrom==5){
            if (!data.get(i).getAttributes().getName().contains("GST")){
                viewHolder.mBillName.setText(data.get(i).getAttributes().getName());
            }else {
                data.remove(i);
                count--;
            }
            if (data.size()==count){
                notifyDataSetChanged();
            }
        }else {
            viewHolder.mBillName.setText(data.get(i).getAttributes().getName());
        }*/
        if (ExpandableItemListActivity.comingFrom == 5) {
            if (data.get(i).getAttributes().getName().equals("IGST")
                    || data.get(i).getAttributes().getName().equals("SGST")
                    || data.get(i).getAttributes().getName().equals("CGST")) {
                viewHolder.bill_main_layout.setVisibility(View.GONE);
            }else {
                viewHolder.bill_main_layout.setVisibility(View.VISIBLE);
                viewHolder.mBillName.setText(data.get(i).getAttributes().getName());
            }
        } else {
            viewHolder.bill_main_layout.setVisibility(View.VISIBLE);
            viewHolder.mBillName.setText(data.get(i).getAttributes().getName());
        }


        if (data.get(i).getAttributes().getUndefined() == true) {
            viewHolder.mDelete.setVisibility(View.VISIBLE);
            viewHolder.mEdit.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mDelete.setVisibility(View.GONE);
            viewHolder.mEdit.setVisibility(View.GONE);
        }
        viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventDeleteBillSundry(i));
            }
        });
        viewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CreateBillSundryActivity.class);
                intent.putExtra("frommbillsundrylist", true);
                intent.putExtra("id", data.get(i).getId());
                context.startActivity(intent);
            }
        });
        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String id=groupPosition+","+childPosition;
                if (ExpandableItemListActivity.comingFrom == 0) {
                    SaleVoucherAddBillActivity.data = data.get(i);
                } else if (ExpandableItemListActivity.comingFrom == 1) {
                    PurchaseAddBillActivity.data = data.get(i);
                } else if (ExpandableItemListActivity.comingFrom == 3) {
                    PurchaseReturnAddBillActivity.data = data.get(i);
                    AppUser appUser = LocalRepositories.getAppUser(context);
                    appUser.billSundryData = data;
                    LocalRepositories.saveAppUser(context, appUser);
                } else if (ExpandableItemListActivity.comingFrom == 2) {
                    SaleReturnAddBillActivity.data = data.get(i);
                } else if (ExpandableItemListActivity.comingFrom == 4) {
                    StockTransferAddBillActivity.data = data.get(i);
                } else if (ExpandableItemListActivity.comingFrom == 5) {
                    PosAddBillActivity.data = data.get(i);
                }
                EventBus.getDefault().post(new EventSaleAddBill(String.valueOf(i)));
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.bill_sundry_name)
        TextView mBillName;
        @Bind(R.id.mainLayout)
        LinearLayout mMainLayout;
        @Bind(R.id.delete)
        LinearLayout mDelete;
        @Bind(R.id.edit)
        LinearLayout mEdit;
        @Bind(R.id.bill_main_layout)
        LinearLayout bill_main_layout;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
}