package com.lkintechnology.mBilling.networks.api_response.unit;

public class UqcData {
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

    public UqcAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(UqcAttributes attributes) {
        this.attributes = attributes;
    }

    public String type;
    public String id;
    public UqcAttributes attributes;

}