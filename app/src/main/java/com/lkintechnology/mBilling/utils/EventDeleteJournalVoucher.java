package com.lkintechnology.mBilling.utils;

public class EventDeleteJournalVoucher {

    private final String position;

    public EventDeleteJournalVoucher(String position){
        this.position=position;
    }
    public String getPosition(){
        return position;
    }
}