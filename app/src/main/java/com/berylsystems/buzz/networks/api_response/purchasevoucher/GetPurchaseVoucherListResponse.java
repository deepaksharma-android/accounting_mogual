package com.berylsystems.buzz.networks.api_response.purchasevoucher;

public class GetPurchaseVoucherListResponse {
    public String message;
    public int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public PurchaseVoucher getPurchase_vouchers() {
        return purchase_vouchers;
    }

    public void setPurchase_vouchers(PurchaseVoucher purchase_vouchers) {
        this.purchase_vouchers = purchase_vouchers;
    }

    public PurchaseVoucher purchase_vouchers;
}