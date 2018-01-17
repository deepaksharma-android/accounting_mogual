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

    public PurchaseVoucherDetails getSale_voucher() {
        return sale_voucher;
    }

    public void setSale_voucher(PurchaseVoucherDetails sale_voucher) {
        this.sale_voucher = sale_voucher;
    }

    public String message;
    public int status;
    public PurchaseVoucherDetails sale_voucher;

}