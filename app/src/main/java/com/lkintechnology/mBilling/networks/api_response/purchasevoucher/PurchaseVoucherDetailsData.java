package com.lkintechnology.mBilling.networks.api_response.purchasevoucher;

public class PurchaseVoucherDetailsData {
    public String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PurchaseVoucherDetailsAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(PurchaseVoucherDetailsAttribute attributes) {
        this.attributes = attributes;
    }

    public String id;
    public PurchaseVoucherDetailsAttribute attributes;
}