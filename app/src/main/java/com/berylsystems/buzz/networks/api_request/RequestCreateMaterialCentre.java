package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateMaterialCentre {
    public Map<String, String> material_center;



    public RequestCreateMaterialCentre(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        material_center = new HashMap<>();
        material_center.put("company_id", Preferences.getInstance(ctx).getCid());
        material_center.put("name",appUser.material_centre_name);
        material_center.put("material_center_group_id", appUser.material_centre_group_id);
        material_center.put("material_center_account_id", "24"/*appUser.material_centre_account_id*/);
        material_center.put("address", appUser.material_centre_address);
        material_center.put("city", appUser.material_centre_city);

    }
}