package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

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