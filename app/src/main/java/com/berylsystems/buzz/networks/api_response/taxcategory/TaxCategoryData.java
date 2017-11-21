package com.berylsystems.buzz.networks.api_response.taxcategory;

public class TaxCategoryData {

    public String type;
    public String id;
    public TaxCategoryAttributes attributes;

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

    public TaxCategoryAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(TaxCategoryAttributes attributes) {
        this.attributes = attributes;
    }
}