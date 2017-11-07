package com.berylsystems.buzz.utils;

public class EventGroupClicked {
    private final int position;

    public EventGroupClicked(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}