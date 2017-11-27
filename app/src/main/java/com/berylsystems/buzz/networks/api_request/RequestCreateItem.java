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
        item.put("tax_category_id", appUser.item_tax_category);
        item.put("hsn_number", appUser.item_hsn_number);

        if (!Preferences.getInstance(ctx).getItem_stock_quantity().equals("")) {
            item.put("stock_quantity", Preferences.getInstance(ctx).getItem_stock_quantity());
        }
        if (!Preferences.getInstance(ctx).getItem_stock_amount().equals("")) {
            item.put("stock_amount", Preferences.getInstance(ctx).getItem_stock_amount());
        }
        if (appUser.item_alternate_unit_id != null) {
            if (!appUser.item_alternate_unit_id.equals("")) {
                item.put("alternate_unit_id", appUser.item_alternate_unit_id);
            }
        }
        if (appUser.item_conversion_factor != null) {
            if (!appUser.item_conversion_factor.equals("")) {
                item.put("conversion_factor", appUser.item_conversion_factor);
            }
        }

        if (appUser.item_conversion_type != null) {
            if (!appUser.item_conversion_type.equals("")) {
                item.put("conversion_type", appUser.item_conversion_type);
            }
        }
        if (appUser.item_opening_stock_quantity_alternate != null) {
            if (!appUser.item_opening_stock_quantity_alternate.equals("")) {
                item.put("opening_stock_quantity_alternate", appUser.item_opening_stock_quantity_alternate);
            }
        }
        if (appUser.item_price_info_sale_price_applied_on != null) {
            if (!appUser.item_price_info_sale_price_applied_on.equals("")) {
                item.put("sales_price_applied_on", appUser.item_price_info_sale_price_applied_on);
            }
        }
        if (appUser.item_price_info_purchase_price_applied_on != null) {
            if (!appUser.item_price_info_purchase_price_applied_on.equals("")) {
                item.put("purchase_price_applied_on", appUser.item_price_info_purchase_price_applied_on);
            }
        }
        if (appUser.item_price_info_sales_price_edittext != null) {
            if (!appUser.item_price_info_sales_price_edittext.equals("")) {
                item.put("sales_price_main", appUser.item_price_info_sales_price_edittext);
            }
        }
        if (appUser.item_price_info_sale_price_alt_unit_edittext != null) {
            if (!appUser.item_price_info_sale_price_alt_unit_edittext.equals("")) {
                item.put("sales_price_alternate", appUser.item_price_info_sale_price_alt_unit_edittext);
            }
        }
        if (appUser.item_price_info_purchase_price_min_edittext != null) {
            if (!appUser.item_price_info_purchase_price_min_edittext.equals("")) {
                item.put("purchase_price_main", appUser.item_price_info_purchase_price_min_edittext);
            }
        }
        if (appUser.item_price_mrp != null) {
            if (!appUser.item_price_mrp.equals("")) {
                item.put("mrp", appUser.item_price_mrp);
            }
        }
        if (appUser.item_price_info_min_sale_price_main_edittext != null) {
            if (!appUser.item_price_info_min_sale_price_main_edittext.equals("")) {
                item.put("min_sales_price_main", appUser.item_price_info_min_sale_price_main_edittext);
            }
        }
        if (appUser.item_price_info_min_sale_price_alt_edittext != null) {
            if (!appUser.item_price_info_min_sale_price_alt_edittext.equals("")) {
                item.put("min_sale_price_alternate", appUser.item_price_info_min_sale_price_alt_edittext);
            }
        }
        if (appUser.item_price_info_self_val_price != null) {
            if (!appUser.item_price_info_self_val_price.equals("")) {
                item.put("self_value_price", appUser.item_price_info_self_val_price);
            }
        }
        if (appUser.item_package_unit_detail_id != null) {
            if (!appUser.item_package_unit_detail_id.equals("")) {
                item.put("item_package_unit_id", appUser.item_package_unit_detail_id);
            }
        }
        if (appUser.item_conversion_factor_pkg_unit != null) {
            if (!appUser.item_conversion_factor_pkg_unit.equals("")) {
                item.put("conversion_factor_package", appUser.item_conversion_factor_pkg_unit);
            }
        }
        if (appUser.item_salse_price != null) {
            if (!appUser.item_salse_price.equals("")) {
                item.put("sale_price", appUser.item_salse_price);
            }
        }
        if (appUser.item_specify_purchase_account != null) {
            if (!appUser.item_specify_purchase_account.equals("")) {
                item.put("purchase_price", appUser.item_specify_purchase_account);
            }
        }
        if (appUser.item_default_unit_for_sales != null) {
            if (!appUser.item_default_unit_for_sales.equals("")) {
                item.put("default_unit_for_sales", appUser.item_default_unit_for_sales);
            }
        }
        if (appUser.item_default_unit_for_purchase != null) {
            if (!appUser.item_default_unit_for_purchase.equals("")) {
                item.put("default_unit_for_purchase", appUser.item_default_unit_for_purchase);
            }
        }
        if (appUser.item_setting_critical_min_level_qty != null) {
            if (!appUser.item_setting_critical_min_level_qty.equals("")) {
                item.put("minimum_level_quantity", appUser.item_setting_critical_min_level_qty);
            }
        }
        if (appUser.item_setting_critical_recorded_level_qty != null) {
            if (!appUser.item_setting_critical_recorded_level_qty.equals("")) {
                item.put("reorder_level_quantity", appUser.item_setting_critical_recorded_level_qty);
            }
        }
        if (appUser.item_setting_critical_max_level_qty != null) {
            if (!appUser.item_setting_critical_max_level_qty.equals("")) {
                item.put("maximum_level_quantity", appUser.item_setting_critical_max_level_qty);
            }
        }
        if (appUser.item_setting_critical_max_level_days != null) {
            if (!appUser.item_setting_critical_max_level_days.equals("")) {
                item.put("minimum_level_days", appUser.item_setting_critical_max_level_days);
            }
        }
        if (appUser.item_setting_critical_recorded_level_days != null) {
            if (!appUser.item_setting_critical_recorded_level_days.equals("")) {
                item.put("reorder_level_days", appUser.item_setting_critical_recorded_level_days);
            }
        }
        if (appUser.item_setting_critical_max_level_days != null) {
            if (!appUser.item_setting_critical_max_level_days.equals("")) {
                item.put("maximum_level_days", appUser.item_setting_critical_max_level_days);
            }
        }
        if (appUser.item_set_critical_level != null) {
            if (!appUser.item_set_critical_level.equals("")) {
                item.put("set_critical_level", appUser.item_set_critical_level);
            }
        }
        if (appUser.item_serial_number_wise_detail != null) {
            if (!appUser.item_serial_number_wise_detail.equals("")) {
                item.put("serial_number_wise_detail", appUser.item_serial_number_wise_detail);
            }
        }
        if (appUser.item_batch_wise_detail != null) {
            if (!appUser.item_batch_wise_detail.equals("")) {
                item.put("batch_wise_detail", appUser.item_batch_wise_detail);
            }
        }
        if (appUser.item_specify_sales_account != null) {
            if (!appUser.item_specify_sales_account.equals("")) {
                item.put("specify_sales_account", appUser.item_specify_sales_account);
            }
        }
        if (appUser.item_specify_purchase_account != null) {
            if (!appUser.item_specify_purchase_account.equals("")) {
                item.put("specify_purchase_account", appUser.item_specify_purchase_account);
            }
        }
        if (appUser.item_dont_maintain_stock_balance != null) {
            if (!appUser.item_dont_maintain_stock_balance.equals("")) {
                item.put("dont_maintain_stock_balance", appUser.item_dont_maintain_stock_balance);
            }
        }
        if (appUser.item_settings_alternate_unit != null) {
            if (!appUser.item_settings_alternate_unit.equals("")) {
                item.put("alternate_unit_detail", appUser.item_settings_alternate_unit);
            }
        }
        if (appUser.item_description != null) {
            if (!appUser.item_description.equals("")) {
                item.put("item_description", appUser.item_description);
            }
        }


    }
}