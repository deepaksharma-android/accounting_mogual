package com.berylsystems.buzz.networks.api_response.itemgroup;

import com.berylsystems.buzz.networks.api_response.accountgroup.AccountGroups;

public class GetItemGroupResponse {
    public String message;
    public int status;
    public ItemGroups item_groups;

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

    public ItemGroups getItem_groups() {
        return item_groups;
    }

    public void setItem_groups(ItemGroups item_groups) {
        this.item_groups = item_groups;
    }
}