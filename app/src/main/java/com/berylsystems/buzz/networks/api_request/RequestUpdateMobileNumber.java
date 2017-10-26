package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

public class RequestUpdateMobileNumber {

    public Map<String, String> user;



    public RequestUpdateMobileNumber(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        user = new HashMap<>();
        user.put("phone_number", appUser.mobile);
        user.put("id",appUser.user_id);

    }
}
