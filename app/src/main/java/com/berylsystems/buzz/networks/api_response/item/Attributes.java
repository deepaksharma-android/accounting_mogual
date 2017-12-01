package com.berylsystems.buzz.networks.api_response.item;

public class Attributes {
    public String name;
    public String company;
    public String item_group;
    public String item_unit;

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

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlternate_unit() {
        return alternate_unit;
    }

    public void setAlternate_unit(String alternate_unit) {
        this.alternate_unit = alternate_unit;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String alternate_unit;
    public String item_description;

    public double getSales_price_main() {
        return sales_price_main;
    }

    public void setSales_price_main(double sales_price_main) {
        this.sales_price_main = sales_price_main;
    }

    public double getSales_price_alternate() {
        return sales_price_alternate;
    }

    public void setSales_price_alternate(double sales_price_alternate) {
        this.sales_price_alternate = sales_price_alternate;
    }

    public double sales_price_main;
    public double sales_price_alternate;
    public boolean serial_number_wise_detail;

    public boolean isBatch_wise_detail() {
        return batch_wise_detail;
    }

    public void setBatch_wise_detail(boolean batch_wise_detail) {
        this.batch_wise_detail = batch_wise_detail;
    }

    public boolean isSerial_number_wise_detail() {
        return serial_number_wise_detail;
    }

    public void setSerial_number_wise_detail(boolean serial_number_wise_detail) {
        this.serial_number_wise_detail = serial_number_wise_detail;
    }

    public boolean batch_wise_detail;

    public String getSales_price_applied_on() {
        return sales_price_applied_on;
    }

    public void setSales_price_applied_on(String sales_price_applied_on) {
        this.sales_price_applied_on = sales_price_applied_on;
    }

    public String sales_price_applied_on;

    public double getConversion_factor() {
        return conversion_factor;
    }

    public void setConversion_factor(double conversion_factor) {
        this.conversion_factor = conversion_factor;
    }

    public double conversion_factor;

    public String getDefault_unit_for_sales() {
        return default_unit_for_sales;
    }

    public void setDefault_unit_for_sales(String default_unit_for_sales) {
        this.default_unit_for_sales = default_unit_for_sales;
    }

    public String default_unit_for_sales;

    public int getConversion_factor_package() {
        return conversion_factor_package;
    }

    public void setConversion_factor_package(int conversion_factor_package) {
        this.conversion_factor_package = conversion_factor_package;
    }



    public int conversion_factor_package;

    public double getSale_price() {
        return sale_price;
    }

    public void setSale_price(double sale_price) {
        this.sale_price = sale_price;
    }

    public double sale_price;

    public String getItem_package_unit() {
        return item_package_unit;
    }

    public void setItem_package_unit(String item_package_unit) {
        this.item_package_unit = item_package_unit;
    }

    public String item_package_unit;
}