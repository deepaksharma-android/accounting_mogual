package com.lkintechnology.mBilling.networks.api_response.purchase_return;

import java.util.ArrayList;

public class PurchaseReturnVoucherDetailsAttribute {
    public String voucher_series;
    public int company_id;

    public int getPurchase_return_type_id() {
        return purchase_return_type_id;
    }

    public void setPurchase_return_type_id(int purchase_return_type_id) {
        this.purchase_return_type_id = purchase_return_type_id;
    }

    public String getPurchase_return_type() {
        return purchase_return_type;
    }

    public void setPurchase_return_type(String purchase_return_type) {
        this.purchase_return_type = purchase_return_type;
    }

    public int purchase_return_type_id;
    public String purchase_return_type;

    public String getVoucher_series() {
        return voucher_series;
    }

    public void setVoucher_series(String voucher_series) {
        this.voucher_series = voucher_series;
    }

    public ArrayList<PurchaseReturnVoucherDetailsBillSundry> getVoucher_bill_sundries() {
        return voucher_bill_sundries;
    }

    public void setVoucher_bill_sundries(ArrayList<PurchaseReturnVoucherDetailsBillSundry> voucher_bill_sundries) {
        this.voucher_bill_sundries = voucher_bill_sundries;
    }

    public ArrayList<PurchaseReturnVoucherDetailsItemList> getVoucher_items() {
        return voucher_items;
    }

    public void setVoucher_items(ArrayList<PurchaseReturnVoucherDetailsItemList> voucher_items) {
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

    public String getMaterial_centre_id() {
        return material_centre_id;
    }

    public void setMaterial_centre_id(String material_centre_id) {
        this.material_centre_id = material_centre_id;
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
    public String material_centre_id;
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
    public ArrayList<PurchaseReturnVoucherDetailsItemList> voucher_items;
    public ArrayList<PurchaseReturnVoucherDetailsBillSundry> voucher_bill_sundries;
}