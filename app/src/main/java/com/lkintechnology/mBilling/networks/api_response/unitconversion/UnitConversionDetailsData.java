package com.lkintechnology.mBilling.networks.api_response.unitconversion;

public class UnitConversionDetailsData {
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

    public UnitConversionDetailsAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(UnitConversionDetailsAttributes attributes) {
        this.attributes = attributes;
    }

    public String type;
    public String id;
    public UnitConversionDetailsAttributes attributes;
}