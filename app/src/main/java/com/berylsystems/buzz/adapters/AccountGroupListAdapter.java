package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.networks.api_response.accountgroup.Data;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AccountGroupListAdapter extends RecyclerView.Adapter<AccountGroupListAdapter.ViewHolder> {
    private ArrayList<Data> data;
    private Context context;


    public AccountGroupListAdapter(Context context,  ArrayList<Data> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public AccountGroupListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_account_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountGroupListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mGroupName.setText(data.get(i).getAttributes().getName());




    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.company_name)
        TextView mGroupName;
        @Bind(R.id.mainLayout)
        LinearLayout mMainLayout;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
}