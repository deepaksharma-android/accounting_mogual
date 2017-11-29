package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateBillSundry {
    public HashMap bill_sundry;
    public Map<String, String> sale;
    public Map<String, String> purchase;
    public Map<String, String> cost;


    public RequestCreateBillSundry(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);

        bill_sundry = new HashMap<>();
        bill_sundry.put("name", appUser.bill_sundry_name);
        bill_sundry.put("bill_sundry_type", appUser.bill_sundry_type);
        bill_sundry.put("bill_sundry_nature_id", appUser.bill_sundry_nature);
        bill_sundry.put("default_value", appUser.bill_sundry_default_value);

        if (!Preferences.getInstance(ctx).getbill_sundry_amount_of_bill_sundry_fed_as_percent().equals("")) {
            bill_sundry.put("bill_sundry_percentage_value", Preferences.getInstance(ctx).getbill_sundry_amount_of_bill_sundry_fed_as_percent());
        }
        if (!Preferences.getInstance(ctx).getbill_sundry_amount_of_bill_sundry_fed_as().equals("")) {
            bill_sundry.put("amount_of_bill_sundry_fed_as", Preferences.getInstance(ctx).getbill_sundry_amount_of_bill_sundry_fed_as());
        }
        if (!Preferences.getInstance(ctx).getbill_sundry_of_percentage().equals("")) {
            bill_sundry.put("bill_sundry_of_percentage", Preferences.getInstance(ctx).getbill_sundry_of_percentage());
        }
        if (!Preferences.getInstance(ctx).getbill_sundry_number_of_bill_sundry().equals("")) {
            bill_sundry.put("number_of_bill_sundry", Preferences.getInstance(ctx).getbill_sundry_number_of_bill_sundry());
        }
        if (!Preferences.getInstance(ctx).getbill_sundry_consolidate_bill_sundry().equals("")) {
            bill_sundry.put("consolidate_bill_sundry", Preferences.getInstance(ctx).getbill_sundry_consolidate_bill_sundry());
        }
        if (!Preferences.getInstance(ctx).getbill_sundry_calculated_on().equals("")) {
            bill_sundry.put("bill_sundry_calculated_on", Preferences.getInstance(ctx).getbill_sundry_calculated_on());
        }
        if (!Preferences.getInstance(ctx).getbill_sundry_amount_round_off().equals("")) {
            bill_sundry.put("bill_sundry_amount_round_off", Preferences.getInstance(ctx).getbill_sundry_amount_round_off());
        }
        if (!Preferences.getInstance(ctx).getbill_sundry_rounding_off_limit().equals("")) {
            bill_sundry.put("bill_sundry_round_off_limit", Preferences.getInstance(ctx).getbill_sundry_rounding_off_limit());
        }
        if (!Preferences.getInstance(ctx).getbill_sundry_rouding_off_nearest().equals("")) {
            bill_sundry.put("rounding_off_nearest", Preferences.getInstance(ctx).getbill_sundry_rouding_off_nearest());
        }
        if (!Preferences.getInstance(ctx).getcalculated_on_bill_sundry_id().equals("")) {
            bill_sundry.put("bill_sundry_id", Preferences.getInstance(ctx).getcalculated_on_bill_sundry_id());
        }


        sale = new HashMap<>();
        if (!Preferences.getInstance(ctx).getsale_affect_accounting().equals("")) {
            sale.put("affect_accounting", Preferences.getInstance(ctx).getsale_affect_accounting());
        }

        if (!Preferences.getInstance(ctx).getsale_affect_sale_amount().equals("")) {
            sale.put("affect_sale_amount", Preferences.getInstance(ctx).getsale_affect_sale_amount());
        }
        if (!Preferences.getInstance(ctx).getsale_affect_sale_amount_specify_in().equals("")) {
            sale.put("affect_sale_amount_specify_in", Preferences.getInstance(ctx).getsale_affect_sale_amount_specify_in());
        }
        if (!Preferences.getInstance(ctx).getsale_adjust_in_party_amount().equals("")) {
            sale.put("adjust_in_party_amount", Preferences.getInstance(ctx).getsale_adjust_in_party_amount());
        }
        if (!Preferences.getInstance(ctx).getsale_party_amount_specify_in().equals("")) {
            sale.put("party_amount_specify_in", Preferences.getInstance(ctx).getsale_party_amount_specify_in());
        }
        if (!Preferences.getInstance(ctx).getsale_account_head_to_post_party_amount_id().equals("")) {
            sale.put("account_head_to_post_party_amount", Preferences.getInstance(ctx).getsale_account_head_to_post_party_amount_id());
        }
        if (!Preferences.getInstance(ctx).getsale_account_head_to_post_sale_amount_id().equals("")) {
            sale.put("account_head_to_post_sale_amount", Preferences.getInstance(ctx).getsale_account_head_to_post_sale_amount_id());
        }
        if (!Preferences.getInstance(ctx).getsale_post_over_above().equals("")) {
            sale.put("post_over_above", Preferences.getInstance(ctx).getsale_post_over_above());
        }


        purchase = new HashMap<>();
        if (!Preferences.getInstance(ctx).getpurchase_affect_accounting().equals("")) {
            purchase.put("affect_accounting", Preferences.getInstance(ctx).getpurchase_affect_accounting());
        }
        if (!Preferences.getInstance(ctx).getpurchase_affect_purchase_amount().equals("")) {
            purchase.put("affect_purchase_amount", Preferences.getInstance(ctx).getpurchase_affect_purchase_amount());
        }
        if (!Preferences.getInstance(ctx).getpurchase_affect_purchase_amount_specify_in().equals("")) {
            purchase.put("affect_purchase_amount_specify_in", Preferences.getInstance(ctx).getpurchase_affect_purchase_amount_specify_in());
        }
        if (!Preferences.getInstance(ctx).getpurchase_account_head_to_post_party_amount_id().equals("")) {
            purchase.put("account_head_to_post_purchase_amount", Preferences.getInstance(ctx).getpurchase_account_head_to_post_party_amount_id());
        }
        if (!Preferences.getInstance(ctx).getpurchase_adjust_in_party_amount().equals("")) {
            purchase.put("adjust_in_party_amount", Preferences.getInstance(ctx).getpurchase_adjust_in_party_amount());
        }
        if (!Preferences.getInstance(ctx).getpurchase_party_amount_specify_in().equals("")) {
            purchase.put("party_amount_specify_in", Preferences.getInstance(ctx).getpurchase_party_amount_specify_in());
        }
        if (!Preferences.getInstance(ctx).getpurchase_account_head_to_post_purchase_amount_id().equals("")) {
            purchase.put("account_head_to_post_party_amount", Preferences.getInstance(ctx).getpurchase_account_head_to_post_purchase_amount_id());
        }
        if (!Preferences.getInstance(ctx).getpurchase_post_over_above().equals("")) {
            purchase.put("post_over_above", Preferences.getInstance(ctx).getpurchase_post_over_above());
        }


        cost = new HashMap<>();
        if (!Preferences.getInstance(ctx).getcost_goods_in_sale().equals("")) {
            cost.put("goods_in_sale", Preferences.getInstance(ctx).getcost_goods_in_sale());
        }
        if (!Preferences.getInstance(ctx).getcost_goods_in_purchase().equals("")) {
            cost.put("goods_in_purchase", Preferences.getInstance(ctx).getcost_goods_in_purchase());
        }
        if (!Preferences.getInstance(ctx).getcost_material_issue().equals("")) {
            cost.put("material_issue", Preferences.getInstance(ctx).getcost_material_issue());
        }
        if (!Preferences.getInstance(ctx).getcost_material_receipt().equals("")) {
            cost.put("material_receipt", Preferences.getInstance(ctx).getcost_material_receipt());
        }
        if (!Preferences.getInstance(ctx).getcost_stock_transfer().equals("")) {
            cost.put("stock_transfer", Preferences.getInstance(ctx).getcost_stock_transfer());
        }


    }
}