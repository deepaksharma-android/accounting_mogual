package com.berylsystems.buzz.networks.api_request;

import android.content.Context;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;
import java.util.HashMap;
import java.util.Map;

public class RequestCreateEXpence {

    public Map expense;

    public RequestCreateEXpence(Context context) {
        AppUser appUser = LocalRepositories.getAppUser(context);

        expense = new HashMap<>();

        expense.put("company_id", Preferences.getInstance(context).getCid());
        expense.put("voucher_series", appUser.expence_voucher_series);
        expense.put("date", appUser.expence_date);
        expense.put("voucher_number", appUser.expence_voucher_no);
        expense.put("paid_from_id", appUser.paid_from_id);
        expense.put("paid_to_id", appUser.paid_to_id);
        expense.put("amount", appUser.expence_amount);
        expense.put("narration", appUser.expence_narration);
        expense.put("attachment", appUser.expence_attachment);
    }
}