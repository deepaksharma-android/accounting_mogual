package com.lkintechnology.mBilling.networks.api_response.debitnotewoitem;

public class DebitNoteItems {
    public String invoice_no;
    public String date;
    public Double amount;
    public Double tax_rate;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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



    public Double igst_amount;

    public String getInvoice_no() {
        return invoice_no;
    }

    public void setInvoice_no(String invoice_no) {
        this.invoice_no = invoice_no;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(Double tax_rate) {
        this.tax_rate = tax_rate;
    }

    public String getItc_eligibility() {
        return itc_eligibility;
    }

    public void setItc_eligibility(String itc_eligibility) {
        this.itc_eligibility = itc_eligibility;
    }

    public Double cgst_amount;
    public Double sgst_amount;
    public String itc_eligibility;

}