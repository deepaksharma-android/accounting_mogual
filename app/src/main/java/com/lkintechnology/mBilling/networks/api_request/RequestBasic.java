package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

public class RequestBasic {
    public Map<String, String> company;



    public RequestBasic(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        company = new HashMap<>();
        company.put("name", appUser.company_name);
        company.put("print_name",appUser.print_name);
        company.put("short_name", appUser.short_name);
        company.put("financial_year_from", appUser.financial_year_from);
        company.put("books_commencing_from", appUser.books_commencing_from);
        company.put("cin", appUser.cin);
        company.put("it_pin", appUser.it_pin);
        company.put("phone_number", appUser.comapny_phone_number);






    }
}