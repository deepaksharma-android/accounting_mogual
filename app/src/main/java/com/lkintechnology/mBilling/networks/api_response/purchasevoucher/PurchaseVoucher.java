package com.lkintechnology.mBilling.networks.api_response.purchasevoucher;

import java.util.ArrayList;

public class PurchaseVoucher {
    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public ArrayList<Data> data;
}