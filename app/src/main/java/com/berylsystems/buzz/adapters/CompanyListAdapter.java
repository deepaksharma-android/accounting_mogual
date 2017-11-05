package com.berylsystems.buzz.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.api_response.company.CompanyData;
import com.berylsystems.buzz.utils.EventOpenCompany;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.ViewHolder> {
    private ArrayList<CompanyData> data;
    private Context context;
    public Dialog dialog;
    AppUser appUser;

    public CompanyListAdapter(Context context, ArrayList<CompanyData> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public CompanyListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_company_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompanyListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mCompanyName.setText(data.get(i).getAttributes().getName());
        appUser=LocalRepositories.getAppUser(context);
        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.titlecname=data.get(i).getAttributes().getName();
                appUser.company_id=data.get(i).getId();
                Preferences.getInstance(context).setCid(data.get(i).getId());
                LocalRepositories.saveAppUser(context,appUser);
               // CompanyDashboardActivity.data = data.get(i);
                showpopup(i);
               /* if(data.get(i).getAttributes().getAuthorization()==true) {
                    showpopup(i);
                }
                else{
                    context.startActivity(new Intent(context,CompanyDashboardActivity.class));
                }*/

            }
        });


    }

    public void showpopup(int pos){
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.layout_login_dialog);
        dialog.setTitle("Company Login");
        dialog.setCancelable(true);
        // set the custom dialog components - text, image and button
        EditText username = (EditText) dialog.findViewById(R.id.cusername);
        EditText password = (EditText) dialog.findViewById(R.id.cpassword);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);

        // if button is clicked, close the custom dialog
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!username.getText().toString().equals("")){
                    if(!password.getText().toString().equals("")){
                        appUser.cusername=username.getText().toString();
                        appUser.cpassword=password.getText().toString();
                        LocalRepositories.saveAppUser(context,appUser);
                        dialog.dismiss();
                        EventBus.getDefault().post(new EventOpenCompany(pos));


                }
                    else{
                        Toast.makeText(context,"Ente password",Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(context,"Enter username",Toast.LENGTH_LONG).show();
                }

            }
        });

        dialog.show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.company_name)
        TextView mCompanyName;
        @Bind(R.id.mainLayout)
        LinearLayout mMainLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
}