package com.lkintechnology.mBilling.networks.api_response.stocktransfer;

public class Attributes {
    public String date;
    public String voucher_number;
    public int company_id;
    public String material_center_from;
    public  String material_center_to;
    public String invoice_html;
    public String attachment;
    public Double total_amount;

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

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getMaterial_center_from() {
        return material_center_from;
    }

    public void setMaterial_center_from(String material_center_from) {
        this.material_center_from = material_center_from;
    }

    public String getMaterial_center_to() {
        return material_center_to;
    }

    public void setMaterial_center_to(String material_center_to) {
        this.material_center_to = material_center_to;
    }

    public String getInvoice_html() {
        return invoice_html;
    }

    public void setInvoice_html(String invoice_html) {
        this.invoice_html = invoice_html;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }
}