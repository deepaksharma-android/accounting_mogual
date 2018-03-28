package com.lkintechnology.mBilling.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.CompanyAuthrizationActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_response.companylogin.Users;
import com.lkintechnology.mBilling.utils.EventEditLogin;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by MANJOOR on 3/28/2018.
 */

public class UserFragmentAdapter extends RecyclerView.Adapter<UserFragmentAdapter.ViewHolder> {
    private ArrayList<Users> data;
    private ArrayList<String> userName;
    private Context context;
    int[] images;
    public Dialog dialog;
    AppUser appUser;


    public UserFragmentAdapter(Context context, ArrayList<String> userName) {
        this.userName = userName;
        this.context = context;
        //this.images=images;
    }

    @Override
    public UserFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_company_login_list, parent, false);
        return new UserFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserFragmentAdapter.ViewHolder holder, int position) {
        appUser=LocalRepositories.getAppUser(context);
        holder.mCompanyUserName.setText(userName.get(position));
      //  appUser.company_user_id= String.valueOf(data.get(i).getId());
        holder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* if(!data.get(i).getAdmin()) {
                    Intent intent = new Intent(context, CompanyAuthrizationActivity.class);
                    CompanyAuthrizationActivity.data=data.get(i);
                    Integer user_id = data.get(i).getId();
                    appUser.authorizations__setting_user_id = user_id;
                    LocalRepositories.saveAppUser(context, appUser);
                    context.startActivity(intent);
                }*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.userName.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.company_login_name)
        TextView mCompanyUserName;
        @Bind(R.id.mainLayout)
        LinearLayout mMainLayout;
        public ViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}