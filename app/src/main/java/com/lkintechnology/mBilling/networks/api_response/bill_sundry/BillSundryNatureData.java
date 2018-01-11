package com.lkintechnology.mBilling.networks.api_response.bill_sundry;

public class BillSundryNatureData {
    public String type;
    public String id;

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

    public BillSundryNatureAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(BillSundryNatureAttributes attributes) {
        this.attributes = attributes;
    }

    public BillSundryNatureAttributes attributes;
}