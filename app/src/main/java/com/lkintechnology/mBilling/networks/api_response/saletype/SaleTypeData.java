package com.lkintechnology.mBilling.networks.api_response.saletype;

public class SaleTypeData {
    public String type;
    public String id;
    public SaleTypeAttributes attributes;

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

    public SaleTypeAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(SaleTypeAttributes attributes) {
        this.attributes = attributes;
    }
}