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
import com.berylsystems.buzz.activities.company.administration.master.materialcentregroup.CreateMaterialCentreGroupActivity;
import com.berylsystems.buzz.networks.api_response.materialcentregroup.MaterialCentreGroupListData;
import com.berylsystems.buzz.utils.EventDeleteMaterailCentreGroup;
import com.berylsystems.buzz.utils.EventGroupClicked;
import com.berylsystems.buzz.utils.EventMaterialCentreGroupClicked;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MaterialCentreGroupListAdapter extends RecyclerView.Adapter<MaterialCentreGroupListAdapter.ViewHolder> {
    private ArrayList<MaterialCentreGroupListData> data;
    private Context context;


    public MaterialCentreGroupListAdapter(Context context, ArrayList<MaterialCentreGroupListData> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MaterialCentreGroupListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_material_centre_group_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MaterialCentreGroupListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mGroupName.setText(data.get(i).getAttributes().getName());
        if(data.get(i).getAttributes().getPrimary_group()==true){
            viewHolder.mEdit.setVisibility(View.GONE);
            viewHolder.mDelete.setVisibility(View.GONE);
        }
        else{
            viewHolder.mEdit.setVisibility(View.VISIBLE);
            viewHolder.mDelete.setVisibility(View.VISIBLE);
        }

        viewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventDeleteMaterailCentreGroup(i));
            }
        });
        viewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CreateMaterialCentreGroupActivity.class);
                intent.putExtra("frommaterialcentregrouplist",true);
                intent.putExtra("id",data.get(i).getId());
                context.startActivity(intent);
            }
        });
        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventMaterialCentreGroupClicked(i));
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