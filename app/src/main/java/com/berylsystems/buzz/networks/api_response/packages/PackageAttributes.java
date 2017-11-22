package com.berylsystems.buzz.networks.api_response.packages;

import java.util.ArrayList;

public class PackageAttributes {
    public String name;
    public String description;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double amount;
    public boolean active;
    public ArrayList<Features> features;

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
}