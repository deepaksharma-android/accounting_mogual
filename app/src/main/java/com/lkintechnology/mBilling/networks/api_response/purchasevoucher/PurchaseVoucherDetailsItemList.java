package com.lkintechnology.mBilling.networks.api_response.purchasevoucher;

import java.util.ArrayList;

public class PurchaseVoucherDetailsItemList {
    public int item_id;
    public  String item;
    public int quantity;
    public Double total_amount;
    public Double rate_item;

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public Double getRate_item() {
        return rate_item;
    }

    public void setRate_item(Double rate_item) {
        this.rate_item = rate_item;
    }

    public Double getPrice_after_discount() {
        return price_after_discount;
    }

    public void setPrice_after_discount(Double price_after_discount) {
        this.price_after_discount = price_after_discount;
    }

    public String getItem_description() {
        return item_description;
    }

    public void setItem_description(String item_description) {
        this.item_description = item_description;
    }

    public String getItem_unit_id() {
        return item_unit_id;
    }

    public void setItem_unit_id(String item_unit_id) {
        this.item_unit_id = item_unit_id;
    }

    public Boolean getSerial_number_wise_detail() {
        return serial_number_wise_detail;
    }

    public void setSerial_number_wise_detail(Boolean serial_number_wise_detail) {
        this.serial_number_wise_detail = serial_number_wise_detail;
    }

    public Boolean getBatch_wise_detail() {
        return batch_wise_detail;
    }

    public void setBatch_wise_detail(Boolean batch_wise_detail) {
        this.batch_wise_detail = batch_wise_detail;
    }

    public String getConversion_factor() {
        return conversion_factor;
    }

    public void setConversion_factor(String conversion_factor) {
        this.conversion_factor = conversion_factor;
    }

    public String getDefault_unit_for_sales() {
        return default_unit_for_sales;
    }

    public void setDefault_unit_for_sales(String default_unit_for_sales) {
        this.default_unit_for_sales = default_unit_for_sales;
    }

    public String getTax_category_id() {
        return tax_category_id;
    }

    public void setTax_category_id(String tax_category_id) {
        this.tax_category_id = tax_category_id;
    }

    public String getTax_category() {
        return tax_category;
    }

    public void setTax_category(String tax_category) {
        this.tax_category = tax_category;
    }


    public Double discount;
    public Double price_after_discount;
    public String item_description;
    public String item_unit_id;
    public Boolean  serial_number_wise_detail;
    public Boolean batch_wise_detail;
    public String conversion_factor;
    public String tax_category_id;
    public String tax_category;
    public ArrayList<String> getBarcode() {
        return barcode;
    }

    public void setBarcode(ArrayList<String> barcode) {
        this.barcode = barcode;
    }

    public ArrayList<String> barcode;
    public String item_unit;

    public String getItem_unit() {
        return item_unit;
    }

    public void setItem_unit(String item_unit) {
        this.item_unit = item_unit;
    }

    public String getAlternate_unit() {
        return alternate_unit;
    }

    public void setAlternate_unit(String alternate_unit) {
        this.alternate_unit = alternate_unit;
    }

    public String alternate_unit;

    public String default_unit_for_sales;

    public String getDefault_unit_for_purchase() {
        return default_unit_for_purchase;
    }

    public void setDefault_unit_for_purchase(String default_unit_for_purchase) {
        this.default_unit_for_purchase = default_unit_for_purchase;
    }

    public Double getPackaging_unit_sales_price() {
        return packaging_unit_sales_price;
    }

    public void setPackaging_unit_sales_price(Double packaging_unit_sales_price) {
        this.packaging_unit_sales_price = packaging_unit_sales_price;
    }

    public Double getSales_price_main() {
        return sales_price_main;
    }

    public void setSales_price_main(Double sales_price_main) {
        this.sales_price_main = sales_price_main;
    }

    public Double getSales_price_alternate() {
        return sales_price_alternate;
    }

    public void setSales_price_alternate(Double sales_price_alternate) {
        this.sales_price_alternate = sales_price_alternate;
    }

    public String default_unit_for_purchase;
    public Double sales_price_alternate;
    public Double  sales_price_main;
    public Double packaging_unit_sales_price;

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double price;

    public String getPurchase_unit() {
        return purchase_unit;
    }

    public void setPurchase_unit(String purchase_unit) {
        this.purchase_unit = purchase_unit;
    }

    public String purchase_unit;

    public String getPackaging_unit() {
        return packaging_unit;
    }

    public void setPackaging_unit(String packaging_unit) {
        this.packaging_unit = packaging_unit;
    }

    public String packaging_unit;


    public String getSale_price_applied_on() {
        return sale_price_applied_on;
    }

    public void setSale_price_applied_on(String sale_price_applied_on) {
        this.sale_price_applied_on = sale_price_applied_on;
    }

    public String sale_price_applied_on;

    public Double getPackaging_conversion_factor() {
        return packaging_conversion_factor;
    }

    public void setPackaging_conversion_factor(Double packaging_conversion_factor) {
        this.packaging_conversion_factor = packaging_conversion_factor;
    }

    public Double packaging_conversion_factor;

    public Double getMrp() {
        return mrp;
    }

    public void setMrp(Double mrp) {
        this.mrp = mrp;
    }

    public Double mrp;

    public String getPurchase_price_applied_on() {
        return purchase_price_applied_on;
    }

    public void setPurchase_price_applied_on(String purchase_price_applied_on) {
        this.purchase_price_applied_on = purchase_price_applied_on;
    }

    public String purchase_price_applied_on;

    public Double purchase_price_main;

    public Double getPurchase_price_main() {
        return purchase_price_main;
    }

    public void setPurchase_price_main(Double purchase_price_main) {
        this.purchase_price_main = purchase_price_main;
    }

    public Double getPurchase_price_alternate() {
        return purchase_price_alternate;
    }

    public void setPurchase_price_alternate(Double purchase_price_alternate) {
        this.purchase_price_alternate = purchase_price_alternate;
    }

    public Double purchase_price_alternate;

}