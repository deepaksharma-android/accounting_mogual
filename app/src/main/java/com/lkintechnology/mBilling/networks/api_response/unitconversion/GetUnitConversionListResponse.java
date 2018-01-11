package com.lkintechnology.mBilling.networks.api_response.unitconversion;

public class GetUnitConversionListResponse {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String message;
    public int status;


    public UnitConversion getUnit_conversions() {
        return unit_conversions;
    }

    public void setUnit_conversions(UnitConversion unit_conversions) {
        this.unit_conversions = unit_conversions;
    }

    public UnitConversion unit_conversions;
}