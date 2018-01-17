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

    public PurchaseReturnVoucherDetails getSale_voucher() {
        return sale_voucher;
    }

    public void setSale_voucher(PurchaseReturnVoucherDetails sale_voucher) {
        this.sale_voucher = sale_voucher;
    }

    public String message;
    public int status;
    public PurchaseReturnVoucherDetails sale_voucher;

}