package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.EventDeleteItem;
import com.lkintechnology.mBilling.utils.EventEditItem;
import com.lkintechnology.mBilling.utils.EventSaleAddItem;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import timber.log.Timber;

public class PosItemExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;
    private HashMap<Integer, String[]> mChildCheckStates;
    private HashMap<Integer, List<String>> listDataChildId;
    public static List<Map> mMapPosItem = new ArrayList();
    AppUser appUser;
    public PosItemExpandableListAdapter(Context context, List<String> listDataHeader,
                                        HashMap<String, List<String>> listChildData,
                                        HashMap<Integer, List<String>> listDataChildId) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        mChildCheckStates = new HashMap<Integer, String[]>();
        System.out.println("aaaaaa "+mChildCheckStates.size());
        System.out.println("aaaaaa "+mChildCheckStates.toString());
        this.listDataChildId = listDataChildId;
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
    int mInteger = 0;

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        appUser = LocalRepositories.getAppUser(_context);
        final int mGroupPosition = groupPosition;
        final int mChildPosition = childPosition;

        final String childText = (String) getChild(groupPosition, childPosition);
        String arr[] = childText.split(",");
        String name = arr[0];
        String quantity = arr[1];
        mInteger = 0;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_for_pos, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        TextView mQuantity = (TextView) convertView.findViewById(R.id.quantity);

        txtListChild.setText(name + " (qty: " + quantity + ")");
        LinearLayout decrease = (LinearLayout) convertView.findViewById(R.id.decrease);
        LinearLayout increase = (LinearLayout) convertView.findViewById(R.id.increase);
        LinearLayout mainLayout = (LinearLayout) convertView.findViewById(R.id.main_layout);
        System.out.println("aaaaaa "+mChildCheckStates.size());
        System.out.println("aaaaaa "+mChildCheckStates.toString());
        if (mChildCheckStates.containsKey(mGroupPosition)) {
            String getChecked[] = mChildCheckStates.get(mGroupPosition);
            mQuantity.setText(getChecked[mChildPosition]);

        } else {
            String getChecked[] = new String[getChildrenCount(mGroupPosition)];
           // getChecked[childPosition]="0";
            mChildCheckStates.put(mGroupPosition, getChecked);
            mQuantity.setText("0");
        }


        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map map;
                try {
                    mInteger= Integer.parseInt(mQuantity.getText().toString());
                }catch (Exception e){
                    mInteger=0;
                }
                mInteger = mInteger - 1;
                mQuantity.setText("" + mInteger);
                String getChecked[] = mChildCheckStates.get(mGroupPosition);
                getChecked[mChildPosition] = mQuantity.getText().toString();
                mChildCheckStates.put(mGroupPosition, getChecked);

               /* String pos = listDataChildId.get(mGroupPosition).get(childPosition);
                if (!mQuantity.getText().toString().equals("") && !mQuantity.getText().toString().equals("0")){

                    System.out.println(appUser.mMapPosItem.toString());
                    map = new HashMap();
                    map.put("pos",pos);
                    map.put("item_name",name);
                    map.put("quantity",quantity);
                    map.put("select_quantity",mQuantity.getText().toString());
                    mMapPosItem.add(map);
                }else {
                    mMapPosItem.remove(pos);
                }
                LocalRepositories.saveAppUser(_context,appUser);
                mMapPosItem.toString();*/

            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map map;
                try {
                    mInteger= Integer.parseInt(mQuantity.getText().toString());
                }catch (Exception e){
                    mInteger=0;
                }
                mInteger = mInteger + 1;
                mQuantity.setText("" + mInteger);
                String getChecked[] = mChildCheckStates.get(mGroupPosition);
                getChecked[mChildPosition] = mQuantity.getText().toString();
                mChildCheckStates.put(mGroupPosition, getChecked);

                if (mMapPosItem.size()>0){
                    String pos = listDataChildId.get(mGroupPosition).get(childPosition);
                    if (!mQuantity.getText().toString().equals("") && !mQuantity.getText().toString().equals("0")){
                        System.out.println(appUser.mMapPosItem.toString());
                        mMapPosItem.remove(pos);
                        map = new HashMap();
                        map.put("pos",pos);
                        map.put("item_name",name);
                        map.put("quantity",quantity);
                        map.put("select_quantity",mQuantity.getText().toString());
                        mMapPosItem.add(map);
                    }else {
                        mMapPosItem.remove(pos);
                    }
                    LocalRepositories.saveAppUser(_context,appUser);
                    mMapPosItem.toString();
                }
            }
        });
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  String id=groupPosition+","+childPosition;
                EventBus.getDefault().post(new EventSaleAddItem(id));*/
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
            convertView = infalInflater.inflate(R.layout.pos_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        ImageView imageview = (ImageView) convertView.findViewById(R.id.image);
        if (isExpanded) {
            imageview.setImageResource(R.drawable.up_arrow);
        } else {
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