package com.berylsystems.buzz.utils;

public class EventDeletePurchaseVoucher {
    private final String position;

    public EventDeletePurchaseVoucher(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}