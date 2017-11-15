package com.berylsystems.buzz.networks.api_response.materialcentregroup;

public class MaterialCentreGroupListData {
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

    public MaterialCentreGroupListAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(MaterialCentreGroupListAttributes attributes) {
        this.attributes = attributes;
    }

    public String id;
    public MaterialCentreGroupListAttributes attributes;
}