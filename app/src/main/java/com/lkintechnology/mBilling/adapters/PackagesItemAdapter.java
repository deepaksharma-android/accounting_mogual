package com.lkintechnology.mBilling.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_response.packages.Features;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.ArrayList;



public class PackagesItemAdapter extends BaseAdapter {
    private ArrayList<Features> data ;
    View row=null;
    ViewHolder holder=null;
    private final Activity activity;
    AppUser appUser;



    public PackagesItemAdapter(Activity activity, ArrayList<Features> data ) {
        this.activity = activity;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    @Override

    public int getViewTypeCount() {

        return getCount();
    }

    @Override

    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        appUser= LocalRepositories.getAppUser(activity);

        row=convertView;
        holder=null;
        if(row==null) {
            LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.layout_pakages_item, parent, false);
            holder = new ViewHolder(row);
            row.setTag(holder);


        }
        else
        {
            holder= (ViewHolder) row.getTag();

        }

        holder.itemName.setText(data.get(i).getName());
       /* if(!data.get(i).getMessage().equals("")){
            holder.mImage.setVisibility(View.VISIBLE);
        }
        else{
            holder.mImage.setVisibility(View.GONE);
        }*/
        holder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
                builder1.setMessage(data.get(i).getDescription());
                builder1.setCancelable(true);
                builder1.show();
            }
        });





        return row;
    }

    private static class ViewHolder
    {
        public TextView itemName;
        public ImageView mImage;


        public ViewHolder(View row) {

            // TODO Auto-generated constructor stub
            itemName = (TextView) row.findViewById(R.id.itemName);
            mImage=(ImageView)row.findViewById(R.id.imageques);
        }

    }

}