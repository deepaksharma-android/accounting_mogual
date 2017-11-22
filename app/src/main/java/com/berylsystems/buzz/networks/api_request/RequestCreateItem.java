package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateItem {
    public HashMap item;

    public RequestCreateItem(Context ctx) {
        AppUser appUser = LocalRepositories.getAppUser(ctx);
        item = new HashMap<>();
        item.put("name", appUser.item_name);
        item.put("company_id", Preferences.getInstance(ctx).getCid());
        item.put("item_group_id", appUser.item_group_id);
        item.put("item_unit_id", appUser.item_unit_id);
        item.put("stock_quantity", appUser.item_stock_quantity);
        item.put("stock_amount", appUser.item_stock_amount);
        item.put("alternate_unit_id",appUser.item_alternate_unit_id);
        item.put("conversion_factor", appUser.item_conversion_factor);
        item.put("conversion_type",appUser.item_conversion_type);
        item.put("opening_stock_quantity_alternate",appUser.item_opening_stock_quantity_alternate);
        item.put("item_price_info_id", appUser.item_price_info_id);
        item.put("item_package_unit_detail_id", appUser.item_package_unit_detail_id);
        item.put("default_unit_for_sales", appUser.item_default_unit_for_sales);
        item.put("default_unit_for_purchase", appUser.item_default_unit_for_purchase);
        item.put("tax_category", appUser.item_tax_category);
        item.put("hsn_number", appUser.item_hsn_number);
        item.put("item_description", appUser.item_description);
        item.put("serial_number_wise_detail", appUser.item_serial_number_wise_detail);
        item.put("batch_wise_detail", appUser.item_batch_wise_detail);
        item.put("set_critical_level", appUser.item_set_critical_level);
        item.put("item_critical_level_id",appUser.item_critical_level_id);
        item.put("parameterized_detail", appUser.item_parameterized_detail);
        item.put("exp_month_date_required",appUser.item_exp_month_date_required);
        item.put("specify_sales_account",appUser.item_specify_sales_account);
        item.put("specify_purchase_account", appUser.item_specify_purchase_account);
        item.put("dont_maintain_stock_balance", appUser.item_dont_maintain_stock_balance);
    }
}