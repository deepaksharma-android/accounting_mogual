package com.lkintechnology.mBilling.networks.api_response.materialcentre;

public class StockData {
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

    public StockAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(StockAttributes attributes) {
        this.attributes = attributes;
    }

    public String type;
    public String id;
    public StockAttributes attributes;
}