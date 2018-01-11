package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestStock {
    public Map<String, String> material_center;



    public RequestStock(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        material_center = new HashMap<>();
        material_center.put("stock_account","Stock-in-hand");
        material_center.put("company_id", Preferences.getInstance(ctx).getCid());


    }
}