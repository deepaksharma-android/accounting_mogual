package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

public class RequestCompanyLogin {
    public Map<String, String> company;



    public RequestCompanyLogin(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        company = new HashMap<>();
        company.put("username", appUser.companyUserName);
        company.put("mobile",appUser.companymobile);

        if(!appUser.companyUserPassword.equals("••••••••")) {
            company.put("password", appUser.companyUserPassword);
        }




    }
}