package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateReceipt {

    public Map receipt_voucher;

    public RequestCreateReceipt(Context context) {
        AppUser appUser = LocalRepositories.getAppUser(context);

        receipt_voucher = new HashMap<>();

        receipt_voucher.put("company_id", Preferences.getInstance(context).getCid());
        receipt_voucher.put("voucher_series", appUser.receipt_voucher_series);
        receipt_voucher.put("date", appUser.receipt_date);
        receipt_voucher.put("voucher_number", appUser.receipt_voucher_no);
        receipt_voucher.put("payment_type", appUser.receipt_type);
        receipt_voucher.put("pdc_date", appUser.receipt_date_pdc);
        receipt_voucher.put("gst_nature", appUser.receipt_gst_nature);
        receipt_voucher.put("gst_nature_description", appUser.receipt_gst_nature_description);
        receipt_voucher.put("received_from_id", appUser.receipt_received_from_id);
        receipt_voucher.put("received_by_id", appUser.receipt_received_by_id);
        receipt_voucher.put("amount", appUser.receipt_amount);
        receipt_voucher.put("narration", appUser.receipt_narration);
        receipt_voucher.put("attachment", appUser.receipt_attachment);
        receipt_voucher.put("send_email",appUser.email_yes_no);
    }
}