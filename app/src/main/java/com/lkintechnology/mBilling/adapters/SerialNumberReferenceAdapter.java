package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.networks.api_response.serialnumber.Data;
import com.lkintechnology.mBilling.utils.EventDeleteSerialNumber;
import com.lkintechnology.mBilling.utils.EventItemMaterialCenterClick;
import com.lkintechnology.mBilling.utils.EventShowPdf;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SerialNumberReferenceAdapter extends RecyclerView.Adapter<SerialNumberReferenceAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Data> data;

    public SerialNumberReferenceAdapter(Context context, ArrayList<Data> data) {
        this.context = context;
        this.data = data;

    }

    @Override
    public SerialNumberReferenceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_serial_number_reference, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if(data.get(position).getAttributes().getType().equals("Item Opening Stock")){
            viewHolder.mMainLayout.setVisibility(View.GONE);
            viewHolder.mOpeningStockLayout.setVisibility(View.VISIBLE);
            viewHolder.mItemName.setText(data.get(position).getAttributes().getName());
            viewHolder.mItemDate.setText(data.get(position).getAttributes().getDate());
            viewHolder.mOpeningStockType.setText(data.get(position).getAttributes().getType());
            viewHolder.mDeleteIcon.setVisibility(View.GONE);
            viewHolder.mIconEye.setVisibility(View.GONE);
            viewHolder.mIconPrinting.setVisibility(View.GONE);
            viewHolder.mIconShare.setVisibility(View.GONE);

        }
        else{
            viewHolder.mMainLayout.setVisibility(View.VISIBLE);
            viewHolder.mOpeningStockLayout.setVisibility(View.GONE);
            viewHolder.mVoucherType.setText(data.get(position).getAttributes().getType());
            viewHolder.mDate.setText(data.get(position).getAttributes().getDate());
            viewHolder.amount.setText(String.valueOf(data.get(position).getAttributes().getAmount()));
            viewHolder.mName.setText(data.get(position).getAttributes().getName());
            viewHolder.mVoucher_no.setText(data.get(position).getAttributes().getVoucher_number());
        }



        viewHolder.mDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventDeleteSerialNumber(data.get(position).getAttributes().getType()+","+String.valueOf(data.get(position).getAttributes().getId()) ));
            }
        });
        viewHolder.mIconEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventShowPdf(data.get(position).getAttributes().getType()+","+String.valueOf(data.get(position).getAttributes().getId())));
            }
        });

        viewHolder.mIconPrinting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventShowPdf(data.get(position).getAttributes().getType()+","+String.valueOf(data.get(position).getAttributes().getId())));
            }
        });

        viewHolder.mIconShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventShowPdf(data.get(position).getAttributes().getType()+","+String.valueOf(data.get(position).getAttributes().getId())));
            }
        });



    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.voucher_type)
        TextView mVoucherType;
        @Bind(R.id.date)
        TextView mDate;
        @Bind(R.id.amount)
        TextView amount;
        @Bind(R.id.voucher_no)
        TextView mVoucher_no;
        @Bind(R.id.name)
        TextView mName;
        @Bind(R.id.icon_delete)
        LinearLayout mDeleteIcon;
        @Bind(R.id.icon_eye)
        LinearLayout mIconEye;
        @Bind(R.id.icon_printing)
        LinearLayout mIconPrinting;
        @Bind(R.id.icon_share)
        LinearLayout mIconShare;
        @Bind(R.id.opening_stock_layout)
        LinearLayout mOpeningStockLayout;
        @Bind(R.id.mainLayout)
        LinearLayout mMainLayout;
        @Bind(R.id.itemname)
        TextView mItemName;
        @Bind(R.id.itemdate)
        TextView mItemDate;
        @Bind(R.id.item_opening_stock)
        TextView mOpeningStockType;



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}