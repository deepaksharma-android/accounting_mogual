package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

public class RequestCompanyBankDetails {
    public Map<String, String> company;



    public RequestCompanyBankDetails(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        company = new HashMap<>();
        company.put("bank_name",appUser.bank_name);
        company.put("bank_account", appUser.bank_account);
        company.put("ifsc_code", appUser.bank_ifsc_code);
        company.put("micr_code", appUser.bank_micr_code);
    }
}