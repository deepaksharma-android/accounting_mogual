package com.lkintechnology.mBilling.networks.api_response;

import java.util.List;
import java.util.Map;

/**
 * Created by abc on 5/29/2018.
 */

public class PaymentSettleModel {
    public String voucher_type;
    public List<Map> payment_mode;

    public String getVoucher_type() {
        return voucher_type;
    }

    public void setVoucher_type(String voucher_type) {
        this.voucher_type = voucher_type;
    }

    public List<Map> getPayment_mode() {
        return payment_mode;
    }

    public void setPayment_mode(List<Map> payment_mode) {
        this.payment_mode = payment_mode;
    }
}
