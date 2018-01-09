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
import com.berylsystems.buzz.activities.company.navigation.reports.TransactionPdfActivity;
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
        holder.bank_edit_text1.setText(list.get(position).getPaid_from());
        holder.bank_edit_text2.setText(list.get(position).getPaid_to());
        holder.bank_edit_text5.setText(list.get(position).getVoucher_number());
        holder.bank_edit_text4.setText(list.get(position).getAmount());
        holder.bank_edit_text3.setText(list.get(position).getPdc_date());

        holder.icon_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String payment_id=list.get(position).getId();
                EventBus.getDefault().post(new EventDeletePaymentPdcDetails(payment_id));
            }
        });

        holder.icon_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TransactionPdfActivity.class);
                intent.putExtra("company_report",list.get(position).getInvoice_html());
                context.startActivity(intent);
            }
        });

        holder.icon_printing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TransactionPdfActivity.class);
                intent.putExtra("company_report",list.get(position).getInvoice_html());
                context.startActivity(intent);
            }
        });

        holder.icon_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TransactionPdfActivity.class);
                intent.putExtra("company_report",list.get(position).getInvoice_html());
                context.startActivity(intent);
            }
        });
        /*holder.editBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CreatePaymentActivity.class);
                intent.putExtra("fromPayment",true);
                intent.putExtra("from", "pdcdetail");
                intent.putExtra("id",list.get(position).getId());
                context.startActivity(intent);
            }
        });*/
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.bank_edit_text1)
        TextView bank_edit_text1;
        @Bind(R.id.bank_edit_text2)
        TextView bank_edit_text2;
        @Bind(R.id.bank_edit_text3)
        TextView bank_edit_text3;
        @Bind(R.id.bank_edit_text4)
        TextView bank_edit_text4;
        @Bind(R.id.bank_edit_text5)
        TextView bank_edit_text5;
        @Bind(R.id.icon_delete)
        LinearLayout icon_delete;
        @Bind(R.id.icon_eye)
        LinearLayout icon_eye;
        @Bind(R.id.icon_printing)
        LinearLayout icon_printing;
        @Bind(R.id.icon_share)
        LinearLayout icon_share;
        @Bind(R.id.main_layout)
        LinearLayout main_layout;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
