package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by BerylSystems on 11/24/2017.
 */

public class AddItemVoucherAdapter extends RecyclerView.Adapter<AddItemVoucherAdapter.ViewHolder> {

    AppUser appUser;
    List<Map<String,String>> list;
    Context context;
    public AddItemVoucherAdapter(Context context,List<Map<String,String>> list) {
        this.list = list;
        this.context=context;
        Timber.i("listMap"+list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_details, parent, false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Map map=list.get(position);

        Timber.i("MEEEEEEEEEE"+list.get(position));
        String itemName= (String) map.get("item_name");
        String description= (String) map.get("description");
        String quality= (String) map.get("quality");
        String unit= (String) map.get("unit");
        String srNo= (String) map.get("sr_no");
        String rate= (String) map.get("rate");
        String discount= (String) map.get("discount");
        String value= (String) map.get("value");
        String total= (String) map.get("total");

        holder.mItemName.setText(itemName);
        holder.mDiscount.setText(discount);
        holder.mUnit.setText(unit);
        holder.mSrNo.setText("Sr.No. "+srNo);
        holder.mTotal.setText(total);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.item_name)
        TextView mItemName;
        @Bind(R.id.discount)
        TextView mDiscount;
        @Bind(R.id.unit)
        TextView mUnit;
        @Bind(R.id.sr_no)
        TextView mSrNo;
        @Bind(R.id.total)
        TextView mTotal;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }
}
