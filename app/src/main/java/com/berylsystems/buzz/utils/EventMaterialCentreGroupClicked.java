package com.berylsystems.buzz.utils;

public class EventMaterialCentreGroupClicked {
    private final int position;

    public EventMaterialCentreGroupClicked(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}