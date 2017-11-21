package com.berylsystems.buzz.networks.api_response.purchasetype;

public class PurchaseTypeData {
    public String type;
    public String id;
    public PurchaseTypeAttributes attributes;

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

    public PurchaseTypeAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PurchaseTypeAttributes attributes) {
        this.attributes = attributes;
    }
}