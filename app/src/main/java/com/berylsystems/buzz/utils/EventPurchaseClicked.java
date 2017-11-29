package com.berylsystems.buzz.utils;

/**
 * Created by BerylSystems on 11/28/2017.
 */

public class EventPurchaseClicked {
    String position;
    public EventPurchaseClicked(String position){
        this.position=position;
    }
    public String getPosition(){
        return this.position;
    }
}
