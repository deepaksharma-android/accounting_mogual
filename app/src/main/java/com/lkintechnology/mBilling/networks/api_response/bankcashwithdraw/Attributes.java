package com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw;

public class Attributes {

    public String company_name;
    public String company_id;
    public String voucher_series;
    public String date;
    public String voucher_number;
    public String withdraw_from;
    public String withdraw_by;
    public String invoice_html;

    public String getInvoice_html() {
        return invoice_html;
    }

    public void setInvoice_html(String invoice_html) {
        this.invoice_html = invoice_html;
    }


    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getCompany_id() {
        return company_id;
    }

    public void setCompany_id(String company_id) {
        this.company_id = company_id;
    }

    public String getVoucher_series() {
        return voucher_series;
    }

    public void setVoucher_series(String voucher_series) {
        this.voucher_series = voucher_series;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getVoucher_number() {
        return voucher_number;
    }

    public void setVoucher_number(String voucher_number) {
        this.voucher_number = voucher_number;
    }

    public String getWithdraw_from() {
        return withdraw_from;
    }

    public void setWithdraw_from(String withdraw_from) {
        this.withdraw_from = withdraw_from;
    }

    public String getWithdraw_by() {
        return withdraw_by;
    }

    public void setWithdraw_by(String withdraw_by) {
        this.withdraw_by = withdraw_by;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double amount;
}