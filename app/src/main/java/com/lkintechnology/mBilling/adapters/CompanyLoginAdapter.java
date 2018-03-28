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
import com.lkintechnology.mBilling.networks.api_response.companylogin.UserName;
import com.lkintechnology.mBilling.networks.api_response.companylogin.Users;
import com.lkintechnology.mBilling.utils.EventEditLogin;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyLoginAdapter extends RecyclerView.Adapter<CompanyLoginAdapter.ViewHolder> {
    private ArrayList<Users> data;
    private ArrayList<String> userName;
    private Context context;
    int[] images;
    public Dialog dialog;
    AppUser appUser;


    public CompanyLoginAdapter(Context context, ArrayList<Users> data) {
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
        appUser=LocalRepositories.getAppUser(context);
        viewHolder.mCompanyUserName.setText(data.get(i).getUsername());
        appUser.company_user_id= String.valueOf(data.get(i).getId());
        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!data.get(i).getAdmin()) {
                    Intent intent = new Intent(context, CompanyAuthrizationActivity.class);
                    CompanyAuthrizationActivity.data=data.get(i);
                    Integer user_id = data.get(i).getId();
                    appUser.authorizations__setting_user_id = user_id;
                    LocalRepositories.saveAppUser(context, appUser);
                    context.startActivity(intent);
                }
            }
        });



    }
  /* @Override
   public void onBindViewHolder(CompanyLoginAdapter.ViewHolder viewHolder, int i) {
       appUser=LocalRepositories.getAppUser(context);
       viewHolder.mCompanyUserName.setText(userName.get(i));
       //appUser.company_user_id= String.valueOf(data.get(i).getId());
       viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
             *//*  if(!userName.get(i).getAdmin()) {
                   Intent intent = new Intent(context, CompanyAuthrizationActivity.class);
                   CompanyAuthrizationActivity.data=data.get(i);
                   Integer user_id = data.get(i).getId();
                   appUser.authorizations__setting_user_id = user_id;
                   LocalRepositories.saveAppUser(context, appUser);
                   context.startActivity(intent);
               }*//*
           }
       });



   }*/

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.company_login_name)
        TextView mCompanyUserName;
        @Bind(R.id.mainLayout)
        LinearLayout mMainLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
    public void showpopup(){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_company_login_diolog);
        dialog.setTitle("Users Login");
        dialog.setCancelable(true);
        // set the custom dialog components - text, image and button
        EditText username = (EditText) dialog.findViewById(R.id.cusername);
        EditText password = (EditText) dialog.findViewById(R.id.cpassword);
        EditText cpassword = (EditText) dialog.findViewById(R.id.cconfirmpassword);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);

        // if button is clicked, close the custom dialog
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!username.getText().toString().equals("")){
                    if(!password.getText().toString().equals("")) {
                        if (password.getText().toString().equals(cpassword.getText().toString())) {
                            appUser.cusername = username.getText().toString();
                            appUser.cpassword = password.getText().toString();
                            LocalRepositories.saveAppUser(context, appUser);
                            dialog.dismiss();
                            int x=1;
                            EventBus.getDefault().post(new EventEditLogin(x));


                        }
                        else{
                            Toast.makeText(context,"Password does not match",Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        Toast.makeText(context,"Enter password",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(context,"Enter username",Toast.LENGTH_LONG).show();
                }

            }
        });

        dialog.show();
    }
}