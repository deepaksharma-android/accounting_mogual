package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.networks.api_response.taxcategory.TaxCategoryData;
import com.lkintechnology.mBilling.utils.EventTaxCategoryClicked;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TaxCategoryListAdapter extends RecyclerView.Adapter<TaxCategoryListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<TaxCategoryData> data;

    public TaxCategoryListAdapter(Context context,ArrayList<TaxCategoryData> data ) {

        this.context=context;
        this.data=data;

    }

    @Override
    public TaxCategoryListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_tax_category_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.mGroupName.setText(data.get(position).getAttributes().getName());
        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventTaxCategoryClicked(data.get(position).getId()+"," +data.get(position).getAttributes().getName()));
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

