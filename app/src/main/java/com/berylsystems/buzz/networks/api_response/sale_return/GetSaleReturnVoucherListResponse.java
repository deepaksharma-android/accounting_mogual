package com.berylsystems.buzz.networks.api_response.sale_return;

public class GetSaleReturnVoucherListResponse {
    public String message;
    public int status;
    public SaleReturnVoucher sale_vouchers;

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

    public SaleReturnVoucher getSale_vouchers() {
        return sale_vouchers;
    }

    public void setSale_vouchers(SaleReturnVoucher sale_vouchers) {
        this.sale_vouchers = sale_vouchers;
    }
}