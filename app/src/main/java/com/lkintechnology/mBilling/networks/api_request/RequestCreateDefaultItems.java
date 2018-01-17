package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.adapters.DefaultItemsExpandableListAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateDefaultItems {
    public Map default_items;

    public RequestCreateDefaultItems(Context context) {
        AppUser appUser = LocalRepositories.getAppUser(context);

        default_items = new HashMap<>();

        default_items.put("company_id", Preferences.getInstance(context).getCid());
        default_items.put("default_item_id", DefaultItemsExpandableListAdapter.listData);

    }
}