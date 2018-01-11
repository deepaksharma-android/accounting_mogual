package com.lkintechnology.mBilling.adapters;

/**
 * Created by BerylSystems on 11-Jan-18.
 */

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.utils.EventClickForAutoCompleteTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import timber.log.Timber;

public class AutoCompleteAdapter extends ArrayAdapter implements Filterable {

    private ArrayList<HashMap<String,String>> fullList;
    private ArrayList<HashMap<String,String>> mOriginalValues;
    private ArrayFilter mFilter;
    Context context;
    int resource;
    int textViewResourceId;
    ArrayList<String> newValues;

    public AutoCompleteAdapter(Context context, int resource, int textViewResourceId, ArrayList objects) {
        super(context, resource, textViewResourceId, objects);

        fullList =  objects;
        mOriginalValues = new ArrayList(fullList);
        this.context=context;
        this.resource=resource;
        this.textViewResourceId=textViewResourceId;

    }

    @Override
    public int getCount() {
        return fullList.size();
    }

    @Override
    public String getItem(int position) {
        return "";
    }
    public String getItem(int position,String key){
        getItem(position);
        return "";
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).
                inflate(resource, parent, false);
        TextView nameTextView= (TextView) convertView.findViewById(textViewResourceId);
        nameTextView.setText(newValues.get(position).toString());
        nameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new EventClickForAutoCompleteTextView(nameTextView.getText().toString()));
            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }


    private class ArrayFilter extends Filter {
        private Object lock;

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (mOriginalValues == null) {
                synchronized (lock) {
                    mOriginalValues = new ArrayList(fullList);
                }
            }

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    ArrayList<HashMap<String,String>> list = new ArrayList(mOriginalValues);
                    results.values = list;
                    results.count = list.size();
                }
            } else {
                final String prefixString = prefix.toString().toLowerCase();
                ArrayList<HashMap<String,String>> values = mOriginalValues;
                int count = values.size();
                newValues = new ArrayList(count);

                for (int i = 0; i < count; i++) {
                    String name = values.get(i).get("Name");
                    if (name.toLowerCase().contains(prefixString)) {
                        newValues.add(name);
                    }
                    String phone = values.get(i).get("Phone");
                    if (phone.toLowerCase().contains(prefixString)) {
                        newValues.add(phone);
                    }
                }
                Timber.i("2222222222222222222222"+newValues);
                results.values = newValues;
                results.count = newValues.size();
            }

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if(results.values!=null){
                fullList = (ArrayList<HashMap<String,String>>) results.values;
            }else{
                fullList = new ArrayList();
            }
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}