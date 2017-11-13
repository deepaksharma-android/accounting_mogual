package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateAccount {
    public Map<String, String> account;



    public RequestCreateAccount(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        account = new HashMap<>();
        account.put("name", appUser.account_name);
        account.put("company_id", Preferences.getInstance(ctx).getCid());
        account.put("mobile_number", appUser.account_mobile_number);
        account.put("account_master_group_id", appUser.create_account_group_id);
        account.put("address", appUser.account_address);
        account.put("city", appUser.account_city);
        account.put("state", appUser.account_state);
        account.put("country","India");
        account.put("adhar_number", appUser.account_aadhaar);
        account.put("gstin_number",appUser.account_gst);
        account.put("pan_number",appUser.account_pan);
        account.put("credit_limit", appUser.account_credit_limit);
        account.put("credit_days_for_sale", appUser.account_credit_sale);
        account.put("credit_days_for_purchase", appUser.account_credit_purchase);
        account.put("amount_receivable", appUser.account_amount_receivable);
        account.put("amount_payable", appUser.account_amount_payable);







    }
}