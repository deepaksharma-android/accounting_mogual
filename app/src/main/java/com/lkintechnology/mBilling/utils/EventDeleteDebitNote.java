package com.lkintechnology.mBilling.utils;

public class EventDeleteDebitNote {

    private final String position;

    public EventDeleteDebitNote(String position){
        this.position=position;
    }
    public String getPosition(){
        return position;
    }
}