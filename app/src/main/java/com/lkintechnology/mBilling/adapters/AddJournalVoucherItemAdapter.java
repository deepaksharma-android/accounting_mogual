package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by manjooralam on 3/3/2018.
 */

public class AddJournalVoucherItemAdapter extends BaseAdapter {
    Context context;
    private List<Map> mListMap;

    public AddJournalVoucherItemAdapter(Context context, List<Map> mListMap) {
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

        AddJournalVoucherItemAdapter.ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_journal_voucheritem, parent, false);
            holder = new AddJournalVoucherItemAdapter.ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (AddJournalVoucherItemAdapter.ViewHolder) convertView.getTag();
        }

            Map map=mListMap.get(position);
            Timber.i("MEEEEEEEEEE"+mListMap.get(position));
            String invoicedetail= (String) map.get("inv_num");
            String partyName= (String) map.get("party_name");
            String accountName= (String) map.get("acount_name");
            String rate= (String) map.get("rate");
            String cgst= (String) map.get("cgst");
            String sgst= (String) map.get("sgst");
            String differenciateitem= (String) map.get("difference_amount");
            String igst= (String) map.get("igst");
            String rcnItem= (String) map.get("spRCNItem");
            String itcEligibility= (String) map.get("spITCEligibility");
        if(igst.equals("")){
            holder.mIgstLayout.setVisibility(View.GONE);
            holder.mCgstLayout.setVisibility(View.VISIBLE);
            holder.mSgstLayout.setVisibility(View.VISIBLE);
        }
        else{
            holder.mIgstLayout.setVisibility(View.VISIBLE);
            holder.mCgstLayout.setVisibility(View.GONE);
            holder.mSgstLayout.setVisibility(View.GONE);
        }
        /*String discount= (String) map.get("discount");
        String value= (String) map.get("value");
        String total= (String) map.get("total");
        String mrp= (String) map.get("mrp");*/

            holder.tvInvoiceDetail.setText(invoicedetail);
            holder.tvPartyName.setText(partyName);
            holder.tvAccountName.setText(accountName);
            holder.tvSGST.setText(sgst);
            holder.tvRate.setText(rate);
            holder.tvCGST.setText(cgst);
            holder.tvDifferencAmount.setText(differenciateitem);
            holder.tvIGST.setText(igst);
            holder.tvRCNItem.setText(rcnItem);
            holder.tvITCEligibility.setText(itcEligibility);
           // holder.tvITCEligibility(itcEligibility);
            //   holder..setText(sgst);


           /* holder.tvITCEligibility.setVisibility(View.VISIBLE);
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
            holder.tvSGST.setText(sgst);
            holder.tvITCEligibility.setText(itcEligibility);*/
            // holder.tvITCEligibility(itcEligibility);
            //   holder..setText(sgst);

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
        @Bind(R.id.tv_account_name)
        TextView tvAccountName;
        @Bind(R.id.tv_party_name)
        TextView tvPartyName;
        @Bind(R.id.tv_difference_amount)
        TextView tvDifferencAmount;
        @Bind(R.id.tv_rate)
        TextView tvRate;
        @Bind(R.id.tv_igst)
        TextView tvIGST;
        @Bind(R.id.tv_cgst)
        TextView tvCGST;
        @Bind(R.id.tv_sgst)
        TextView tvSGST;
        @Bind(R.id.tv_itc_eligibility)
        TextView tvITCEligibility;
        @Bind(R.id.tv_rcn_item)
        TextView tvRCNItem;
        @Bind(R.id.igst_layout)
        LinearLayout mIgstLayout;
        @Bind(R.id.cgst_layout)
        LinearLayout mCgstLayout;
        @Bind(R.id.sgst_layout)
        LinearLayout mSgstLayout;




        public ViewHolder(View view) {
            ButterKnife.bind(this, view);

        }
    }
}
