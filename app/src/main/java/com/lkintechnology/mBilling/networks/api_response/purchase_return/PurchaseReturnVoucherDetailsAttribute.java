package com.lkintechnology.mBilling.networks.api_response.purchase_return;

import com.lkintechnology.mBilling.networks.api_response.transport.Transport;

import java.util.ArrayList;

public class PurchaseReturnVoucherDetailsAttribute {
    public String voucher_series;
    public String shipped_to_name;
    public String shipped_to_id;
    public String purchase_name;
    public String purchase_id;
    public String time;
    public int company_id;
    String po_date;
    String po_number;


    public String getPo_date() {
        return po_date;
    }

    public void setPo_date(String po_date) {
        this.po_date = po_date;
    }

    public String getPo_number() {
        return po_number;
    }

    public void setPo_number(String po_number) {
        this.po_number = po_number;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPurchase_name() {
        return purchase_name;
    }

    public void setPurchase_name(String purchase_name) {
        this.purchase_name = purchase_name;
    }

    public String getPurchase_id() {
        return purchase_id;
    }

    public void setPurchase_id(String purchase_id) {
        this.purchase_id = purchase_id;
    }

    public String getShipped_to_name() {
        return shipped_to_name;
    }

    public void setShipped_to_name(String shipped_to_name) {
        this.shipped_to_name = shipped_to_name;
    }

    public String getShipped_to_id() {
        return shipped_to_id;
    }

    public void setShipped_to_id(String shipped_to_id) {
        this.shipped_to_id = shipped_to_id;
    }

    public int getSale_type_id() {
        return sale_type_id;
    }

    public void setSale_type_id(int sale_type_id) {
        this.sale_type_id = sale_type_id;
    }

    public String getSale_type() {
        return sale_type;
    }

    public void setSale_type(String sale_type) {
        this.sale_type = sale_type;
    }

    public int sale_type_id;
    public String sale_type;

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

    public String getMaterial_center_id() {
        return material_center_id;
    }

    public void setMaterial_center_id(String material_center_id) {
        this.material_center_id = material_center_id;
    }

    public String material_center_id;
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

    public Double total_amount;
    public Double items_amount;

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public Double getBill_sundries_amount() {
        return bill_sundries_amount;
    }

    public void setBill_sundries_amount(Double bill_sundries_amount) {
        this.bill_sundries_amount = bill_sundries_amount;
    }

    public Double getItems_amount() {
        return items_amount;
    }

    public void setItems_amount(Double items_amount) {
        this.items_amount = items_amount;
    }

    public Double bill_sundries_amount;

    public Transport getTransport_details() {
        return transport_details;
    }

    public void setTransport_details(Transport transport_details) {
        this.transport_details = transport_details;
    }

    public Transport transport_details;
    public ArrayList<PurchaseReturnVoucherDetailsPaymentSettlement> payment_settlement;

    public ArrayList<PurchaseReturnVoucherDetailsPaymentSettlement> getPayment_settlement() {
        return payment_settlement;
    }

    public void setPayment_settlement(ArrayList<PurchaseReturnVoucherDetailsPaymentSettlement> payment_settlement) {
        this.payment_settlement = payment_settlement;
    }
}