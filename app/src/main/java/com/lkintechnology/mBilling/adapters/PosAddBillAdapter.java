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
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.EventForBillDelete;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by BerylSystems on 11/25/2017.
 */

public class PosAddBillAdapter extends RecyclerView.Adapter<PosAddBillAdapter.ViewHolder> {

    Context context;
    List<Map<String, String>> mListMap;
    int mInteger = 0;
    ArrayList<String> billSundryTotal;
    AppUser appUser;

    //ViewHolder holder;

    public PosAddBillAdapter(Context context, List<Map<String, String>> mListMap,ArrayList<String> billSundryTotal) {
        this.context = context;
        this.mListMap = mListMap;
        this.billSundryTotal = billSundryTotal;
    }

    @Override
    public PosAddBillAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_bill_for_pos, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mListMap.size();
    }

    @Override
    public void onBindViewHolder(PosAddBillAdapter.ViewHolder viewHolder, int position) {
        Double total = 0.0, subtotal = 0.0, subTotal = 0.0, grandTotal = 0.0,total1 = 0.0,subtotal1 = 0.0;
        Map map = mListMap.get(position);
        String itemName = (String) map.get("courier_charges");
        String amount = (String) map.get("amount");
        String percentage = (String) map.get("percentage");
        String fed_as_percentage = (String) map.get("fed_as_percentage");
        String fed_as = (String) map.get("fed_as");
        String type = (String) map.get("type");
        if (billSundryTotal.get(position)!=null) {
            if (!billSundryTotal.get(position).equals("0.00")) {
                viewHolder.mAmount.setText("₹ " + billSundryTotal.get(position));
            }
        }

        if (fed_as_percentage != null) {
            if (fed_as_percentage.equals("valuechange")) {
                viewHolder.mDiscount.setText(itemName + " ");
            }else {
                if (billSundryTotal.get(position)!=null) {
                    if (!billSundryTotal.get(position).equals("0.00")) {
                        viewHolder.mDiscount.setText(itemName + " " + amount + " %");
                        viewHolder.igst_layout.setVisibility(View.VISIBLE);
                    } else {
                        viewHolder.igst_layout.setVisibility(View.GONE);
                    }
                }else {
                    viewHolder.igst_layout.setVisibility(View.GONE);
                }
            }
        }

        if (itemName.equals("IGST") || itemName.equals("CGST") || itemName.equals("SGST")){
            viewHolder.cancel_button.setVisibility(View.GONE);
        }else {
            viewHolder.cancel_button.setVisibility(View.VISIBLE);
        }

        viewHolder.cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new EventForBillDelete(String.valueOf(position) + "," + type));
            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.discount)
        TextView mDiscount;
        @Bind(R.id.amount)
        TextView mAmount;
        @Bind(R.id.igst_layout)
        LinearLayout igst_layout;
        @Bind(R.id.cancel_button)
        LinearLayout cancel_button;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public Double getGrandTotal(String amount) {
        String[] arr = amount.split("₹ ");
        Double a = Double.valueOf(arr[1].trim());
        return a;
    }
}
