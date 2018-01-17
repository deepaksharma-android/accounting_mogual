package com.lkintechnology.mBilling.networks.api_response.salevoucher;

public class SaleVoucherDetailsData {
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

    public SaleVoucherDetailsAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(SaleVoucherDetailsAttribute attributes) {
        this.attributes = attributes;
    }

    public String id;
    public SaleVoucherDetailsAttribute attributes;
}