package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity;
import com.lkintechnology.mBilling.activities.company.pos.PosItemAddActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.EventForPos;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by BerylSystems on 11/25/2017.
 */

public class PosAddItemsAdapter extends RecyclerView.Adapter<PosAddItemsAdapter.ViewHolder> {

    Context context;
    List<Map> mListMap;
    int mInteger = 0;
    AppUser appUser;
    public Map mMapPosItem;
    Boolean mBool = true;
    //ViewHolder holder;

    public PosAddItemsAdapter(Context context, List<Map> mListMap) {
        this.context = context;
        this.mListMap = mListMap;
        appUser = LocalRepositories.getAppUser(context);
    }

    @Override
    public PosAddItemsAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_for_pos, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return mListMap.size();
    }

    @Override
    public void onBindViewHolder(PosAddItemsAdapter.ViewHolder viewHolder, int position) {
        // appUser = LocalRepositories.getAppUser(context);
        mInteger = 0;
        mBool = true;
        Map map = mListMap.get(position);
        String item_id = (String) map.get("item_id");
        String itemName = (String) map.get("item_name");
        String quantity = (String) map.get("quantity");
        Double item_amount = Double.valueOf((String) map.get("sales_price_main"));
        Double total = Double.valueOf((String) map.get("total"));
        String tax = (String) map.get("tax").toString();
        viewHolder.mItemName.setText(itemName);
        viewHolder.mQuantity.setText(quantity);
        viewHolder.mItemAmount.setText("₹ " + String.format("%.2f", item_amount));
        viewHolder.mItemTotal.setText("₹ " + String.format("%.2f", total));

        viewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser = LocalRepositories.getAppUser(context);
                mInteger = Integer.parseInt(viewHolder.mQuantity.getText().toString());
                mInteger = mInteger + 1;
                viewHolder.mQuantity.setText("" + mInteger);
                String arr = viewHolder.mItemTotal.getText().toString();
                String[] arr1 = arr.split("₹ ");
                Double total = Double.valueOf(arr1[1]);
                String arr2 = viewHolder.mItemAmount.getText().toString();
                String[] arr3 = arr2.split("₹ ");
                Double item_amount = Double.valueOf(arr3[1]);
                Double temp_item_amount = 0.0;
                Map map = mListMap.get(position);
                String tax = (String) map.get("tax");
                // mMapPosItem.put(pos, mQuantity.getText().toString());
                Double s = 0.0;
                Double discount = Double.valueOf(ExpandableItemListActivity.mListMapForItemSale.get(position).get("discount").toString());
                Double rate = 0.0;
                if (discount == 0){
                    Double value = Double.valueOf(ExpandableItemListActivity.mListMapForItemSale.get(position).get("value").toString());
                    if (value!=0){
                        rate = Double.valueOf(ExpandableItemListActivity.mListMapForItemSale.get(position).get("rate").toString());
                        item_amount = Double.valueOf(String.format("% .2f", ((rate - (value / mInteger)))));
                        ExpandableItemListActivity.mListMapForItemSale.get(position).put("sales_price_main", ""+item_amount);
                        viewHolder.mItemAmount.setText("₹ " + String.format("% .2f",item_amount));
                        temp_item_amount = rate - item_amount;
                    }
                }
                s = total + item_amount + temp_item_amount;

                if (Preferences.getInstance(context).getPos_sale_type().contains("GST-ItemWise") && tax.contains("GST ")) {
                    Double taxValue = taxSplit(tax);
                    Double item_tax = (item_amount * taxValue) / 100;
                    Double taxInclude = total + item_tax;
                    viewHolder.mItemTotal.setText("₹ " + taxInclude);
                    setTotal(String.valueOf(item_tax + temp_item_amount), true, 0.0, 0.0, tax);
                } else if (Preferences.getInstance(context).getPos_sale_type().contains("GST-MultiRate")) {
                    Double taxValue = taxSplit(tax);
                    Double gst = item_amount * taxValue / 100;
                    viewHolder.mItemTotal.setText("₹ " + s);
                    setTotal(String.valueOf(item_amount + temp_item_amount), true, gst, taxValue, tax);
                } else {
                    viewHolder.mItemTotal.setText("₹ " + s);
                    setTotal(String.valueOf(item_amount + temp_item_amount), true, 0.0, 0.0, tax);
                }
                ExpandableItemListActivity.mListMapForItemSale.get(position).put("quantity", viewHolder.mQuantity.getText().toString());
                mListMap.get(position).put("quantity", viewHolder.mQuantity.getText().toString());
                ExpandableItemListActivity.mListMapForItemSale.get(position).put("total", String.valueOf(getMultiRateTaxChange(viewHolder.mItemTotal.getText().toString())));
                mListMap.get(position).put("total", String.valueOf(getMultiRateTaxChange(viewHolder.mItemTotal.getText().toString())));
                LocalRepositories.saveAppUser(context, appUser);
                //  notifyDataSetChanged();
            }

        });

        viewHolder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBool = true;
                appUser = LocalRepositories.getAppUser(context);
                mInteger = Integer.parseInt(viewHolder.mQuantity.getText().toString());
                if (mInteger > 0) {
                    mInteger = mInteger - 1;
                    viewHolder.mQuantity.setText("" + mInteger);

                    if (ExpandableItemListActivity.mListMapForItemSale.size() == 1) {
                        if (mInteger == 0) {
                            mBool = false;
                        }
                    } else {
                        mBool = true;
                    }

                    if (mBool) {
                        String arr = viewHolder.mItemTotal.getText().toString();
                        String[] arr1 = arr.split("₹ ");
                        Double total = Double.valueOf(arr1[1]);
                        String arr2 = viewHolder.mItemAmount.getText().toString();
                        String[] arr3 = arr2.split("₹ ");
                        Double item_amount = Double.valueOf(arr3[1]);
                        Double temp_item_amount = 0.0;
                        Double discount = Double.valueOf(ExpandableItemListActivity.mListMapForItemSale.get(position).get("discount").toString());
                        Double value = Double.valueOf(ExpandableItemListActivity.mListMapForItemSale.get(position).get("value").toString());
                        Double rate = Double.valueOf(ExpandableItemListActivity.mListMapForItemSale.get(position).get("rate").toString());
                        if (mInteger==0){
                            if (discount==0){
                                item_amount = rate - value;
                            }
                        }else {
                            if (discount == 0){
                                if (value!=0){
                                    item_amount = Double.valueOf(String.format("% .2f", ((rate - (value / mInteger)))));
                                    ExpandableItemListActivity.mListMapForItemSale.get(position).put("sales_price_main", ""+item_amount);
                                    viewHolder.mItemAmount.setText("₹ " + String.format("% .2f",item_amount));
                                    temp_item_amount = rate - item_amount;
                                }
                            }
                        }


                        Map map = mListMap.get(position);
                        Double s = total - item_amount - temp_item_amount;
                        if (Preferences.getInstance(context).getPos_sale_type().contains("GST-ItemWise") && tax.contains("GST ")) {
                            String tax = (String) map.get("tax");
                            Double taxValue = taxSplit(tax);
                            Double item_tax = (item_amount * taxValue) / 100;
                            String taxInclude = String.format("%.2f", (total - item_tax));
                            viewHolder.mItemTotal.setText("₹ " + taxInclude);
                            setTotal(String.valueOf(item_tax + temp_item_amount), false, 0.0, 0.0, tax);
                        } else if (Preferences.getInstance(context).getPos_sale_type().contains("GST-MultiRate") && tax.contains("GST ")) {
                            String tax = (String) map.get("tax");
                            Double taxValue = taxSplit(tax);
                            Double gst = item_amount * taxValue / 100;
                            viewHolder.mItemTotal.setText("₹ " + s);
                            setTotal(String.valueOf(item_amount + temp_item_amount), false, gst, taxValue, tax);
                        } else {
                            viewHolder.mItemTotal.setText("₹ " + s);
                            setTotal(String.valueOf(item_amount + temp_item_amount), false, 0.0, 0.0, tax);
                        }
                        if (mInteger == 0) {
                            ExpandableItemListActivity.mListMapForItemSale.remove(position);
                           // mListMap.remove(position);
                            notifyDataSetChanged();
                        } else {
                            ExpandableItemListActivity.mListMapForItemSale.get(position).put("quantity", viewHolder.mQuantity.getText().toString());
                            mListMap.get(position).put("quantity", viewHolder.mQuantity.getText().toString());
                            ExpandableItemListActivity.mListMapForItemSale.get(position).put("total", String.valueOf(getMultiRateTaxChange(viewHolder.mItemTotal.getText().toString())));
                            mListMap.get(position).put("total", String.valueOf(getMultiRateTaxChange(viewHolder.mItemTotal.getText().toString())));
                        }
                        LocalRepositories.saveAppUser(context, appUser);
                        //  EventBus.getDefault().post(new EventForPos("true"));
                    } else {
                        mInteger = mInteger + 1;
                        viewHolder.mQuantity.setText("" + mInteger);
                        Toast.makeText(context, " Item quantity should be at least one in a list", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public void setTotal(String amount, Boolean mBool, Double gst, Double taxValue, String tax) {
        Double total = 0.0, grandTotal = 0.0;
        String taxString = Preferences.getInstance(context).getPos_sale_type();
        appUser = LocalRepositories.getAppUser(context);
        if (mBool) {
            total = getTotal(PosItemAddActivity.mSubtotal.getText().toString()) + Double.valueOf(amount);
            grandTotal = getTotal(PosItemAddActivity.grand_total.getText().toString()) + Double.valueOf(amount);
        } else {
            total = getTotal(PosItemAddActivity.mSubtotal.getText().toString()) - Double.valueOf(amount);
            grandTotal = getTotal(PosItemAddActivity.grand_total.getText().toString()) - Double.valueOf(amount);
        }

        PosItemAddActivity.mSubtotal.setText("₹ " + String.format("%.2f", total));
        PosItemAddActivity.grand_total.setText("₹ " + String.format("%.2f", grandTotal));
        //setTaxChange(context, total, gst, taxValue, tax, mBool);
        //  appUser = LocalRepositories.getAppUser(context);
        if (ExpandableItemListActivity.mListMapForBillSale.size() > 0) {
            if (taxString.contains("GST-MultiRate")) {
                String subTotal = String.valueOf(total) + "," + String.valueOf(gst) + "," + String.valueOf(taxValue) + "," + String.valueOf(mBool);
                EventBus.getDefault().post(new EventForPos(subTotal));
            } else {
                String subTotal = String.valueOf(total);
                EventBus.getDefault().post(new EventForPos(subTotal));
            }
        } else {
            granTotal(total, 0.00/*taxSplit(tax)*/);
        }
    }

    public Double getTotal(String total) {
        String[] arr = total.split("₹ ");
        Double a = Double.valueOf(arr[1].trim());
        return a;
    }

    public static void granTotal(Double subtotal, Double tax) {
        Double grandTotal = subtotal + tax;
        PosItemAddActivity.grand_total.setText("₹ " + String.format("%.2f", grandTotal));
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

    public static Double getMultiRateTaxChange(String total) {
        // String total = PosItemAddActivity.mSubtotal.getText().toString();
        if (total.equals("0.0")) {
            total = "₹ 0.0";
        }
        String[] arr = total.split("₹ ");
        Double a = Double.valueOf(arr[1].trim());
        return a;
    }

    public static Double gstPlusMinus(Double gst_total, Double gst, Boolean mBool) {
        Double total = 0.0;
        if (mBool) {
            total = gst_total + gst;
        } else {
            total = gst_total - gst;
        }
        return total;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.lblListItem)
        TextView mItemName;
        @Bind(R.id.quantity)
        TextView mQuantity;
        @Bind(R.id.item_amount)
        TextView mItemAmount;
        @Bind(R.id.item_total)
        TextView mItemTotal;
        @Bind(R.id.decrease)
        LinearLayout decrease;
        @Bind(R.id.increase)
        LinearLayout increase;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
