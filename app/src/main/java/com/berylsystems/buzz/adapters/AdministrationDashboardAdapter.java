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
import com.berylsystems.buzz.activities.administration.account.AccountListActivity;
import com.berylsystems.buzz.activities.administration.accountgroup.AccountGroupListActivity;
import com.berylsystems.buzz.activities.administration.item.ItemListActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdministrationDashboardAdapter extends RecyclerView.Adapter<AdministrationDashboardAdapter.ViewHolder> {
    private String[] data;
    private Context context;
    int[] images;

    public AdministrationDashboardAdapter(Context context,String[] data,int[] images) {
        this.data = data;
        this.context = context;
        this.images=images;
    }

    @Override
    public AdministrationDashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_landing_page_grid, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdministrationDashboardAdapter.ViewHolder viewHolder,  int i) {
        viewHolder.mImage.setImageResource(images[i]);
        viewHolder.mTitleText.setText(data[i]);
        viewHolder.mGridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i==0){
                    context.startActivity(new Intent(context, AccountListActivity.class));
                }
                if(i==1){
                    context.startActivity(new Intent(context, AccountGroupListActivity.class));
                }
                if(i==2){
                    context.startActivity(new Intent(context, ItemListActivity.class));
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