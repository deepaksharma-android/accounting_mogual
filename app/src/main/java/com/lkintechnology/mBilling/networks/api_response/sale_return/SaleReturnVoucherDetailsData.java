package com.lkintechnology.mBilling.networks.api_response.sale_return;

public class SaleReturnVoucherDetailsData {
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

    public SaleReturnVoucherDetailsAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(SaleReturnVoucherDetailsAttribute attributes) {
        this.attributes = attributes;
    }

    public String id;
    public SaleReturnVoucherDetailsAttribute attributes;
}