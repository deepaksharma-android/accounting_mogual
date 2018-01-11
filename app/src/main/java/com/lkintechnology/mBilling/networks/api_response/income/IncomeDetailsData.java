package com.lkintechnology.mBilling.networks.api_response.income;

public class IncomeDetailsData {

    public String type;
    public String id;
    public IncomeDetailsAttributes attributes;

    public IncomeDetailsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(IncomeDetailsAttributes attributes) {
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}