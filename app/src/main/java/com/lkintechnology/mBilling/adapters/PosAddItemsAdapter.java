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
import com.lkintechnology.mBilling.utils.Preferences;

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
        Double item_amount = (Double) map.get("sales_price_main");
        Double total = (Double) map.get("total");
        String tax = (String) map.get("tax").toString();
        mItemName.setText(itemName);
        mQuantity.setText(quantity);
        mItemAmount.setText("₹ " + String.format("%.2f",item_amount));
        mItemTotal.setText("₹ " + String.format("%.2f",total));

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

                Map map = mListMap.get(position);
                String tax = (String) map.get("tax");

                if (Preferences.getInstance(context).getPos_sale_type().contains("GST-ItemWise") && tax.contains("GST ")) {
                    Double item_tax = (item_amount * taxSplit(tax))/100;
                    Double taxInclude = total + item_tax;
                    mItemTotal.setText("₹ " + String.format("%.2f",taxInclude));
                    setTotal(String.valueOf(item_tax), true);
                } else {
                    Double s = total + item_amount;
                    mItemTotal.setText("₹ " + String.format("%.2f",s));
                    setTotal(String.valueOf(item_amount), true);
                }
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInteger = Integer.parseInt(mQuantity.getText().toString());
                if (mInteger > 1) {
                    mInteger = mInteger - 1;
                    mQuantity.setText("" + mInteger);

                    String arr = mItemTotal.getText().toString();
                    String[] arr1 = arr.split("₹ ");
                    Double total = Double.valueOf(arr1[1]);
                    String arr2 = mItemAmount.getText().toString();
                    String[] arr3 = arr2.split("₹ ");
                    Double item_amount = Double.valueOf(arr3[1]);

                    Map map = mListMap.get(position);
                    String tax = (String) map.get("tax");

                    if (Preferences.getInstance(context).getPos_sale_type().contains("GST-ItemWise") && tax.contains("GST ")) {
                        Double item_tax = (item_amount * taxSplit(tax))/100;
                        Double taxInclude = total - item_tax;
                        mItemTotal.setText("₹ " + String.format("%.2f",taxInclude));
                        setTotal(String.valueOf(item_tax), false);
                    } else {
                        Double s = total - item_amount;
                        mItemTotal.setText("₹ " + String.format("%.2f",s));
                        setTotal(String.valueOf(item_amount), false);
                    }
                }
            }
        });
        return convertView;
    }

    public void setTotal(String amount, Boolean mBool) {
        Double total = 0.0;
        if (mBool) {
            total = getTotal() + Double.valueOf(amount);
        } else {
            total = getTotal() - Double.valueOf(amount);
        }
        setTaxChange(context, total);
        PosItemAddActivity.mSubtotal.setText("₹ " + String.format("%.2f",total));
    }

    public Double getTotal() {
        String total = PosItemAddActivity.mSubtotal.getText().toString();
        String[] arr = total.split("₹ ");
        Double a = Double.valueOf(arr[1].trim());
        return a;
    }

    public static void setTaxChange(Context context, Double subtotal) {
        Double tax = 0.0;
        if (Preferences.getInstance(context).getPos_sale_type() != null && !Preferences.getInstance(context).getPos_sale_type().equals("")) {
            String taxString = Preferences.getInstance(context).getPos_sale_type();
            String taxName = "";
            String taxValue = "";
            String arrTaxString[] = taxString.split("-");
            taxName = arrTaxString[0].trim();
            taxValue = arrTaxString[1].trim();
            if (taxString.startsWith("I") && taxString.endsWith("%")) {
                PosItemAddActivity.igst_layout.setVisibility(View.VISIBLE);
                PosItemAddActivity.sgst_cgst_layout.setVisibility(View.GONE);
                String[] arr = taxValue.split("%");
                String s = arr[0];
                if (!s.equals("")) {
                    tax = (subtotal * Double.valueOf(s)) / 100;
                    PosItemAddActivity.igst.setText("₹ " + String.format("%.2f",tax));
                }
            } else if (taxString.startsWith("L") && taxString.endsWith("%")) {
                PosItemAddActivity.igst_layout.setVisibility(View.GONE);
                PosItemAddActivity.sgst_cgst_layout.setVisibility(View.VISIBLE);
                String[] arr = taxValue.split("%");
                String s = arr[0];
                if (!s.equals("")) {
                    tax = (subtotal * Double.valueOf(s)) / 100;
                    PosItemAddActivity.sgst.setText("₹ " + String.format("%.2f",tax / 2));
                    PosItemAddActivity.cgst.setText("₹ " + String.format("%.2f",tax / 2));
                }
            } else {
                //For Exempt
                //For Export(ZeroRated)
                //Tax Include
                PosItemAddActivity.igst_layout.setVisibility(View.GONE);
                PosItemAddActivity.sgst_cgst_layout.setVisibility(View.GONE);
            }
        }

        granTotal(subtotal, tax);
    }

    public static void granTotal(Double subtotal, Double tax) {
        Double grandTotal = subtotal + tax;
        PosItemAddActivity.grand_total.setText("₹ " + String.format("%.2f",grandTotal));
    }

    public Double taxSplit(String tax) {
        Double a = 0.0;
        if (tax.contains("GST ")) {
            String[] arr = tax.split(" ");
            String[] arr1 = arr[1].split("%");
            a = Double.valueOf(arr1[0]);
        }
        return a;
    }
}
