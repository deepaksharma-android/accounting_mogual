package com.lkintechnology.mBilling.networks.api_response.voucherseries;

/**
 * Created by SAMSUNG on 8/2/2018.
 */

public class Data {
    public String type;

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

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public String id;
    public Attributes attributes;
}
