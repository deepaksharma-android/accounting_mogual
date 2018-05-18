package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.transaction.SaleVouchersItemDetailsListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SaleVouchersItemAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> _listDataHeader;
    private HashMap<String,List<String>> _listDataChild;
    AppUser appUser;

    public SaleVouchersItemAdapter(Context context, List<String> _listDataHeader, HashMap<String,List<String>> _listDataChild){

        this.context=context;
        this._listDataHeader=_listDataHeader;
        this._listDataChild=_listDataChild;
    }

    @Override
    public Object getGroup(int groupPosition) {

        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {

        return groupPosition;
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String headerTitle = (String) getGroup(groupPosition);
        String arr[] = headerTitle.split(",");
        String groupName = arr[0];
        String amount = arr[1];
        String quantity = arr[2];

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_transaction_stock_in_hand, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader1);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(groupName);

        TextView lblListAmount = (TextView) convertView.findViewById(R.id.lblListHeader2);
        TextView lblListQuantity = (TextView) convertView.findViewById(R.id.lblListHeader3);
        lblListAmount.setText("₹ " + ""+String.format("%.2f",Double.parseDouble(amount)));
        lblListQuantity.setText("qty: " + ""+quantity);

        ImageView imageview=(ImageView)convertView.findViewById(R.id.image);
        if(isExpanded){
            imageview.setImageResource(R.drawable.up_arrow);
        }
        else{
            imageview.setImageResource(R.drawable.down_arrow);
        }

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);

    }
    @Override
    public long getChildId(int groupPosition, int childPosititon) {

        return childPosititon;

    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
    }
    @Override
    public View getChildView(int groupPosition, int childPosititon, boolean isLastChild, View convertView, ViewGroup parent) {

        final String nameAmount = (String) getChild(groupPosition, childPosititon);
        String[] strArr=nameAmount.split(",");
        String name = strArr[0];
       // String undefined=strArr[1];
        String amount = strArr[1];
        String quantity =strArr[2];
        String itemId =strArr[3];

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_transaction_stock_in_hand,null);
        }
        TextView lblListItem1 = (TextView) convertView.findViewById(R.id.lblListItem1);
        TextView lblListItem2 = (TextView) convertView.findViewById(R.id.lblListItem2);
        TextView lblListItem3 = (TextView) convertView.findViewById(R.id.lblListItem3);
        lblListItem1.setTypeface(null, Typeface.BOLD);
        //lblListHeader2.setTypeface(null, Typeface.BOLD);
        lblListItem1.setText(name);
        lblListItem2.setText("₹ " +String.format("%.2f", Double.valueOf(amount)));
        lblListItem3.setText("qty: " + quantity);

        LinearLayout mMainLayout = (LinearLayout) convertView.findViewById(R.id.main_layout);

        mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appUser = LocalRepositories.getAppUser(context);
                appUser.sale_voucher_item_id = itemId;
                LocalRepositories.saveAppUser(context,appUser);
                context.startActivity(new Intent(context, SaleVouchersItemDetailsListActivity.class));
            }
        });

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {

        return true;
    }
}