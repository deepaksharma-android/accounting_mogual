package com.lkintechnology.mBilling.networks.api_response.receiptvoucher;

public class ReceiptVouchersDetailsData {

    public String type;
    public String id;
    public ReceiptVouchersDetailsAttributes attributes;

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

    public ReceiptVouchersDetailsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ReceiptVouchersDetailsAttributes attributes) {
        this.attributes = attributes;
    }
}