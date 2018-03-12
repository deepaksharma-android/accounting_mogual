package com.lkintechnology.mBilling.networks.api_response.payment;

public class PaymentDetailsAttributes {

    public String company_name;
    public String company_id;
    public String voucher_series;
    public String date;

    public DetailsPaymentItem getPayment_item() {
        return payment_item;
    }

    public void setPayment_item(DetailsPaymentItem payment_item) {
        this.payment_item = payment_item;
    }

    public String pdc_date;
    public String gst_nature;
    public String gst_nature_description;
    public String voucher_number;
    public String paid_from;
    public DetailsPaidTo paid_to;
    public DetailsPaymentItem payment_item;

    public DetailsPaidTo getPaid_to() {
        return paid_to;
    }

    public void setPaid_to(DetailsPaidTo paid_to) {
        this.paid_to = paid_to;
    }

    public Double amount;
    public String narration;
    public String attachment;

    public String getPayment_type() {
        return payment_type;
    }
    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String payment_type;
    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
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

    public String getPdc_date() {
        return pdc_date;
    }

    public void setPdc_date(String pdc_date) {
        this.pdc_date = pdc_date;
    }

    public String getGst_nature() {
        return gst_nature;
    }

    public void setGst_nature(String gst_nature) {
        this.gst_nature = gst_nature;
    }

    public String getGst_nature_description() {
        return gst_nature_description;
    }

    public void setGst_nature_description(String gst_nature_description) {
        this.gst_nature_description = gst_nature_description;
    }

    public String getVoucher_number() {
        return voucher_number;
    }

    public void setVoucher_number(String voucher_number) {
        this.voucher_number = voucher_number;
    }

    public String getPaid_from() {
        return paid_from;
    }

    public void setPaid_from(String paid_from) {
        this.paid_from = paid_from;
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

    public int getPaid_from_id() {
        return paid_from_id;
    }

    public void setPaid_from_id(int paid_from_id) {
        this.paid_from_id = paid_from_id;
    }

    public int paid_from_id;
}