package com.lkintechnology.mBilling.networks.api_response.sale_return;

public class GetSaleReturnVoucherDetails {

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

    public SaleReturnVoucherDetails getSale_voucher() {
        return sale_voucher;
    }

    public void setSale_voucher(SaleReturnVoucherDetails sale_voucher) {
        this.sale_voucher = sale_voucher;
    }

    public String message;
    public int status;
    public SaleReturnVoucherDetails sale_voucher;

}