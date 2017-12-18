package com.berylsystems.buzz.utils;

public class EventItemClicked {
    private final int position;

    public EventItemClicked(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}