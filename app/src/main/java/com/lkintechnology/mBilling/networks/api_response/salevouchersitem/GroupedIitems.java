package com.lkintechnology.mBilling.networks.api_response.salevouchersitem;

import java.util.ArrayList;

/**
 * Created by abc on 5/18/2018.
 */

public class GroupedIitems {
    public String group_name;
    public ArrayList<Items> items;
    public Double total_amount;
    public int total_quantity;

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public ArrayList<Items> getItems() {
        return items;
    }

    public void setItems(ArrayList<Items> items) {
        this.items = items;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }
}
