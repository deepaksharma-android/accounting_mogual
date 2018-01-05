package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.transaction.receiptvoucher.CreateReceiptVoucherActivity;
import com.berylsystems.buzz.networks.api_response.receiptvoucher.Data;
import com.berylsystems.buzz.utils.EventClickAlert;
import com.berylsystems.buzz.utils.EventDeleteReceiptVoucher;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ReceiptVoucherListAdapter extends  RecyclerView.Adapter<ReceiptVoucherListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Data> data;

    public ReceiptVoucherListAdapter (Context context, ArrayList<Data> data){
        this.context=context;
        this.data=data;
    }

    @Override
    public ReceiptVoucherListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_receipt_voucher, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReceiptVoucherListAdapter.ViewHolder viewHolder, int position) {

        viewHolder.bank_edit_text1.setText(data.get(position).getAttributes().received_from);
        viewHolder.bank_edit_text2.setText(data.get(position).getAttributes().received_by);
        viewHolder.bank_edit_text3.setText(data.get(position).getAttributes().date);
        viewHolder.bank_edit_text4.setText(""+ String.format("%.2f",data.get(position).getAttributes().amount));
        viewHolder.bank_edit_text5.setText(data.get(position).getAttributes().voucher_number);
        //viewHolder.bank_edit_text3.setText(String.valueOf(data.get(position).getAttributes().amount));

        viewHolder.mEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* String receipt_voucher_id=data.get(position).getId();
                EventBus.getDefault().post(new EventDeleteReceiptVoucher(receipt_voucher_id));*/
                Toast.makeText(context, "Eye Icon", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.mPrinting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* String receipt_voucher_id=data.get(position).getId();
                EventBus.getDefault().post(new EventDeleteReceiptVoucher(receipt_voucher_id));*/
                Toast.makeText(context, "Printing Icon", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* String receipt_voucher_id=data.get(position).getId();
                EventBus.getDefault().post(new EventDeleteReceiptVoucher(receipt_voucher_id));*/
                Toast.makeText(context, "Share Icon", Toast.LENGTH_SHORT).show();
            }
        });
        viewHolder.main_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String receipt_voucher_id=data.get(position).getId();
                EventBus.getDefault().post(new EventClickAlert(receipt_voucher_id));
            }
        });




       /* viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String receipt_voucher_id=data.get(position).getId();
                EventBus.getDefault().post(new EventDeleteReceiptVoucher(receipt_voucher_id));
            }
        });

        viewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(context, CreateReceiptVoucherActivity.class);
                i.putExtra("fromReceipt",true);
                i.putExtra("from", "");
                String receipt_voucher_id=data.get(position).getId();
                i.putExtra("id",receipt_voucher_id);
                context.startActivity(i);
            }
        });*/
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
        @Bind(R.id.icon_eye)
        LinearLayout mEye;
        @Bind(R.id.icon_printing)
        LinearLayout mPrinting;
        @Bind(R.id.icon_share)
        LinearLayout mShare;
        @Bind(R.id.main_layout)
        LinearLayout main_layout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}