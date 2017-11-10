package com.berylsystems.buzz.networks.api_response.account;

public class AccountAttributes {
    public String id;

    public String getAccount_group() {
        return account_group;
    }

    public void setAccount_group(String account_group) {
        this.account_group = account_group;
    }

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

    public String account_group;
    public String name;
}