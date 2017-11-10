package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateAccountGroup {
    public Map<String, String> account;



    public RequestCreateAccountGroup(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        account = new HashMap<>();
        account.put("name",appUser.account_group_name);
        account.put("company_id",Preferences.getInstance(ctx).getCid());
        account.put("account_group_id", appUser.account_group_id);
        account.put("phone_number",appUser.account_mobile_number);
        account.put("account_amount_receivable",appUser.account_amount_receivable);
        account.put("account_amount_payable",appUser.account_amount_payable);
        account.put("account_address",appUser.account_address);
        account.put("account_city",appUser.account_city);
        account.put("account_state",appUser.account_state);
        account.put("account_gst",appUser.account_gst);
        account.put("account_aadhaar",appUser.account_aadhaar);
        account.put("account_pan",appUser.account_pan);
        account.put("account_credit_limit",appUser.account_credit_limit);
        account.put("account_credit_sale",appUser.account_credit_sale);
        account.put("account_credit_purchase",appUser.account_credit_purchase);



    }
}