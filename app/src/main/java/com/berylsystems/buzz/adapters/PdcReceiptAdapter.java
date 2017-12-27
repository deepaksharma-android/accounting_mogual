package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.transaction.receiptvoucher.CreateReceiptVoucherActivity;
import com.berylsystems.buzz.networks.api_response.pdc.Attribute;
import com.berylsystems.buzz.networks.api_response.pdc.Data;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by BerylSystems on 11/25/2017.
 */

public class PdcReceiptAdapter extends BaseAdapter {

    Context context;
    List<Attribute> list;

    public PdcReceiptAdapter(Context context, ArrayList<Attribute> list) {
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
        holder.from.setText(list.get(position).getReceived_from());
        holder.by.setText(list.get(position).getReceived_by());
        holder.amount.setText(list.get(position).getAmount());
        holder.voucher_number.setText(list.get(position).getVoucher_number());
        holder.date.setText(list.get(position).getDate());

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CreateReceiptVoucherActivity.class);
                intent.putExtra("fromReceipt",true);
                intent.putExtra("id",list.get(position).getId());
                Toast.makeText(context, ""+list.get(position).getId(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });

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
        @Bind(R.id.edit_btn)
        LinearLayout editBtn;
        @Bind(R.id.delete_btn)
        LinearLayout deleteBtn;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
