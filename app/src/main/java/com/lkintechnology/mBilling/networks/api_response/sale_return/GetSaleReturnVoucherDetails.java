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



    public String message;

    public SaleReturnVoucherDetails getSale_return_voucher() {
        return sale_return_voucher;
    }

    public void setSale_return_voucher(SaleReturnVoucherDetails sale_return_voucher) {
        this.sale_return_voucher = sale_return_voucher;
    }

    public int status;
    public SaleReturnVoucherDetails sale_return_voucher;

}