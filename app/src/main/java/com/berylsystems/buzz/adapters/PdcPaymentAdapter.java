package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.networks.api_response.pdc.Attribute;
import com.berylsystems.buzz.networks.api_response.pdc.Data;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by BerylSystems on 11/25/2017.
 */

public class PdcPaymentAdapter extends BaseAdapter {

    Context context;
    List<Attribute> list;

    public PdcPaymentAdapter(Context context, List<Attribute> list) {
        this.context = context;
        this.list = list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdc_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.from.setText(list.get(position).getPaid_from());
        holder.by.setText(list.get(position).getPaid_to());
        holder.voucher_number.setText(list.get(position).getVoucher_number());
        holder.amount.setText(list.get(position).getAmount());
        holder.date.setText(list.get(position).getDate());

        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.from)
        TextView from;
        @Bind(R.id.by)
        TextView by;
        @Bind(R.id.amount)
        TextView amount;
        @Bind(R.id.voucher_number)
        TextView voucher_number;
        @Bind(R.id.date)
        TextView date;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
