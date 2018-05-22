package com.lkintechnology.mBilling.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_response.company.CompanyData;
import com.lkintechnology.mBilling.utils.EventOpenCompany;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CompanyListAdapter extends RecyclerView.Adapter<CompanyListAdapter.ViewHolder> implements Filterable {
    private ArrayList<CompanyData> data;
    private List<CompanyData> mFilteredList;
    private Context context;
    public Dialog dialog;
    AppUser appUser;

    public CompanyListAdapter(Context context, ArrayList<CompanyData> data) {
        this.data = data;
        this.context = context;
        mFilteredList=data;
    }

    @Override
    public CompanyListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_company_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CompanyListAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mCompanyName.setText(mFilteredList.get(i).getAttributes().getName()+" "+"("+mFilteredList.get(i).getAttributes().getUnique_id()+")");
        viewHolder.mCompany_address.setText(mFilteredList.get(i).getAttributes().getAddress());
        appUser=LocalRepositories.getAppUser(context);
        viewHolder.mMainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.company_name=mFilteredList.get(i).getAttributes().getName();
                appUser.company_id=mFilteredList.get(i).getId();
                Preferences.getInstance(context).setCid(mFilteredList.get(i).getId());
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
    public Filter getFilter() {
        return new Filter(){

            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty()) {

                    mFilteredList = data;
                } else {

                    ArrayList<CompanyData> filteredList = new ArrayList<>();

                    for (CompanyData androidVersion : data) {

                        if (androidVersion.getAttributes().getName().toLowerCase().contains(charString)) {

                            filteredList.add(androidVersion);
                        }
                    }

                    mFilteredList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredList;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                mFilteredList = (ArrayList<CompanyData>) filterResults.values;
                notifyDataSetChanged();

            }
        };
    }

    public void showpopup(int pos){
        dialog = new Dialog(context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_login);
        dialog.setCancelable(true);
        // set the custom dialog components - text, image and button
        EditText username = (EditText) dialog.findViewById(R.id.cusername);
        EditText password = (EditText) dialog.findViewById(R.id.cpassword);
        CheckBox checkBox = (CheckBox) dialog.findViewById(R.id.remember_password);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialog.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                dialog.dismiss();
            }
        });

        for (int i = 0; i < appUser.companyLoginArray.size(); i++) {
            Map map = appUser.companyLoginArray.get(i);
            String position = (String) map.get("position");
            if (pos == Integer.parseInt(position)) {
                Boolean status = (Boolean) map.get("status");
                String uName = (String) map.get("username");
                String pass = (String) map.get("password");
                if (status) {
                    checkBox.setChecked(true);
                    username.setText(uName);
                    password.setText(pass);
                }
            }

        }
        // if button is clicked, close the custom dialog
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                appUser.boolSetOrAddtoMap=false;
                appUser.company_logo=data.get(pos).getAttributes().getLogo();
                appUser.company_state=data.get(pos).getAttributes().getState();
                LocalRepositories.saveAppUser(context,appUser);
                Map mapAdd= new HashMap();
                if (!username.getText().toString().equals("")) {
                    if (!password.getText().toString().equals("")) {
                        appUser.cusername = username.getText().toString();
                        appUser.cpassword = password.getText().toString();
                        LocalRepositories.saveAppUser(context, appUser);
                        dialog.dismiss();

                        if (checkBox.isChecked()) {


                            mapAdd.put("position", String.valueOf(pos));
                            mapAdd.put("username", username.getText().toString());
                            mapAdd.put("password", password.getText().toString());
                            mapAdd.put("status", true);

                            for (int i=0;i<appUser.companyLoginArray.size();i++){
                                Map mapGet=appUser.companyLoginArray.get(i);
                                String str= (String) mapGet.get("position");
                                Integer integer= Integer.valueOf(str);
                                if (integer==Integer.valueOf(pos)){
                                    LocalRepositories.saveAppUser(context,appUser);
                                    appUser.companyLoginArray.set(i,mapGet);
                                    LocalRepositories.saveAppUser(context, appUser);
                                }
                            }
                            if (!appUser.boolSetOrAddtoMap){
                                appUser.companyLoginArray.add(mapAdd);
                                LocalRepositories.saveAppUser(context, appUser);
                            }
                        } else {
                            for (int i = 0; i < appUser.companyLoginArray.size(); i++) {
                                Map mapRemove = appUser.companyLoginArray.get(i);
                                String position = (String) mapRemove.get("position");
                                if (pos ==Integer.valueOf(position)) {
                                    appUser.companyLoginArray.remove(i);
                                    LocalRepositories.saveAppUser(context, appUser);
                                }
                            }
                        }
                        EventBus.getDefault().post(new EventOpenCompany(pos));

                    } else {
                        Toast.makeText(context, "Enter password", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, "Enter username", Toast.LENGTH_LONG).show();
                }
            }
        });

        dialog.show();
    }



    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.company_name)
        TextView mCompanyName;
        @Bind(R.id.company_address)
        TextView mCompany_address;
        @Bind(R.id.mainLayout)
        LinearLayout mMainLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }
}