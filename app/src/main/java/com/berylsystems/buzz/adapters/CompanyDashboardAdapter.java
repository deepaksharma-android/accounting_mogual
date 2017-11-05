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
import com.berylsystems.buzz.activities.dashboard.AdministationDashboardActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyDashboardAdapter extends RecyclerView.Adapter<CompanyDashboardAdapter.ViewHolder> {
    private String[] data;
    private Context context;
    int[] images;

    public CompanyDashboardAdapter(Context context, String[] data, int[] images) {
        this.data = data;
        this.context = context;
        this.images=images;
    }

    @Override
    public CompanyDashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_landing_page_grid, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompanyDashboardAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mImage.setImageResource(images[i]);
        viewHolder.mTitleText.setText(data[i]);
        if(i==0){
            context.startActivity(new Intent(context, AdministationDashboardActivity.class));
        }


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