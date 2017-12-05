package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.berylsystems.buzz.R;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by BerylSystems on 11/25/2017.
 */

public class AddBillsPurchaseReturnAdapter extends BaseAdapter {

    Context context;
    List<Map<String, String>> mListMap;

    public AddBillsPurchaseReturnAdapter(Context context, List<Map<String, String>> mListMap) {
        this.context = context;
        this.mListMap = mListMap;

    }

    @Override
    public int getCount() {
        return mListMap.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bill_details, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map map = mListMap.get(position);
        Timber.i("MEEEEEEEEEE" + mListMap.get(position));
        String itemName = (String) map.get("courier_charges");
        String amount = (String) map.get("amount");
        holder.mItemName.setText(itemName);
        holder.mDiscount.setText(amount);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_name)
        TextView mItemName;
        @Bind(R.id.discount)
        TextView mDiscount;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
