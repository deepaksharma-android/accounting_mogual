package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by BerylSystems on 11/25/2017.
 */

public class AddBillsPurchaseAdapter extends BaseAdapter {

    Context context;
    List<Map<String, String>> mListMap;
    ArrayList<String> mListItemMap;


    public AddBillsPurchaseAdapter(Context context, List<Map<String, String>> mListMap, ArrayList<String> mListItemMap) {
        this.context = context;
        this.mListMap = mListMap;
        this.mListItemMap=mListItemMap;

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
        String fed_as_percentage = (String) map.get("fed_as_percentage");
        String fed_as = (String) map.get("fed_as");
        if(fed_as_percentage!=null) {
            if (fed_as_percentage.equals("valuechange")) {
                Double changeamount = Double.parseDouble((String) map.get("changeamount"));
                holder.mDiscount.setText(String.valueOf(changeamount));
                holder.mDefaultText.setText("DEFAULT VALUE (₹)");

            }else {
                if (fed_as != null) {
                    if (fed_as.equals("Absolute Amount")) {
                        holder.mDefaultText.setText("DEFAULT VALUE (₹)");
                    }
                }
                holder.mDiscount.setText(amount);
            }
        }
        holder.mItemName.setText(itemName);
        holder.mTotal.setText(mListItemMap.get(position));
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_name)
        TextView mItemName;
        @Bind(R.id.discount)
        TextView mDiscount;
        @Bind(R.id.total)
        TextView mTotal;
        @Bind(R.id.default_text)
        TextView mDefaultText;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
