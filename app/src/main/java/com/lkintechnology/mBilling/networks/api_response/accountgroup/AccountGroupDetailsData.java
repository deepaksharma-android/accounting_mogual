package com.lkintechnology.mBilling.networks.api_response.accountgroup;

public class AccountGroupDetailsData {
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public AccountGroupDetailsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(AccountGroupDetailsAttributes attributes) {
        this.attributes = attributes;
    }

    public String type;
    public String id;
    public AccountGroupDetailsAttributes attributes;
}