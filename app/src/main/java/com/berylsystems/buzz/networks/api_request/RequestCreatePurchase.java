package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

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
        voucher.put("puchase_type_id",appUser.purchase_puchase_type_id);
        voucher.put("payment_type",appUser.purchase_payment_type);
        voucher.put("mobile_number",appUser.purchase_mobile_number);
        voucher.put("account_master_id",appUser.purchase_account_master_id);
        voucher.put("material_center_id",appUser.purchase_material_center_id);
        voucher.put("itc_eligibility",appUser.purchase_itc_eligibility);
        voucher.put("narration",appUser.purchase_narration);
        voucher.put("created_at",appUser.purchase_created_at);
        voucher.put("update_at",appUser.purchase_update_at);
        voucher.put("items",appUser.mListMapForItemPurchase);

    }
}
