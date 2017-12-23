package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.utils.EventDeleteAccount;
import com.berylsystems.buzz.utils.EventEditAccount;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TransactionBankAdapter extends BaseExpandableListAdapter{

    private Context context;
    private List<String> _listDataHeader;
    private HashMap<String,List<String>> _listDataChild;
    private ArrayList addAmount;

    public TransactionBankAdapter(Context context,List<String> _listDataHeader,HashMap<String,List<String>> _listDataChild, ArrayList addAmount){

        this.context=context;
        this._listDataHeader=_listDataHeader;
        this._listDataChild=_listDataChild;
        this.addAmount=addAmount;
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

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_transaction_cash_in_hand, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader1);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        TextView lblListAmount = (TextView) convertView.findViewById(R.id.lblListHeader2);

        //lblListAmount.setText("₹ " + ""+addAmount.get(groupPosition));

        lblListAmount.setText("₹ " + ""+String.format("%.2f", addAmount.get(groupPosition)));

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
        String undefined=strArr[1];
        String amount = strArr[2];

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_transaction_cash_in_hand,null);
        }
        TextView lblListItem1 = (TextView) convertView.findViewById(R.id.lblListItem1);
        TextView lblListItem2 = (TextView) convertView.findViewById(R.id.lblListItem2);
        lblListItem1.setTypeface(null, Typeface.BOLD);
        //lblListHeader2.setTypeface(null, Typeface.BOLD);
        lblListItem1.setText(name);
        lblListItem2.setText("₹ " + String.format("%.2f",Double.valueOf(amount)));

       // LinearLayout delete = (LinearLayout) convertView.findViewById(R.id.delete_icon);
       // LinearLayout edit = (LinearLayout) convertView.findViewById(R.id.edit_icon);
       // LinearLayout mMainLayout = (LinearLayout) convertView.findViewById(R.id.main_layout);

       /* if (undefined.equals("true")) {
            delete.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
        }*/

       /* delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = groupPosition + "," +childPosititon;
                EventBus.getDefault().post(new EventDeleteAccount(id));
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = groupPosition + "," + childPosititon;
                EventBus.getDefault().post(new EventEditAccount(id));
            }
        });*/

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