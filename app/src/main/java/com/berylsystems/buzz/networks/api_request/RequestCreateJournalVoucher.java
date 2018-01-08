package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateJournalVoucher {

    public Map journal_voucher;

    public RequestCreateJournalVoucher(Context context) {
        AppUser appUser = LocalRepositories.getAppUser(context);

        journal_voucher = new HashMap<>();

        journal_voucher.put("company_id", Preferences.getInstance(context).getCid());
        journal_voucher.put("voucher_series", appUser.journal_voucher_voucher_series);
        journal_voucher.put("date", appUser.journal_voucher_date);
        journal_voucher.put("voucher_number", appUser.journal_voucher_voucher_no);
        journal_voucher.put("gst_nature",appUser.journal_voucher_gst_nature);
        journal_voucher.put("account_name_credit_id", appUser.account_name_credit_id);
        journal_voucher.put("account_name_debit_id", appUser.account_name_debit_id);
        journal_voucher.put("amount", appUser.journal_voucher_amount);
        journal_voucher.put("narration", appUser.journal_voucher_narration);
        journal_voucher.put("attachment", appUser.journal_voucher_attachment);
        journal_voucher.put("send_email",appUser.email_yes_no);
    }
}