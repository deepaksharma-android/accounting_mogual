package com.lkintechnology.mBilling.networks.api_response.purchasevoucher;

public class GetPurchaseVoucherDetails {

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



    public String message;
    public int status;

    public PurchaseVoucherDetails getPurchase_voucher() {
        return purchase_voucher;
    }

    public void setPurchase_voucher(PurchaseVoucherDetails purchase_voucher) {
        this.purchase_voucher = purchase_voucher;
    }

    public PurchaseVoucherDetails purchase_voucher;

}