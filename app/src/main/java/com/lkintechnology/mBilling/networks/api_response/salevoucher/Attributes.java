package com.lkintechnology.mBilling.networks.api_response.salevoucher;

public class Attributes {

    public String date;
    public String voucher_number;
    public int company_id;
    public String account_master;
    public Double total_amount;
    public String invoice_html;
    public String attachment;
    public Boolean pos;

    public Boolean getPos() {
        return pos;
    }

    public void setPos(Boolean pos) {
        this.pos = pos;
    }

    public VoucherSeriesDetails getVoucher_series() {
        return voucher_series;
    }

    public void setVoucher_series(VoucherSeriesDetails voucher_series) {
        this.voucher_series = voucher_series;
    }

    public VoucherSeriesDetails voucher_series;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getInvoice_html() {
        return invoice_html;
    }

    public void setInvoice_html(String invoice_html) {
        this.invoice_html = invoice_html;
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

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getAccount_master() {
        return account_master;
    }

    public void setAccount_master(String account_master) {
        this.account_master = account_master;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

}