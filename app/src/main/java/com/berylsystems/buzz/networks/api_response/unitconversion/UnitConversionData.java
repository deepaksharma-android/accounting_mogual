package com.berylsystems.buzz.networks.api_response.unitconversion;

public class UnitConversionData {
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

    public UnitConversionAttributes getAttributes() {
        return attributes;
    }

    public void setAttributes(UnitConversionAttributes attributes) {
        this.attributes = attributes;
    }

    public String id;
    public UnitConversionAttributes attributes;
}