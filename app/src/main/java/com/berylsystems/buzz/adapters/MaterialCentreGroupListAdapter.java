package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berylsystems.buzz.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class MaterialCentreGroupListAdapter extends RecyclerView.Adapter<MaterialCentreGroupListAdapter.ViewHolder> {
    private ArrayList<String> data;
    private Context context;


    public MaterialCentreGroupListAdapter(Context context, ArrayList<String> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MaterialCentreGroupListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MaterialCentreGroupListAdapter.ViewHolder viewHolder, int i) {
    }


    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
}