package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by abc on 7/12/2018.
 */

public class RequestInvoiceFormat {
    public Map<String, String> company;
    public RequestInvoiceFormat(Context ctx) {

        AppUser appUser = LocalRepositories.getAppUser(ctx);

        company = new HashMap<>();
        company.put("invoice_format",appUser.invoice_format.trim());

    }

}
