package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.HashMap;

/**
 * Created by BerylSystems on 11/23/2017.
 */

public class RequestCreateSaleVoucher {
    public HashMap voucher;
    public HashMap items;
    public RequestCreateSaleVoucher(Context ctx){
       AppUser appUser = LocalRepositories.getAppUser(ctx);
       voucher = new HashMap<>();
        voucher.put("date", appUser.sale_date);
        voucher.put("voucher_series", appUser.sale_series);
        voucher.put("voucher_number", appUser.sale_vchNo);
        voucher.put("company_id", Preferences.getInstance(ctx).getCid());
        voucher.put("sale_type_id", Preferences.getInstance(ctx).getSale_type_id());
        voucher.put("payment_type", Preferences.getInstance(ctx).getCash_credit());
        voucher.put("account_master_id",Preferences.getInstance(ctx).getParty_id());
        voucher.put("mobile_number", appUser.sale_mobileNumber);
        voucher.put("material_center_id",Preferences.getInstance(ctx).getStoreId());
        voucher.put("narration", appUser.sale_narration);
        voucher.put("items", appUser.mListMapForItemSale);
        voucher.put("bill_sundry_amount",appUser.billsundrytotal);
        voucher.put("bill_sundry", appUser.mListMapForBillSale);
        voucher.put("total", appUser.totalamount);
        voucher.put("items_amount", appUser.items_amount);
        voucher.put("bill_sundries_amount", appUser.bill_sundries_amount);
        voucher.put("send_email",appUser.email_yes_no);
        voucher.put("attachment",appUser.sale_attachment);
   }
}