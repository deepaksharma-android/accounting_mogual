package com.berylsystems.buzz.networks.api_response.packages;

import com.berylsystems.buzz.networks.api_response.account.Data;

import java.util.ArrayList;

public class Plan {

    public ArrayList<Data> data;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }
}