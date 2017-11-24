package com.berylsystems.buzz.networks.api_response.bill_sundry;

public class BillSundryAttributes {
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

    public Double getDefault_value() {
        return default_value;
    }

    public void setDefault_value(Double default_value) {
        this.default_value = default_value;
    }

    public Double default_value;
    public int number_of_bill_sundry;
    public BillSundryAffectCost bill_sundry_affects_cost;
    public BillSundryAffectSale bill_sundry_affects_sale;
    public BillSundryAffectPurchase bill_sundry_affects_purchase;

    public Boolean getUndefined() {
        return undefined;
    }

    public void setUndefined(Boolean undefined) {
        this.undefined = undefined;
    }

    public Boolean undefined;

    public String getAmount_of_bill_sundry_fed_as() {
        return amount_of_bill_sundry_fed_as;
    }

    public void setAmount_of_bill_sundry_fed_as(String amount_of_bill_sundry_fed_as) {
        this.amount_of_bill_sundry_fed_as = amount_of_bill_sundry_fed_as;
    }

    public String amount_of_bill_sundry_fed_as;
    public String bill_sundry_of_percentage;

    public String getBill_sundry_of_percentage() {
        return bill_sundry_of_percentage;
    }

    public void setBill_sundry_of_percentage(String bill_sundry_of_percentage) {
        this.bill_sundry_of_percentage = bill_sundry_of_percentage;
    }

    public Boolean bill_sundry_amount_round_off;

    public String getBill_sundry_round_off_limit() {
        return bill_sundry_round_off_limit;
    }

    public void setBill_sundry_round_off_limit(String bill_sundry_round_off_limit) {
        this.bill_sundry_round_off_limit = bill_sundry_round_off_limit;
    }

    public Integer getRounding_off_nearest() {
        return rounding_off_nearest;
    }

    public void setRounding_off_nearest(Integer rounding_off_nearest) {
        this.rounding_off_nearest = rounding_off_nearest;
    }

    public Boolean getBill_sundry_amount_round_off() {
        return bill_sundry_amount_round_off;
    }

    public void setBill_sundry_amount_round_off(Boolean bill_sundry_amount_round_off) {
        this.bill_sundry_amount_round_off = bill_sundry_amount_round_off;
    }

    public Integer getBill_sundry_id() {
        return bill_sundry_id;
    }

    public void setBill_sundry_id(Integer bill_sundry_id) {
        this.bill_sundry_id = bill_sundry_id;
    }

    public String getBill_sundry_calculated_on() {
        return bill_sundry_calculated_on;
    }

    public void setBill_sundry_calculated_on(String bill_sundry_calculated_on) {
        this.bill_sundry_calculated_on = bill_sundry_calculated_on;
    }

    public String bill_sundry_round_off_limit;
    public Integer rounding_off_nearest;
    public Integer bill_sundry_id;
    public String bill_sundry_calculated_on;
}