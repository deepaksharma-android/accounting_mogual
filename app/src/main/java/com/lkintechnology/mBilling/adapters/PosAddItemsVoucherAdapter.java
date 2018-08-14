package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;

import net.sourceforge.zbar.Image;

import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by BerylSystems on 11/25/2017.
 */

public class PosAddItemsVoucherAdapter extends BaseAdapter {

    Context context;
    List<Map> mListMap;
    int mInteger = 0;
    //ViewHolder holder;

    public PosAddItemsVoucherAdapter(Context context, List<Map> mListMap) {
        this.context = context;
        this.mListMap = mListMap;

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
        mInteger = 0;
       // holder = null;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_for_pos, null);
        }
        TextView mItemName = (TextView) convertView.findViewById(R.id.lblListItem);
        TextView mQuantity = (TextView) convertView.findViewById(R.id.quantity);
        LinearLayout decrease = (LinearLayout) convertView.findViewById(R.id.decrease);
        LinearLayout increase = (LinearLayout) convertView.findViewById(R.id.increase);
        Map map = mListMap.get(position);
        String item_id = (String) map.get("item_id");
        String itemName = (String) map.get("item_name");
        String quantity = (String) map.get("quantity");
        String total = (String) map.get("total").toString();
        mItemName.setText(itemName);
        mQuantity.setText(quantity);

        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInteger = Integer.parseInt(mQuantity.getText().toString());
                mInteger = mInteger + 1;
                mQuantity.setText("" + mInteger);
            }
        });

        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInteger = Integer.parseInt(mQuantity.getText().toString());
                if (mInteger > 0) {
                    mInteger = mInteger - 1;
                    mQuantity.setText("" + mInteger);
                }
            }
        });


        return convertView;
    }
}
