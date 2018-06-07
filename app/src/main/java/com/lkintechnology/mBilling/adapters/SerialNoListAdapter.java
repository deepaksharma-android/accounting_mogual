package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.transaction.barcode.CheckBoxVoucherBarcodeActivity;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by manjoor on 5/22/2018.
 */

public class SerialNoListAdapter extends RecyclerView.Adapter<SerialNoListAdapter.MyViewHolders> {
    private Context context;
    private ArrayList arrayList;
    private ArrayList serialNoList, serialNoPurchaseReturn;
    private int quantity, locQuantity = 0;
    private String tempSaleNo;
    private boolean fromPurchaseReturn, frombillitemvoucherlist;
    // public static ArrayList checkArrayList;


    public SerialNoListAdapter(Context context, ArrayList arrayList, ArrayList serialNoList, String quantity, boolean frombillitemvoucherlist, String tempSaleNo) {
        this.arrayList = arrayList;
        this.context = context;
        this.serialNoList = serialNoList;
        this.quantity = Integer.valueOf(quantity);
        this.tempSaleNo = tempSaleNo;
        this.frombillitemvoucherlist = frombillitemvoucherlist;

        //this.images=images;
    }

    public SerialNoListAdapter(Context context, ArrayList arrayList, ArrayList serialNoPurchaseReturn, String quantity, boolean fromPurchaseReturn) {
        this.arrayList = arrayList;
        this.context = context;
        this.serialNoPurchaseReturn = serialNoPurchaseReturn;
        this.quantity = Integer.valueOf(quantity);
        this.fromPurchaseReturn = fromPurchaseReturn;
        //this.images=images;
    }

    @Override
    public MyViewHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.serial_no_row, parent, false);
        MyViewHolders myViewHolders = new MyViewHolders(view);
        return myViewHolders;

    }

    @Override
    public void onBindViewHolder(MyViewHolders holder, int position) {
        if (fromPurchaseReturn == true) {

            if (serialNoPurchaseReturn.get(position) != null && serialNoPurchaseReturn.get(position).equals("true")) {
                holder.checkBox.setChecked(true);
                //locQuantity=serialNoPurchaseReturn.size();
            } else {
                holder.checkBox.setChecked(false);
            }
            if (arrayList.get(position).equals("")) {
                CheckBoxVoucherBarcodeActivity.barcodeMessage.setVisibility(View.VISIBLE);
                CheckBoxVoucherBarcodeActivity.mSubmit.setVisibility(View.GONE);
                CheckBoxVoucherBarcodeActivity.serialNo.setVisibility(View.GONE);
            } else {
                CheckBoxVoucherBarcodeActivity.serialNo.setVisibility(View.VISIBLE);
                holder.itemName.setText((CharSequence) arrayList.get(position));
                CheckBoxVoucherBarcodeActivity.barcodeMessage.setVisibility(View.GONE);
                CheckBoxVoucherBarcodeActivity.mSubmit.setVisibility(View.VISIBLE);

            }


            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (serialNoPurchaseReturn.get(position).equals("true")) {
                        serialNoPurchaseReturn.remove(position);
                        serialNoPurchaseReturn.add(position, "false");
                        locQuantity--;
                        notifyDataSetChanged();
                    } else {
                        serialNoPurchaseReturn.remove(position);
                        serialNoPurchaseReturn.add(position, "true");
                        locQuantity++;
                        notifyDataSetChanged();
                    }
                    if (locQuantity > quantity) {
                        Toast.makeText(context, "Quantity exceeds!", Toast.LENGTH_SHORT).show();
                        serialNoPurchaseReturn.remove(position);
                        serialNoPurchaseReturn.add(position, "false");
                        locQuantity--;
                        notifyDataSetChanged();

                    }
                }
            });
        } else {
            if (serialNoList.get(position) != null && serialNoList.get(position).equals("true")) {
                holder.checkBox.setChecked(true);
                //locQuantity=serialNoList.size();
            } else {
                holder.checkBox.setChecked(false);
            }

            if (arrayList.get(position).equals("")) {
                CheckBoxVoucherBarcodeActivity.barcodeMessage.setVisibility(View.VISIBLE);
                CheckBoxVoucherBarcodeActivity.mSubmit.setVisibility(View.GONE);
                CheckBoxVoucherBarcodeActivity.serialNo.setVisibility(View.GONE);
            } else {
                CheckBoxVoucherBarcodeActivity.serialNo.setVisibility(View.VISIBLE);
                holder.itemName.setText((CharSequence) arrayList.get(position));
                CheckBoxVoucherBarcodeActivity.barcodeMessage.setVisibility(View.GONE);
                CheckBoxVoucherBarcodeActivity.mSubmit.setVisibility(View.VISIBLE);

            }

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (serialNoList.get(position).equals("true")) {
                        serialNoList.remove(position);
                        serialNoList.add(position, "false");
                        locQuantity--;
                        notifyDataSetChanged();
                    } else {
                        serialNoList.remove(position);
                        serialNoList.add(position, "true");
                        locQuantity++;
                        notifyDataSetChanged();
                    }
                    if (locQuantity > quantity) {
                        Toast.makeText(context, "Quantity exceeds!", Toast.LENGTH_SHORT).show();
                        serialNoList.remove(position);
                        serialNoList.add(position, "false");
                        locQuantity--;
                        notifyDataSetChanged();

                    }
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return this.arrayList.size();
    }

    public class MyViewHolders extends RecyclerView.ViewHolder {
        @Bind(R.id.rowSerialNoItem)
        TextView itemName;
        @Bind(R.id.checkBox)
        CheckBox checkBox;
        @Bind(R.id.main_row)
        RelativeLayout mainRow;

        public MyViewHolders(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
