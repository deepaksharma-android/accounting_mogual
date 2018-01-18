package com.lkintechnology.mBilling.networks.api_response.sale_return;

public class SaleReturnVoucherDetailsItemList {
    public int item_id;
    public  String item;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int quantity;
   // public String item_description;

}