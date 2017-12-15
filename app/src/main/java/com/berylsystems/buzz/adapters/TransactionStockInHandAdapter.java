package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.utils.EventAccountChildClicked;
import com.berylsystems.buzz.utils.EventSelectAccountPurchase;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

public class TransactionStockInHandAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> _listDataHeader;
    private HashMap<String,List<String>> _listDataChild;

    public TransactionStockInHandAdapter(Context context,List<String> _listDataHeader,HashMap<String,List<String>> _listDataChild){

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

        String headerAmount = (String) getGroup(groupPosition);
        String[] strArr=headerAmount.split(",");
        String header=strArr[0];
        String amount=strArr[1];

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group_transaction_stock_in_hand,null);
        }
        TextView lblListHeader1 = (TextView) convertView.findViewById(R.id.lblListHeader1);
        TextView lblListHeader2 = (TextView) convertView.findViewById(R.id.lblListHeader1);
        lblListHeader1.setTypeface(null, Typeface.BOLD);
        lblListHeader2.setTypeface(null, Typeface.BOLD);

        lblListHeader1.setText(header);
        lblListHeader2.setText(amount);

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

      /*  final String childText = (String) getChild(groupPosition, childPosititon);
        String arr[] = childText.split(",");
        String acc_name = arr[0];
        String undefined = arr[1];*/

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_transaction_stock_in_hand, null);
        }
        //TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);

        LinearLayout mMainLayout = (LinearLayout) convertView.findViewById(R.id.main_layout);
       /* mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = groupPosition + "," + childPosititon;
                EventBus.getDefault().post(new EventAccountChildClicked(id));
                EventBus.getDefault().post(new EventSelectAccountPurchase(id));
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