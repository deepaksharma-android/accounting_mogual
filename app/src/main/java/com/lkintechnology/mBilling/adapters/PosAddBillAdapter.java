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
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

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
        String fed_as_percentage = (String) map.get("fed_as_percentage");
        String fed_as = (String) map.get("fed_as");
        String type = (String) map.get("type");
        viewHolder.mAmount.setText("₹ " + billSundryTotal.get(position));
        if (fed_as_percentage != null) {
            if (fed_as_percentage.equals("valuechange")) {
                viewHolder.mDiscount.setText("Discount ");
            }else {
                viewHolder.mDiscount.setText(itemName +" "+ amount + " %");
            }
        }
     /*   if (fed_as_percentage != null) {
          *//*  if (position==0){
                appUser = LocalRepositories.getAppUser(context);
                grandTotal = Double.valueOf(appUser.grandTotal);
                subTotal = Double.valueOf(appUser.subTotal);
            }else {*//*
                grandTotal = getGrandTotal(PosItemAddActivity.grand_total.getText().toString());
                subTotal = getGrandTotal(PosItemAddActivity.mSubtotal.getText().toString());
          //  }
            if (fed_as_percentage.equals("valuechange")) {
               *//* Double changeamount = Double.parseDouble((String) map.get("changeamount"));
                if (type.equals("Additive")) {
                    total = grandTotal + changeamount;
                    subtotal = subTotal + changeamount;
                } else {
                    total = grandTotal - changeamount;
                    subtotal = subTotal - changeamount;
                }
                PosItemAddActivity.mSubtotal.setText("₹ " + String.format("%.2f", subtotal));
                PosItemAddActivity.grand_total.setText("₹ " + String.format("%.2f", total));
                viewHolder.mAmount.setText(String.valueOf("₹ " + String.format("%.2f", changeamount)));
                // viewHolder.mDefaultText.setText("DEFAULT VALUE (₹)");*//*
            } else {
              *//*  total = (grandTotal * Double.valueOf(amount)) / 100;
                subtotal = (subTotal * Double.valueOf(amount)) / 100;
                viewHolder.mAmount.setText("₹ " + String.format("%.2f", total));
                if (type.equals("Additive")) {
                    total1 = grandTotal + total;
                    subtotal1 = subTotal + subtotal;
                } else {
                    total1 = grandTotal - total;
                    subtotal1 = subTotal - subtotal;
                }
                viewHolder.mDiscount.setText("Discount " + amount + "%");
                PosItemAddActivity.mSubtotal.setText("₹ " + String.format("%.2f", subtotal1));
                PosItemAddActivity.grand_total.setText("₹ " + String.format("%.2f", total1));*//*
            }
        }*/
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.discount)
        TextView mDiscount;
        @Bind(R.id.amount)
        TextView mAmount;

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
