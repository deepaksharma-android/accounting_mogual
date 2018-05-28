package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;
import java.util.HashMap;
import java.util.Map;

public class RequestCreateIncome {

    public Map income;

    public RequestCreateIncome(Context context) {
        AppUser appUser = LocalRepositories.getAppUser(context);

        income = new HashMap<>();

        income.put("company_id", Preferences.getInstance(context).getCid());
        income.put("voucher_series", appUser.income_voucher_series);
        income.put("date", appUser.income_date);
        income.put("voucher_number", appUser.income_voucher_no);
        income.put("received_into_id", appUser.received_into_id);
        income.put("received_from_id", appUser.received_from_id);
        income.put("amount", appUser.income_amount);
        income.put("narration", appUser.income_narration);
        //income.put("attachment", appUser.income_attachment);
        income.put("attachment", Preferences.getInstance(context).getAttachment());
    }
}