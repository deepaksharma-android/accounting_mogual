package com.lkintechnology.mBilling.networks.api_response.salevoucher;

public class GetSaleVoucherDetails {

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

    public SaleVoucherDetails getSale_voucher() {
        return sale_voucher;
    }

    public void setSale_voucher(SaleVoucherDetails sale_voucher) {
        this.sale_voucher = sale_voucher;
    }

    public String message;
    public int status;
    public SaleVoucherDetails sale_voucher;

}