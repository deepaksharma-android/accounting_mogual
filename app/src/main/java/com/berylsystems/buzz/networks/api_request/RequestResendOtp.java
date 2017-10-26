package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

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
