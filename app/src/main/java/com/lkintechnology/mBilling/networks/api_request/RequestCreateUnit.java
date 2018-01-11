package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateUnit {
    public Map<String, String> item_unit;



    public RequestCreateUnit(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        item_unit = new HashMap<>();
        item_unit.put("company_id", Preferences.getInstance(ctx).getCid());
        item_unit.put("name",appUser.unit_name);
        item_unit.put("uqc_id", appUser.uqc);


    }
}