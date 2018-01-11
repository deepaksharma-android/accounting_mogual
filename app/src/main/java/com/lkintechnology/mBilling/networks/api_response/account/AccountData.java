package com.lkintechnology.mBilling.networks.api_response.account;

public class AccountData {
    public String type;

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

    public AccountAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(AccountAttributes attributes) {
        this.attributes = attributes;
    }

    public String id;
    public AccountAttributes attributes;
}