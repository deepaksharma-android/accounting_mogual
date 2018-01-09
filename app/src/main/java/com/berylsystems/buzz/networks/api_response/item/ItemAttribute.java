package com.berylsystems.buzz.networks.api_response.item;

import java.util.ArrayList;

/**
 * Created by BerylSystems on 11/20/2017.
 */

public class ItemAttribute {


    public int id;
    public String name;
    public String company;
    public String item_group;
    public String alternate_unit_id;
    public String item_group_id;
    public String item_unit_id;
    public Double amount=5.0;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAlternate_unit_id() {
        return alternate_unit_id;
    }

    public void setAlternate_unit_id(String alternate_unit_id) {
        this.alternate_unit_id = alternate_unit_id;
    }

    public String getItem_group_id() {
        return item_group_id;
    }

    public void setItem_group_id(String item_group_id) {
        this.item_group_id = item_group_id;
    }

    public String getItem_unit_id() {
        return item_unit_id;
    }

    public void setItem_unit_id(String item_unit_id) {
        this.item_unit_id = item_unit_id;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getItem_group() {
        return item_group;
    }

    public void setItem_group(String item_group) {
        this.item_group = item_group;
    }

    public String getItem_unit() {
        return item_unit;
    }

    public void setItem_unit(String item_unit) {
        this.item_unit = item_unit;
    }

    public String item_unit;

    public String getTax_category() {
        return tax_category;
    }

    public void setTax_category(String tax_category) {
        this.tax_category = tax_category;
    }

    public String getHsn_number() {
        return hsn_number;
    }

    public void setHsn_number(String hsn_number) {
        this.hsn_number = hsn_number;
    }

    public String tax_category;
    public String hsn_number;

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public double getStock_amount() {
        return stock_amount;
    }

    public void setStock_amount(double stock_amount) {
        this.stock_amount = stock_amount;
    }

    public int stock_quantity;
    public double stock_amount;
    public String conversion_type;

    public int getOpening_stock_quantity_alternate() {
        return opening_stock_quantity_alternate;
    }

    public void setOpening_stock_quantity_alternate(int opening_stock_quantity_alternate) {
        this.opening_stock_quantity_alternate = opening_stock_quantity_alternate;
    }

    public String getConversion_type() {
        return conversion_type;
    }

    public void setConversion_type(String conversion_type) {
        this.conversion_type = conversion_type;
    }

    public double getConversion_factor() {
        return conversion_factor;
    }

    public void setConversion_factor(double conversion_factor) {
        this.conversion_factor = conversion_factor;
    }

    public int opening_stock_quantity_alternate;
    public double conversion_factor;

    public String getDefault_unit_for_purchase() {
        return default_unit_for_purchase;
    }

    public void setDefault_unit_for_purchase(String default_unit_for_purchase) {
        this.default_unit_for_purchase = default_unit_for_purchase;
    }

    public String getItem_package_unit() {
        return item_package_unit;
    }

    public void setItem_package_unit(String item_package_unit) {
        this.item_package_unit = item_package_unit;
    }

    public double getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(double purchase_price) {
        this.purchase_price = purchase_price;
    }

    public double getSales_price() {
        return sales_price;
    }

    public void setSales_price(double sales_price) {
        this.sales_price = sales_price;
    }

    public int getConversion_factor_package() {
        return conversion_factor_package;
    }

    public void setConversion_factor_package(int conversion_factor_package) {
        this.conversion_factor_package = conversion_factor_package;
    }

    public String getDefault_unit_for_sales() {
        return default_unit_for_sales;
    }

    public void setDefault_unit_for_sales(String default_unit_for_sales) {
        this.default_unit_for_sales = default_unit_for_sales;
    }

    public String default_unit_for_purchase;
    public String default_unit_for_sales;
    public int conversion_factor_package;
    public double sales_price;
    public double purchase_price;
    public String item_package_unit;

    public String sales_price_applied_on;
    public String purchase_price_applied_on;

    public double getSales_price_alternate() {
        return sales_price_alternate;
    }

    public void setSales_price_alternate(double sales_price_alternate) {
        this.sales_price_alternate = sales_price_alternate;
    }

    public String getPurchase_price_applied_on() {
        return purchase_price_applied_on;
    }

    public void setPurchase_price_applied_on(String purchase_price_applied_on) {
        this.purchase_price_applied_on = purchase_price_applied_on;
    }

    public String getSales_price_applied_on() {
        return sales_price_applied_on;
    }

    public void setSales_price_applied_on(String sales_price_applied_on) {
        this.sales_price_applied_on = sales_price_applied_on;
    }

    public double getSales_price_main() {
        return sales_price_main;
    }

    public void setSales_price_main(double sales_price_main) {
        this.sales_price_main = sales_price_main;
    }

    public double getPurchase_price_main() {
        return purchase_price_main;
    }

    public void setPurchase_price_main(double purchase_price_main) {
        this.purchase_price_main = purchase_price_main;
    }

    public double getMrp() {
        return mrp;
    }

    public void setMrp(double mrp) {
        this.mrp = mrp;
    }

    public double getMin_sales_price_main() {
        return min_sales_price_main;
    }

    public void setMin_sales_price_main(double min_sales_price_main) {
        this.min_sales_price_main = min_sales_price_main;
    }

    public double getMin_sale_price_alternate() {
        return min_sale_price_alternate;
    }

    public void setMin_sale_price_alternate(double min_sale_price_alternate) {
        this.min_sale_price_alternate = min_sale_price_alternate;
    }

    public double getSelf_value_price() {
        return self_value_price;
    }

    public void setSelf_value_price(double self_value_price) {
        this.self_value_price = self_value_price;
    }

    public double sales_price_alternate;
    public double sales_price_main;
    public double purchase_price_main;
    public double mrp;
    public double min_sales_price_main;
    public double min_sale_price_alternate;
    public double self_value_price;

    public double getPurchase_price_alternate() {
        return purchase_price_alternate;
    }

    public void setPurchase_price_alternate(double purchase_price_alternate) {
        this.purchase_price_alternate = purchase_price_alternate;
    }

    public double purchase_price_alternate;

    public boolean set_critical_level;

    public boolean isSerial_number_wise_detail() {
        return serial_number_wise_detail;
    }

    public void setSerial_number_wise_detail(boolean serial_number_wise_detail) {
        this.serial_number_wise_detail = serial_number_wise_detail;
    }

    public boolean isSet_critical_level() {
        return set_critical_level;
    }

    public void setSet_critical_level(boolean set_critical_level) {
        this.set_critical_level = set_critical_level;
    }

    public boolean isBatch_wise_detail() {
        return batch_wise_detail;
    }

    public void setBatch_wise_detail(boolean batch_wise_detail) {
        this.batch_wise_detail = batch_wise_detail;
    }

    public boolean isAlternate_unit_detail() {
        return alternate_unit_detail;
    }

    public void setAlternate_unit_detail(boolean alternate_unit_detail) {
        this.alternate_unit_detail = alternate_unit_detail;
    }

    public boolean isSpecify_sales_account() {
        return specify_sales_account;
    }

    public void setSpecify_sales_account(boolean specify_sales_account) {
        this.specify_sales_account = specify_sales_account;
    }

    public boolean isSpecify_purchase_account() {
        return specify_purchase_account;
    }

    public void setSpecify_purchase_account(boolean specify_purchase_account) {
        this.specify_purchase_account = specify_purchase_account;
    }

    public boolean isDont_maintain_stock_balance() {
        return dont_maintain_stock_balance;
    }

    public void setDont_maintain_stock_balance(boolean dont_maintain_stock_balance) {
        this.dont_maintain_stock_balance = dont_maintain_stock_balance;
    }

    public boolean serial_number_wise_detail;
    public boolean batch_wise_detail;
    public boolean alternate_unit_detail;
    public boolean specify_sales_account;
    public boolean specify_purchase_account;
    public boolean dont_maintain_stock_balance;

    public String getAlternate_unit() {
        return alternate_unit;
    }

    public void setAlternate_unit(String alternate_unit) {
        this.alternate_unit = alternate_unit;
    }

    public String alternate_unit;

    public int minimum_level_quantity;
    public int reorder_level_quantity;

    public int getMaximum_level_quantity() {
        return maximum_level_quantity;
    }

    public void setMaximum_level_quantity(int maximum_level_quantity) {
        this.maximum_level_quantity = maximum_level_quantity;
    }

    public int getMaximum_level_days() {
        return maximum_level_days;
    }

    public void setMaximum_level_days(int maximum_level_days) {
        this.maximum_level_days = maximum_level_days;
    }

    public int getReorder_level_days() {
        return reorder_level_days;
    }

    public void setReorder_level_days(int reorder_level_days) {
        this.reorder_level_days = reorder_level_days;
    }

    public int getMinimum_level_days() {
        return minimum_level_days;
    }

    public void setMinimum_level_days(int minimum_level_days) {
        this.minimum_level_days = minimum_level_days;
    }

    public int getReorder_level_quantity() {
        return reorder_level_quantity;
    }

    public void setReorder_level_quantity(int reorder_level_quantity) {
        this.reorder_level_quantity = reorder_level_quantity;
    }

    public int getMinimum_level_quantity() {
        return minimum_level_quantity;
    }

    public void setMinimum_level_quantity(int minimum_level_quantity) {
        this.minimum_level_quantity = minimum_level_quantity;
    }

    public int maximum_level_quantity;
    public int minimum_level_days;
    public int reorder_level_days;
    public int maximum_level_days;

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String item_description;

    public int getTax_category_id() {
        return tax_category_id;
    }

    public void setTax_category_id(int tax_category_id) {
        this.tax_category_id = tax_category_id;
    }

    public int tax_category_id;

    public ArrayList<String> getBarcode() {
        return barcode;
    }

    public void setBarcode(ArrayList<String> barcode) {
        this.barcode = barcode;
    }

    public ArrayList<String> barcode;

    public ArrayList<String> getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(ArrayList<String> serial_number) {
        this.serial_number = serial_number;
    }

    public ArrayList<String> serial_number;



}
