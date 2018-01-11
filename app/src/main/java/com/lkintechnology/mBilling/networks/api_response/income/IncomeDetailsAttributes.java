package com.lkintechnology.mBilling.networks.api_response.income;

public class IncomeDetailsAttributes {

    public String company_name;
    public String voucher_series;
    public String date;
    public String voucher_number;
    public String received_into;
    public String received_from;
    public Double amount;
    public String attachment;
    public String narration;

    public String getVoucher_series() {
        return voucher_series;
    }

    public void setVoucher_series(String voucher_series) {
        this.voucher_series = voucher_series;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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

    public String getReceived_into() {
        return received_into;
    }

    public void setReceived_into(String received_into) {
        this.received_into = received_into;
    }

    public String getReceived_from() {
        return received_from;
    }

    public void setReceived_from(String received_from) {
        this.received_from = received_from;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
}