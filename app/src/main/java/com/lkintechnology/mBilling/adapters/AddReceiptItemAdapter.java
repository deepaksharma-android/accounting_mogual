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

public class AddReceiptItemAdapter extends BaseAdapter {
    Context context;
    List<Map> mListMap;

    public AddReceiptItemAdapter(Context context, List<Map> mListMap) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_receipt_item_row, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map map=mListMap.get(position);
        Timber.i("MEEEEEEEEEE"+mListMap.get(position));
        String ref_num= (String) map.get("ref_num");
        String amount= (String) map.get("amount");
        String total= (String) map.get("total");
        String taxrate= (String) map.get("taxrate");
        holder.mRefNum.setText(ref_num);
        holder.mTaxRate.setText(taxrate);
        holder.mAmount.setText(amount);
        holder.mTotalAmount.setText(total);



        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.ref_num)
        TextView mRefNum;
        @Bind(R.id.tax_rate)
        TextView mTaxRate;
        @Bind(R.id.amount)
        TextView mAmount;
        @Bind(R.id.total)
        TextView mTotalAmount;



        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}