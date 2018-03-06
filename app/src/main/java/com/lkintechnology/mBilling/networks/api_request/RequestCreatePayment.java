package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreatePayment {

    public Map payment;

    public RequestCreatePayment(Context context) {
        AppUser appUser = LocalRepositories.getAppUser(context);

        payment = new HashMap<>();

        payment.put("company_id", Preferences.getInstance(context).getCid());
        payment.put("voucher_series", appUser.payment_voucher_series);
        payment.put("date", appUser.payment_date);
        payment.put("voucher_number", appUser.payment_voucher_no);
        payment.put("payment_type",appUser.payment_type);
        payment.put("pdc_date",appUser.payment_date_pdc);
        payment.put("gst_nature",appUser.payment_gst_nature);
        payment.put("gst_nature_description",appUser.payment_gst_nature_description);
        payment.put("paid_to_id", appUser.payment_paid_to_id);
        payment.put("paid_from_id", appUser.payment_paid_from_id);
        payment.put("amount", appUser.payment_amount);
        payment.put("narration", appUser.payment_narration);
        payment.put("attachment", appUser.payment_attachment);
        payment.put("send_email",appUser.email_yes_no);
        payment.put("payment_item",appUser.mListMapForItemPaymentList);
    }
}