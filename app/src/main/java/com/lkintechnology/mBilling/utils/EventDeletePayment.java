package com.lkintechnology.mBilling.utils;

public class EventDeletePayment {

    private final String position;

    public EventDeletePayment(String position){
        this.position=position;
    }
    public String getPosition(){
        return position;
    }
}