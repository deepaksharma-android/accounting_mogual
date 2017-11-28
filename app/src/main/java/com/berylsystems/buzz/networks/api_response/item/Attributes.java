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
}