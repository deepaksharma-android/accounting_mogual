package com.lkintechnology.mBilling.networks.api_response.itemgroup;

public class GetItemGroupDetailsResponse {

    public String message;
    public int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ItemGroupsDetails getItem_group() {
        return item_group;
    }

    public void setItem_group(ItemGroupsDetails item_group) {
        this.item_group = item_group;
    }

    public ItemGroupsDetails item_group;

}