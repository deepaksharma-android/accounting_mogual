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
import com.lkintechnology.mBilling.utils.EventAccountChildClicked;
import com.lkintechnology.mBilling.utils.EventDeleteAccount;
import com.lkintechnology.mBilling.utils.EventEditAccount;
import com.lkintechnology.mBilling.utils.EventSelectAccountPurchase;
import com.lkintechnology.mBilling.utils.ParameterConstant;

import org.greenrobot.eventbus.EventBus;
import java.util.HashMap;
import java.util.List;

public class AccountExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public AccountExpandableListAdapter(Context context, List<String> listDataHeader, HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);
        String arr[] = childText.split(",");
        String acc_name = arr[0];
        String undefined = arr[1];
        String amount = arr[2];


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
        TextView txtListAmount = (TextView) convertView.findViewById(R.id.amount_qty);
        txtListChild.setText(acc_name);
        if(amount.equals("null")){
            amount="0.0";
        }
        txtListAmount.setText("₹ " +String.format("%.2f", Double.valueOf(amount)));

        LinearLayout delete = (LinearLayout) convertView.findViewById(R.id.delete_icon);
        LinearLayout edit = (LinearLayout) convertView.findViewById(R.id.edit_icon);
        LinearLayout mMainLayout = (LinearLayout) convertView.findViewById(R.id.main_layout);
        //LinearLayout mainLayout = (LinearLayout) convertView.findViewById(R.id.mainLayout);

        if (undefined.equals("true")) {
            delete.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
        } else {
            delete.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = groupPosition + "," + childPosition;
                EventBus.getDefault().post(new EventDeleteAccount(id));
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = groupPosition + "," + childPosition;
                EventBus.getDefault().post(new EventEditAccount(id));
            }
        });
        mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = groupPosition + "," + childPosition;
                EventBus.getDefault().post(new EventAccountChildClicked(id));
                EventBus.getDefault().post(new EventSelectAccountPurchase(id));
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
       /* if(!ParameterConstant.forPaymentSettlement.equals("")&&headerTitle.equals("Sundry Debtors")){

        }*/

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        if(ParameterConstant.forPaymentSettlement!=null) {
            if (!ParameterConstant.forPaymentSettlement.equals("")) {
                if (headerTitle.equals("Sundry Debtors")) {
                    ImageView imageview = (ImageView) convertView.findViewById(R.id.image);
                    imageview.setVisibility(View.GONE);
                } else {
                    ImageView imageview = (ImageView) convertView.findViewById(R.id.image);
                    if (isExpanded) {
                        imageview.setImageResource(R.drawable.up_arrow);
                    } else {
                        imageview.setImageResource(R.drawable.down_arrow);
                    }
                }
            }
        }
        else{
            ImageView imageview = (ImageView) convertView.findViewById(R.id.image);
            if (isExpanded) {
                imageview.setImageResource(R.drawable.up_arrow);
            } else {
                imageview.setImageResource(R.drawable.down_arrow);
            }
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