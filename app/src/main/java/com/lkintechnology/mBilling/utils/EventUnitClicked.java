package com.lkintechnology.mBilling.utils;

public class EventUnitClicked {
    private final int position;

    public EventUnitClicked(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}