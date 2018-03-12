package com.lkintechnology.mBilling.networks.api_response.receiptvoucher;

public class ReceiptItems {
    public Double total_amount;
    public Double tax_rate;
    public Double amount;

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public Double getTax_rate() {
        return tax_rate;
    }

    public void setTax_rate(Double tax_rate) {
        this.tax_rate = tax_rate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getReference_no() {
        return reference_no;
    }

    public void setReference_no(String reference_no) {
        this.reference_no = reference_no;
    }

    public String reference_no;
}