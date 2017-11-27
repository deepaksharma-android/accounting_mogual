package com.berylsystems.buzz.networks.api_response.item;

/**
 * Created by BerylSystems on 11/20/2017.
 */

public class ItemAttribute {


    public int id;
    public String name;
    public String company;
    public String item_group;

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
}
