package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.HashMap;

/**
 * Created by BerylSystems on 11/23/2017.
 */

public class RequestCreateSaleReturn {
    public HashMap voucher;
    public RequestCreateSaleReturn(Context ctx){
       AppUser appUser = LocalRepositories.getAppUser(ctx);
       voucher = new HashMap<>();
        voucher.put("date", appUser.sale_return_date);
        voucher.put("voucher_series", appUser.sale_return_series);
        voucher.put("voucher_number", appUser.sale_return_vchNo);
        voucher.put("company_id", Preferences.getInstance(ctx).getCid());
        voucher.put("purchase_type_id", Preferences.getInstance(ctx).getPurchase_type_id());
        voucher.put("payment_type", Preferences.getInstance(ctx).getCash_credit());
        voucher.put("account_master_id", Preferences.getInstance(ctx).getParty_id());
        voucher.put("mobile_number", appUser.sale_return_mobileNumber);
        voucher.put("material_center_id", Preferences.getInstance(ctx).getStoreId());
        voucher.put("narration", appUser.sale_return_narration);
        voucher.put("items", appUser.mListMapForItemSaleReturn);
        voucher.put("bill_sundry",appUser.mListMapForBillSaleReturn);
        voucher.put("bill_sundry_amount",appUser.billsundrytotal);
        voucher.put("total", appUser.totalamount);
        voucher.put("items_amount", appUser.items_amount);
        voucher.put("bill_sundries_amount", appUser.bill_sundries_amount);
        voucher.put("send_email",appUser.email_yes_no);


   }
}
