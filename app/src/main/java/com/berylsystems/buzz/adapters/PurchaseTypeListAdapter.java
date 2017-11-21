package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.networks.api_response.purchasetype.PurchaseTypeData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PurchaseTypeListAdapter extends RecyclerView.Adapter<PurchaseTypeListAdapter.ViewHolder>{

    private Context context;
    private ArrayList<PurchaseTypeData> data;

    public PurchaseTypeListAdapter(Context context,ArrayList<PurchaseTypeData> data ) {

        this.context=context;
        this.data=data;

    }

    @Override
    public PurchaseTypeListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_purchase_type_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mGroupName.setText(data.get(position).getAttributes().getName());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        @Bind(R.id.company_name)
        TextView mGroupName;
        @Bind(R.id.mainLayout)
        LinearLayout mMainLayout;
       /* @Bind(R.id.delete)
        LinearLayout mDelete;
        @Bind(R.id.edit)
        LinearLayout mEdit;*/

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}