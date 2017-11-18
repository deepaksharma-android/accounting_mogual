package com.berylsystems.buzz.networks.api_response.unitconversion;

public class UnitConversionDetailsAttributes {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain_unit() {
        return main_unit;
    }

    public void setMain_unit(String main_unit) {
        this.main_unit = main_unit;
    }

    public String getSub_unit() {
        return sub_unit;
    }

    public void setSub_unit(String sub_unit) {
        this.sub_unit = sub_unit;
    }

    public Double getConversion_factor() {
        return conversion_factor;
    }

    public void setConversion_factor(Double conversion_factor) {
        this.conversion_factor = conversion_factor;
    }

    public Boolean getUndefined() {
        return undefined;
    }

    public void setUndefined(Boolean undefined) {
        this.undefined = undefined;
    }

    public int id;
    public String main_unit;
    public String sub_unit;
    public Double conversion_factor;
    public Boolean undefined;
}