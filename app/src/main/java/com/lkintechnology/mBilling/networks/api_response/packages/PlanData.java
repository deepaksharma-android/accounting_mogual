package com.lkintechnology.mBilling.networks.api_response.packages;


public class PlanData {

    public String type;
    public String id;
    public PackageAttributes attributes;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PackageAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PackageAttributes attributes) {
        this.attributes = attributes;
    }
}