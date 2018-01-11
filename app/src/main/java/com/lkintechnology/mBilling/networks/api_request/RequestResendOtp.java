package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

public class RequestResendOtp {
    public Map<String, String> user;



    public RequestResendOtp(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        user = new HashMap<>();
        user.put("id", appUser.user_id);
        user.put("mobile",appUser.mobile);
    }
}
