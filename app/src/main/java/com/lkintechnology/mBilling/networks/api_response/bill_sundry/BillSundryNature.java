package com.lkintechnology.mBilling.networks.api_response.bill_sundry;

import java.util.ArrayList;

public class BillSundryNature {
    public ArrayList<BillSundryNatureData> getData() {
        return data;
    }

    public void setData(ArrayList<BillSundryNatureData> data) {
        this.data = data;
    }

    public ArrayList<BillSundryNatureData> data;
}