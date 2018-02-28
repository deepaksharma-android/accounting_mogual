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
 * Created by manjooralam on 2/28/2018.
 */

public class AddCreditNoteItemAdapter extends BaseAdapter {

    Context context;
    List<Map> mListMap;

    public AddCreditNoteItemAdapter(Context context, List<Map> mListMap) {
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_credit_note_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Map map=mListMap.get(position);
        Timber.i("MEEEEEEEEEE"+mListMap.get(position));
        String invoicedetail= (String) map.get("item_name");
        String date= (String) map.get("description");
        String gst= (String) map.get("quantity");
        String ccst= (String) map.get("unit");
        String sgst= (String) map.get("sr_no");
        String differenciateitem= (String) map.get("rate");
        /*String discount= (String) map.get("discount");
        String value= (String) map.get("value");
        String total= (String) map.get("total");
        String mrp= (String) map.get("mrp");*/

        /*holder.mItemName.setText(itemName);
        holder.mDiscount.setText(discount);
        holder.mMrp.setText(mrp);
        holder.mQuantity.setText(quantity);
        holder.mSalePrice.setText(rate);
        holder.mTotal.setText(total);*/
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.tv_invoice_detail)
        TextView tvInvoiceDetail;
        @Bind(R.id.tv_date)
        TextView tvDate;
        @Bind(R.id.tv_differenciate_item)
        TextView tvDifferenciateItem;
        @Bind(R.id.tv_ccst)
        TextView tvCCST;
        @Bind(R.id.tv_gst)
        TextView tvGST;
        @Bind(R.id.tv_igst)
        TextView tvIGST;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
