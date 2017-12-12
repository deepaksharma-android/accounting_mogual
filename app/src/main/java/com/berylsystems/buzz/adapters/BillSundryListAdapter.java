package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.billsundry.CreateBillSundryActivity;
import com.berylsystems.buzz.activities.company.administration.master.item.ExpandableItemListActivity;
import com.berylsystems.buzz.activities.company.administration.master.unit.CreateUnitActivity;
import com.berylsystems.buzz.activities.company.purchase.PurchaseAddBillActivity;
import com.berylsystems.buzz.activities.company.purchase_return.PurchaseReturnAddBillActivity;
import com.berylsystems.buzz.activities.company.sale.SaleVoucherAddBillActivity;
import com.berylsystems.buzz.activities.company.sale_return.SaleReturnAddBillActivity;
import com.berylsystems.buzz.activities.company.sale_return.SaleReturnAddItemActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.fragments.company.sale.AddItemVoucherFragment;
import com.berylsystems.buzz.networks.api_response.bill_sundry.BillSundry;
import com.berylsystems.buzz.networks.api_response.bill_sundry.BillSundryData;
import com.berylsystems.buzz.utils.EventDeleteBillSundry;
import com.berylsystems.buzz.utils.EventDeleteUnit;
import com.berylsystems.buzz.utils.EventSaleAddBill;
import com.berylsystems.buzz.utils.EventSaleAddItem;
import com.berylsystems.buzz.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BillSundryListAdapter extends RecyclerView.Adapter<BillSundryListAdapter.ViewHolder> {
    private ArrayList<BillSundryData> data;
    private Context context;


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
        viewHolder.mBillName.setText(data.get(i).getAttributes().getName());
        if(data.get(i).getAttributes().getUndefined()==true){
            viewHolder.mDelete.setVisibility(View.VISIBLE);
            viewHolder.mEdit.setVisibility(View.VISIBLE);
        }
        else{
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
                Intent intent=new Intent(context, CreateBillSundryActivity.class);
                intent.putExtra("frommbillsundrylist",true);
                intent.putExtra("id",data.get(i).getId());
                context.startActivity(intent);
            }
        });
        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String id=groupPosition+","+childPosition;
                if (ExpandableItemListActivity.comingFrom==0){
                    SaleVoucherAddBillActivity.data=data.get(i);
                }else if(ExpandableItemListActivity.comingFrom==1){
                    PurchaseAddBillActivity.data=data.get(i);
                }
                else if(ExpandableItemListActivity.comingFrom==3){
                    PurchaseReturnAddBillActivity.data=data.get(i);
                    AppUser appUser= LocalRepositories.getAppUser(context);
                    appUser.billSundryData=data;
                    LocalRepositories.saveAppUser(context,appUser);
                }else if(ExpandableItemListActivity.comingFrom==2){
                    SaleReturnAddBillActivity.data=data.get(i);
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


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
}