package com.berylsystems.buzz.networks.api_response.user;

/**
 * Created by pc on 6/26/2017.
 */
public class Data {
    public String type;

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

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public String id;
    public Attributes attributes;
}
