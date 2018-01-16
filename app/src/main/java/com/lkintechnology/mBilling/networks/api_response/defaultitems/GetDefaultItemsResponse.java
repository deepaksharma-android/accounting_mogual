package com.lkintechnology.mBilling.networks.api_response.defaultitems;


public class GetDefaultItemsResponse {
    public int status;
    public String message;
    public DefaultItemGroup default_item_group;

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

    public DefaultItemGroup getDefault_item_group() {
        return default_item_group;
    }

    public void setDefault_item_group(DefaultItemGroup default_item_group) {
        this.default_item_group = default_item_group;
    }
}