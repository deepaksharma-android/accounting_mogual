package com.lkintechnology.mBilling.utils;

public class EventDeleteExpence {

    private final String position;

    public EventDeleteExpence(String position){
        this.position=position;
    }
    public String getPosition(){
        return position;
    }
}