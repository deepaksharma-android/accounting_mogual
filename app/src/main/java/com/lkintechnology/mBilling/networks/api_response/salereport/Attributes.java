package com.lkintechnology.mBilling.networks.api_response.salereport;

public class Attributes {
    public String voucher_series;
    public int company_id;
    public String sale_type;

    public String getVoucher_series() {
        return voucher_series;
    }

    public void setVoucher_series(String voucher_series) {
        this.voucher_series = voucher_series;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getSale_type() {
        return sale_type;
    }

    public void setSale_type(String sale_type) {
        this.sale_type = sale_type;
    }
}