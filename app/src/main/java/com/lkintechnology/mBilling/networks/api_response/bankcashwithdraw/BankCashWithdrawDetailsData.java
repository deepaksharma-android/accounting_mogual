package com.lkintechnology.mBilling.networks.api_response.bankcashwithdraw;

public class BankCashWithdrawDetailsData {
    public String type;
    public String id;
    public BankCashWithdrawDetailsAttributes attributes;

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

    public BankCashWithdrawDetailsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(BankCashWithdrawDetailsAttributes attributes) {
        this.attributes = attributes;
    }
}