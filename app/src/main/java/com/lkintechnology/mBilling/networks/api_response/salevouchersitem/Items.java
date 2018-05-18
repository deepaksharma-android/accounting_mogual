package com.lkintechnology.mBilling.networks.api_response.salevouchersitem;

/**
 * Created by abc on 5/18/2018.
 */

public class Items {
    public String id;
    public String name;
    public Double amount;
    public int quantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
