package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

public class RequestCompanyAuthenticate {
    public Map<String, String> company;



    public RequestCompanyAuthenticate(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        company=new HashMap<>();
        company.put("username",appUser.cusername);
        company.put("password",appUser.cpassword);









    }
}