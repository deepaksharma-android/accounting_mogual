package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.networks.api_response.saletype.SaleTypeData;
import com.berylsystems.buzz.utils.EventGroupClicked;
import com.berylsystems.buzz.utils.EventPurchaseClicked;
import com.berylsystems.buzz.utils.EventSaleClicked;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SaleTypeListAdapter extends RecyclerView.Adapter<SaleTypeListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SaleTypeData> data;

    public SaleTypeListAdapter(Context context, ArrayList<SaleTypeData> data) {

        this.context = context;
        this.data = data;

    }

    @Override
    public SaleTypeListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_sale_type_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mGroupName.setText(data.get(position).getAttributes().getName());

        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s=data.get(position).getAttributes().getName()+","+data.get(position).getId();
                EventBus.getDefault().post(new EventSaleClicked(s));
               // EventBus.getDefault().post(new EventPurchaseClicked(s));
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
       /* @Bind(R.id.delete)
        LinearLayout mDelete;
        @Bind(R.id.edit)
        LinearLayout mEdit;*/

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}