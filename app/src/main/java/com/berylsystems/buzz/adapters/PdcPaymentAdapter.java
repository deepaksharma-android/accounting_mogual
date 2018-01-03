package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.transaction.payment.CreatePaymentActivity;
import com.berylsystems.buzz.networks.api_response.pdc.Attribute;
import com.berylsystems.buzz.utils.EventDeletePaymentPdcDetails;
import org.greenrobot.eventbus.EventBus;
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdc_payment_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.from.setText(list.get(position).getPaid_from());
        holder.by.setText(list.get(position).getPaid_to());
        holder.voucher_number.setText(list.get(position).getVoucher_number());
        holder.amount.setText(list.get(position).getAmount());
        holder.date.setText(list.get(position).getPdc_date());

        holder.deleteBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String payment_id=list.get(position).getId();
                EventBus.getDefault().post(new EventDeletePaymentPdcDetails(payment_id));
            }
        });
        holder.editBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CreatePaymentActivity.class);
                intent.putExtra("fromPayment",true);
                intent.putExtra("from", "pdcdetail");
                intent.putExtra("id",list.get(position).getId());
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
        @Bind(R.id.edit_btn1)
        LinearLayout editBtn1;
        @Bind(R.id.delete_btn1)
        LinearLayout deleteBtn1;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
