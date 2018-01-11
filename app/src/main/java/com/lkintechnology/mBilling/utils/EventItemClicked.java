package com.lkintechnology.mBilling.utils;

public class EventItemClicked {
    private final int position;

    public EventItemClicked(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}