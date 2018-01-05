package com.berylsystems.buzz.utils;

public class EventDeleteReceipt {

    private final String position;

    public EventDeleteReceipt(String position){
        this.position=position;
    }
    public String getPosition(){
        return position;
    }
}