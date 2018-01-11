package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateMaterialCentreGroup {
    public Map<String, String> material_center_group;



    public RequestCreateMaterialCentreGroup(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        material_center_group = new HashMap<>();
        material_center_group.put("company_id", Preferences.getInstance(ctx).getCid());
        material_center_group.put("name",appUser.material_centre_group_name);
        material_center_group.put("material_center_group_id", appUser.material_centre_group_id);


    }
}