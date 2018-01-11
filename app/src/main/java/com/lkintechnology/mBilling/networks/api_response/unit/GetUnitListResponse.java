package com.lkintechnology.mBilling.networks.api_response.unit;

public class GetUnitListResponse {
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

    public ItemUnitList getItem_units() {
        return item_units;
    }

    public void setItem_units(ItemUnitList item_units) {
        this.item_units = item_units;
    }

    public ItemUnitList item_units;
}