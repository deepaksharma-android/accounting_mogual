package com.lkintechnology.mBilling.networks.api_response.journalvoucher;

public class JournalVoucherDetailsAttributes {

    public String company_name;
    public String company_id;
    public String voucher_series;
    public String date;
    public String gst_nature;
    public String voucher_number;
    public String account_name_credit;
    public DetailsAccountDebit account_debit;
    public DetailsAccountCredit account_credit ;
    public DetailsJournalItem journal_item;

    public DetailsAccountCredit getAccount_credit() {
        return account_credit;
    }

    public void setAccount_credit(DetailsAccountCredit account_credit) {
        this.account_credit = account_credit;
    }

    public DetailsJournalItem getJournal_item() {
        return journal_item;
    }

    public void setJournal_item(DetailsJournalItem journal_item) {
        this.journal_item = journal_item;
    }

    public DetailsAccountDebit getAccount_debit() {
        return account_debit;
    }

    public void setAccount_debit(DetailsAccountDebit account_debit) {
        this.account_debit = account_debit;
    }

    public int getAccount_name_credit_id() {
        return account_name_credit_id;
    }

    public void setAccount_name_credit_id(int account_name_credit_id) {
        this.account_name_credit_id = account_name_credit_id;
    }

    public Double amount;
    public String narration;
    public String attachment;
    public int account_name_credit_id;

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

    public String getGst_nature() {
        return gst_nature;
    }

    public void setGst_nature(String gst_nature) {
        this.gst_nature = gst_nature;
    }

    public String getVoucher_number() {
        return voucher_number;
    }

    public void setVoucher_number(String voucher_number) {
        this.voucher_number = voucher_number;
    }

    public String getAccount_name_credit() {
        return account_name_credit;
    }

    public void setAccount_name_credit(String account_name_credit) {
        this.account_name_credit = account_name_credit;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
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

    public String reason;
}