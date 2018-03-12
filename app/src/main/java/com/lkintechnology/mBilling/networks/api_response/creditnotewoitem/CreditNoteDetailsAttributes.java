package com.lkintechnology.mBilling.networks.api_response.creditnotewoitem;

import java.util.ArrayList;

public class CreditNoteDetailsAttributes {


    public String company_name;

    public CreditAccount getAccount_credit() {
        return account_credit;
    }

    public void setAccount_credit(CreditAccount account_credit) {
        this.account_credit = account_credit;
    }

    public String company_id;
    public String voucher_series;
    public String date;
    public String gst_nature;
    public String voucher_number;

    public String getAccount_name_debit_id() {
        return account_name_debit_id;
    }

    public void setAccount_name_debit_id(String account_name_debit_id) {
        this.account_name_debit_id = account_name_debit_id;
    }

    public String account_name_credit;
    public CreditAccount account_credit;
    public String account_name_debit_id;
    public String account_name_debit;
    public Double amount;


    public CreditNoteItems getCredit_note_item() {
        return credit_note_item;
    }

    public void setCredit_note_item(CreditNoteItems credit_note_item) {
        this.credit_note_item = credit_note_item;
    }

    public String narration;
    public String attachment;
    public CreditNoteItems credit_note_item;

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

    public String getAccount_name_debit() {
        return account_name_debit;
    }

    public void setAccount_name_debit(String account_name_debit) {
        this.account_name_debit = account_name_debit;
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