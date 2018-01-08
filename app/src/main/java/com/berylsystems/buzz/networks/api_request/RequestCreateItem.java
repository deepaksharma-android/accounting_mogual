package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

        if (!Preferences.getInstance(ctx).getitem_alternate_unit_id().equals("")) {
            item.put("alternate_unit_id", Preferences.getInstance(ctx).getitem_alternate_unit_id());
        }


        if (!Preferences.getInstance(ctx).getitem_conversion_factor().equals("")) {
            item.put("conversion_factor", Preferences.getInstance(ctx).getitem_conversion_factor());
        }


        if (!Preferences.getInstance(ctx).getitem_conversion_type().equals("")) {
            item.put("conversion_type", Preferences.getInstance(ctx).getitem_conversion_type());
        }
        if (!Preferences.getInstance(ctx).getitem_opening_stock_quantity_alternate().equals("")) {
            item.put("opening_stock_quantity_alternate", Preferences.getInstance(ctx).getitem_opening_stock_quantity_alternate());

        }

        if (!Preferences.getInstance(ctx).getitem_price_info_sale_price_applied_on().equals("")) {
            item.put("sales_price_applied_on", Preferences.getInstance(ctx).getitem_price_info_sale_price_applied_on());
        }


        if (!Preferences.getInstance(ctx).getitem_price_info_purchase_price_applied_on().equals("")) {
            item.put("purchase_price_applied_on", Preferences.getInstance(ctx).getitem_price_info_purchase_price_applied_on());
        }


        if (!Preferences.getInstance(ctx).getitem_price_info_sales_price_edittext().equals("")) {
            item.put("sales_price_main", Preferences.getInstance(ctx).getitem_price_info_sales_price_edittext());
        }


        if (!Preferences.getInstance(ctx).getitem_price_info_sale_price_alt_unit_edittext().equals("")) {
            item.put("sales_price_alternate", Preferences.getInstance(ctx).getitem_price_info_sale_price_alt_unit_edittext());
        }


        if (!Preferences.getInstance(ctx).getitem_price_info_purchase_price_min_edittext().equals("")) {
            item.put("purchase_price_main", Preferences.getInstance(ctx).getitem_price_info_purchase_price_min_edittext());
        }
        if (!Preferences.getInstance(ctx).getitem_price_info_purchase_price_alt_edittext().equals("")) {
            item.put("purchase_price_alternate", Preferences.getInstance(ctx).getitem_price_info_purchase_price_alt_edittext());
        }


        if (!Preferences.getInstance(ctx).getitem_price_mrp().equals("")) {
            item.put("mrp", Preferences.getInstance(ctx).getitem_price_mrp());
        }else {
            item.put("mrp","0.00");
        }


        if (!Preferences.getInstance(ctx).getitem_price_info_min_sale_price_main_edittext().equals("")) {
            item.put("min_sales_price_main", Preferences.getInstance(ctx).getitem_price_info_min_sale_price_main_edittext());
        }


        if (!Preferences.getInstance(ctx).getitem_price_info_min_sale_price_alt_edittext().equals("")) {
            item.put("min_sale_price_alternate", Preferences.getInstance(ctx).getitem_price_info_min_sale_price_alt_edittext());
        }


        if (!Preferences.getInstance(ctx).getitem_price_info_self_val_price().equals("")) {
            item.put("self_value_price", Preferences.getInstance(ctx).getitem_price_info_self_val_price());
        }


        if (!Preferences.getInstance(ctx).getitem_package_unit_detail_id().equals("")) {
            item.put("item_package_unit_id", Preferences.getInstance(ctx).getitem_package_unit_detail_id());
        }


        if (!Preferences.getInstance(ctx).getitem_conversion_factor_pkg_unit().equals("")) {
            item.put("conversion_factor_package", Preferences.getInstance(ctx).getitem_conversion_factor_pkg_unit());
        }


        if (!Preferences.getInstance(ctx).getitem_salse_price().equals("")) {
            item.put("sale_price", Preferences.getInstance(ctx).getitem_salse_price());
        }


        if (!Preferences.getInstance(ctx).getitem_purchase_price().equals("")) {
            item.put("purchase_price", Preferences.getInstance(ctx).getitem_purchase_price());
        }


        if (!Preferences.getInstance(ctx).getitem_default_unit_for_sales().equals("")) {
            item.put("default_unit_for_sales", Preferences.getInstance(ctx).getitem_default_unit_for_sales());
        }


        if (!Preferences.getInstance(ctx).getitem_default_unit_for_purchase().equals("")) {
            item.put("default_unit_for_purchase", Preferences.getInstance(ctx).getitem_default_unit_for_purchase());
        }


        if (!Preferences.getInstance(ctx).getitem_setting_critical_min_level_qty().equals("")) {
            item.put("minimum_level_quantity", Preferences.getInstance(ctx).getitem_setting_critical_min_level_qty());
        }


        if (!Preferences.getInstance(ctx).getitem_setting_critical_recorded_level_qty().equals("")) {
            item.put("reorder_level_quantity", Preferences.getInstance(ctx).getitem_setting_critical_recorded_level_qty());
        }


        if (!Preferences.getInstance(ctx).getitem_setting_critical_max_level_qty().equals("")) {
            item.put("maximum_level_quantity", Preferences.getInstance(ctx).getitem_setting_critical_max_level_qty());
        }


        if (!Preferences.getInstance(ctx).getitem_setting_critical_max_level_days().equals("")) {
            item.put("minimum_level_days", Preferences.getInstance(ctx).getitem_setting_critical_max_level_days());
        }


        if (!Preferences.getInstance(ctx).getitem_setting_critical_recorded_level_days().equals("")) {
            item.put("reorder_level_days", Preferences.getInstance(ctx).getitem_setting_critical_recorded_level_days());
        }


        if (!Preferences.getInstance(ctx).getitem_setting_critical_max_level_days().equals("")) {
            item.put("maximum_level_days", Preferences.getInstance(ctx).getitem_setting_critical_max_level_days());
        }


        if (!Preferences.getInstance(ctx).getitem_set_critical_level().equals("")) {
            item.put("set_critical_level", Preferences.getInstance(ctx).getitem_set_critical_level());
        }


        if (!Preferences.getInstance(ctx).getitem_serial_number_wise_detail().equals("")) {
            item.put("serial_number_wise_detail", Preferences.getInstance(ctx).getitem_serial_number_wise_detail());
        }


        if (!Preferences.getInstance(ctx).getitem_batch_wise_detail().equals("")) {
            item.put("batch_wise_detail", Preferences.getInstance(ctx).getitem_batch_wise_detail());
        }


        if (!Preferences.getInstance(ctx).getitem_specify_sales_account().equals("")) {
            item.put("specify_sales_account", Preferences.getInstance(ctx).getitem_specify_sales_account());
        }


        if (!Preferences.getInstance(ctx).getitem_specify_purchase_account().equals("")) {
            item.put("specify_purchase_account", Preferences.getInstance(ctx).getitem_specify_purchase_account());
        }


        if (!Preferences.getInstance(ctx).getitem_dont_maintain_stock_balance().equals("")) {
            item.put("dont_maintain_stock_balance", Preferences.getInstance(ctx).getitem_dont_maintain_stock_balance());
        }


        if (!Preferences.getInstance(ctx).getitem_settings_alternate_unit().equals("")) {
            item.put("alternate_unit_detail", Preferences.getInstance(ctx).getitem_settings_alternate_unit());
        }


        if (!Preferences.getInstance(ctx).getitem_description().equals("")) {
            item.put("item_description", Preferences.getInstance(ctx).getitem_description());
        }
        String unit_list=Preferences.getInstance(ctx).getStockSerial();
        List<String> myList = new ArrayList<String>(Arrays.asList(unit_list.split(",")));
        item.put("serial_number",myList);


    }
}