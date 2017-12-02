package com.berylsystems.buzz.networks.api_response.receiptvoucher;

public class GetReceiptVoucherDetailsResponse {

    public String message;
    public int status;
    public ReceiptVouchersDetails receipt_voucher;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ReceiptVouchersDetails getReceipt_voucher() {
        return receipt_voucher;
    }

    public void setReceipt_voucher(ReceiptVouchersDetails receipt_voucher) {
        this.receipt_voucher = receipt_voucher;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}