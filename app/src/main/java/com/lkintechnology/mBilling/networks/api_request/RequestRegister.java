package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;


public class RequestRegister {
    public Map<String, String> user;



    public RequestRegister(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        user = new HashMap<>();
        user.put("name", appUser.name);
        user.put("email", appUser.email);
        user.put("mobile", appUser.mobile);
        user.put("password", appUser.password);
        user.put("fb_id",appUser.fb_id);
        user.put("postal_code",appUser.zipcode);
        user.put("salesman_mobile",appUser.salesmanmobile);
    }
}
