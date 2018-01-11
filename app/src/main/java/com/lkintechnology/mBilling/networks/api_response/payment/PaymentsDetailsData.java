package com.lkintechnology.mBilling.networks.api_response.payment;

public class PaymentsDetailsData {

    public String type;
    public String id;
    public PaymentDetailsAttributes attributes;

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

    public PaymentDetailsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PaymentDetailsAttributes attributes) {
        this.attributes = attributes;
    }
}