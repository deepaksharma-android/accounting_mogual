package com.lkintechnology.mBilling.networks.api_response.packages;

import java.util.ArrayList;

public class PackageAttributes {
    public String name;
    public String description;
    public double amount;
    public double yearly_price;
    public double monthly_price;
    public double six_month_price;
    public double five_year_price;
    public boolean active;
    public ArrayList<Features> features;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ArrayList<Features> getFeatures() {
        return features;
    }

    public void setFeatures(ArrayList<Features> features) {
        this.features = features;
    }

    public double getYearly_price() {
        return yearly_price;
    }

    public void setYearly_price(double yearly_price) {
        this.yearly_price = yearly_price;
    }

    public double getMonthly_price() {
        return monthly_price;
    }

    public void setMonthly_price(double monthly_price) {
        this.monthly_price = monthly_price;
    }

    public double getSix_month_price() {
        return six_month_price;
    }

    public void setSix_month_price(double six_month_price) {
        this.six_month_price = six_month_price;
    }

    public double getFive_year_price() {
        return five_year_price;
    }

    public void setFive_year_price(double five_year_price) {
        this.five_year_price = five_year_price;
    }
}