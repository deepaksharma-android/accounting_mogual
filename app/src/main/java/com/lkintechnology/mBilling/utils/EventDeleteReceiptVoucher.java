package com.lkintechnology.mBilling.utils;

public class EventDeleteReceiptVoucher {

    private final String position;

    public EventDeleteReceiptVoucher(String position){
        this.position=position;
    }
    public String getPosition(){
        return position;
    }
}