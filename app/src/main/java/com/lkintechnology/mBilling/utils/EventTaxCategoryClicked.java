package com.lkintechnology.mBilling.utils;

public class EventTaxCategoryClicked {
    String position;

    public EventTaxCategoryClicked(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}