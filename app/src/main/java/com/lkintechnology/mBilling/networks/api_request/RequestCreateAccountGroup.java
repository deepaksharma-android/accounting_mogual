package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

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

    }
}