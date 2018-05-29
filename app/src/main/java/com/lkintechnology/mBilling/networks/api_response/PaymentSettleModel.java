package com.lkintechnology.mBilling.networks.api_response;

import java.util.List;
import java.util.Map;

/**
 * Created by abc on 5/29/2018.
 */

public class PaymentSettleModel {
    public String Type;
    public List<Map> payment_mode;

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public List<Map> getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(List<Map> payment_mode) {
        this.payment_mode = payment_mode;
    }
}
