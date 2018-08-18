package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.pos.PosItemAddActivity;

import java.util.List;
import java.util.Map;


/**
 * Created by BerylSystems on 11/25/2017.
 */

public class PosAddItemsAdapter extends BaseAdapter {

    Context context;
    List<Map> mListMap;
    int mInteger = 0;
    //ViewHolder holder;

    public PosAddItemsAdapter(Context context, List<Map> mListMap) {
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
        mInteger = 0;
        LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.list_item_for_pos, null);

        TextView mItemName = (TextView) convertView.findViewById(R.id.lblListItem);
        TextView mQuantity = (TextView) convertView.findViewById(R.id.quantity);
        TextView mItemAmount = (TextView) convertView.findViewById(R.id.item_amount);
        TextView mItemTotal = (TextView) convertView.findViewById(R.id.item_total);
        LinearLayout decrease = (LinearLayout) convertView.findViewById(R.id.decrease);
        LinearLayout increase = (LinearLayout) convertView.findViewById(R.id.increase);
        Map map = mListMap.get(position);
        String item_id = (String) map.get("item_id");
        String itemName = (String) map.get("item_name");
        String quantity = (String) map.get("quantity");
        String item_amount = map.get("sales_price_main").toString();
        String item_total = (String) map.get("total").toString();
        mItemName.setText(itemName);
        mQuantity.setText(quantity);
        mItemAmount.setText("₹ " + item_amount);
        mItemTotal.setText("₹ " + item_total);

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInteger = Integer.parseInt(mQuantity.getText().toString());
                mInteger = mInteger + 1;
                mQuantity.setText("" + mInteger);

                String arr = mItemTotal.getText().toString();
                String[] arr1 = arr.split("₹ ");
                Double total = Double.valueOf(arr1[1]);
                String arr2 = mItemAmount.getText().toString();
                String[] arr3 = arr2.split("₹ ");
                Double item_amount = Double.valueOf(arr3[1]);
                String s = String.valueOf(total + item_amount);
                mItemTotal.setText("₹ " + s);

                setTotal(String.valueOf(item_amount),true);
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInteger = Integer.parseInt(mQuantity.getText().toString());
                if (mInteger >1) {
                    mInteger = mInteger - 1;
                    mQuantity.setText("" + mInteger);

                    String arr = mItemTotal.getText().toString();
                    String[] arr1 = arr.split("₹ ");
                    Double total = Double.valueOf(arr1[1]);
                    String arr2 = mItemAmount.getText().toString();
                    String[] arr3 = arr2.split("₹ ");
                    Double item_amount = Double.valueOf(arr3[1]);
                    String s = String.valueOf(total - item_amount);
                    mItemTotal.setText("₹ " + s);

                    setTotal(String.valueOf(item_amount),false);
                }
            }
        });


        return convertView;
    }

    public void setTotal(String amount, Boolean mBool){
        Double total = 0.0;
        if (mBool){
            total =  getTotal() + Double.valueOf(amount);
        }else {
            total = getTotal() - Double.valueOf(amount);
        }
        PosItemAddActivity.mSubtotal.setText("₹ "+total);
    }

    public Double getTotal(){
        String total = PosItemAddActivity.mSubtotal.getText().toString();
        String[] arr = total.split("₹ ");
        Double a = Double.valueOf(arr[1].trim());
        return a;
    }
}
