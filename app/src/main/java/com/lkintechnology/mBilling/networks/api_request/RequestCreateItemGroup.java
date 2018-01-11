package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateItemGroup {

    public Map<String, String> item_group;

    public RequestCreateItemGroup(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        item_group = new HashMap<>();
        item_group.put("name",appUser.item_group_name);
        item_group.put("company_id", Preferences.getInstance(ctx).getCid());
        item_group.put("undergroup_id", appUser.item_group_id);

    }
}