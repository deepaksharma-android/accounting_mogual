package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.navigations.TransactionPdfActivity;
import com.lkintechnology.mBilling.activities.company.transaction.ImageOpenActivity;
import com.lkintechnology.mBilling.networks.api_response.payment.Data;
import com.lkintechnology.mBilling.utils.EventClickAlertForPayment;
import com.lkintechnology.mBilling.utils.EventDeletePayment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Data> data;

    public PaymentListAdapter (Context context, ArrayList<Data> data){
        this.context=context;
        this.data=data;
    }

    @Override
    public PaymentListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_payment_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PaymentListAdapter.ViewHolder viewHolder, int position) {

        viewHolder.bank_edit_text1.setText(data.get(position).getAttributes().paid_from);
        viewHolder.bank_edit_text2.setText(data.get(position).getAttributes().getPaid_to().getName());
        viewHolder.bank_edit_text3.setText(data.get(position).getAttributes().date);
        viewHolder.bank_edit_text4.setText(""+ String.format("%.2f",data.get(position).getAttributes().amount));
        viewHolder.bank_edit_text5.setText(data.get(position).getAttributes().voucher_number);

        if (data.get(position).getAttributes().getIs_payment_settlement()!=null){
            if (data.get(position).getAttributes().getIs_payment_settlement().equals("true")){
                viewHolder.icon_delete.setVisibility(View.GONE);
            }else {
                viewHolder.icon_delete.setVisibility(View.VISIBLE);
            }
        }

        viewHolder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String receipt_voucher_id=data.get(position).getId();
                EventBus.getDefault().post(new EventClickAlertForPayment(receipt_voucher_id));
            }
        });

        viewHolder.icon_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String receipt_voucher_id=data.get(position).getId();
                EventBus.getDefault().post(new EventDeletePayment(receipt_voucher_id));
            }
        });

        viewHolder.icon_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.get(position).getAttributes().getInvoice_html()!=null){
                    Intent intent = new Intent(context, TransactionPdfActivity.class);
                    intent.putExtra("company_report",data.get(position).getAttributes().getInvoice_html());
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "PDF not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.icon_printing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.get(position).getAttributes().getInvoice_html()!=null){
                    Intent intent = new Intent(context, TransactionPdfActivity.class);
                    intent.putExtra("company_report",data.get(position).getAttributes().getInvoice_html());
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "PDF not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.icon_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(data.get(position).getAttributes().getInvoice_html()!=null){
                    Intent intent = new Intent(context, TransactionPdfActivity.class);
                    intent.putExtra("company_report",data.get(position).getAttributes().getInvoice_html());
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "PDF not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewHolder.mAttachment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!data.get(position).getAttributes().getAttachment().equals("")){
                    Intent intent = new Intent(context, ImageOpenActivity.class);
                    intent.putExtra("attachment",data.get(position).getAttributes().getAttachment());
                    intent.putExtra("booleAttachment",true);
                    context.startActivity(intent);
                }else {
                    Toast.makeText(context, "Attachment not found!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
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
        @Bind(R.id.main_layout)
        LinearLayout main_layout;
        @Bind(R.id.icon_delete)
        LinearLayout icon_delete;
        @Bind(R.id.icon_eye)
        LinearLayout icon_eye;
        @Bind(R.id.icon_printing)
        LinearLayout icon_printing;
        @Bind(R.id.icon_share)
        LinearLayout icon_share;
        @Bind(R.id.attachment_layout)
        LinearLayout mAttachment;
       /*
        @Bind(R.id.edit1)
        LinearLayout mEdit;*/

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}