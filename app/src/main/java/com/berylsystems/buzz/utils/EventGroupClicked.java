package com.berylsystems.buzz.utils;

public class EventGroupClicked {
    private final String position;

    public EventGroupClicked(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}