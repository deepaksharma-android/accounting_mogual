package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.sale.SaleVoucherAddBillActivity;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by BerylSystems on 11/25/2017.
 */

public class AddBillsVoucherAdapter extends BaseAdapter {

    Context context;
    List<Map<String, String>> mListMap;
    List<Map<String, String>> mListItemMap;

    public AddBillsVoucherAdapter(Context context, List<Map<String, String>> mListMap, List<Map<String, String>> mListItemMap) {
        this.context = context;
        this.mListMap = mListMap;
        this.mListItemMap = mListItemMap;

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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bill_details, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Map map = mListMap.get(position);
        String itemName = (String) map.get("courier_charges");
        String amount = (String) map.get("amount");
        double amt = Double.parseDouble(amount);
        String fedas = (String) map.get("fed_as");
        String type = (String) map.get("type");
        double billsundrymamount = 0.0;


        if (fedas.equals("Absolute Amount")) {
            if (type.equals("Additive")) {
                billsundrymamount = billsundrymamount + amt;
            } else {
                billsundrymamount = billsundrymamount - amt;
            }
        } else if (fedas.equals("Per Main Qty.")) {
            if (mListItemMap.size() > 0) {
                for (int j = 0; j < mListItemMap.size(); j++) {
                    Map mapj = mListItemMap.get(j);
                    double subtot = 0.0;
                    String price_selected_unit = (String) mapj.get("price_selected_unit");
                    if (price_selected_unit.equals("main")) {
                        String quantity = (String) mapj.get("quantity");
                        int quan = Integer.parseInt(quantity);
                        subtot = subtot + (amt * quan);
                        if (type.equals("Additive")) {
                            billsundrymamount = billsundrymamount + subtot;
                        } else {
                            billsundrymamount = billsundrymamount - subtot;
                        }

                    } else if (price_selected_unit.equals("alternate")) {
                        String alternate_unit_con_factor = (String) mapj.get("alternate_unit_con_factor");
                        if (alternate_unit_con_factor.equals("") || alternate_unit_con_factor.equals("0.0")) {
                            alternate_unit_con_factor = "1.0";
                        }
                        double con_factor = Double.parseDouble(alternate_unit_con_factor);
                        String quantity = (String) mapj.get("quantity");
                        int quan = Integer.parseInt(quantity);
                        subtot = subtot + ((amt * quan) / con_factor);
                        if (type.equals("Additive")) {
                            billsundrymamount = billsundrymamount + subtot;
                        } else {
                            billsundrymamount = billsundrymamount - subtot;
                        }
                    } else {
                        String quantity = (String) mapj.get("quantity");
                        String packaging_unit_con_factor = (String) mapj.get("packaging_unit_con_factor");
                        if (packaging_unit_con_factor.equals("") || packaging_unit_con_factor.equals("0.0")) {
                            packaging_unit_con_factor = "1.0";
                        }
                        double packaging_con = Double.parseDouble(packaging_unit_con_factor);
                        int quan = Integer.parseInt(quantity);
                        subtot = subtot + (amt * quan * packaging_con);
                        if (type.equals("Additive")) {
                            billsundrymamount = billsundrymamount + subtot;
                        } else {
                            billsundrymamount = billsundrymamount - subtot;
                        }
                    }
                }

            } else {
                billsundrymamount = 0.0;


            }

        } else if (fedas.equals("Per Alt. Qty.")) {
            if (mListItemMap.size() > 0) {
                for (int j = 0; j < mListItemMap.size(); j++) {
                    Map mapj = mListItemMap.get(j);
                    double subtot = 0.0;
                    String price_selected_unit = (String) mapj.get("price_selected_unit");
                    String alternate_unit_con_factor = (String) mapj.get("alternate_unit_con_factor");
                    if (alternate_unit_con_factor.equals("0.0") || alternate_unit_con_factor.equals("")) {
                        alternate_unit_con_factor = "1.0";
                    }
                    double con_factor = Double.parseDouble(alternate_unit_con_factor);
                    if (price_selected_unit.equals("main")) {
                        String quantity = (String) mapj.get("quantity");
                        int quan = Integer.parseInt(quantity);
                        subtot = subtot + (amt * quan * con_factor);
                        if (type.equals("Additive")) {
                            billsundrymamount = billsundrymamount + subtot;
                        } else {
                            billsundrymamount = billsundrymamount - subtot;
                        }

                    } else if (price_selected_unit.equals("alternate")) {
                        String quantity = (String) mapj.get("quantity");
                        int quan = Integer.parseInt(quantity);
                        subtot = subtot + (amt * quan);
                        if (type.equals("Additive")) {
                            billsundrymamount = billsundrymamount + subtot;
                        } else {
                            billsundrymamount = billsundrymamount - subtot;
                        }
                    } else {

                        String quantity = (String) mapj.get("quantity");
                        String packaging_unit_con_factor = (String) mapj.get("packaging_unit_con_factor");
                        if (packaging_unit_con_factor.equals("") || packaging_unit_con_factor.equals("0.0")) {
                            packaging_unit_con_factor = "1.0";
                        }
                        double packaging_con = Double.parseDouble(packaging_unit_con_factor);
                        int quan = Integer.parseInt(quantity);
                        subtot = subtot + (amt * quan * packaging_con * con_factor);
                        if (type.equals("Additive")) {
                            billsundrymamount = billsundrymamount + subtot;
                        } else {
                            billsundrymamount = billsundrymamount - subtot;
                        }
                    }
                }

            } else {
                billsundrymamount = 0.0;

            }
        } else if (fedas.equals("Per Packaging Qty.")) {
            if (mListItemMap.size() > 0) {
                for (int j = 0; j < mListItemMap.size(); j++) {
                    Map mapj = mListItemMap.get(j);
                    double subtot = 0.0;
                    String price_selected_unit = (String) mapj.get("price_selected_unit");
                    String alternate_unit_con_factor = (String) mapj.get("alternate_unit_con_factor");
                    if (alternate_unit_con_factor.equals("") || alternate_unit_con_factor.equals("0.0")) {
                        alternate_unit_con_factor = "1.0";
                    }
                    double con_factor = Double.parseDouble(alternate_unit_con_factor);
                    if (price_selected_unit.equals("main")) {
                        String quantity = (String) mapj.get("quantity");
                        int quan = Integer.parseInt(quantity);
                        subtot = subtot + (amt * quan * con_factor);
                        if (type.equals("Additive")) {
                            billsundrymamount = billsundrymamount + subtot;
                        } else {
                            billsundrymamount = billsundrymamount - subtot;
                        }

                    } else if (price_selected_unit.equals("alternate")) {
                        String quantity = (String) mapj.get("quantity");
                        int quan = Integer.parseInt(quantity);
                        subtot = subtot + (amt * quan);
                        if (type.equals("Additive")) {
                            billsundrymamount = billsundrymamount + subtot;
                        } else {
                            billsundrymamount = billsundrymamount - subtot;
                        }
                    } else {
                        String quantity = (String) mapj.get("quantity");
                        String packaging_unit_con_factor = (String) mapj.get("packaging_unit_con_factor");
                        if (packaging_unit_con_factor.equals("") || packaging_unit_con_factor.equals("0.0")) {
                            packaging_unit_con_factor = "1.0";
                        }
                        double packaging_con = Double.parseDouble(packaging_unit_con_factor);
                        int quan = Integer.parseInt(quantity);
                        subtot = subtot + ((amt * quan) / (packaging_con * con_factor));
                        if (type.equals("Additive")) {
                            billsundrymamount = billsundrymamount + subtot;
                        } else {
                            billsundrymamount = billsundrymamount - subtot;
                        }
                    }
                }

            } else {
                billsundrymamount = 0.0;


            }
        }
        holder.mTotal.setText(String.valueOf(billsundrymamount));
        holder.mItemName.setText(itemName);
        holder.mDiscount.setText(amount);
        return convertView;
    }

    static class ViewHolder {
        @Bind(R.id.item_name)
        TextView mItemName;
        @Bind(R.id.discount)
        TextView mDiscount;
        @Bind(R.id.total)
        TextView mTotal;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
