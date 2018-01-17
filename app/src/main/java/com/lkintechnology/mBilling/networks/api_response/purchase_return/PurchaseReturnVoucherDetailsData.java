package com.lkintechnology.mBilling.networks.api_response.purchase_return;

public class PurchaseReturnVoucherDetailsData {
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

    public PurchaseReturnVoucherDetailsAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(PurchaseReturnVoucherDetailsAttribute attributes) {
        this.attributes = attributes;
    }

    public String id;
    public PurchaseReturnVoucherDetailsAttribute attributes;
}