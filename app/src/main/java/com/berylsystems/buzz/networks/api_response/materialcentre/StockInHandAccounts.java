package com.berylsystems.buzz.networks.api_response.materialcentre;

import java.util.ArrayList;

public class StockInHandAccounts {
    public ArrayList<StockData> getData() {
        return data;
    }

    public void setData(ArrayList<StockData> data) {
        this.data = data;
    }

    public ArrayList<StockData> data;

}