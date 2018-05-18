package com.lkintechnology.mBilling.networks.api_response.salevouchersitem;

import java.util.ArrayList;

/**
 * Created by abc on 5/18/2018.
 */

public class GetSaleVouchersItem {
    public int status;
    public String message;
    public ArrayList<GroupedIitems> grouped_items;

    public ArrayList<GroupedIitems> getGrouped_items() {
        return grouped_items;
    }

    public void setGrouped_items(ArrayList<GroupedIitems> grouped_items) {
        this.grouped_items = grouped_items;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
