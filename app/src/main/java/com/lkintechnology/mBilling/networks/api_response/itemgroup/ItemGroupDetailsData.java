package com.lkintechnology.mBilling.networks.api_response.itemgroup;

public class ItemGroupDetailsData {
    public String type;
    public String id;
    public ItemGroupDetailsAttributes attributes;

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

    public ItemGroupDetailsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(ItemGroupDetailsAttributes attributes) {
        this.attributes = attributes;
    }
}