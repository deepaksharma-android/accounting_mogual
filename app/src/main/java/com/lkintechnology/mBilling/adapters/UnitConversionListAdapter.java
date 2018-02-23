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
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.unitconversion.CreateUnitConversionActivity;
import com.lkintechnology.mBilling.networks.api_response.unitconversion.UnitConversionData;
import com.lkintechnology.mBilling.utils.EventDeleteConversionUnit;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class UnitConversionListAdapter extends RecyclerView.Adapter<UnitConversionListAdapter.ViewHolder> {
    private ArrayList<UnitConversionData> data;
    private Context context;


    public UnitConversionListAdapter(Context context, ArrayList<UnitConversionData> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public UnitConversionListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_conversion_unit_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UnitConversionListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mMainUnitName.setText("Main Unit -> "+data.get(i).getAttributes().getMain_unit());
        viewHolder.mSubUnitName.setText("Sub Unit -> "+data.get(i).getAttributes().getSub_unit());
        viewHolder.mConFactor.setText("Conversion Factor -> "+String.valueOf(data.get(i).getAttributes().getConversion_factor()));
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
                EventBus.getDefault().post(new EventDeleteConversionUnit(i));
            }
        });
        viewHolder.mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CreateUnitConversionActivity.class);
                intent.putExtra("fromunitconversionlist",true);
                intent.putExtra("id",data.get(i).getAttributes().getId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.main_unit_name)
        TextView mMainUnitName;
        @Bind(R.id.sub_unit_name)
        TextView mSubUnitName;
        @Bind(R.id.con_factor)
        TextView mConFactor;
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