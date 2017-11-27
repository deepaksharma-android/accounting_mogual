package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.sale.CreateSaleActivity;
import com.berylsystems.buzz.activities.company.sale.GetSaleListActivity;
import com.berylsystems.buzz.activities.company.transection.bankcasedeposit.BankCaseDepositListActivity;
import com.berylsystems.buzz.activities.company.transection.bankcasewithdraw.BankCaseWithdrawActivity;
import com.berylsystems.buzz.activities.dashboard.MasterDashboardActivity;
import com.berylsystems.buzz.activities.dashboard.TransactionDashboardActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransactionDashboardAdapter extends RecyclerView.Adapter<TransactionDashboardAdapter.ViewHolder> {

    private String[] data;
    private Context context;
    int[] images;

    public TransactionDashboardAdapter(Context context, String[] data, int[] images) {
        this.data = data;
        this.context = context;
        this.images=images;
    }

    @Override
    public TransactionDashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_generic_grid_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionDashboardAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mImage.setImageResource(images[i]);
        viewHolder.mTitleText.setText(data[i]);
        viewHolder.mGridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i==0){
                    context.startActivity(new Intent(context, CreateSaleActivity.class));
                }

                if(i==6){
                    context.startActivity(new Intent(context, BankCaseDepositListActivity.class));
                }
                if(i==7){
                    context.startActivity(new Intent(context, BankCaseWithdrawActivity.class));
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.title)
        TextView mTitleText;
        @Bind(R.id.imageicon)
        ImageView mImage;
        @Bind(R.id.grid_layout)
        LinearLayout mGridLayout;
        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
}