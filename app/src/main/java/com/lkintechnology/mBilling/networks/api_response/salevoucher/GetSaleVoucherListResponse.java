package com.lkintechnology.mBilling.networks.api_response.salevoucher;

public class GetSaleVoucherListResponse {
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

    public SaleVoucher getSale_vouchers() {
        return sale_vouchers;
    }

    public void setSale_vouchers(SaleVoucher sale_vouchers) {
        this.sale_vouchers = sale_vouchers;
    }

    public SaleVoucher sale_vouchers;
}