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
        if(!appUser.bill_sundry_amount_of_bill_sundry_fed_as.equals("")) {
            bill_sundry.put("amount_of_bill_sundry_fed_as", appUser.bill_sundry_amount_of_bill_sundry_fed_as);
        }
        if(!appUser.bill_sundry_of_percentage.equals("")) {
            bill_sundry.put("bill_sundry_of_percentage", appUser.bill_sundry_of_percentage);
        }
        if(!appUser.bill_sundry_number_of_bill_sundry.equals("")) {
            bill_sundry.put("number_of_bill_sundry", appUser.bill_sundry_number_of_bill_sundry);
        }
        if(!appUser.bill_sundry_consolidate_bill_sundry.equals("")) {
            bill_sundry.put("consolidate_bill_sundry", appUser.bill_sundry_consolidate_bill_sundry);
        }
        if(!appUser.bill_sundry_calculated_on.equals("")) {
            bill_sundry.put("bill_sundry_calculated_on", appUser.bill_sundry_calculated_on);
        }
        if(!appUser.bill_sundry_amount_round_off.equals("")) {
            bill_sundry.put("bill_sundry_amount_round_off", appUser.bill_sundry_amount_round_off);
        }
        if(!appUser.bill_sundry_default_value.equals("")) {
            bill_sundry.put("bill_sundry_round_off_limit", appUser.bill_sundry_default_value);
        }
        if(!appUser.bill_sundry_rouding_off_nearest.equals("")) {
            bill_sundry.put("rounding_off_nearest", appUser.bill_sundry_rouding_off_nearest);
        }
        if(!appUser.calculated_on_bill_sundry_id.equals("")) {
            bill_sundry.put("bill_sundry_id", appUser.calculated_on_bill_sundry_id);
        }



        sale = new HashMap<>();
        if(!appUser.sale_affect_accounting.equals("")) {
            sale.put("affect_accounting", appUser.sale_affect_accounting);
        }
        if(!appUser.sale_affect_sale_amount.equals("")) {
            sale.put("affect_sale_amount", appUser.sale_affect_sale_amount);
        }
        if(!appUser.sale_affect_sale_amount_specify_in.equals("")) {
            sale.put("affect_sale_amount_specify_in", appUser.sale_affect_sale_amount_specify_in);
        }
        if(!appUser.sale_adjust_in_party_amount.equals("")) {
            sale.put("adjust_in_party_amount", appUser.sale_adjust_in_party_amount);
        }
        if(!appUser.sale_party_amount_specify_in.equals("")) {
            sale.put("party_amount_specify_in", appUser.sale_party_amount_specify_in);
        }
        if(!appUser.sale_account_head_to_post_sale_amount_id.equals("")) {
            sale.put("account_head_to_post_sale_amount", appUser.sale_account_head_to_post_sale_amount_id);
        }
        if(!appUser.sale_account_head_to_post_party_amount_id.equals("")) {
            sale.put("account_head_to_post_party_amount", appUser.sale_account_head_to_post_party_amount_id);
        }
        if(!appUser.sale_post_over_above.equals("")) {
            sale.put("post_over_above", appUser.sale_post_over_above);
        }

        purchase = new HashMap<>();
        if(!appUser.purchase_affect_accounting.equals("")) {
            purchase.put("affect_accounting", appUser.purchase_affect_accounting);
        }
        if(!appUser.purchase_affect_purchase_amount.equals("")) {
            purchase.put("affect_purchase_amount", appUser.purchase_affect_purchase_amount);
        }
        if(!appUser.purchase_affect_purchase_amount_specify_in.equals("")) {
            purchase.put("affect_purchase_amount_specify_in", appUser.purchase_affect_purchase_amount_specify_in);
        }
        if(!appUser.purchase_account_head_to_post_purchase_amount_id.equals("")) {
            purchase.put("account_head_to_post_purchase_amount", appUser.purchase_account_head_to_post_purchase_amount_id);
        }
        if(!appUser.purchase_affect_accounting.equals("")) {
            purchase.put("affect_accounting", appUser.purchase_affect_accounting);
        }
        if(!appUser.purchase_party_amount_specify_in.equals("")) {
            purchase.put("party_amount_specify_in", appUser.purchase_party_amount_specify_in);
        }
        if(!appUser.purchase_account_head_to_post_party_amount_id.equals("")) {
            purchase.put("account_head_to_post_party_amount", appUser.purchase_account_head_to_post_party_amount_id);
        }
        if(!appUser.purchase_post_over_above.equals("")) {
            purchase.put("post_over_above", appUser.purchase_post_over_above);
        }

        cost = new HashMap<>();
        if(!appUser.cost_goods_in_sale.equals("")) {
            cost.put("goods_in_sale", appUser.cost_goods_in_sale);
        }
        if(!appUser.cost_goods_in_purchase.equals("")) {
            cost.put("goods_in_purchase", appUser.cost_goods_in_purchase);
        }
        if(!appUser.cost_material_issue.equals("")) {
            cost.put("material_issue", appUser.cost_material_issue);
        }
        if(!appUser.cost_material_receipt.equals("")) {
            cost.put("material_receipt", appUser.cost_material_receipt);
        }
        if(!appUser.cost_stock_transfer.equals("")) {
            cost.put("stock_transfer", appUser.cost_stock_transfer);
        }





    }
}