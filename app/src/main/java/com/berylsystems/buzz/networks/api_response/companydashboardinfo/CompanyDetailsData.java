package com.berylsystems.buzz.networks.api_response.companydashboardinfo;

public class CompanyDetailsData {
    public String type;
    public String id;
    public CompanyDetailsAttributes attributes;

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

    public CompanyDetailsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(CompanyDetailsAttributes attributes) {
        this.attributes = attributes;
    }
}