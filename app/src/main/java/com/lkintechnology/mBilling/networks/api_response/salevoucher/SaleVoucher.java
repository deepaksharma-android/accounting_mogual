package com.lkintechnology.mBilling.networks.api_response.salevoucher;

import java.util.ArrayList;

public class SaleVoucher {
    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public ArrayList<Data> data;
}