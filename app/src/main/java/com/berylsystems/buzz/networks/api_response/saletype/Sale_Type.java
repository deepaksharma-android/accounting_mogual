package com.berylsystems.buzz.networks.api_response.saletype;

import com.berylsystems.buzz.networks.api_response.purchasetype.PurchaseTypeData;

import java.util.ArrayList;

public class Sale_Type {

    public ArrayList<SaleTypeData> data;

    public ArrayList<SaleTypeData> getData() {
        return data;
    }

    public void setData(ArrayList<SaleTypeData> data) {
        this.data = data;
    }
}