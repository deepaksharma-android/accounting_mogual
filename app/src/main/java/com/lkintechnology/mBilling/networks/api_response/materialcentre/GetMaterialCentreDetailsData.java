package com.lkintechnology.mBilling.networks.api_response.materialcentre;

public class GetMaterialCentreDetailsData {
    public String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GetMaterialCentreDetailAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(GetMaterialCentreDetailAttributes attributes) {
        this.attributes = attributes;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String id;
    public GetMaterialCentreDetailAttributes attributes;
}