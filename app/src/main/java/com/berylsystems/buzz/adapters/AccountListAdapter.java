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

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.ViewHolder> {
    private ArrayList<String> data;
    private Context context;


    public AccountListAdapter(Context context, ArrayList<String> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public AccountListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_account_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AccountListAdapter.ViewHolder viewHolder, int i) {




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