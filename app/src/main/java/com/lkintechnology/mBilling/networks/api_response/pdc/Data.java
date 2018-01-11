package com.lkintechnology.mBilling.networks.api_response.pdc;

/**
 * Created by BerylSystems on 27-Dec-17.
 */

public class Data {
    public String type;
    public String id;
    Attribute attributes;

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

    public Attribute getAttributes() {
        return attributes;
    }

    public void setAttributes(Attribute attributes) {
        this.attributes = attributes;
    }
}
