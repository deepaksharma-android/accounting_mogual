package com.lkintechnology.mBilling.utils;

public class EventMaterialCentreGroupClicked {
    private final int position;

    public EventMaterialCentreGroupClicked(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }
}