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
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
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
        mItemAmount.setText("₹ " + String.format("%.2f", item_amount));
        mItemTotal.setText("₹ " + String.format("%.2f", total));

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
                Double s = total + item_amount;
                if (Preferences.getInstance(context).getPos_sale_type().contains("GST-ItemWise") && tax.contains("GST ")) {
                    Double taxValue = taxSplit(tax);
                    Double item_tax = (item_amount * taxValue) / 100;
                    Double taxInclude = total + item_tax;
                    mItemTotal.setText("₹ " + String.format("%.2f", taxInclude));
                    setTotal(String.valueOf(item_tax), true, 0.0, 0.0, tax);
                } else if (Preferences.getInstance(context).getPos_sale_type().contains("GST-MultiRate")) {
                    Double taxValue = taxSplit(tax);
                    Double gst = item_amount * taxValue / 100;
                    mItemTotal.setText("₹ " + String.format("%.2f", s));
                    setTotal(String.valueOf(item_amount), true, gst, taxValue, tax);
                } else {
                    mItemTotal.setText("₹ " + String.format("%.2f", s));
                    setTotal(String.valueOf(item_amount), true, 0.0, 0.0, tax);
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
                    Double s = total - item_amount;
                    if (Preferences.getInstance(context).getPos_sale_type().contains("GST-ItemWise") && tax.contains("GST ")) {
                        String tax = (String) map.get("tax");
                        Double taxValue = taxSplit(tax);
                        Double item_tax = (item_amount * taxValue) / 100;
                        Double taxInclude = total - item_tax;
                        mItemTotal.setText("₹ " + String.format("%.2f", taxInclude));
                        setTotal(String.valueOf(item_tax), false, 0.0, 0.0, tax);
                    } else if (Preferences.getInstance(context).getPos_sale_type().contains("GST-MultiRate") && tax.contains("GST ")) {
                        String tax = (String) map.get("tax");
                        Double taxValue = taxSplit(tax);
                        Double gst = item_amount * taxValue / 100;
                        mItemTotal.setText("₹ " + String.format("%.2f", s));
                        setTotal(String.valueOf(item_amount), false, gst, taxValue, tax);
                    } else {
                        mItemTotal.setText("₹ " + String.format("%.2f", s));
                        setTotal(String.valueOf(item_amount), false, 0.0, 0.0, tax);
                    }
                }
            }
        });
        return convertView;
    }

    public void setTotal(String amount, Boolean mBool, Double gst, Double taxValue, String tax) {
        Double total = 0.0;
        if (mBool) {
            total = getTotal() + Double.valueOf(amount);
        } else {
            total = getTotal() - Double.valueOf(amount);
        }
       /* if(Preferences.getInstance(context).getPos_sale_type().contains("GST-MultiRate") && tax.contains("GST ")){
            setMultiRateTaxChange(gst,taxValue);
            granTotal(total, gst);
        }else {*/
        setTaxChange(context, total, gst, taxValue, tax, mBool);
        PosItemAddActivity.mSubtotal.setText("₹ " + String.format("%.2f", total));
    }

    public Double getTotal() {
        String total = PosItemAddActivity.mSubtotal.getText().toString();
        String[] arr = total.split("₹ ");
        Double a = Double.valueOf(arr[1].trim());
        return a;
    }

    public static void setTaxChange(Context context, Double subtotal, Double gst, Double taxValueInt, String tax1, Boolean mBool) {
        Double tax = 0.0;
        if (Preferences.getInstance(context).getPos_sale_type() != null && !Preferences.getInstance(context).getPos_sale_type().equals("")) {
            String taxString = Preferences.getInstance(context).getPos_sale_type();
            String taxName = "";
            String taxValue = "";
            if (taxString.startsWith("I") && taxString.endsWith("%")) {
                String arrTaxString[] = taxString.split("-");
                taxName = arrTaxString[0].trim();
                taxValue = arrTaxString[1].trim();
                PosItemAddActivity.igst_layout.setVisibility(View.VISIBLE);
                PosItemAddActivity.sgst_cgst_layout.setVisibility(View.GONE);
                String[] arr = taxValue.split("%");
                String s = arr[0];
                if (!s.equals("")) {
                    tax = (subtotal * Double.valueOf(s)) / 100;
                    PosItemAddActivity.igst.setText("₹ " + String.format("%.2f", tax));
                }
            } else if (taxString.startsWith("L") && taxString.endsWith("%")) {
                String arrTaxString[] = taxString.split("-");
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
                }
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
}
