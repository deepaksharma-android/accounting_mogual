package com.berylsystems.buzz.networks.api_response.account;

public class AccountAttributes {
    public String id;
    public String account_group;
    public String name;
    public String mobile_number;
    public String address;
    public String city;
    public String state;
    public Boolean undefined;
    public String adhar_number;
    public String gstin_number;
    public String pan_number;
    public String credit_limit;
    public String credit_days_for_sale;
    public String credit_days_for_purchase;
    public String amount_receivable;
    public String amount_payable;
    public Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAccount_group() {
        return account_group;
    }

    public void setAccount_group(String account_group) {
        this.account_group = account_group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getUndefined() {
        return undefined;
    }

    public void setUndefined(Boolean undefined) {
        this.undefined = undefined;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getAmount_payable() {
        return amount_payable;
    }

    public void setAmount_payable(String amount_payable) {
        this.amount_payable = amount_payable;
    }

    public String getAmount_receivable() {
        return amount_receivable;
    }

    public void setAmount_receivable(String amount_receivable) {
        this.amount_receivable = amount_receivable;
    }

    public String getCredit_days_for_purchase() {
        return credit_days_for_purchase;
    }

    public void setCredit_days_for_purchase(String credit_days_for_purchase) {
        this.credit_days_for_purchase = credit_days_for_purchase;
    }

    public String getCredit_days_for_sale() {
        return credit_days_for_sale;
    }

    public void setCredit_days_for_sale(String credit_days_for_sale) {
        this.credit_days_for_sale = credit_days_for_sale;
    }

    public String getCredit_limit() {
        return credit_limit;
    }

    public void setCredit_limit(String credit_limit) {
        this.credit_limit = credit_limit;
    }

    public String getPan_number() {
        return pan_number;
    }

    public void setPan_number(String pan_number) {
        this.pan_number = pan_number;
    }

    public String getGstin_number() {
        return gstin_number;
    }

    public void setGstin_number(String gstin_number) {
        this.gstin_number = gstin_number;
    }

    public String getAdhar_number() {
        return adhar_number;
    }

    public void setAdhar_number(String adhar_number) {
        this.adhar_number = adhar_number;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }




}