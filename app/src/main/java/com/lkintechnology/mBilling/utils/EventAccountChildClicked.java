package com.lkintechnology.mBilling.utils;

public class EventAccountChildClicked {
    private final String position;

    public EventAccountChildClicked(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}