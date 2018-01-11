package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;


public class RequestVerification {

    public Map<String, String> user;



    public RequestVerification(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        user = new HashMap<>();
        user.put("mobile", appUser.mobile);
        user.put("otp", appUser.otp);
    }
}
