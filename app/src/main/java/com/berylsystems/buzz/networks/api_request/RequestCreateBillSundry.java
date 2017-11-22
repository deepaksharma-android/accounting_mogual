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
        bill_sundry.put("name",appUser.bill_sundry_name);
        bill_sundry.put("bill_sundry_type", appUser.bill_sundry_type);
        bill_sundry.put("bill_sundry_nature_id",appUser.bill_sundry_nature);
        bill_sundry.put("default_value", appUser.bill_sundry_default_value);
        bill_sundry.put("amount_of_bill_sundry_fed_as", appUser.bill_sundry_amount_of_bill_sundry_fed_as);
        bill_sundry.put("bill_sundry_of_percentage", appUser.bill_sundry_of_percentage);
        bill_sundry.put("number_of_bill_sundry", appUser.bill_sundry_number_of_bill_sundry);
        bill_sundry.put("consolidate_bill_sundry", appUser.bill_sundry_consolidate_bill_sundry);
        bill_sundry.put("bill_sundry_calculated_on", appUser.bill_sundry_calculated_on);
        bill_sundry.put("bill_sundry_amount_round_off", appUser.bill_sundry_amount_round_off);
        bill_sundry.put("bill_sundry_round_off_limit", appUser.bill_sundry_default_value);
        bill_sundry.put("rounding_off_nearest", appUser.bill_sundry_rouding_off_nearest);



        sale = new HashMap<>();
        sale.put("affect_accounting", appUser.sale_affect_accounting);
        sale.put("affect_sale_amount", appUser.sale_affect_sale_amount);
        sale.put("affect_sale_amount_specify_in", appUser.sale_affect_sale_amount_specify_in);
        sale.put("adjust_in_party_amount", appUser.sale_adjust_in_party_amount);
        sale.put("party_amount_specify_in", appUser.sale_party_amount_specify_in);
        sale.put("account_head_to_post_party_amount", appUser.sale_account_head_to_post_party_amount);
        sale.put("post_over_above", appUser.sale_post_over_above);

        purchase = new HashMap<>();
        purchase.put("affect_accounting",appUser.purchase_affect_accounting);
        purchase.put("affect_purchase_amount",appUser.purchase_affect_purchase_amount);
        purchase.put("affect_purchase_amount_specify_in",appUser.purchase_affect_purchase_amount_specify_in);
        purchase.put("account_head_to_post_purchase_amount",appUser.purchase_account_head_to_post_purchase_amount);
        purchase.put("affect_accounting",appUser.purchase_affect_accounting);
        purchase.put("party_amount_specify_in",appUser.purchase_party_amount_specify_in);
        purchase.put("account_head_to_post_party_amount",appUser.purchase_account_head_to_post_party_amount);
        purchase.put("post_over_above",appUser.purchase_post_over_above);

        cost = new HashMap<>();
        cost.put("goods_in_sale", appUser.cost_goods_in_sale);
        cost.put("goods_in_purchase",appUser.cost_goods_in_purchase);
        cost.put("material_issue",appUser.cost_material_issue);
        cost.put("material_receipt",appUser.cost_material_receipt);
        cost.put("stock_transfer",appUser.cost_stock_transfer);





    }
}