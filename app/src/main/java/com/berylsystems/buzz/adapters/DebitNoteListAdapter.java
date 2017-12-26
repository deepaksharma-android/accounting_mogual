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
import com.berylsystems.buzz.activities.company.transaction.debitnotewoitem.CreateDebitNoteWoItemActivity;
import com.berylsystems.buzz.networks.api_response.debitnotewoitem.Data;
import com.berylsystems.buzz.utils.EventDeleteDebitNote;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DebitNoteListAdapter extends RecyclerView.Adapter<DebitNoteListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Data> data;

    public DebitNoteListAdapter (Context context, ArrayList<Data> data){
        this.context=context;
        this.data=data;
    }

    @Override
    public DebitNoteListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_debit_note_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DebitNoteListAdapter.ViewHolder viewHolder, int position) {

        viewHolder.bank_edit_text1.setText(data.get(position).getAttributes().account_name_debit);
       // viewHolder.bank_edit_text2.setText(data.get(position).getAttributes().account_name_credit);
        viewHolder.bank_edit_text2.setText("Credit Note");
        viewHolder.bank_edit_text3.setText(data.get(position).getAttributes().date);
        viewHolder.bank_edit_text4.setText(""+data.get(position).getAttributes().amount);
        viewHolder.bank_edit_text5.setText(data.get(position).getAttributes().voucher_number);

        //viewHolder.bank_edit_text3.setText(String.valueOf(data.get(position).getAttributes().amount));

        viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String debit_note_id=data.get(position).getId();
                EventBus.getDefault().post(new EventDeleteDebitNote(debit_note_id));
            }
        });
        viewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(context, CreateDebitNoteWoItemActivity.class);
                i.putExtra("fromDebitNote",true);
                String debit_note_id=data.get(position).getId();
                i.putExtra("id",debit_note_id);
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
        @Bind(R.id.bank_edit_text4)
        TextView bank_edit_text4;
        @Bind(R.id.bank_edit_text5)
        TextView bank_edit_text5;
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