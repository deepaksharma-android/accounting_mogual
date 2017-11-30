package com.berylsystems.buzz.networks.api_response.expence;

public class ExpenceDetailsData {

    public String type;
    public String id;
    public ExpenceDetailsAttributes attributes;

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

    public ExpenceDetailsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ExpenceDetailsAttributes attributes) {
        this.attributes = attributes;
    }
}