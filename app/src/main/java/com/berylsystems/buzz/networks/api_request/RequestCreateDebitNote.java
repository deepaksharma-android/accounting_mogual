package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateDebitNote {

    public Map debit_note;

    public RequestCreateDebitNote(Context context) {
        AppUser appUser = LocalRepositories.getAppUser(context);

        debit_note = new HashMap<>();

        debit_note.put("company_id", Preferences.getInstance(context).getCid());
        debit_note.put("voucher_series", appUser.debit_note_voucher_series);
        debit_note.put("date", appUser.debit_note_date);
        debit_note.put("voucher_number", appUser.debit_note_voucher_no);
        debit_note.put("gst_nature",appUser.debit_note_gst_nature);
        //debit_note.put("account_name_credit_id", appUser.account_name_credit_note);
        debit_note.put("account_name_debit_id", appUser.account_name_debit_note_id);
        debit_note.put("amount", appUser.debit_note_amount);
        debit_note.put("narration", appUser.debit_note_narration);
        debit_note.put("attachment", appUser.debit_note_attachment);
    }
}