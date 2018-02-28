package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateCreditNote {

    public Map credit_note;

    public RequestCreateCreditNote(Context context) {
        AppUser appUser = LocalRepositories.getAppUser(context);

        credit_note = new HashMap<>();

        credit_note.put("company_id", Preferences.getInstance(context).getCid());
        credit_note.put("voucher_series", appUser.credit_note_voucher_series);
        credit_note.put("date", appUser.credit_note_date);
        credit_note.put("voucher_number", appUser.credit_note_voucher_no);
        credit_note.put("gst_nature",appUser.credit_note_gst_nature);
        credit_note.put("account_name_credit_id", appUser.account_name_credit_note_id);
        //credit_note.put("account_name_debit_id", appUser.account_name_debit_note);
        credit_note.put("amount", appUser.credit_note_amount);
        credit_note.put("narration", appUser.credit_note_narration);
        credit_note.put("attachment", appUser.credit_note_attachment);
        credit_note.put("send_email",appUser.email_yes_no);
        credit_note.put("credit_note_item",appUser.mListMapForItemCreditNote);
    }
}