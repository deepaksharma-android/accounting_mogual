package com.berylsystems.buzz.networks.api_response.unit;

public class GetUnitDetailsResponse {
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

    public UnitDetail getItem_unit() {
        return item_unit;
    }

    public void setItem_unit(UnitDetail item_unit) {
        this.item_unit = item_unit;
    }

    public UnitDetail item_unit;
}