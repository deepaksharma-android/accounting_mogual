package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.pos.PosItemAddActivity;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by BerylSystems on 11/25/2017.
 */

public class PosAddBillAdapter extends  RecyclerView.Adapter<PosAddBillAdapter.ViewHolder> {

    Context context;
    List<String> mListMap;
    int mInteger = 0;
    //ViewHolder holder;

    public PosAddBillAdapter(Context context, List<String> mListMap) {
        this.context = context;
        this.mListMap = mListMap;

    }

    @Override
    public PosAddBillAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_for_pos, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mListMap.size();
    }
    @Override
    public void onBindViewHolder(PosAddBillAdapter.ViewHolder viewHolder, int position) {
        mInteger = 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.lblListItem)
        TextView mItemName;
        @Bind(R.id.quantity)
        TextView mQuantity;
        @Bind(R.id.item_amount)
        TextView mItemAmount;
        @Bind(R.id.item_total)
        TextView mItemTotal;
        @Bind(R.id.decrease)
        LinearLayout decrease;
        @Bind(R.id.increase)
        LinearLayout increase;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
