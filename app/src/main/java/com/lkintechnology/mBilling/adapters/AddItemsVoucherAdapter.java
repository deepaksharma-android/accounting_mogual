package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by BerylSystems on 11/25/2017.
 */

public class AddItemsVoucherAdapter extends BaseAdapter {

    Context context;
    List<Map> mListMap;

    public AddItemsVoucherAdapter(Context context, List<Map> mListMap) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_details, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map map=mListMap.get(position);
        Timber.i("MEEEEEEEEEE"+mListMap.get(position));
        String itemName= (String) map.get("item_name");
        String description= (String) map.get("description");
        String quantity= (String) map.get("quantity");
        String unit= (String) map.get("unit");
        String srNo= (String) map.get("sr_no");
        String rate= (String) map.get("rate");
        String discount= (String) map.get("discount");
        String value= (String) map.get("value");
        String total= (String) map.get("total");
        String mrp= (String) map.get("mrp");

        holder.mItemName.setText(itemName);
        holder.mDiscount.setText(discount);
        holder.mMrp.setText(mrp);
        holder.mQuantity.setText(quantity);
        holder.mSalePrice.setText(rate);
        holder.mTotal.setText(total);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_name)
        TextView mItemName;
        @Bind(R.id.discount)
        TextView mDiscount;
        @Bind(R.id.total)
        TextView mTotal;
        @Bind(R.id.mrp)
        TextView mMrp;
        @Bind(R.id.sale_price)
        TextView mSalePrice;
        @Bind(R.id.quantity)
        TextView mQuantity;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
