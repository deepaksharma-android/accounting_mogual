package com.berylsystems.buzz.networks.api_response.account;

import com.berylsystems.buzz.networks.api_response.packages.PackageAttributes;

public class Data {
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



    public String type;
    public String id;

    public PackageAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(PackageAttributes attributes) {
        this.attributes = attributes;
    }

    public PackageAttributes attributes;
}