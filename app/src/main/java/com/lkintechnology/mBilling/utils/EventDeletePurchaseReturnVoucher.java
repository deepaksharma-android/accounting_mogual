package com.lkintechnology.mBilling.utils;

public class EventDeletePurchaseReturnVoucher {
    private final String position;

    public EventDeletePurchaseReturnVoucher(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}