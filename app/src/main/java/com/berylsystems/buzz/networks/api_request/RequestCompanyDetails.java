package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

public class RequestCompanyDetails {
    public Map<String, String> company;



    public RequestCompanyDetails(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        company = new HashMap<>();
        company.put("city",appUser.city);
        company.put("country", appUser.country);
        company.put("state", appUser.state);
        company.put("address", appUser.address);
        company.put("ward", appUser.ward);
        company.put("fax", appUser.fax);
        company.put("email", appUser.company_email);
        company.put("industry_id",appUser.industryId);

    }
}