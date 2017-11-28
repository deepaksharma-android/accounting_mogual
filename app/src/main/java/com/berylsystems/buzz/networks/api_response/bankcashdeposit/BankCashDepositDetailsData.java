package com.berylsystems.buzz.networks.api_response.bankcashdeposit;

public class BankCashDepositDetailsData {
    public String type;
    public String id;
    public BankCashDepositDetailsAttributes attributes;

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

    public BankCashDepositDetailsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(BankCashDepositDetailsAttributes attributes) {
        this.attributes = attributes;
    }
}