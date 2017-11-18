package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateUnitConversion {
    public Map<String, String> unit_conversion;



    public RequestCreateUnitConversion(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        unit_conversion = new HashMap<>();
        unit_conversion.put("company_id", Preferences.getInstance(ctx).getCid());
        unit_conversion.put("main_unit_id",appUser.main_unit_id);
        unit_conversion.put("sub_unit_id", appUser.sub_unit_id);
        unit_conversion.put("conversion_factor", appUser.confactor);


    }
}