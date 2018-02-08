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
        viewHolder.mVoucherType.setText(data.get(position).getAttributes().getType());
        viewHolder.mDate.setText(data.get(position).getAttributes().getDate());
        viewHolder.amount.setText(String.valueOf(data.get(position).getAttributes().getAmount()));
        viewHolder.mName.setText(data.get(position).getAttributes().getName());
        viewHolder.mVoucher_no.setText(data.get(position).getAttributes().getVoucher_number());

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



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}