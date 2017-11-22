package com.berylsystems.buzz.networks.api_response.item;

/**
 * Created by BerylSystems on 11/20/2017.
 */

public class GetItemDetailData {
    public String type;
    public String id;
    public ItemAttribute attributes;


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

    public ItemAttribute getAttributes() {
        return attributes;
    }

    public void setAttributes(ItemAttribute attributes) {
        this.attributes = attributes;
    }
}
