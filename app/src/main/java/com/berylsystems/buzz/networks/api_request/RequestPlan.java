package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

public class RequestPlan {
    public Map<String, String> user_plan;



    public RequestPlan(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        user_plan=new HashMap<>();
        user_plan.put("user_id",appUser.user_id);
        user_plan.put("plan_id",appUser.package_id);
        user_plan.put("amount",appUser.package_amount);








    }
}