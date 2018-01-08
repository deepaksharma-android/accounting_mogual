package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.HashMap;

/**
 * Created by BerylSystems on 11/23/2017.
 */

public class RequestCreatePurchaseReturn {
    public HashMap voucher;
    public RequestCreatePurchaseReturn(Context ctx){
       AppUser appUser = LocalRepositories.getAppUser(ctx);
       voucher = new HashMap<>();
        voucher.put("date", appUser.purchase_date);
        voucher.put("voucher_series", appUser.purchase_voucher_series);
        voucher.put("voucher_number", appUser.purchase_voucher_number);
        voucher.put("company_id", Preferences.getInstance(ctx).getCid());
        voucher.put("purchase_type_id", Preferences.getInstance(ctx).getSale_type_id());
        voucher.put("payment_type", Preferences.getInstance(ctx).getCash_credit());
        voucher.put("account_master_id", Preferences.getInstance(ctx).getParty_id());
        voucher.put("mobile_number", appUser.purchase_mobile_number);
        voucher.put("material_center_id", Preferences.getInstance(ctx).getStoreId());
        voucher.put("narration", appUser.purchase_narration);
        voucher.put("items", appUser.mListMapForItemPurchaseReturn);
        voucher.put("bill_sundry",appUser.mListMapForBillPurchaseReturn);
        voucher.put("total", appUser.totalamount);
        voucher.put("items_amount", appUser.items_amount);
        voucher.put("bill_sundries_amount", appUser.bill_sundries_amount);
        voucher.put("send_email",appUser.email_yes_no);


   }
}
