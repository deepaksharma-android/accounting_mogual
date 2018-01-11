package com.lkintechnology.mBilling.networks.api_response.unit;

public class ItemUnitData {
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

    public ItemUnitAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ItemUnitAttributes attributes) {
        this.attributes = attributes;
    }

    public String type;
    public String id;
    public ItemUnitAttributes attributes;
}