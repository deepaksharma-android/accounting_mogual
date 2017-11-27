package com.berylsystems.buzz.utils;

import com.berylsystems.buzz.networks.api_response.bill_sundry.BillSundryData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BerylSystems on 11/25/2017.
 */

public class EventSaleAddBill {

    private final String position;


    public EventSaleAddBill(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
