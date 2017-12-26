package com.berylsystems.buzz.networks.api_response.salereport;

import java.util.ArrayList;

public class GetSaleReportResponse {
    public String message;
    public int status;
    public SaleVoucherItems sale_vouchers;

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

    public SaleVoucherItems getSale_vouchers() {
        return sale_vouchers;
    }

    public void setSale_vouchers(SaleVoucherItems sale_vouchers) {
        this.sale_vouchers = sale_vouchers;
    }
}