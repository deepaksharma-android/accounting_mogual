package com.berylsystems.buzz.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.networks.api_response.companylogin.Attributes;
import com.berylsystems.buzz.networks.api_response.companylogin.UserName;
import com.kyanogen.signatureview.SignatureView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyLoginAdapter extends RecyclerView.Adapter<CompanyLoginAdapter.ViewHolder> {
    private ArrayList<UserName> data;
    private Context context;
    int[] images;

    public CompanyLoginAdapter(Context context, ArrayList<UserName> data) {
        this.data = data;
        this.context = context;
        //this.images=images;
    }

    @Override
    public CompanyLoginAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_company_login_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompanyLoginAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mCompanyUserName.setText(data.get(i).getName());


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.company_login_name)
        TextView mCompanyUserName;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
}