package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.administration.master.unit.CreateUnitActivity;
import com.berylsystems.buzz.networks.api_response.unit.ItemUnitData;
import com.berylsystems.buzz.utils.EventDeleteUnit;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UnitListAdapter extends RecyclerView.Adapter<UnitListAdapter.ViewHolder> {
    private ArrayList<ItemUnitData> data;
    private Context context;


    public UnitListAdapter(Context context, ArrayList<ItemUnitData> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public UnitListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_unit_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UnitListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mUnitName.setText(data.get(i).getAttributes().getName());
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
                EventBus.getDefault().post(new EventDeleteUnit(i));
            }
        });
        viewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CreateUnitActivity.class);
                intent.putExtra("fromunitlist",true);
                intent.putExtra("id",data.get(i).getId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.unit_name)
        TextView mUnitName;
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