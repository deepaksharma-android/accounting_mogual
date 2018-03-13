package com.lkintechnology.mBilling.networks.api_response.journalvoucher;

public class JournalItemAttribute {
    public Double amount;
    public String invoice_no;
    public Double igst_amount;
    public Double cgst_amount;
    public Double sgst_amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public Double getIgst_amount() {
        return igst_amount;
    }

    public void setIgst_amount(Double igst_amount) {
        this.igst_amount = igst_amount;
    }

    public Double getCgst_amount() {
        return cgst_amount;
    }

    public void setCgst_amount(Double cgst_amount) {
        this.cgst_amount = cgst_amount;
    }

    public Double getSgst_amount() {
        return sgst_amount;
    }

    public void setSgst_amount(Double sgst_amount) {
        this.sgst_amount = sgst_amount;
    }

    public String getItc_eligibility() {
        return itc_eligibility;
    }

    public void setItc_eligibility(String itc_eligibility) {
        this.itc_eligibility = itc_eligibility;
    }

    public String getAccount_id() {
        return account_id;
    }

    public void setAccount_id(String account_id) {
        this.account_id = account_id;
    }

    public String getParty_id() {
        return party_id;
    }

    public void setParty_id(String party_id) {
        this.party_id = party_id;
    }

    public Double getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(Double tax_rate) {
        this.tax_rate = tax_rate;
    }

    public String itc_eligibility;
    public Double tax_rate;
    public String account_id;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String party_id;

    public String getRcn_item() {
        return rcn_item;
    }

    public void setRcn_item(String rcn_item) {
        this.rcn_item = rcn_item;
    }

    public String rcn_item;

    public String date;


}
