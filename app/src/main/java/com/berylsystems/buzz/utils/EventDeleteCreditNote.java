package com.berylsystems.buzz.utils;

public class EventDeleteCreditNote {
    private final String position;

    public EventDeleteCreditNote(String position){
        this.position=position;
    }
    public String getPosition(){
        return position;
    }
}