package com.lkintechnology.mBilling.networks.api_response.unitconversion;

public class GetUnitConversionDetailsResponse {
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

    public UnitConversionDetails getUnit_conversion_detail() {
        return unit_conversion_detail;
    }

    public void setUnit_conversion_detail(UnitConversionDetails unit_conversion_detail) {
        this.unit_conversion_detail = unit_conversion_detail;
    }

    public UnitConversionDetails unit_conversion_detail;
}