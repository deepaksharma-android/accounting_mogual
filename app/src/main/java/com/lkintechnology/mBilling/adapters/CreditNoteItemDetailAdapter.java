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
 * Created by manjooralam on 3/1/2018.
 */

public class CreditNoteItemDetailAdapter extends BaseAdapter {
    Context context;
   private List<Map> mListMap;

    public CreditNoteItemDetailAdapter(Context context, List<Map> mListMap) {
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

        CreditNoteItemDetailAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add_credit_note_item, parent, false);
            holder = new CreditNoteItemDetailAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (CreditNoteItemDetailAdapter.ViewHolder) convertView.getTag();
        }
        if (mListMap.get(position).get("goodsItem") != null && mListMap.get(position).get("goodsItem").equals("")){
            Map map=mListMap.get(position);
            Timber.i("MEEEEEEEEEE"+mListMap.get(position));
            String invoicedetail= (String) map.get("inv_num");
            String date= (String) map.get("date");
            String gst= (String) map.get("gst");
            String cgst= (String) map.get("cgst");
            String sgst= (String) map.get("sgst");
            String differenciateitem= (String) map.get("difference_amount");
            String igst= (String) map.get("igst");
            String itcEligibility= (String) map.get("goodsItem");
        /*String discount= (String) map.get("discount");
        String value= (String) map.get("value");
        String total= (String) map.get("total");
        String mrp= (String) map.get("mrp");*/

            holder.tvInvoiceDetail.setText(invoicedetail);
            holder.tvDate.setText(date);
            holder.tvGST.setText(gst);
            holder.tvCCST.setText(cgst);
            holder.tvDifferenciateItem.setText(differenciateitem);
            holder.tvIGST.setText(igst);
            // holder.tvITCEligibility(itcEligibility);
            //   holder..setText(sgst);

        }else {
            holder.tvITCEligibility.setVisibility(View.VISIBLE);
            holder.tvViewITCEligibility.setVisibility(View.VISIBLE);
            Map map=mListMap.get(position);
            Timber.i("MEEEEEEEEEE"+mListMap.get(position));
            String invoicedetail= (String) map.get("inv_num");
            String date= (String) map.get("date");
            String gst= (String) map.get("gst");
            String cgst= (String) map.get("cgst");
            String sgst= (String) map.get("sgst");
            String differenciateitem= (String) map.get("difference_amount");
            String igst= (String) map.get("igst");
            String itcEligibility= (String) map.get("goodsItem");
        /*String discount= (String) map.get("discount");
        String value= (String) map.get("value");
        String total= (String) map.get("total");
        String mrp= (String) map.get("mrp");*/

            holder.tvInvoiceDetail.setText(invoicedetail);
            holder.tvDate.setText(date);
            holder.tvGST.setText(gst);
            holder.tvCCST.setText(cgst);
            holder.tvDifferenciateItem.setText(differenciateitem);
            holder.tvIGST.setText(igst);
            holder.tvSGST.setText(sgst);
            holder.tvITCEligibility.setText(itcEligibility);
            // holder.tvITCEligibility(itcEligibility);
            //   holder..setText(sgst);
        }
        /*Map map=mListMap.get(position);
        Timber.i("MEEEEEEEEEE"+mListMap.get(position));
        String invoicedetail= (String) map.get("inv_num");
        String date= (String) map.get("date");
        String gst= (String) map.get("gst");
        String cgst= (String) map.get("cgst");
        String sgst= (String) map.get("sgst");
        String differenciateitem= (String) map.get("difference_amount");
        String igst= (String) map.get("igst");
        String itcEligibility= (String) map.get("goodsItem");
        *//*String discount= (String) map.get("discount");
        String value= (String) map.get("value");
        String total= (String) map.get("total");
        String mrp= (String) map.get("mrp");*//*

        holder.tvInvoiceDetail.setText(invoicedetail);
        holder.tvDate.setText(date);
        holder.tvGST.setText(gst);
        holder.tvCCST.setText(cgst);
        holder.tvDifferenciateItem.setText(differenciateitem);
        holder.tvIGST.setText(igst);
       // holder.tvITCEligibility(itcEligibility);
     //   holder..setText(sgst);
        return convertView;*/
        return convertView;
    }

     class ViewHolder {
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
        @Bind(R.id.tv_sgst)
        TextView tvSGST;
        @Bind(R.id.tv_itceligibility)
        TextView tvITCEligibility;
        @Bind(R.id.tv_view_igst)
        TextView tvViewIGST;
        @Bind(R.id.tv_view_itc_eligibility)
        TextView tvViewITCEligibility;



        public ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }
    }
}
