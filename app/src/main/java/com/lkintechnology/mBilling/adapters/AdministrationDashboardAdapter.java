package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.MasterDashboardActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdministrationDashboardAdapter extends RecyclerView.Adapter<AdministrationDashboardAdapter.ViewHolder> {
    private String[] data;
    private Context context;
    int[] images;

    public AdministrationDashboardAdapter(Context context, String[] data, int[] images) {
        this.data = data;
        this.context = context;
        this.images=images;
    }

    @Override
    public AdministrationDashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_generic_grid_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdministrationDashboardAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mImage.setImageResource(images[i]);
        viewHolder.mTitleText.setText(data[i]);
        viewHolder.mGridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i==0){
                    context.startActivity(new Intent(context, MasterDashboardActivity.class));
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