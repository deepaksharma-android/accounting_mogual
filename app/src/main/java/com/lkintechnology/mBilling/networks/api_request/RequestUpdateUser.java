package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

public class RequestUpdateUser {

    public Map<String, String> user;

    public RequestUpdateUser(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        user = new HashMap<>();
        user.put("name", appUser.name);
        user.put("email", appUser.email);
        if(!appUser.password.equals("••••••••")) {
            user.put("password", appUser.password);
        }
        user.put("postal_code",appUser.zipcode);
        user.put("id",appUser.user_id);
    }
}