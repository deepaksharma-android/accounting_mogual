package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.activities.company.transaction.purchase.PurchaseAddItemActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale_return.SaleReturnAddItemActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestCheckBarcode {
    public Map item;
    public static Boolean bollForBarcode;



    public RequestCheckBarcode(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        item = new HashMap<>();
        item.put("item_id", appUser.item_id);
        item.put("id", appUser.voucher_id_barcode);
        item.put("voucher_type", appUser.barcode_voucher_type);
        if(RequestCheckBarcode.bollForBarcode!=null) {
            if (RequestCheckBarcode.bollForBarcode) {
                if (PurchaseAddItemActivity.boolForBarcode) {
                    for (int i = 0; i < PurchaseAddItemActivity.myListForSerialNo.size(); i++) {
                        if (PurchaseAddItemActivity.myListForSerialNo.get(i).equals("")) {
                            PurchaseAddItemActivity.myListForSerialNo.remove(PurchaseAddItemActivity.myListForSerialNo.get(i));
                        }
                    }
                    item.put("barcode", PurchaseAddItemActivity.myListForSerialNo);
                } else {
                    for (int i = 0; i < appUser.purchase_item_serail_arr.size(); i++) {
                        if (appUser.purchase_item_serail_arr.get(i).equals("")) {
                            appUser.purchase_item_serail_arr.remove(appUser.purchase_item_serail_arr.get(i));
                        }
                    }
                    item.put("barcode", appUser.purchase_item_serail_arr);
                }
            } else if (!RequestCheckBarcode.bollForBarcode) {
                if (SaleReturnAddItemActivity.boolForBarcode) {
                    for (int i = 0; i < SaleReturnAddItemActivity.myListForSerialNo.size(); i++) {
                        if (SaleReturnAddItemActivity.myListForSerialNo.get(i).equals("")) {
                            SaleReturnAddItemActivity.myListForSerialNo.remove(SaleReturnAddItemActivity.myListForSerialNo.get(i));
                        }
                    }
                    item.put("barcode", SaleReturnAddItemActivity.myListForSerialNo);
                } else {
                    for (int i = 0; i < appUser.purchase_item_serail_arr.size(); i++) {
                        if (appUser.purchase_item_serail_arr.get(i).equals("")) {
                            appUser.purchase_item_serail_arr.remove(appUser.purchase_item_serail_arr.get(i));
                        }
                    }
                    item.put("barcode", appUser.purchase_item_serail_arr);
                }
            }
        }
        /*else{
            String unit_list= Preferences.getInstance(ctx).getStockSerial();
            List<String> myList = new ArrayList<String>(Arrays.asList(unit_list.split(",")));
            item.put("barcode", myList);
        }*/
    }
}