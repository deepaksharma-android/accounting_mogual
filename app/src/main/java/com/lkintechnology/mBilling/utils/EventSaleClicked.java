package com.lkintechnology.mBilling.utils;

/**
 * Created by BerylSystems on 11/23/2017.
 */

public class EventSaleClicked {
    private final String position;

    public EventSaleClicked(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
