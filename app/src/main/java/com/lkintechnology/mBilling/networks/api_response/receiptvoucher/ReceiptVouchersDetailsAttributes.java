package com.lkintechnology.mBilling.networks.api_response.receiptvoucher;

import java.util.ArrayList;

public class ReceiptVouchersDetailsAttributes {
    public String company_name;
    public String company_id;
    public String voucher_series;
    public String date;
    public String payment_type;
    public String pdc_date;
    public String gst_nature;
    public String voucher_number;
    public String received_by;

    public DetailsReceivedFrom received_from_state;
    public DetailsReceivedFromState received_from;

    public DetailsReceivedFromState getReceived_from() {
        return received_from;
    }

    public void setReceived_from(DetailsReceivedFromState received_from) {
        this.received_from = received_from;
    }

    public DetailsReceivedFrom getReceived_from_state() {
        return received_from_state;
    }

    public void setReceived_from_state(DetailsReceivedFrom received_from_state) {
        this.received_from_state = received_from_state;
    }

    public ArrayList<ReceiptItems> getReceipt_item() {
        return receipt_item;
    }

    public void setReceipt_item(ArrayList<ReceiptItems> receipt_item) {
        this.receipt_item = receipt_item;
    }

    public Double amount;
    public String narration;
    public String attachment;
    public String is_payment_settlement;
    public ArrayList<ReceiptItems> receipt_item;

    public String getIs_payment_settlement() {
        return is_payment_settlement;
    }

    public void setIs_payment_settlement(String is_payment_settlement) {
        this.is_payment_settlement = is_payment_settlement;
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

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
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

    public String getVoucher_number() {
        return voucher_number;
    }

    public void setVoucher_number(String voucher_number) {
        this.voucher_number = voucher_number;
    }

    public String getReceived_by() {
        return received_by;
    }

    public void setReceived_by(String received_by) {
        this.received_by = received_by;
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

    public int received_by_id;

    public int getReceived_by_id() {
        return received_by_id;
    }

    public void setReceived_by_id(int received_by_id) {
        this.received_by_id = received_by_id;
    }

    public int getReceived_from_id() {
        return received_from_id;
    }

    public void setReceived_from_id(int received_from_id) {
        this.received_from_id = received_from_id;
    }

    public int received_from_id;
}