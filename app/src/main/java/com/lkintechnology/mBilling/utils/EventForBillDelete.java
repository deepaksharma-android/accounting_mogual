package com.lkintechnology.mBilling.utils;

/**
 * Created by manjoor on 31-Aug-18.
 */

public class EventForBillDelete {
    String position;

    public EventForBillDelete(String position){
        this.position = position;
    }

    public String getPosition(){
        return this.position;
    }

}
