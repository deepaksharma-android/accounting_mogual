package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.HashMap;

/**
 * Created by BerylSystems on 11/29/2017.
 */

public class RequestCreatePurchase {
    public HashMap voucher;
    public RequestCreatePurchase(Context context){
        AppUser appUser = LocalRepositories.getAppUser(context);
        voucher = new HashMap<>();

        voucher.put("date",appUser.purchase_date);
        voucher.put("voucher_series",appUser.purchase_voucher_series);
        voucher.put("voucher_number",appUser.purchase_voucher_number);
        voucher.put("company_id", Preferences.getInstance(context).getCid());
        voucher.put("purchase_type_id",Preferences.getInstance(context).getPurchase_type_id());
        voucher.put("payment_type",Preferences.getInstance(context).getCash_credit());
        voucher.put("mobile_number",appUser.purchase_mobile_number);
        voucher.put("account_master_id",Preferences.getInstance(context).getParty_id());
        voucher.put("shipped_to_id",Preferences.getInstance(context).getShipped_to_id());
        voucher.put("material_center_id",Preferences.getInstance(context).getStoreId());
        voucher.put("itc_eligibility",appUser.purchase_itc_eligibility);
        voucher.put("narration",appUser.purchase_narration);
        voucher.put("created_at",appUser.purchase_created_at);
        voucher.put("update_at",appUser.purchase_update_at);
        voucher.put("items",appUser.mListMapForItemPurchase);
        voucher.put("bill_sundry",appUser.mListMapForBillPurchase);
        voucher.put("total", appUser.totalamount);
        voucher.put("items_amount", appUser.items_amount);
        voucher.put("bill_sundry_amount",appUser.billsundrytotalPurchase);
        voucher.put("bill_sundries_amount", appUser.bill_sundries_amount);
        voucher.put("send_email",appUser.email_yes_no);
        //voucher.put("attachment",appUser.purchase_attachment);
        voucher.put("attachment",Preferences.getInstance(context).getAttachment());
        voucher.put("itc_eligibility",Preferences.getInstance(context).getPurchase_gst_nature());
        voucher.put("transport_details",appUser.transport_details);
    }
}
