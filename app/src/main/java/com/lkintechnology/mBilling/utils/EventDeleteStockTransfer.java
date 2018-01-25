package com.lkintechnology.mBilling.utils;

public class EventDeleteStockTransfer {
    private final String position;

    public EventDeleteStockTransfer(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}