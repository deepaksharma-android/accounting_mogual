package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import timber.log.Timber;

public class DefaultItemsExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> _listDataHeader;
    private HashMap<String,List<String>> _listDataChild;
    private HashMap<Integer, List<Integer>> listDataChildId;
    public static List<String> listData ;
//    public static List<String> listData2 = new ArrayList<>();

    private HashMap<Integer, boolean[]> mChildCheckStates;
    private ChildViewHolder childViewHolder;
    private GroupViewHolder groupViewHolder;
    private String groupText;
    private String childText;

    AppUser appuser;
    Boolean flag=true;

    public DefaultItemsExpandableListAdapter(Context context, List<String> _listDataHeader, HashMap<String,List<String>> _listDataChild,HashMap<Integer, List<Integer>> listDataChildId){

        this.context=context;
        this._listDataHeader=_listDataHeader;
        this._listDataChild=_listDataChild;
        this.listDataChildId=listDataChildId;
        mChildCheckStates = new HashMap<Integer, boolean[]>();
        listData = new ArrayList<>();
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

        final int mGroupPosition = groupPosition;
        final int mChildPosition = childPosition;

        final String nameAmount = (String) getChild(groupPosition, childPosition);
        String[] strArr=nameAmount.split(",");
        String name = strArr[0];
        String childId = strArr[1];

        //childText = getChild(mGroupPosition, mChildPosition).getChildText();

        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item_default_items,null);
            childViewHolder = new ChildViewHolder();

            childViewHolder.lblListItem1 = (TextView) convertView.findViewById(R.id.lblListItem1);
            childViewHolder.chkBox1 = (CheckBox) convertView.findViewById(R.id.checkbox1);

            convertView.setTag(R.layout.list_item_default_items, childViewHolder);
        } else {

            childViewHolder = (ChildViewHolder) convertView.getTag(R.layout.list_item_default_items);
        }
        childViewHolder.lblListItem1.setTypeface(null, Typeface.BOLD);
        childViewHolder.lblListItem1.setText(name);


        childViewHolder.chkBox1.setOnCheckedChangeListener(null);
            if (mChildCheckStates.containsKey(mGroupPosition)) {
                boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                childViewHolder.chkBox1.setChecked(getChecked[mChildPosition]);

            } else {
                boolean getChecked[] = new boolean[getChildrenCount(mGroupPosition)];
                mChildCheckStates.put(mGroupPosition, getChecked);
                childViewHolder.chkBox1.setChecked(false);
            }

   // if(mChildPosition!=0){
        childViewHolder.chkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

              /*  if(childPosition==0){
                    if(isChecked){
                        Toast.makeText(context,"Added",Toast.LENGTH_LONG).show();

                    }else{

                        Toast.makeText(context,"Removed",Toast.LENGTH_LONG).show();
                    }
                }
                else {*/

                    if (isChecked) {

                        boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                        getChecked[mChildPosition] = isChecked;
                        mChildCheckStates.put(mGroupPosition, getChecked);
                        listData.add(childId);


                    } else {

                        boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                        getChecked[mChildPosition] = isChecked;
                        mChildCheckStates.put(mGroupPosition, getChecked);
                        listData.remove(childId);

                    }
              //  }
            }
        });
   // }
    /*else {
            childViewHolder.chkBox1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    *//*boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[0] = isChecked;*//*
                    //mChildCheckStates.put(mGroupPosition, getChecked);

                    for(int i = 0;i<_listDataChild.get(_listDataHeader.get(groupPosition)).size();i++){
                        childViewHolder.chkBox1.setChecked(true);
                        listData.add(childId);
                    }
                } else {
                   *//* boolean getChecked[] = mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = isChecked;*//*
                    //mChildCheckStates.put(mGroupPosition, getChecked);
                    for(int i = 0;i<_listDataChild.get(_listDataHeader.get(groupPosition)).size();i++){
                        childViewHolder.chkBox1.setChecked(true);
                        listData.remove(childId);
                    }
                   // listData.remove(childId);

                }
            }
        });
    }*/

       /* childViewHolder.chkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(childViewHolder.chkBox1.isChecked()){
                    listData.add(childId);
                }else {
                        listData.remove(childId);

                    }
                Timber.i("aaaaaaaa" +listData);
            }
        });*/


        return convertView;
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
            convertView = infalInflater.inflate(R.layout.list_group_default_items, null);

            groupViewHolder = new GroupViewHolder();

            groupViewHolder.lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader1);
            groupViewHolder.groupCheckbox = (CheckBox)convertView.findViewById(R.id.groupCheckbox);
            //groupViewHolder.groupCheckbox.setOnCheckedChangeListener(groupchecklistener);

            convertView.setTag(groupViewHolder);
        }else {

            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }

        groupViewHolder.lblListHeader.setTypeface(null, Typeface.BOLD);
        groupViewHolder.lblListHeader.setText(headerTitle);

       /* groupViewHolder.groupCheckbox.setTag(groupPosition);
        groupViewHolder.groupCheckbox.setChecked(array.get(groupPosition).isCheck());*/

       /* groupViewHolder.groupCheckbox.isChecked();
        groupViewHolder.groupCheckbox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                for(int i = 0;i<_listDataChild.get(_listDataHeader.get(groupPosition)).size();i++){
                    childViewHolder.chkBox1.setChecked(true);
                }

                //array.get(groupPosition).setCheck(groupViewHolder.groupCheckbox.isChecked());
                //groupViewHolder.groupCheckbox.setChecked(true);

                notifyDataSetChanged();
            }
        });*/





        groupViewHolder.imageview=(ImageView)convertView.findViewById(R.id.image);
        if(isExpanded){
            groupViewHolder.imageview.setImageResource(R.drawable.up_arrow);
        }
        else{
            groupViewHolder.imageview.setImageResource(R.drawable.down_arrow);
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

    public final class GroupViewHolder {

        TextView lblListHeader;
        ImageView imageview;
        CheckBox groupCheckbox;
    }

    public final class ChildViewHolder {

        TextView lblListItem1;
        CheckBox chkBox1;
    }
}