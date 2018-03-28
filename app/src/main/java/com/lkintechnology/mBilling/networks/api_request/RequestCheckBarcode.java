package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.activities.company.transaction.purchase.PurchaseAddItemActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import java.util.HashMap;
import java.util.Map;

public class RequestCheckBarcode {
    public Map item;



    public RequestCheckBarcode(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        item = new HashMap<>();
        item.put("item_id", appUser.item_id);
        item.put("id", appUser.voucher_id_barcode);
        item.put("voucher_type", appUser.barcode_voucher_type);
        item.put("barcode", appUser.purchase_item_serail_arr);
/*
        if(PurchaseAddItemActivity.boolForBarcode){
            item.put("barcode", PurchaseAddItemActivity.myListForSerialNo);
        }else {
            item.put("barcode", appUser.purchase_item_serail_arr);
        }
*/

    }
}