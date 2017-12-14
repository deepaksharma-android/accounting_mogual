package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.transaction.payment.CreatePaymentActivity;
import com.berylsystems.buzz.networks.api_response.payment.Data;
import com.berylsystems.buzz.utils.EventDeletePayment;

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

        viewHolder.bank_edit_text1.setText(data.get(position).getAttributes().paid_to);
        viewHolder.bank_edit_text2.setText(data.get(position).getAttributes().paid_from);
        viewHolder.bank_edit_text3.setText(data.get(position).getAttributes().date);

        //viewHolder.bank_edit_text3.setText(String.valueOf(data.get(position).getAttributes().amount));

        viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String payment_id=data.get(position).getId();
                EventBus.getDefault().post(new EventDeletePayment(payment_id));
            }
        });

        viewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(context, CreatePaymentActivity.class);
                i.putExtra("fromPayment",true);
                String payment_id=data.get(position).getId();
                i.putExtra("id",payment_id);
                context.startActivity(i);
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
        @Bind(R.id.delete)
        LinearLayout mDelete;
        @Bind(R.id.edit1)
        LinearLayout mEdit;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}