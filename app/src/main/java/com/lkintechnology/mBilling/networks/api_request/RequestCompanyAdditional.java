package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

public class RequestCompanyAdditional {
    public Map<String, String> company;



    public RequestCompanyAdditional(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        company = new HashMap<>();
        company.put("currency_symbol", appUser.currency_symbol);
        company.put("currency_string", appUser.currenyString);
        company.put("currency_sub_string", appUser.currenySubString);




    }
}