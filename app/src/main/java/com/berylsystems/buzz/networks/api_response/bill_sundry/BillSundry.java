package com.berylsystems.buzz.networks.api_response.bill_sundry;

import java.util.ArrayList;

public class BillSundry {
    public ArrayList<BillSundryData> getData() {
        return data;
    }

    public void setData(ArrayList<BillSundryData> data) {
        this.data = data;
    }

    public ArrayList<BillSundryData> data;
}