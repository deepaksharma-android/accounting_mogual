package com.lkintechnology.mBilling.utils;

public class EventTransaction {

    private final String position;

    public EventTransaction(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}