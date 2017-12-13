package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

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
        voucher.put("sale_date", appUser.sale_date);
        voucher.put("voucher_series", appUser.sale_series);
        voucher.put("voucher_number", appUser.sale_vchNo);
        voucher.put("company_id", Preferences.getInstance(ctx).getCid());
        voucher.put("sale_type_id", appUser.sale_saleType);
        voucher.put("payment_type", appUser.sale_cash_credit);
        voucher.put("account_master_id", appUser.sale_partyName);
        voucher.put("mobile_number", appUser.sale_mobileNumber);
        voucher.put("material_center_id", appUser.sale_store);
        voucher.put("narration", appUser.sale_narration);
        voucher.put("items", appUser.mListMapForItemSale);
        voucher.put("bill_sundry", appUser.mListMapForBillSale);
   }
}
