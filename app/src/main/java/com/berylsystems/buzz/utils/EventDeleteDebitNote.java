package com.berylsystems.buzz.utils;

public class EventDeleteDebitNote {

    private final String position;

    public EventDeleteDebitNote(String position){
        this.position=position;
    }
    public String getPosition(){
        return position;
    }
}