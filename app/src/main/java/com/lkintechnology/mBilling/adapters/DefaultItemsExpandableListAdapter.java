package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultItemsExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> _listDataHeader;
    private HashMap<String,List<String>> _listDataChild;
    public static List<String> listDataChildId = new ArrayList<>();
    AppUser appuser;

    public DefaultItemsExpandableListAdapter(Context context, List<String> _listDataHeader, HashMap<String,List<String>> _listDataChild){

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

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_sync_with_items, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader1);

        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

       /* TextView lblListAmount = (TextView) convertView.findViewById(R.id.lblListHeader2);
        TextView lblListQuantity = (TextView) convertView.findViewById(R.id.lblListHeader3);
        lblListAmount.setText("₹ " + ""+String.format("%.2f",addAmount.get(groupPosition)));
        lblListQuantity.setText("qty: " + ""+addQuantity.get(groupPosition));*/

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
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String nameAmount = (String) getChild(groupPosition, childPosition);
        String[] strArr=nameAmount.split(",");
        String name = strArr[0];
       // String undefined=strArr[1];
        String childId = strArr[1];
        appuser = LocalRepositories.getAppUser(context);


        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_sync_with_items,null);
        }
        TextView lblListItem1 = (TextView) convertView.findViewById(R.id.lblListItem1);
        CheckBox chkBox1 = (CheckBox) convertView.findViewById(R.id.checkbox1);

        lblListItem1.setTypeface(null, Typeface.BOLD);
        lblListItem1.setText(name);

        chkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //appuser.listDataChildId=new ArrayList<String>();
                if(chkBox1.isChecked()){
                    listDataChildId.add(childId);
                }else {
                    for(int i=0;i<listDataChildId.size();i++){
                        if(listDataChildId.get(i)==childId){
                            listDataChildId.remove(childId);
                        }
                    }
                }
            }
        });

       /* if (undefined.equals("true")) {
            delete.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
        }*/

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