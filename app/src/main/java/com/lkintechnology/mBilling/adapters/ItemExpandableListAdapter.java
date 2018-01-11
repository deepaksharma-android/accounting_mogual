package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;

import com.lkintechnology.mBilling.utils.EventDeleteItem;
import com.lkintechnology.mBilling.utils.EventEditItem;
import com.lkintechnology.mBilling.utils.EventSaleAddItem;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;

public class ItemExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ItemExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    String childName;
    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        String arr[]=childText.split(",");
        String name =arr[0];
        String quantity =arr[1];
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_for_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);

        txtListChild.setText(name+" (qty:"+quantity+")");
        LinearLayout delete=(LinearLayout) convertView.findViewById(R.id.delete_icon);
        LinearLayout edit=(LinearLayout) convertView.findViewById(R.id.edit_icon);
        LinearLayout mainLayout=(LinearLayout) convertView.findViewById(R.id.main_layout);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=groupPosition+","+childPosition;
                EventBus.getDefault().post(new EventDeleteItem(id));
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id=groupPosition+","+childPosition;
                EventBus.getDefault().post(new EventEditItem(id));

            }
        });
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=groupPosition+","+childPosition;
                EventBus.getDefault().post(new EventSaleAddItem(id));
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
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
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}