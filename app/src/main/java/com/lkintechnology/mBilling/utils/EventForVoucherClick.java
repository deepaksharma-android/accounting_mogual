package com.lkintechnology.mBilling.utils;

/**
 * Created by abc on 6/14/2018.
 */

public class EventForVoucherClick {
    String position;
    public EventForVoucherClick(String position){
        this.position=position;
    }
    public String getPosition(){
        return this.position;
    }
}
