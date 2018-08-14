package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;

import net.sourceforge.zbar.Image;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by BerylSystems on 11/25/2017.
 */

public class PosAddItemsVoucherAdapter extends BaseAdapter {

    Context context;
    List<Map> mListMap;

    public PosAddItemsVoucherAdapter(Context context, List<Map> mListMap) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_for_pos, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map map=mListMap.get(position);
        String item_id= (String) map.get("item_id");
        String itemName= (String) map.get("item_name");
        String quantity= (String) map.get("quantity");
        String total= (String) map.get("total");

        holder.mItemName.setText(itemName);
        holder.mQuantity.setText(quantity);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.lblListItem)
        TextView mItemName;
        @Bind(R.id.quantity)
        TextView mQuantity;
        @Bind(R.id.increase)
        LinearLayout increase;
        @Bind(R.id.decrease)
        LinearLayout decrease;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
