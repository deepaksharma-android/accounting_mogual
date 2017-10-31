package com.berylsystems.buzz.networks.api_response.company;

public class CompanyData {
    public String type;
    public String id;

    public CompanyAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(CompanyAttributes attributes) {
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

    public CompanyAttributes attributes;
}