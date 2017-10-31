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
import com.berylsystems.buzz.activities.AddCompanyActivity;
import com.berylsystems.buzz.activities.ComapanyListActivity;
import com.berylsystems.buzz.networks.api_response.company.CompanyData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.ViewHolder> {
    private ArrayList<CompanyData> data;
    private Context context;

    public CompanyListAdapter(Context context, ArrayList<CompanyData> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public CompanyListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_company_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompanyListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mCompanyName.setText(data.get(i).getAttributes().getName());
        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, AddCompanyActivity.class);
                AddCompanyActivity.data = data.get(i);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.company_name)
        TextView mCompanyName;
        @Bind(R.id.mainLayout)
        LinearLayout mMainLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
}