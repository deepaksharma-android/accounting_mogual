package com.lkintechnology.mBilling.utils;

public class EventDeletePaymentPdcDetails {

    private final String position;

    public EventDeletePaymentPdcDetails(String position){
        this.position=position;
    }
    public String getPosition(){
        return position;
    }
}