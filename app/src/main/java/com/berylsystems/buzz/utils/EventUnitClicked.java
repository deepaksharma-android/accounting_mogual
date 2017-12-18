package com.berylsystems.buzz.utils;

public class EventUnitClicked {
    private final int position;

    public EventUnitClicked(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}