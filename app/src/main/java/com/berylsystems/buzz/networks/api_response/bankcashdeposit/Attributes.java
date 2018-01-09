package com.berylsystems.buzz.networks.api_response.bankcashdeposit;

public class Attributes {
    public String company_id;
    public String voucher_series;
    public String date;
    public String voucher_number;
    public String deposit_to;
    public String deposit_by;
    public Double amount;
    public String narration;
    public String attachment;
    public String invoice_html;

    public String getInvoice_html() {
        return invoice_html;
    }

    public void setInvoice_html(String invoice_html) {
        this.invoice_html = invoice_html;
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

    public String getDeposit_to() {
        return deposit_to;
    }

    public void setDeposit_to(String deposit_to) {
        this.deposit_to = deposit_to;
    }

    public String getDeposit_by() {
        return deposit_by;
    }

    public void setDeposit_by(String deposit_by) {
        this.deposit_by = deposit_by;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }


}