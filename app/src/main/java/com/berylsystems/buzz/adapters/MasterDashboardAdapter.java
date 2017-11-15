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
import com.berylsystems.buzz.activities.company.administration.master.account.ExpandableAccountListActivity;
import com.berylsystems.buzz.activities.company.administration.master.accountgroup.AccountGroupListActivity;
import com.berylsystems.buzz.activities.company.administration.master.item.ItemListActivity;
import com.berylsystems.buzz.activities.company.administration.master.materialcentre.MaterialCentreListActivity;
import com.berylsystems.buzz.activities.company.administration.master.materialcentregroup.MaterialCentreGroupListActivity;
import com.berylsystems.buzz.activities.company.administration.master.unit.UnitListActivity;
import com.berylsystems.buzz.activities.company.administration.master.unitconversion.UnitConversionListActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MasterDashboardAdapter extends RecyclerView.Adapter<MasterDashboardAdapter.ViewHolder> {
    private String[] data;
    private Context context;
    int[] images;

    public MasterDashboardAdapter(Context context, String[] data, int[] images) {
        this.data = data;
        this.context = context;
        this.images=images;
    }

    @Override
    public MasterDashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_generic_grid_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MasterDashboardAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mImage.setImageResource(images[i]);
        viewHolder.mTitleText.setText(data[i]);
        viewHolder.mGridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i==0){
                    context.startActivity(new Intent(context, ExpandableAccountListActivity.class));
                }
                if(i==1){
                    Intent intent=new Intent(context,AccountGroupListActivity.class);
                    intent.putExtra("frommaster",true);
                    context.startActivity(intent);
                }
                if(i==2){
                    context.startActivity(new Intent(context, ItemListActivity.class));
                }
                if(i==4){
                    context.startActivity(new Intent(context, MaterialCentreListActivity.class));
                }
                if(i==5){
                    context.startActivity(new Intent(context, MaterialCentreGroupListActivity.class));
                }
                if(i==6){
                    context.startActivity(new Intent(context, UnitListActivity.class));
                }
                if(i==7){
                    context.startActivity(new Intent(context, UnitConversionListActivity.class));
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