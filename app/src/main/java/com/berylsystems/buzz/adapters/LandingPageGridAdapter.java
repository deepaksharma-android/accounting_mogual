package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berylsystems.buzz.R;

import java.util.ArrayList;

public class LandingPageGridAdapter  extends RecyclerView.Adapter<LandingPageGridAdapter.ViewHolder> {
    private ArrayList<String> data;
    private Context context;

    public LandingPageGridAdapter(Context context,ArrayList<String> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public LandingPageGridAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_landing_page_grid, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LandingPageGridAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View view) {
            super(view);

        }
    }
}