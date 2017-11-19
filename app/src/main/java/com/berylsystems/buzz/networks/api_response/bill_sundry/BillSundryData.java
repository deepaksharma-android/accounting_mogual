package com.berylsystems.buzz.networks.api_response.bill_sundry;

public class BillSundryData {
    public String id;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BillSundryAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(BillSundryAttributes attributes) {
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String type;
    public BillSundryAttributes attributes;
}