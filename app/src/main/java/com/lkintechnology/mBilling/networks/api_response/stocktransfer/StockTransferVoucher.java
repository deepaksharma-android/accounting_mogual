package com.lkintechnology.mBilling.networks.api_response.stocktransfer;

import java.util.ArrayList;

public class StockTransferVoucher {
    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public ArrayList<Data> data;
}