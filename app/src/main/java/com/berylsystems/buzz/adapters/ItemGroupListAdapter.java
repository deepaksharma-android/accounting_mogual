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
import com.berylsystems.buzz.activities.company.administration.master.item_group.CreateItemGroupActivity;
import com.berylsystems.buzz.networks.api_response.itemgroup.Data;
import com.berylsystems.buzz.utils.EventDeleteItemGroup;
import com.berylsystems.buzz.utils.EventGroupClicked;
import com.berylsystems.buzz.utils.EventItemClicked;

import org.greenrobot.eventbus.EventBus;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemGroupListAdapter extends RecyclerView.Adapter<ItemGroupListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Data> data;

    public ItemGroupListAdapter(Context context,ArrayList<Data> data ) {

        this.context=context;
        this.data=data;

    }

    @Override
    public ItemGroupListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_group_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mGroupName.setText(data.get(position).getAttributes().getName());
        if(data.get(position).getAttributes().getUndefined()==true){
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
                EventBus.getDefault().post(new EventDeleteItemGroup(position));
                //EventBus.getDefault().post(new EventDeleteItemGroup(data.get(position).getAttributes().getId()));
            }
        });

        viewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CreateItemGroupActivity.class);
                intent.putExtra("fromitemgrouplist",true);
                intent.putExtra("id",data.get(position).getId());
                context.startActivity(intent);
            }
        });

        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventItemClicked(position));
            }
        });
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
        @Bind(R.id.delete)
        LinearLayout mDelete;
        @Bind(R.id.edit)
        LinearLayout mEdit;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}