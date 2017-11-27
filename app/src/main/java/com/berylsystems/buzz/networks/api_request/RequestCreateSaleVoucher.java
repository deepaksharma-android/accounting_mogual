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
    public HashMap item;
    public RequestCreateSaleVoucher(Context ctx){
       AppUser appUser = LocalRepositories.getAppUser(ctx);
       item = new HashMap<>();
        item.put("sale_date", appUser.sale_date);
        item.put("voucher_series", appUser.sale_series);
        item.put("voucher_number", appUser.sale_vchNo);
        item.put("company_id", Preferences.getInstance(ctx).getCid());
        item.put("sale_type_id", appUser.sale_saleType);
        item.put("payment_type", appUser.sale_cash_credit);
        item.put("account_master_id", appUser.sale_partyName);
        item.put("mobile_number", appUser.sale_mobileNumber);
        item.put("material_center_id", appUser.sale_store);
        item.put("narration", appUser.sale_narration);
   }
}
