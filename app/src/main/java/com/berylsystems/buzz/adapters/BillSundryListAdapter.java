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
import com.berylsystems.buzz.activities.company.administration.master.billsundry.CreateBillSundryActivity;
import com.berylsystems.buzz.activities.company.administration.master.unit.CreateUnitActivity;
import com.berylsystems.buzz.networks.api_response.bill_sundry.BillSundryData;
import com.berylsystems.buzz.utils.EventDeleteBillSundry;
import com.berylsystems.buzz.utils.EventDeleteUnit;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BillSundryListAdapter extends RecyclerView.Adapter<BillSundryListAdapter.ViewHolder> {
    private ArrayList<BillSundryData> data;
    private Context context;


    public BillSundryListAdapter(Context context, ArrayList<BillSundryData> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public BillSundryListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_bill_sundry_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BillSundryListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mBillName.setText(data.get(i).getAttributes().getName());
        if(data.get(i).getAttributes().getUndefined()==false){
            viewHolder.mDelete.setVisibility(View.VISIBLE);
            viewHolder.mEdit.setVisibility(View.VISIBLE);
        }
        else{
            viewHolder.mDelete.setVisibility(View.GONE);
            viewHolder.mEdit.setVisibility(View.GONE);
        }
        viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventDeleteBillSundry(i));
            }
        });
        viewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CreateBillSundryActivity.class);
                intent.putExtra("frommbillsundrylist",true);
                intent.putExtra("id",data.get(i).getId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.bill_sundry_name)
        TextView mBillName;
        @Bind(R.id.mainLayout)
        LinearLayout mMainLayout;
        @Bind(R.id.delete)
        LinearLayout mDelete;
        @Bind(R.id.edit)
        LinearLayout mEdit;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
}