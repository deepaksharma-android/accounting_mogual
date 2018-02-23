package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.accountgroup.CreateAccountGroupActivity;
import com.lkintechnology.mBilling.networks.api_response.accountgroup.Data;
import com.lkintechnology.mBilling.utils.EventDeleteGroup;
import com.lkintechnology.mBilling.utils.EventGroupClicked;

import org.greenrobot.eventbus.EventBus;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_account_group_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountGroupListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mGroupName.setText(data.get(i).getAttributes().getName());
        if(data.get(i).getAttributes().getUndefined()==true){
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
                EventBus.getDefault().post(new EventDeleteGroup(data.get(i).getAttributes().getId()));
            }
        });
        viewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CreateAccountGroupActivity.class);
                intent.putExtra("fromaccountgrouplist",true);
                intent.putExtra("id",data.get(i).getId());
                context.startActivity(intent);
            }
        });
        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventGroupClicked(data.get(i).getId()+","+data.get(i).getAttributes().getName()));
            }
        });




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