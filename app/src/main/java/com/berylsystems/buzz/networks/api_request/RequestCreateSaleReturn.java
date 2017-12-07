package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

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
        voucher.put("sale_type_id", appUser.sale_return_saleType);
        voucher.put("payment_type", appUser.sale_return_cash_credit);
        voucher.put("account_master_id", appUser.sale_return_partyName);
        voucher.put("mobile_number", appUser.sale_return_mobileNumber);
        voucher.put("material_center_id", appUser.sale_return_store);
        voucher.put("narration", appUser.sale_return_narration);
        voucher.put("item_id", appUser.mListMapForItemSaleReturn);


   }
}