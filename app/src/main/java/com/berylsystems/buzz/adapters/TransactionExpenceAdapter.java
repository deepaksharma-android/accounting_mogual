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
import com.berylsystems.buzz.activities.company.transaction.expence.CreateExpenceActivity;
import com.berylsystems.buzz.networks.api_response.expence.Data;
import com.berylsystems.buzz.utils.EventDeleteExpence;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransactionExpenceAdapter extends RecyclerView.Adapter<TransactionExpenceAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Data> data;

    public TransactionExpenceAdapter(Context context, ArrayList<Data> data){
        this.context=context;
        this.data=data;
    }

    @Override
    public TransactionExpenceAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_transaction_expense, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionExpenceAdapter.ViewHolder viewHolder, int position) {

        viewHolder.bank_edit_text1.setText(data.get(position).getAttributes().paid_from);
        viewHolder.bank_edit_text2.setText(data.get(position).getAttributes().paid_to);
        viewHolder.bank_edit_text3.setText(data.get(position).getAttributes().date);
        viewHolder.bank_edit_text4.setText(data.get(position).getAttributes().voucher_number);
        viewHolder.bank_edit_text5.setText(""+String.format("%.2f", data.get(position).getAttributes().amount));
        //viewHolder.bank_edit_text3.setText(String.valueOf(data.get(position).getAttributes().amount));

      /*  viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String expence_id=data.get(position).getId();
                EventBus.getDefault().post(new EventDeleteExpence(expence_id));
            }
        });

        viewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(context, CreateExpenceActivity.class);
                i.putExtra("fromExpense",true);
                String expence_id=data.get(position).getId();
                i.putExtra("id",expence_id);
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
       /* @Bind(R.id.delete)
        LinearLayout mDelete;
        @Bind(R.id.edit1)
        LinearLayout mEdit;*/



        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}