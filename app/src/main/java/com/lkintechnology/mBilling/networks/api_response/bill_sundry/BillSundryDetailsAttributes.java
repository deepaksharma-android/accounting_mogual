package com.lkintechnology.mBilling.networks.api_response.bill_sundry;

public class BillSundryDetailsAttributes {
    public String name;
    public String bill_sundry_type;

    public String getBill_sundry_nature() {
        return bill_sundry_nature;
    }

    public void setBill_sundry_nature(String bill_sundry_nature) {
        this.bill_sundry_nature = bill_sundry_nature;
    }

    public BillSundryAffectPurchase getBill_sundry_affects_purchase() {
        return bill_sundry_affects_purchase;
    }

    public void setBill_sundry_affects_purchase(BillSundryAffectPurchase bill_sundry_affects_purchase) {
        this.bill_sundry_affects_purchase = bill_sundry_affects_purchase;
    }

    public BillSundryAffectSale getBill_sundry_affects_sale() {
        return bill_sundry_affects_sale;
    }

    public void setBill_sundry_affects_sale(BillSundryAffectSale bill_sundry_affects_sale) {
        this.bill_sundry_affects_sale = bill_sundry_affects_sale;
    }

    public BillSundryAffectCost getBill_sundry_affects_cost() {
        return bill_sundry_affects_cost;
    }

    public void setBill_sundry_affects_cost(BillSundryAffectCost bill_sundry_affects_cost) {
        this.bill_sundry_affects_cost = bill_sundry_affects_cost;
    }

    public int getNumber_of_bill_sundry() {
        return number_of_bill_sundry;
    }

    public void setNumber_of_bill_sundry(int number_of_bill_sundry) {
        this.number_of_bill_sundry = number_of_bill_sundry;
    }

    public int getDefault_value() {
        return default_value;
    }

    public void setDefault_value(int default_value) {
        this.default_value = default_value;
    }

    public String getBill_sundry_type() {
        return bill_sundry_type;
    }

    public void setBill_sundry_type(String bill_sundry_type) {
        this.bill_sundry_type = bill_sundry_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String bill_sundry_nature;
    public int default_value;
    public int number_of_bill_sundry;
    public BillSundryAffectCost bill_sundry_affects_cost;
    public BillSundryAffectSale bill_sundry_affects_sale;
    public BillSundryAffectPurchase bill_sundry_affects_purchase;

}