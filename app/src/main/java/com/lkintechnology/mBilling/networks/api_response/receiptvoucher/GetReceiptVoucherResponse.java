package com.lkintechnology.mBilling.networks.api_response.receiptvoucher;

public class GetReceiptVoucherResponse {

    public String message;
    public int status;
    public ReceiptVouchers receipt_vouchers;

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

    public ReceiptVouchers getReceipt_vouchers() {
        return receipt_vouchers;
    }

    public void setReceipt_vouchers(ReceiptVouchers receipt_vouchers) {
        this.receipt_vouchers = receipt_vouchers;
    }
}