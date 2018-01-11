package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;


public class RequestForgotPassword {

    public Map<String, String> user;



    public RequestForgotPassword(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        user = new HashMap<>();
        user.put("mobile", appUser.mobile);

    }
}
