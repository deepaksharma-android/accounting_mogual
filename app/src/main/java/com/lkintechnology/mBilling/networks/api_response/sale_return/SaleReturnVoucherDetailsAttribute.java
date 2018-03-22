package com.lkintechnology.mBilling.networks.api_response.sale_return;

import com.lkintechnology.mBilling.networks.api_response.transport.Transport;

import java.util.ArrayList;

public class SaleReturnVoucherDetailsAttribute {
    public String voucher_series;
    public int company_id;
    public int purchase_type_id;

    public int getPurchase_type_id() {
        return purchase_type_id;
    }

    public void setPurchase_type_id(int purchase_type_id) {
        this.purchase_type_id = purchase_type_id;
    }

    public String getPurchase_type() {
        return purchase_type;
    }

    public void setPurchase_type(String purchase_type) {
        this.purchase_type = purchase_type;
    }

    public String purchase_type;

    public String getVoucher_series() {
        return voucher_series;
    }

    public void setVoucher_series(String voucher_series) {
        this.voucher_series = voucher_series;
    }

    public ArrayList<SaleReturnVoucherDetailsBillSundry> getVoucher_bill_sundries() {
        return voucher_bill_sundries;
    }

    public void setVoucher_bill_sundries(ArrayList<SaleReturnVoucherDetailsBillSundry> voucher_bill_sundries) {
        this.voucher_bill_sundries = voucher_bill_sundries;
    }

    public ArrayList<SaleReturnVoucherDetailsItemList> getVoucher_items() {
        return voucher_items;
    }

    public void setVoucher_items(ArrayList<SaleReturnVoucherDetailsItemList> voucher_items) {
        this.voucher_items = voucher_items;
    }

    public String getVoucher_number() {
        return voucher_number;
    }

    public void setVoucher_number(String voucher_number) {
        this.voucher_number = voucher_number;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }


    public String getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(String mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getMaterial_center() {
        return material_center;
    }

    public void setMaterial_center(String material_center) {
        this.material_center = material_center;
    }


    public String getAccount_master_id() {
        return account_master_id;
    }

    public void setAccount_master_id(String account_master_id) {
        this.account_master_id = account_master_id;
    }

    public String getAccount_master() {
        return account_master;
    }

    public void setAccount_master(String account_master) {
        this.account_master = account_master;
    }


    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String account_master;
    public String account_master_id;
    public String material_center_id;

    public String getMaterial_center_id() {
        return material_center_id;
    }

    public void setMaterial_center_id(String material_center_id) {
        this.material_center_id = material_center_id;
    }

    public String material_center;
    public String mobile_number;

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String attachment;
    public String narration;
    public String voucher_number;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String date;
    public ArrayList<SaleReturnVoucherDetailsItemList> voucher_items;
    public ArrayList<SaleReturnVoucherDetailsBillSundry> voucher_bill_sundries;
    public Double total_amount;
    public Double items_amount;
    public Double bill_sundries_amount;

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public Double getItems_amount() {
        return items_amount;
    }

    public void setItems_amount(Double items_amount) {
        this.items_amount = items_amount;
    }

    public Double getBill_sundries_amount() {
        return bill_sundries_amount;
    }

    public void setBill_sundries_amount(Double bill_sundries_amount) {
        this.bill_sundries_amount = bill_sundries_amount;
    }

    public Transport getTransport_details() {
        return transport_details;
    }

    public void setTransport_details(Transport transport_details) {
        this.transport_details = transport_details;
    }

    public Transport transport_details;
}