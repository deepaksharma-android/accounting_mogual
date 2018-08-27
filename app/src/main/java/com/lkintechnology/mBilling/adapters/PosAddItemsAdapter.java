package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity;
import com.lkintechnology.mBilling.activities.company.pos.PosItemAddActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.EventForPos;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by BerylSystems on 11/25/2017.
 */

public class PosAddItemsAdapter extends  RecyclerView.Adapter<PosAddItemsAdapter.ViewHolder> {

    Context context;
    List<Map> mListMap;
    int mInteger = 0;
    AppUser appUser;
    public Map mMapPosItem;
    //ViewHolder holder;

    public PosAddItemsAdapter(Context context, List<Map> mListMap) {
        this.context = context;
        this.mListMap = mListMap;

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
        appUser = LocalRepositories.getAppUser(context);
        mInteger = 0;
      /*  TextView mItemName = (TextView) convertView.findViewById(R.id.lblListItem);
        TextView mQuantity = (TextView) convertView.findViewById(R.id.quantity);
        TextView mItemAmount = (TextView) convertView.findViewById(R.id.item_amount);
        TextView mItemTotal = (TextView) convertView.findViewById(R.id.item_total);
        LinearLayout decrease = (LinearLayout) convertView.findViewById(R.id.decrease);
        LinearLayout increase = (LinearLayout) convertView.findViewById(R.id.increase);*/
        Map map = mListMap.get(position);
        String item_id = (String) map.get("item_id");
        String itemName = (String) map.get("item_name");
        String quantity = (String) map.get("quantity");
        Double item_amount = (Double) map.get("sales_price_main");
        Double total = (Double) map.get("total");
        String tax = (String) map.get("tax").toString();
        viewHolder.mItemName.setText(itemName);
        viewHolder.mQuantity.setText(quantity);
        viewHolder.mItemAmount.setText("₹ " + String.format("%.2f", item_amount));
        viewHolder.mItemTotal.setText("₹ " + String.format("%.2f", total));

        viewHolder.increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInteger = Integer.parseInt(viewHolder.mQuantity.getText().toString());
                mInteger = mInteger + 1;
                viewHolder.mQuantity.setText("" + mInteger);

                String arr = viewHolder.mItemTotal.getText().toString();
                String[] arr1 = arr.split("₹ ");
                Double total = Double.valueOf(arr1[1]);
                String arr2 = viewHolder.mItemAmount.getText().toString();
                String[] arr3 = arr2.split("₹ ");
                Double item_amount = Double.valueOf(arr3[1]);


                Map map = mListMap.get(position);
                String tax = (String) map.get("tax");
               // mMapPosItem.put(pos, mQuantity.getText().toString());
                Double s = total + item_amount;
                if (Preferences.getInstance(context).getPos_sale_type().contains("GST-ItemWise") && tax.contains("GST ")) {
                    Double taxValue = taxSplit(tax);
                    Double item_tax = (item_amount * taxValue) / 100;
                    Double taxInclude = total + item_tax;
                    viewHolder.mItemTotal.setText("₹ " + String.format("%.2f", taxInclude));
                    setTotal(String.valueOf(item_tax), true, 0.0, 0.0, tax);
                } else if (Preferences.getInstance(context).getPos_sale_type().contains("GST-MultiRate")) {
                    Double taxValue = taxSplit(tax);
                    Double gst = item_amount * taxValue / 100;
                    viewHolder.mItemTotal.setText("₹ " + String.format("%.2f", s));
                    setTotal(String.valueOf(item_amount), true, gst, taxValue, tax);
                } else {
                    viewHolder.mItemTotal.setText("₹ " + String.format("%.2f", s));
                    setTotal(String.valueOf(item_amount), true, 0.0, 0.0, tax);
                }
            }
        });

        viewHolder.decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInteger = Integer.parseInt(viewHolder.mQuantity.getText().toString());


                if (mInteger > 0) {
                    mInteger = mInteger - 1;
                    viewHolder.mQuantity.setText("" + mInteger);

                    String arr = viewHolder.mItemTotal.getText().toString();
                    String[] arr1 = arr.split("₹ ");
                    Double total = Double.valueOf(arr1[1]);
                    String arr2 = viewHolder.mItemAmount.getText().toString();
                    String[] arr3 = arr2.split("₹ ");
                    Double item_amount = Double.valueOf(arr3[1]);

                    Map map = mListMap.get(position);
                    Double s = total - item_amount;
                    if (Preferences.getInstance(context).getPos_sale_type().contains("GST-ItemWise") && tax.contains("GST ")) {
                        String tax = (String) map.get("tax");
                        Double taxValue = taxSplit(tax);
                        Double item_tax = (item_amount * taxValue) / 100;
                        Double taxInclude = total - item_tax;
                        viewHolder.mItemTotal.setText("₹ " + String.format("%.2f", taxInclude));
                        setTotal(String.valueOf(item_tax), false, 0.0, 0.0, tax);
                    } else if (Preferences.getInstance(context).getPos_sale_type().contains("GST-MultiRate") && tax.contains("GST ")) {
                        String tax = (String) map.get("tax");
                        Double taxValue = taxSplit(tax);
                        Double gst = item_amount * taxValue / 100;
                        viewHolder.mItemTotal.setText("₹ " + String.format("%.2f", s));
                        setTotal(String.valueOf(item_amount), false, gst, taxValue, tax);
                    } else {
                        viewHolder.mItemTotal.setText("₹ " + String.format("%.2f", s));
                        setTotal(String.valueOf(item_amount), false, 0.0, 0.0, tax);
                    }

                    if (mInteger==0){
                        appUser.mListMapForItemSale.remove(position);
                        // appUser.billsundrytotal.set(position,"0.0");
                        LocalRepositories.saveAppUser(context,appUser);
                        mListMap.remove(position);
                        notifyDataSetChanged();
                    }

                  //  EventBus.getDefault().post(new EventForPos("true"));
                }
            }
        });
    }

    public void setTotal(String amount, Boolean mBool, Double gst, Double taxValue, String tax) {
        Double total = 0.0,grandTotal = 0.0;
        if (mBool) {
            total = getTotal(PosItemAddActivity.mSubtotal.getText().toString()) + Double.valueOf(amount);
            grandTotal = getTotal(PosItemAddActivity.grand_total.getText().toString()) + Double.valueOf(amount);
        } else {
            total = getTotal(PosItemAddActivity.mSubtotal.getText().toString()) - Double.valueOf(amount);
            grandTotal = getTotal(PosItemAddActivity.grand_total.getText().toString()) - Double.valueOf(amount);
        }
       /* if(Preferences.getInstance(context).getPos_sale_type().contains("GST-MultiRate") && tax.contains("GST ")){
            setMultiRateTaxChange(gst,taxValue);
            granTotal(total, gst);
        }else {*/
        PosItemAddActivity.mSubtotal.setText("₹ " + String.format("%.2f", total));
        PosItemAddActivity.grand_total.setText("₹ " + String.format("%.2f", grandTotal));
        //setTaxChange(context, total, gst, taxValue, tax, mBool);
        appUser = LocalRepositories.getAppUser(context);
        if (appUser.mListMapForBillSale.size()>0){
            // new PosItemAddActivity().billCalculation((subtotal+tax),true);
           // String subTotal = String.valueOf(total+ taxSplit(String.valueOf(tax)));
            String subTotal = String.valueOf(total);
            EventBus.getDefault().post(new EventForPos(subTotal));
        }else {
            granTotal(total, 0.00/*taxSplit(tax)*/);
        }
    }

    public Double getTotal(String total) {
        String[] arr = total.split("₹ ");
        Double a = Double.valueOf(arr[1].trim());
        return a;
    }

    public static void setTaxChange(Context context, Double subtotal, Double gst, Double taxValueInt, String tax1, Boolean mBool) {
        Double tax = 0.0;
        Double mainGrandTotal = subtotal;
        AppUser appUser = LocalRepositories.getAppUser(context);
        if (Preferences.getInstance(context).getPos_sale_type() != null && !Preferences.getInstance(context).getPos_sale_type().equals("")) {
            String taxString = Preferences.getInstance(context).getPos_sale_type();
            String taxName = "";
            String taxValue = "";
            if (taxString.startsWith("I") && taxString.endsWith("%")) {
               // subtotal = getMultiRateTaxChange(PosItemAddActivity.grand_total.getText().toString());
               /* String arrTaxString[] = taxString.split("-");
                taxName = arrTaxString[0].trim();
                taxValue = arrTaxString[1].trim();
                PosItemAddActivity.igst_layout.setVisibility(View.VISIBLE);
                PosItemAddActivity.sgst_cgst_layout.setVisibility(View.GONE);
                String[] arr = taxValue.split("%");
                String s = arr[0];
                if (!s.equals("")) {
                    tax = (subtotal * Double.valueOf(s)) / 100;
                    PosItemAddActivity.igst.setText("₹ " + String.format("%.2f", tax));
                }*/
            } else if (taxString.startsWith("L") && taxString.endsWith("%")) {
              //  subtotal = getMultiRateTaxChange(PosItemAddActivity.grand_total.getText().toString());
               /* String arrTaxString[] = taxString.split("-");
                taxName = arrTaxString[0].trim();
                taxValue = arrTaxString[1].trim();
                PosItemAddActivity.igst_layout.setVisibility(View.GONE);
                PosItemAddActivity.sgst_cgst_layout.setVisibility(View.VISIBLE);
                String[] arr = taxValue.split("%");
                String s = arr[0];
                if (!s.equals("")) {
                    tax = (subtotal * Double.valueOf(s)) / 100;
                    PosItemAddActivity.sgst.setText("₹ " + String.format("%.2f", tax / 2));
                    PosItemAddActivity.cgst.setText("₹ " + String.format("%.2f", tax / 2));
                    appUser.billsundrytotal.add(""+tax/2);
                    appUser.billsundrytotal.add(""+tax/2);
                    LocalRepositories.saveAppUser(context,appUser);
                }*/
            } else if (taxString.contains("GST-MultiRate")) {
                if (taxString.contains("I/GST-MultiRate")) {
                    if (tax1.contains("GST ")) {
                        if (taxValueInt == 12) {
                            Double gst_total = getMultiRateTaxChange(PosItemAddActivity.igst_12.getText().toString());
                            Double plusMinus = gstPlusMinus(gst_total, gst, mBool);
                            PosItemAddActivity.igst_12.setText("₹ " + String.format("%.2f", (plusMinus)));
                            tax = tax + plusMinus;
                        } else if (taxValueInt == 18) {
                            Double gst_total = getMultiRateTaxChange(PosItemAddActivity.igst_18.getText().toString());
                            Double plusMinus = gstPlusMinus(gst_total, gst, mBool);
                            PosItemAddActivity.igst_18.setText("₹ " + String.format("%.2f", (plusMinus)));
                            tax = tax + plusMinus;
                        } else if (taxValueInt == 28) {
                            Double gst_total = getMultiRateTaxChange(PosItemAddActivity.igst_28.getText().toString());
                            Double plusMinus = gstPlusMinus(gst_total, gst, mBool);
                            PosItemAddActivity.igst_28.setText("₹ " + String.format("%.2f", (plusMinus)));
                            tax = tax + plusMinus;
                        } else if (taxValueInt == 5) {
                            Double gst_total = getMultiRateTaxChange(PosItemAddActivity.igst_5.getText().toString());
                            Double plusMinus = gstPlusMinus(gst_total, gst, mBool);
                            PosItemAddActivity.igst_5.setText("₹ " + String.format("%.2f", (plusMinus)));
                            tax = tax + plusMinus;
                        }
                    }else {
                        subtotal = subtotal + getMultiRateTaxChange(PosItemAddActivity.igst_12.getText().toString())
                                +getMultiRateTaxChange(PosItemAddActivity.igst_18.getText().toString())
                                        +getMultiRateTaxChange(PosItemAddActivity.igst_28.getText().toString())
                                                +getMultiRateTaxChange(PosItemAddActivity.igst_5.getText().toString());
                    }
                } else {
                    if (tax1.contains("GST ")) {
                        if (taxValueInt == 12) {
                            Double sgst_total = getMultiRateTaxChange(PosItemAddActivity.sgst_12.getText().toString());
                            Double cgst_total = getMultiRateTaxChange(PosItemAddActivity.cgst_12.getText().toString());
                            Double sgstPlusMinus = gstPlusMinus(sgst_total, gst / 2, mBool);
                            Double cgstPlusMinus = gstPlusMinus(cgst_total, gst / 2, mBool);
                            PosItemAddActivity.sgst_12.setText("₹ " + String.format("%.2f", (sgstPlusMinus)));
                            PosItemAddActivity.cgst_12.setText("₹ " + String.format("%.2f", (cgstPlusMinus)));
                            tax = tax + sgstPlusMinus + cgstPlusMinus;
                        } else if (taxValueInt == 18) {
                            Double sgst_total = getMultiRateTaxChange(PosItemAddActivity.sgst_18.getText().toString());
                            Double cgst_total = getMultiRateTaxChange(PosItemAddActivity.cgst_18.getText().toString());
                            Double sgstPlusMinus = gstPlusMinus(sgst_total, gst / 2, mBool);
                            Double cgstPlusMinus = gstPlusMinus(cgst_total, gst / 2, mBool);
                            PosItemAddActivity.sgst_18.setText("₹ " + String.format("%.2f", (sgstPlusMinus)));
                            PosItemAddActivity.cgst_18.setText("₹ " + String.format("%.2f", (cgstPlusMinus)));
                            tax = tax + sgstPlusMinus + cgstPlusMinus;
                        } else if (taxValueInt == 28) {
                            Double sgst_total = getMultiRateTaxChange(PosItemAddActivity.sgst_28.getText().toString());
                            Double cgst_total = getMultiRateTaxChange(PosItemAddActivity.cgst_28.getText().toString());
                            Double sgstPlusMinus = gstPlusMinus(sgst_total, gst / 2, mBool);
                            Double cgstPlusMinus = gstPlusMinus(cgst_total, gst / 2, mBool);
                            PosItemAddActivity.sgst_28.setText("₹ " + String.format("%.2f", (sgstPlusMinus)));
                            PosItemAddActivity.cgst_28.setText("₹ " + String.format("%.2f", (cgstPlusMinus)));
                            tax = tax + sgstPlusMinus + cgstPlusMinus;
                        } else if (taxValueInt == 5) {
                            Double sgst_total = getMultiRateTaxChange(PosItemAddActivity.sgst_5.getText().toString());
                            Double cgst_total = getMultiRateTaxChange(PosItemAddActivity.cgst_5.getText().toString());
                            Double sgstPlusMinus = gstPlusMinus(sgst_total, gst / 2, mBool);
                            Double cgstPlusMinus = gstPlusMinus(cgst_total, gst / 2, mBool);
                            PosItemAddActivity.sgst_5.setText("₹ " + String.format("%.2f", (sgstPlusMinus)));
                            PosItemAddActivity.cgst_5.setText("₹ " + String.format("%.2f", (cgstPlusMinus)));
                            tax = tax + sgstPlusMinus + cgstPlusMinus;
                        }
                    }else {
                        subtotal = subtotal + getMultiRateTaxChange(PosItemAddActivity.sgst_12.getText().toString())
                                +getMultiRateTaxChange(PosItemAddActivity.cgst_12.getText().toString())
                                + getMultiRateTaxChange(PosItemAddActivity.sgst_18.getText().toString())
                                +getMultiRateTaxChange(PosItemAddActivity.cgst_18.getText().toString())
                                + getMultiRateTaxChange(PosItemAddActivity.sgst_28.getText().toString())
                                +getMultiRateTaxChange(PosItemAddActivity.cgst_28.getText().toString())
                                + getMultiRateTaxChange(PosItemAddActivity.sgst_5.getText().toString())
                                +getMultiRateTaxChange(PosItemAddActivity.cgst_5.getText().toString());
                    }
                }
            } else {
                PosItemAddActivity.igst_layout.setVisibility(View.GONE);
                PosItemAddActivity.sgst_cgst_layout.setVisibility(View.GONE);
            }
        }
        appUser = LocalRepositories.getAppUser(context);
        if (appUser.mListMapForBillSale.size()>0){
          // new PosItemAddActivity().billCalculation((subtotal+tax),true);
            String subTotal = String.valueOf(subtotal+tax);
            EventBus.getDefault().post(new EventForPos(subTotal));
        }else {
            granTotal(subtotal, tax);
        }
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
        if (total.equals("0.0")){
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
