package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

public class RequestCompanyLogo {
    public Map<String, String> company;



    public RequestCompanyLogo(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        company = new HashMap<>();
        company.put("logo", appUser.logo);




    }
}