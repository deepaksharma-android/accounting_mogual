package com.lkintechnology.mBilling.networks.api_response.stocktransfer;

public class StockTransferVoucherDetailsData {
    public String type;
    public String id;
    public StockTransferVoucherDetailsAttribute attributes;

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

    public StockTransferVoucherDetailsAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(StockTransferVoucherDetailsAttribute attributes) {
        this.attributes = attributes;
    }
}