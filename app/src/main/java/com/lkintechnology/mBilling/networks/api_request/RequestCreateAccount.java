package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateAccount {
    public Map<String, String> account;



    public RequestCreateAccount(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        account = new HashMap<>();
        String account_name=appUser.account_name;
        if(account_name.contains(",")){
            String accname=  account_name.replace(","," ");
            account.put("name", accname);
        }
        else{
            account.put("name", account_name);
        }


        account.put("company_id", Preferences.getInstance(ctx).getCid());
        account.put("mobile_number", appUser.account_mobile_number);
        account.put("email", appUser.account_email);
        account.put("account_master_group_id", appUser.create_account_group_id);
        account.put("address", appUser.account_address);
        account.put("pincode",appUser.account_pinCode);
        account.put("city", appUser.account_city);
        if(!appUser.account_state.equals("")){
            account.put("state", appUser.account_state);
        }else {
            account.put("state", appUser.state);
        }
        account.put("country","India");
        account.put("adhar_number", appUser.account_aadhaar);
        account.put("gstin_number",appUser.account_gst);
        if(!appUser.account_type_of_dealer.equals("")){
            account.put("type_of_dealer",appUser.account_type_of_dealer);
        }else {
            account.put("type_of_dealer","Un-Registered");
        }
        account.put("pan_number",appUser.account_pan);
        account.put("credit_limit", appUser.account_credit_limit);
        account.put("credit_days_for_sale", appUser.account_credit_sale);
        account.put("credit_days_for_purchase", appUser.account_credit_purchase);
        account.put("amount_receivable", appUser.account_amount_receivable);
        account.put("amount_payable", appUser.account_amount_payable);







    }
}