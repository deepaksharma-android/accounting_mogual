package com.berylsystems.buzz.networks.api_response.bankcashwithdraw;

public class BankCashWithdrawDetailsAttributes {

    public String company_name;
    public String voucher_series;
    public String date;
    public String voucher_number;
    public String withdraw_from;
    public String withdraw_by;
    public Double amount;
    public String attachment;
    public String narration;

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
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