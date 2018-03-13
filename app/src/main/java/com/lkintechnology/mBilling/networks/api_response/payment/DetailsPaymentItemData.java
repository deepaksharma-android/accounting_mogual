package com.lkintechnology.mBilling.networks.api_response.payment;

/**
 * Created by abc on 3/12/2018.
 */

public class DetailsPaymentItemData {
    public String type;

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

    public PaymentItemAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PaymentItemAttributes attributes) {
        this.attributes = attributes;
    }

    public String id;
    public PaymentItemAttributes attributes;

}
