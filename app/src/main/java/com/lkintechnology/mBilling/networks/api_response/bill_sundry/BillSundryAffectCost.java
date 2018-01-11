package com.lkintechnology.mBilling.networks.api_response.bill_sundry;

public class BillSundryAffectCost {
    public Boolean goods_in_sale;
    public Boolean goods_in_purchase;

    public Boolean getMaterial_receipt() {
        return material_receipt;
    }

    public void setMaterial_receipt(Boolean material_receipt) {
        this.material_receipt = material_receipt;
    }

    public Boolean getStock_transfer() {
        return stock_transfer;
    }

    public void setStock_transfer(Boolean stock_transfer) {
        this.stock_transfer = stock_transfer;
    }

    public Boolean getGoods_in_sale() {
        return goods_in_sale;
    }

    public void setGoods_in_sale(Boolean goods_in_sale) {
        this.goods_in_sale = goods_in_sale;
    }

    public Boolean getGoods_in_purchase() {
        return goods_in_purchase;
    }

    public void setGoods_in_purchase(Boolean goods_in_purchase) {
        this.goods_in_purchase = goods_in_purchase;
    }

    public Boolean getMaterial_issue() {
        return material_issue;
    }

    public void setMaterial_issue(Boolean material_issue) {
        this.material_issue = material_issue;
    }

    public Boolean material_issue;
    public Boolean material_receipt;
    public Boolean stock_transfer;
}