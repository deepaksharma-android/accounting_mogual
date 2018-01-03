package com.berylsystems.buzz.utils;

public class EventDeletePaymentPdcDetails {

    private final String position;

    public EventDeletePaymentPdcDetails(String position){
        this.position=position;
    }
    public String getPosition(){
        return position;
    }
}