package com.lkintechnology.mBilling.networks.api_response.purchase_return;

public class GetPurchaseReturnVoucherDetails {

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

    public PurchaseReturnVoucherDetails getPurchase_return_voucher() {
        return purchase_return_voucher;
    }

    public void setPurchase_return_voucher(PurchaseReturnVoucherDetails purchase_return_voucher) {
        this.purchase_return_voucher = purchase_return_voucher;
    }

    public int status;
    public PurchaseReturnVoucherDetails purchase_return_voucher;

}