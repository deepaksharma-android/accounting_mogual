package com.berylsystems.buzz.networks.api_response.purchase_return;

public class GetPurchaseReturnVoucherListResponse {
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


    public PurchaseReturnVoucher getPurchase_vouchers() {
        return purchase_vouchers;
    }

    public void setPurchase_vouchers(PurchaseReturnVoucher purchase_vouchers) {
        this.purchase_vouchers = purchase_vouchers;
    }

    public PurchaseReturnVoucher purchase_vouchers;
}