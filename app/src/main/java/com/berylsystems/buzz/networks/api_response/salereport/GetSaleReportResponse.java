package com.berylsystems.buzz.networks.api_response.salereport;

import java.util.ArrayList;

public class GetSaleReportResponse {
    public String message;
    public int status;
    public ArrayList<SaleVoucherItems> sale_voucher_items;

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

    public ArrayList<SaleVoucherItems> getSale_voucher_items() {
        return sale_voucher_items;
    }

    public void setSale_voucher_items(ArrayList<SaleVoucherItems> sale_voucher_items) {
        this.sale_voucher_items = sale_voucher_items;
    }
}