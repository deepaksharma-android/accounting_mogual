package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

public class RequestCompanyGst {
    public Map<String, String> company;



    public RequestCompanyGst(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        company = new HashMap<>();
        company.put("gst",appUser.gst);
        company.put("type_of_dealer", appUser.type_of_dealer);
        company.put("default_tax_rate1", appUser.default_tax_rate1);
        company.put("default_tax_rate2", appUser.default_tax_rate2);



    }
}