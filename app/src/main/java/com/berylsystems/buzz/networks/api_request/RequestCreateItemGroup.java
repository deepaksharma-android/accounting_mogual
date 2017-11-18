package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

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