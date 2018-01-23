package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.HashMap;

public class RequestCreateAuthorizationSettings {

    public HashMap authorization_settings;

    public RequestCreateAuthorizationSettings(Context context){
        AppUser appUser = LocalRepositories.getAppUser(context);
        authorization_settings = new HashMap();

        authorization_settings.put("enable",appUser.enable_user);
        authorization_settings.put("allow_to_delete_voucher",appUser.allow_user_to_delete_voucher);
        authorization_settings.put("allow_to_edit_voucher",appUser.allow_user_to_edit_voucher);

    }
}