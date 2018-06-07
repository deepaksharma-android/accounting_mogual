package com.lkintechnology.mBilling.networks.api_response.purchasevoucher;

/**
 * Created by abc on 6/4/2018.
 */

public class PurchaseVoucherDetailsPaymentSettlement {
    public int id;
    public Double amount;
    public String payment_account_id;
    public String payment_account_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPayment_account_id() {
        return payment_account_id;
    }

    public void setPayment_account_id(String payment_account_id) {
        this.payment_account_id = payment_account_id;
    }

    public String getPayment_account_name() {
        return payment_account_name;
    }

    public void setPayment_account_name(String payment_account_name) {
        this.payment_account_name = payment_account_name;
    }
}
