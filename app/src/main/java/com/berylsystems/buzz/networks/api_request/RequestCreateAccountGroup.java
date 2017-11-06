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

    }
}