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
        account.put("account_name", appUser.account_name);
        account.put("company_id", Preferences.getInstance(ctx).getCid());
        account.put("type_of_dealer", appUser.account_type_of_dealer);
        account.put("account_master_group_id", appUser.create_account_group_id);
        account.put("opening_balance", appUser.acount_opening_balance);
        account.put("opening_balance_type", appUser.acount_opening_balance_type);
        account.put("prev_year_balance", appUser.prev_year_balance);
        account.put("prev_year_balance_type", appUser.prev_year_balance_type);
        account.put("credit_days_for_sale", appUser.credit_days_for_sale);
        account.put("credit_days_for_purchase", appUser.credit_days_for_purchase);
        account.put("bank_account_number", appUser.bank_account_number);
        account.put("bank_ifsc_code", appUser.bank_ifsc_code);
        account.put("bank_name", appUser.bank_name);






    }
}